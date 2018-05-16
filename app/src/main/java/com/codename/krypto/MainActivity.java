package com.codename.krypto;

import android.content.Context;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    public static final String KRYPTO_LOG = "KRYPTO_LOG:";
    Context context;
    ViewPager Main_ViewPager;
    TabLayout Main_Tab;
    ImageButton Main_Bottom_Home_Button,Main_Bottom_MyPage_Button,Main_Bottom_Graph_Button,Main_Bottom_List_Button;
    FragmentManager manager;
    String Group_Name;
    KryptoDAO kryptoDAO;
    String month;
    Main_Fragment main_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        System.out.println(Group_Name);
        getGroupName(intent);
        createcomponent();
        addListener();

    }/////////////////

    @Override
    protected void onNewIntent(Intent intent) {///////////클릭한 그룹명 받기 그리고 프래그먼트에 그룹이름 넘겨줌
        super.onNewIntent(intent);
        getGroupName(intent);
    }

    private void getGroupName(Intent intent) {//////////////어댑터에서 해당 클릭한 그룹명을 인텐트로 전달
        if(intent != null) {
            Group_Name = intent.getStringExtra("Group_Name");
        }else{return;}
    }/////////////////////////////

    private void createcomponent() {
        try{
            /*
                심플데이터 포맷은 삭제해서 MainFragment로 옴겨야함
                뷰페이저사용하지않을때
                 getSupportFragmentManager().beginTransaction().replace(R.id.main_Fragment,main_fragment).addToBackStack(null).commit();
             */

          /*  main_fragment = new Main_Fragment();*///add하면 프래그먼트랑 레이아웃 겹침 문제가 발생해서 replace 로 .addTobackStack 설정했으나안됨;
            Main_Fragment main_fragment = new Main_Fragment();
            Main_ViewPager_Adapter adapter = new Main_ViewPager_Adapter(getSupportFragmentManager());
            Main_ViewPager = findViewById(R.id.Main_ViewPager);
            Main_Tab = findViewById(R.id.Main_Tab);
            Main_Tab.setSelectedTabIndicatorColor(Color.BLUE);
            Main_ViewPager.setAdapter(adapter);
            Main_Tab.setupWithViewPager(Main_ViewPager);
            Main_Bottom_Home_Button = findViewById(R.id.Main_Bottom_Home_Button);
            Main_Bottom_MyPage_Button = findViewById(R.id.Main_Bottom_MyPage_Button);
            Main_Bottom_Graph_Button = findViewById(R.id.Main_Bottom_Graph_Button);
            Main_Bottom_List_Button = findViewById(R.id.Main_Bottom_List_Button);
            kryptoDAO = new KryptoDAO();

        }catch (Exception e){
            e.printStackTrace();
        }
    }/////////////////////createcomponent

    private void addListener() {

        Main_Bottom_Home_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(KRYPTO_LOG,"MainActivity : Main_Bottom_Home_Button : handling ");
                startActivity(new Intent(getApplicationContext(),MainMyGroupHome.class));
                finish();
            }
        });////////////////////MainActivity_Main_Bottom_Home_Button Listener

        Main_Bottom_Graph_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(KRYPTO_LOG,"MainActivity : Main_Bottom_Graph_Button : handling ");
                Snackbar.make(Main_Bottom_Graph_Button,"잠금모드 사용중입니다.",Snackbar.LENGTH_LONG).show();
            }
        });
        Main_Bottom_List_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(KRYPTO_LOG,"MainActivity : Main_Bottom_List_Button : handling ");
                startActivity(new Intent(getApplicationContext(),TotalListActivity.class));
                finish();

            }
        });
        Main_Bottom_MyPage_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(KRYPTO_LOG,"MainActivity : Main_Bottom_MyPage_Button : handling ");
                startActivity(new Intent(getApplicationContext(),MyPageActivity.class));
                finish();
            }
        });

    }//////////////////addListener

    public String getData(){
        return Group_Name;
    }

    public class ButtonListener implements View.OnTouchListener{
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return false;
        }
    }/////////////////////ButtonListener
}
