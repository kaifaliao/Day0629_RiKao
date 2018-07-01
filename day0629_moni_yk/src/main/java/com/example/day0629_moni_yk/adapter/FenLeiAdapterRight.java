package com.example.day0629_moni_yk.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.day0629_moni_yk.R;
import com.example.day0629_moni_yk.bean.GsonFenLeiRightData;

import java.util.List;

/**
 * Created by John on 2018/6/29 0029.
 */

public class FenLeiAdapterRight extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<GsonFenLeiRightData.DataBean> list;

    public FenLeiAdapterRight(Context context, List<GsonFenLeiRightData.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_shopping_rigth, parent, false);
        ViewHolderRight holderRight = new ViewHolderRight(view);
        return holderRight;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolderRight holderRight = (ViewHolderRight) holder;
        //绑定数据
        holderRight.mFenleiReightName.setText(list.get(position).getName());
        //得到集合 设置适配器
        List<GsonFenLeiRightData.DataBean.ListBean> listb = list.get(position).getList();
        //设置布局管理器
        holderRight.mFenleiReightContent.setLayoutManager(new GridLayoutManager(context,3));
        //设置适配器
        FenLeiChildrenRightAdapter rightAdapter = new FenLeiChildrenRightAdapter(context,listb);
        holderRight.mFenleiReightContent.setAdapter(rightAdapter);

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    static class ViewHolderRight extends RecyclerView.ViewHolder {
        View view;
        TextView mFenleiReightName;
        RecyclerView mFenleiReightContent;

        ViewHolderRight(View view) {
            super(view);
            this.view = view;
            this.mFenleiReightName = (TextView) view.findViewById(R.id.fenlei_reight_name);
            this.mFenleiReightContent = (RecyclerView) view.findViewById(R.id.fenlei_reight_content);
        }
    }
}
