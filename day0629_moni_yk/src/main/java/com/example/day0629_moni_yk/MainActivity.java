package com.example.day0629_moni_yk;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.day0629_moni_yk.view.fragment.FragmentFenClass;
import com.example.day0629_moni_yk.view.fragment.FragmentShoppingCart;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_fenlei)
    RadioButton mBtnFenlei;
    @BindView(R.id.btn_shoppingcart)
    RadioButton mBtnShoppingcart;
    @BindView(R.id.main_group)
    RadioGroup mMainGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getFragments(new FragmentFenClass());//默认加载分类页面
        mMainGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.btn_fenlei://分类页面
                        getFragments(new FragmentFenClass());
                        break;
                    case R.id.btn_shoppingcart://购物车页面
                        getFragments(new FragmentShoppingCart());
                        break;
                }
            }
        });
    }
    private void getFragments(Fragment fragment){//动态加载Fragment
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_frame,fragment);
        transaction.commit();

    }
}
