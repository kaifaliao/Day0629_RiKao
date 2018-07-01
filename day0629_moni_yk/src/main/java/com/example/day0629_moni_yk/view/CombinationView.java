package com.example.day0629_moni_yk.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.day0629_moni_yk.R;

/**
 * Created by John on 2018/6/29 0029.
 */

public class CombinationView extends RelativeLayout implements View.OnClickListener {

    private View view;
    private ImageView btn_add;
    private ImageView btn_jian;
    private TextView txt_num;

    private int shoppingNums = 1;//商品的数量
    private int maxNnms  = 5;//商品数量的最大值
    private int minNnms  = 1;//商品数量的最小值

    public CombinationView(Context context) {
        this(context,null);
    }

    public CombinationView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CombinationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //加载布局
        view = LayoutInflater.from(context).inflate(R.layout.combination_button_layout, this, true);
        initView();
    }

    private void initView() {//初始化
        //加
        btn_add = (ImageView) view.findViewById(R.id.btn_add);
        //减
        btn_jian = (ImageView) view.findViewById(R.id.btn_jian);
        //数量
        txt_num = (TextView) view.findViewById(R.id.txt_num);

        //添加点击事件
        btn_add.setOnClickListener(this);
        btn_jian.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add://加
                String s = txt_num.getText().toString();
                Integer numu = Integer.valueOf(s);
                numu++;
                txt_num.setText(numu+"");
                if(icallBackNum!=null){
                    icallBackNum.getNnm(numu);
                }
                break;
            case R.id.btn_jian://减
                String s1 = txt_num.getText().toString();
                Integer numu1 = Integer.valueOf(s1);
                if(numu1>minNnms){
                    numu1--;
                }
                txt_num.setText(numu1+"");
                if(icallBackNum!=null){
                    icallBackNum.getNnm(numu1);
                }
                break;
        }
    }

    //返回商品的数量
    public int getShoppingNumss(){
        String num = txt_num.getText().toString();
        return Integer.parseInt(num);
    }

    public void setMaxNnms(int shu){//设置商品的最大数量
        maxNnms = shu;
    }
    //创建一个方法用于设置商品数量
    public void setNums(int shuliang){//设置商品的数量
        txt_num.setText(shuliang+"");//赋值
    }
    //定义接口
    public interface IcallBackNum{
        void getNnm(int nums);//返回商品的数量
    }
    //2.声明接口对象
    private IcallBackNum icallBackNum;
    //3.提供外部调用的方法
    public void setShoppingNum(IcallBackNum icallBackNum){
        this.icallBackNum = icallBackNum;
    }
}
