package com.example.day0629_moni_yk.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.day0629_moni_yk.R;
import com.example.day0629_moni_yk.adapter.ShoppingCartAdapter;
import com.example.day0629_moni_yk.bean.Constant;
import com.example.day0629_moni_yk.bean.GsonShoppingCartData;
import com.example.day0629_moni_yk.util.RequestDataUtil;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by John on 2018/6/29 0029.
 */

public class FragmentShoppingCart extends Fragment implements View.OnClickListener {
    private View view;
    private RecyclerView mShoppingCartData;
    /**
     * 全选
     */
    private CheckBox mAllCbx;
    /**
     * 总额: ￥ 0
     */
    private TextView mAllMoneyTv;
    /**
     * 去结算(0)
     */
    private TextView mSettleTv;
    private RequestDataUtil requestDataUtil;
    private List<GsonShoppingCartData.DataBean> shoppingList;
    private String myUpdateCars;
    private List<GsonShoppingCartData.DataBean.ListBean> list;
    private boolean bian = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shoppingcart, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(view);
        //请求网络数据
        requestDataFunction();
    }

    private void requestDataFunction() {//请求数据
        //拼接接口数据
        String myUrl = Constant.ShoppingCartUrl + "?uid=15909";
        Log.d("ppp", "onRequest: "+myUrl);
        requestDataUtil.getRequestJsonData(myUrl, new RequestDataUtil.IcallBack() {
            @Override
            public void onRequest(String jsonData) {
                //请求数据成功 解析数据
                Gson gson = new Gson();
                GsonShoppingCartData data = gson.fromJson(jsonData, GsonShoppingCartData.class);
                shoppingList = data.getData();
                //设置布局管理器
                mShoppingCartData.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                //设置适配器
                final ShoppingCartAdapter adapter = new ShoppingCartAdapter(getContext(), shoppingList);
                mShoppingCartData.setAdapter(adapter);
                getPrice();//计算总价

                //接口回调
                adapter.setPrantDataState(new ShoppingCartAdapter.PSCIcallBack() {
                    @Override
                    public void getPrantDataState(int sellerid, int pid, int selected, int nums) {//这个是加减数据与子复选框
                        //请求更新购物车的接口
                        //拼接地址https://www.zhaoapi.cn/product/updateCarts?uid=71&sellerid=1&pid=1&selected=0&num=10
                        String myUpdateCar = Constant.UpdataShoppingCartUrl+"?uid=15909"+"&sellerid="+sellerid+"&pid="+pid+"&selected="+selected+"&num="+nums;
                        requestDataUtil.getRequestJsonData(myUpdateCar, new RequestDataUtil.IcallBack() {
                            @Override
                            public void onRequest(String jsonData) {
                                getPrice();//计算总价
                                requestDataFunction();
                            }
                        });
                    }

                    @Override
                    public void getShangJiaChecked(int position, String sellerid,boolean b) {//这个是父复选框的接口回调
                        for (int i = 0; i < shoppingList.size(); i++) {
                            if(position == i){
                                //根据传过来的下标 得到你点击下面的商品集合
                                list = shoppingList.get(position).getList();
                            }

                        }
                        for (int j = 0; j <list.size() ; j++) {
                            if(b==true){
                                myUpdateCars = Constant.UpdataShoppingCartUrl+"" +
                                        "?uid=15909"+"&sellerid="+list.get(j).getSellerid()+"&pid="+list.get(j).getPid()+"&selected="+1+"&num="+list.get(j).getNum();
                            }else if(b==false){
                                myUpdateCars = Constant.UpdataShoppingCartUrl+"" +
                                        "?uid=15909"+"&sellerid="+list.get(j).getSellerid()+"&pid="+list.get(j).getPid()+"&selected="+0+"&num="+list.get(j).getNum();
                            }

                            Log.d("iiiii", "getShangJiaChecked: "+myUpdateCars);
                            requestDataUtil.getRequestJsonData(myUpdateCars, new RequestDataUtil.IcallBack() {
                                @Override
                                public void onRequest(String jsonData) {
                                    getPrice();//计算总价
                                    requestDataFunction();
                                    bian = true;
                                    Log.d("iiiii", "getShangJiaChecked: "+bian);
                                }
                            });
                        }

                        /*if(bian == true){
                            requestDataFunction();
                        }*/
                        /*requestDataUtil.getRequestJsonData(myUpdateCars, new RequestDataUtil.IcallBack() {
                            @Override
                            public void onRequest(String jsonData) {
                                getPrice();//计算总价
                                requestDataFunction();
                            }
                        });*/

                    }

                });
            }
        });

    }

    /**
     * 计算价格
     */
    private void getPrice() {
        int allprice = 0;
        for (int i = 0; i < shoppingList.size(); i++) {
            for (int j = 0; j < shoppingList.get(i).getList().size(); j++) {
                if(shoppingList.get(i).getList().get(j).getSelected() == 1){
                    int price = (int)shoppingList.get(i).getList().get(j).getPrice();
                    int n = shoppingList.get(i).getList().get(j).getNum();
                    Log.d("ttt",n+"      n    ");
                    allprice += price * n;
                }
            }
        }
        mAllMoneyTv.setText("￥"+allprice+".00");
    }

    /**
     * 初始化
     * @param view
     */
    private void initView(View view) {
        mShoppingCartData = (RecyclerView) view.findViewById(R.id.shopping_cart_data);
        mAllCbx = (CheckBox) view.findViewById(R.id.all_cbx);
        mAllMoneyTv = (TextView) view.findViewById(R.id.all_money_tv);
        mSettleTv = (TextView) view.findViewById(R.id.settle_tv);
        mSettleTv.setOnClickListener(this);

        //得到网络请求工具类
        requestDataUtil = RequestDataUtil.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.settle_tv://结算总价
                Toast.makeText(getContext(), "开始结算总价", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
