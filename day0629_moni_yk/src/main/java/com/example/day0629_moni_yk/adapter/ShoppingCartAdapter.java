package com.example.day0629_moni_yk.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.day0629_moni_yk.R;
import com.example.day0629_moni_yk.bean.GsonShoppingCartData;

import java.util.List;

/**
 * Created by John on 2018/6/29 0029.
 */

public class ShoppingCartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<GsonShoppingCartData.DataBean> shoppingList;
    private int i = 0;

    public ShoppingCartAdapter(Context context, List<GsonShoppingCartData.DataBean> shoppingList) {
        this.context = context;
        this.shoppingList = shoppingList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shoppingcart_layout, parent, false);
        ViewHolderShopping holderShopping = new ViewHolderShopping(view);
        return holderShopping;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolderShopping holderShopping = (ViewHolderShopping) holder;
        //绑定数据
        holderShopping.mShopName.setText(shoppingList.get(position).getSellerName());
        //得到集合 设置适配器
        List<GsonShoppingCartData.DataBean.ListBean> list = shoppingList.get(position).getList();
        //设置布局管理器
        holderShopping.mShopRecycle.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        ShoppingCartChildrenAdapter adapter = new ShoppingCartChildrenAdapter(context,list);
        holderShopping.mShopRecycle.setAdapter(adapter);

        //接口回调
        adapter.setDataState(new ShoppingCartChildrenAdapter.SCIcallBack() {
            @Override
            public void getDataState(int sellerid, int pid, int selected, int nums) {
                if(pscIcallBack!=null){
                    pscIcallBack.getPrantDataState(sellerid,pid,selected,nums);
                }
            }
        });

        //实现全选
            List<GsonShoppingCartData.DataBean.ListBean> listBeans = shoppingList.get(position).getList();
            for (int j = 0; j < listBeans.size() ; j++) {
                if(listBeans.size() == 1 && listBeans.get(j).getSelected() == 1){
                    holderShopping.mShopCheckBox.setChecked(true);
                    //不用刷新适配器 因为你在点击子的复选框时之前就写过重新请求数据的功能
                }else {
                    holderShopping.mShopCheckBox.setChecked(false);
                }
                if(listBeans.size() > 1){
                    if(listBeans.get(j).getSelected() == 1){
                        i++;//设置一个变量用于存储为选中状态的条目有多少 判断他如果与长度一样的话就将商家设置为选中状态
                    }
                    Log.d("changdu", "onBindViewHolder: i="+ i +"size="+listBeans.size());
                    if(i == listBeans.size()){
                        holderShopping.mShopCheckBox.setChecked(true);
                    }else {
                        holderShopping.mShopCheckBox.setChecked(false);
                    }

                }
            }
        //的到复选框是否为选中状态
        holderShopping.mShopCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(pscIcallBack!=null){
                    String sellerid = shoppingList.get(position).getSellerid();
                    pscIcallBack.getShangJiaChecked(position,sellerid,b);
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return shoppingList.size();
    }

    static class ViewHolderShopping extends RecyclerView.ViewHolder{
        View view;
        CheckBox mShopCheckBox;
        TextView mShopName;
        RecyclerView mShopRecycle;

        ViewHolderShopping(View view) {
            super(view);
            this.view = view;
            this.mShopCheckBox = (CheckBox) view.findViewById(R.id.shop_checkBox);
            this.mShopName = (TextView) view.findViewById(R.id.shop_name);
            this.mShopRecycle = (RecyclerView) view.findViewById(R.id.shop_recycle);
        }
    }

    //1.定义接口
    public interface PSCIcallBack{
        void getPrantDataState(int sellerid,int pid,int selected,int nums);//得到商品此时的状态值。。。。 参数还没写
        void getShangJiaChecked(int position,String sellerid,boolean b);//商家是否选中
    }
    //2.声明接口对象
    private PSCIcallBack pscIcallBack;
    //3.提供外部调用的方法
    public void setPrantDataState(PSCIcallBack pscIcallBack){
        this.pscIcallBack = pscIcallBack;
    }
}
