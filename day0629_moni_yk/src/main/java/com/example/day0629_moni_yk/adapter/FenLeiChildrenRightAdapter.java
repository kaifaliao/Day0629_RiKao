package com.example.day0629_moni_yk.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.day0629_moni_yk.R;
import com.example.day0629_moni_yk.bean.GsonFenLeiRightData;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by John on 2018/6/29 0029.
 */

public class FenLeiChildrenRightAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<GsonFenLeiRightData.DataBean.ListBean> listb;

    public FenLeiChildrenRightAdapter(Context context, List<GsonFenLeiRightData.DataBean.ListBean> listb) {
        this.context = context;
        this.listb = listb;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fenleirightchildren, parent, false);
        ViewHolderChildren holderChildren = new ViewHolderChildren(view);
        return holderChildren;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolderChildren holderChildren = (ViewHolderChildren) holder;
        //绑定数据
        holderChildren.mChildrenName.setText(listb.get(position).getName());
        //下载图片
        Uri uri = Uri.parse(listb.get(position).getIcon());
        holderChildren.mMyChildrenImage.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return listb.size();
    }

    static class ViewHolderChildren extends RecyclerView.ViewHolder {
        View view;
        SimpleDraweeView mMyChildrenImage;
        TextView mChildrenName;

        ViewHolderChildren(View view) {
            super(view);
            this.view = view;
            this.mMyChildrenImage = (SimpleDraweeView) view.findViewById(R.id.my_children_image);
            this.mChildrenName = (TextView) view.findViewById(R.id.children_name);
        }
    }
}
