package com.codename.krypto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MyPage_Setting_Activity extends AppCompatActivity {
    FirebaseAuth Auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page__setting_);
        create();
    }




    private void create() {
        try {
            Auth = FirebaseAuth.getInstance();

            Button Mypage_Setting_back = findViewById(R.id.Mypage_Setting_back);
            Mypage_Setting_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(),MyPageActivity.class));
                    finish();
                }
            });
            Button Mypage_setting_Logout = findViewById(R.id.Mypage_setting_Logout);
            Mypage_setting_Logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Auth.signOut();
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    finish();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "MyPage_Setting_Error", Toast.LENGTH_SHORT).show();
        }
    }////create Method



}///////////class
