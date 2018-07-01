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
import android.widget.TextView;

import com.example.day0629_moni_yk.R;
import com.example.day0629_moni_yk.adapter.FenLeiAdapterRight;
import com.example.day0629_moni_yk.bean.Constant;
import com.example.day0629_moni_yk.bean.GsonFenLeiRightData;
import com.example.day0629_moni_yk.util.RequestDataUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by John on 2018/6/29 0029.
 */

public class FragmentShoppingCartRigth extends Fragment {

    private View view;
    private EventBus eventBus;
    private String cid = "1";//默认为1
    /**
     * 热门分类
     */
    private TextView mFenleiReightName;
    private RecyclerView mFenleiReightContent;
    private RequestDataUtil dataUtil;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shopping_rigth, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(view);
        Bundle arguments = getArguments();
        if (arguments != null) {
            cid = arguments.getString("cid");
            //Log.d("zzz", "onActivityCreated: "+ cid);
        }
        //网络请求数据
        requestDataFunction();

    }

    private void requestDataFunction() {
        //拼接地址
        String urlRight = Constant.FenLeiUrlRight+"?cid="+cid;
        Log.d("oooo", "getItemCount: "+urlRight);
        dataUtil.getRequestJsonData(urlRight, new RequestDataUtil.IcallBack() {
            @Override
            public void onRequest(String jsonData) {
                //请求数据成功
                Gson gson = new Gson();
                GsonFenLeiRightData rightData = gson.fromJson(jsonData, GsonFenLeiRightData.class);
                List<GsonFenLeiRightData.DataBean> beanList = rightData.getData();
                //设置适配器
                mFenleiReightContent.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                FenLeiAdapterRight adapterRight = new FenLeiAdapterRight(getContext(),beanList);
                mFenleiReightContent.setAdapter(adapterRight);
            }
        });
    }

    private void initView(View view) {
        mFenleiReightName = (TextView) view.findViewById(R.id.fenlei_reight_name);
        mFenleiReightContent = (RecyclerView) view.findViewById(R.id.fenlei_reight_content);

        //得到网络请求工具类
        dataUtil = RequestDataUtil.getInstance();
    }
}
