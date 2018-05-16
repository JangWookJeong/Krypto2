package com.codename.krypto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainMyGroupHome extends AppCompatActivity {
    ImageButton Main_Bottom_Home_Button,Main_Bottom_Notification_Button,Main_Bottom_SearchButton,Main_Bottom_MyPage_Button;
    RecyclerView MyHome_RecycleView;
    KryptoDAO kryptoDAO;
    KryptoDTO kryptoDTO;
    TextView MyGroup_Count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_my_group_home);
        createcomponent();
        addListener();
        connectDTO();
    }/////////////////////

    private void connectDTO() {///////////////유저가 가입한 자신의 모임 목록을 가지고와 뿌려주기
        kryptoDAO.getjoiningGroup(getApplicationContext(),MyHome_RecycleView,MyGroup_Count);
    }

    private void addListener() {
        Main_Bottom_Home_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainMyGroupHome.this, "현재 화면 입니다.", Toast.LENGTH_SHORT).show();
            }
        });
        Main_Bottom_Notification_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainMyGroupHome.this, "잠금모드", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        Main_Bottom_SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Home_Group_ListActivity.class));
                finish();
            }
        });
        Main_Bottom_MyPage_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MyPageActivity.class));
                finish();
            }
        });
    }

    private void createcomponent() {
        MyHome_RecycleView = findViewById(R.id.MyHome_RecycleView);
        kryptoDAO = new KryptoDAO();
        kryptoDTO = new KryptoDTO();
        Main_Bottom_Home_Button = findViewById(R.id.Main_Bottom_Home_Button);
        Main_Bottom_Notification_Button = findViewById(R.id.Main_Bottom_Notification_Button);
        Main_Bottom_SearchButton = findViewById(R.id.Main_Bottom_SearchButton);
        Main_Bottom_MyPage_Button = findViewById(R.id.Main_Bottom_MyPage_Button);
        MyGroup_Count = findViewById(R.id.MyGroup_Count);

    }

    public void finishActivity(){
        finish();
    }
}
