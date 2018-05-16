package com.codename.krypto;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyPageActivity extends AppCompatActivity {
    public static final String KRYPTO_LOG = "KRYPTO_LOG:";
    ImageButton Main_Bottom_Home_Button,Main_Bottom_List_Button,Main_Bottom_Graph_Button,Main_Bottom_MyPage_Button;
    Button Mypage_CrateContent,Mypage_Settings,MyPage_Top_Profile_Setting,Create_Group_Make;
    ImageView MyPage_Mybackground_Imagehave;
    CircleImageView MyPage_Profile_imageHave;
    TextView MyPage_Top_Myname;
    KryptoDTO kryptoDTO;
    KryptoDAO kryptoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
        createComponent();
        addListener();
        ConnectDAO();

    }////////////////////////onCreate();


    public void ConnectDAO(){/////////////////////나의 프로필데이터를 서버로부터 가져오기위한 메서드
        kryptoDAO.getUserInfo(MyPage_Profile_imageHave,MyPage_Mybackground_Imagehave,MyPage_Top_Myname,getApplicationContext());
    }////////////////////////////

    private void addListener() {

        Main_Bottom_Home_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(KRYPTO_LOG,"MyPageActivity : Main_Bottom_Home_Button : handling");
                startActivity(new Intent(getApplicationContext(),MainMyGroupHome.class));
                finish();
            }
        });//////////////////////Main_Botton_Home_Button Listener
        Main_Bottom_List_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(KRYPTO_LOG,"MyPageActivity : Main_Bottom_Home_Button : handling");
                startActivity(new Intent(getApplicationContext(),TotalListActivity.class));
                finish();

            }
        });/////////////Main_Bottom_List_Button Listenter
        Main_Bottom_Graph_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(KRYPTO_LOG,"MyPageActivity : Main_Bottom_Home_Button : handling ");
                Snackbar.make(Main_Bottom_Graph_Button,"잠금모드 사용중입니다.",Snackbar.LENGTH_LONG).show();
            }
        });///////////////Main_Bottom_Graph_Button Listener
        Main_Bottom_MyPage_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MyPageActivity.this, "현재 화면 입니다.", Toast.LENGTH_SHORT).show();

            }
        });////////////////Main_Bottom_MyPage_Button Listener

        Mypage_CrateContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),InputActivity.class));
                Log.d(KRYPTO_LOG,"MyPageActivity : Mypage_CrateContent : handling");
                finish();
            }
        });////////////////////////////Mypage_CrateContent

        Mypage_Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MyPage_Setting_Activity.class));
                Log.d(KRYPTO_LOG,"MyPageActivity : Mypage_Settings : handling");
                finish();

            }
        });////////////////////////////Mypage_Settings
        MyPage_Top_Profile_Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MyPage_Profile_Activity.class));
                finish();
            }
        });////////////

        Create_Group_Make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Create_GroupActivity.class));

            }
        });
    }/////////////addListener


    private void createComponent() {
        try {
            kryptoDTO = new KryptoDTO();
            kryptoDAO = new KryptoDAO();
            MyPage_Top_Profile_Setting = findViewById(R.id.MyPage_Top_Profile_Setting);
            Main_Bottom_Home_Button = (ImageButton)findViewById(R.id.Main_Bottom_Home_Button);
            Main_Bottom_List_Button = (ImageButton)findViewById(R.id.Main_Bottom_List_Button);
            Main_Bottom_Graph_Button = (ImageButton)findViewById(R.id.Main_Bottom_Graph_Button);
            Main_Bottom_MyPage_Button = (ImageButton)findViewById(R.id.Main_Bottom_MyPage_Button);
            Mypage_CrateContent = findViewById(R.id.Mypage_CrateContent);
            Mypage_Settings = findViewById(R.id.Mypage_Settings);
            MyPage_Mybackground_Imagehave = findViewById(R.id.MyPage_Mybackground_Imagehave);
            MyPage_Profile_imageHave = findViewById(R.id.MyPage_Profile_imageHave);
            MyPage_Top_Myname = findViewById(R.id.MyPage_Top_Myname);
            Create_Group_Make = findViewById(R.id.Create_Group_Make);

        }catch (Exception e){
            Toast.makeText(this, "MyPage CreateComponent Error", Toast.LENGTH_SHORT).show();
        }
    }//////////////create Component

}/////////////////////class
