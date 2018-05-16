package com.codename.krypto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.DataOutput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main_Fragment extends Fragment {
    TextView Main_Date_text,Main_Total_Money,Main_depoitmoney,Main_Withdrawmoney;

    private String time;
    private KryptoDAO kryptoDAO;
    private KryptoServiceImpl service;
    private OngetMaindataListener GetMaindata;
    private onGroupNameListener getGroupName;
    private String GroupName;
    private ImageView Group_main_Image;

    @Override
    public void onAttach(Context context) {/////////액티비티에 프래그먼트가 부착될때 인텐트로 받은 그룹명을 가지고와서 세팅
        super.onAttach(context);
        if(getActivity() != null && getActivity() instanceof MainActivity){
           GroupName = ((MainActivity)getActivity()).getData();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {//1
        super.onCreate(savedInstanceState);
        createComponent();
    }

    public void setOngetMaindataListener(OngetMaindataListener listener){
        System.out.println("setOngetMaindataListener 1번째");
        this.GetMaindata = listener;
        System.out.println(GetMaindata);
    }

    private void createComponent() {/////////객체 생성 2번
        kryptoDAO =new KryptoDAO();
        SimpleDateFormat date = new SimpleDateFormat("M");
        time =date.format(new Date());
        System.out.println("createComponent1");

        GetMaindata = new OngetMaindataListener() {
            @Override
            public void onGetData(String Total_money ,String Depoit,String withdraw) {////////////////////DAO 에서 데이터를 가지고와서 초기화작업
                System.out.println("로그 : Main_Fragment onGetData안 초기화작업");
                Main_Date_text.setText(time+"월 총잔고");
                Main_Total_Money.setText(Total_money+" 원");
                Main_depoitmoney.setText(Depoit+" 원");
                Main_Withdrawmoney.setText(withdraw+" 원");
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.main_fragment,container,false);
        Main_Date_text = root.findViewById(R.id.Main_Date_textview);
        Main_Total_Money = root.findViewById(R.id.Main_Total_Money);
        Main_depoitmoney = root.findViewById(R.id.Main_depoitmoney);
        Main_Withdrawmoney = root.findViewById(R.id.Main_Withdrawmoney);
        Group_main_Image = root.findViewById(R.id.Group_main_Image);
        System.out.println("onCreateView");
        kryptoDAO.GetMainData(GetMaindata,GroupName);
        return root;
    }

    public static Main_Fragment newInstance() {
        Bundle args = new Bundle();
        Main_Fragment fragment = new Main_Fragment();
        fragment.setArguments(args);
        return fragment;
    }///////////////////////////////////////////*/

    public interface OngetMaindataListener{//인터페이스
        void onGetData(String Total_money,String depoit,String withdraw);
    }
    public interface onGroupNameListener{
        void onGetName(String GroupName);
    }
}
