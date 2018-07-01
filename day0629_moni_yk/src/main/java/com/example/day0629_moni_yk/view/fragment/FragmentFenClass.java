package com.example.day0629_moni_yk.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.day0629_moni_yk.R;
import com.example.day0629_moni_yk.adapter.FenLeiAdapterLeft;
import com.example.day0629_moni_yk.bean.Constant;
import com.example.day0629_moni_yk.bean.GsonFenLeiLeftData;
import com.example.day0629_moni_yk.util.RequestDataUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by John on 2018/6/29 0029.
 */

public class FragmentFenClass extends Fragment {

    private View view;
    private RecyclerView mRecyclerLeft;
    private RequestDataUtil dataUtil;
    private FragmentShoppingCartRigth fragmentRigth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fenlei, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(view);
        //请求左侧数据
        requestLeft();
    }

    private void requestLeft() {
        dataUtil.getRequestJsonData(Constant.FenLeiUrl, new RequestDataUtil.IcallBack() {
            @Override
            public void onRequest(String jsonData) {
                //请求成功
                Gson gson = new Gson();
                GsonFenLeiLeftData leftData = gson.fromJson(jsonData, GsonFenLeiLeftData.class);
                final List<GsonFenLeiLeftData.DataBean> leftList = leftData.getData();
                //设置布局管理器
                mRecyclerLeft.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

                //设置适配器
                FenLeiAdapterLeft adapterLeft = new FenLeiAdapterLeft(getContext(),leftList);
                mRecyclerLeft.setAdapter(adapterLeft);

                //接口回调
                adapterLeft.setOnItemClickListener(new FenLeiAdapterLeft.LeftIcallBack() {
                    @Override
                    public void onItemClick(int position) {
                        //动态加载Fragment
                        fragmentRigth = new FragmentShoppingCartRigth();
                        Bundle bundle = new Bundle();
                        bundle.putString("cid",leftList.get(position).getCid()+"");
                        fragmentRigth.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_right, fragmentRigth).commit();
                        Toast.makeText(getContext(), ""+position, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void initView(View view) {
        mRecyclerLeft = (RecyclerView) view.findViewById(R.id.recycler_left);
        //得到网络请求工具类
        dataUtil = RequestDataUtil.getInstance();
        //动态加载Fragment
        fragmentRigth = new FragmentShoppingCartRigth();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_right, fragmentRigth).commit();

    }

}
