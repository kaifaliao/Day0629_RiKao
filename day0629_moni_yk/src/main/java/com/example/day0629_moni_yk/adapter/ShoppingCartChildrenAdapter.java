package com.example.day0629_moni_yk.adapter;

import android.content.Context;
import android.net.Uri;
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
import com.example.day0629_moni_yk.view.CombinationView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by John on 2018/6/29 0029.
 */

public class ShoppingCartChildrenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<GsonShoppingCartData.DataBean.ListBean> list;

    public ShoppingCartChildrenAdapter(Context context, List<GsonShoppingCartData.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shoppingcartchildren_layout, parent, false);
        ViewHolderShoppingChildren holderShoppingChildren = new ViewHolderShoppingChildren(view);
        return holderShoppingChildren;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolderShoppingChildren holderShoppingChildren = (ViewHolderShoppingChildren) holder;
        //绑定数据
        holderShoppingChildren.mTxtShoppingTitle.setText(list.get(position).getTitle());//设置标题
        holderShoppingChildren.mTxtShoppingPrice.setText("￥"+list.get(position).getPrice()+"");//设置价格
        //得到商品的数量
        int num = list.get(position).getNum();
        holderShoppingChildren.mJiaJian.setNums(num);//设置显示的商品的数量
        //下载图片
        String path = list.get(position).getImages().split("\\|")[0];
        Uri uri = Uri.parse(path);
        holderShoppingChildren.mShoppingChildrenImage.setImageURI(uri);
        //得到是否未选中状态值 设置是否为选中状态
        int selected = list.get(position).getSelected();
        if(selected == 0){
            holderShoppingChildren.mCheckBox.setChecked(false);
        }else if(selected == 1){
            holderShoppingChildren.mCheckBox.setChecked(true);
        }

        //接口回调的到文本框得值
        holderShoppingChildren.mJiaJian.setShoppingNum(new CombinationView.IcallBackNum() {
            @Override
            public void getNnm(int nums) {
                Log.d("kkkk", "getNnm: "+nums);
                //得到数据信息 uid=71&sellerid=1&pid=1&selected=0&num=10
                int sellerid = list.get(position).getSellerid();
                int pid = list.get(position).getPid();
                int selecteds = list.get(position).getSelected();
                if(scIcallBack != null){
                    scIcallBack.getDataState(sellerid,pid,selecteds,nums);
                }
            }
        });

        //复选框的状态值改变
        holderShoppingChildren.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int shoppingNumss = holderShoppingChildren.mJiaJian.getShoppingNumss();//得到商品的数量
                int sellerid = list.get(position).getSellerid();
                int pid = list.get(position).getPid();
                int xuanZhong = 0;
                if(b == false){
                    xuanZhong = 0;
                }else {
                    xuanZhong = 1;
                }
                if(scIcallBack != null){
                    scIcallBack.getDataState(sellerid,pid,xuanZhong,shoppingNumss);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolderShoppingChildren extends RecyclerView.ViewHolder {
        View view;
        CheckBox mCheckBox;
        SimpleDraweeView mShoppingChildrenImage;
        TextView mTxtShoppingTitle;
        TextView mTxtShoppingPrice;
        CombinationView mJiaJian;

        ViewHolderShoppingChildren(View view) {
            super(view);
            this.view = view;
            this.mCheckBox = (CheckBox) view.findViewById(R.id.checkBox);
            this.mShoppingChildrenImage = (SimpleDraweeView) view.findViewById(R.id.shopping_children_image);
            this.mTxtShoppingTitle = (TextView) view.findViewById(R.id.txt_shopping_title);
            this.mTxtShoppingPrice = (TextView) view.findViewById(R.id.txt_shopping_price);
            this.mJiaJian = (CombinationView) view.findViewById(R.id.jia_jian);
        }
    }

    //1.定义接口
    public interface SCIcallBack{
        void getDataState(int sellerid,int pid,int selected,int nums);//得到商品此时的状态值。。。。 参数还没写
    }
    //2.声明接口对象
    private SCIcallBack scIcallBack;
    //3.提供外部调用的方法
    public void setDataState(SCIcallBack scIcallBack){
        this.scIcallBack = scIcallBack;
    }
}
