package com.example.day0629_moni_yk.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.day0629_moni_yk.R;
import com.example.day0629_moni_yk.bean.GsonFenLeiLeftData;

import java.util.List;

/**
 * Created by John on 2018/6/29 0029.
 */

public class FenLeiAdapterLeft extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<GsonFenLeiLeftData.DataBean> list;

    public FenLeiAdapterLeft(Context context, List<GsonFenLeiLeftData.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fenlei_leftlayout, parent, false);
        ViewHolderLeft holderLeft = new ViewHolderLeft(view);
        return holderLeft;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ViewHolderLeft holderLeft = (ViewHolderLeft) holder;
        holderLeft.mTxtLeftname.setText(list.get(position).getName());

        //添加点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(leftIcallBack != null){
                    int layoutPosition = holder.getLayoutPosition();
                    leftIcallBack.onItemClick(layoutPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolderLeft extends RecyclerView.ViewHolder {
        View view;
        TextView mTxtLeftname;

        ViewHolderLeft(View view) {
            super(view);
            this.view = view;
            this.mTxtLeftname = (TextView) view.findViewById(R.id.txt_leftname);
        }
    }

    //1.定义接口
    public interface LeftIcallBack{
        void onItemClick(int position);
    }
    //2.声明接口对象
    private LeftIcallBack leftIcallBack;
    //3.提供外部调用的方法
    public void setOnItemClickListener(LeftIcallBack leftIcallBack){
        this.leftIcallBack = leftIcallBack;
    }
}
