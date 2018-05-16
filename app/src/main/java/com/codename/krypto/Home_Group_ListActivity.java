package com.codename.krypto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class Home_Group_ListActivity extends AppCompatActivity {
    RecyclerView Home_RecyclerView;
    KryptoDTO kryptoDTO;
    KryptoDAO kryptoDAO;
    ImageButton Main_Bottom_MyPageButton,Main_Bottom_Notification_Button,Home_Bottom_SearchButton,Home_Bottom_HomeButton;
    RecyclerView.LayoutManager layoutmanager;
    AlertDialog.Builder alert;
    SearchView Home_Group_Search;
    boolean searchstart = true;
    String Searchdata ="null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__group__list);
        createComponent();
        addListener();
        adapterConnect();
        HomeConnectDTO();



    }/////////////////

    private void adapterConnect() {

    }

    private void addListener() {
        Main_Bottom_MyPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MyPageActivity.class));
                finish();

            }
        });
        Main_Bottom_Notification_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home_Group_ListActivity.this, "잠금모드", Toast.LENGTH_SHORT).show();
            }
        });
        Home_Bottom_SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home_Group_ListActivity.this, "현재 화면 입니다.", Toast.LENGTH_SHORT).show();

            }
        });
        Home_Bottom_HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainMyGroupHome.class));
                finish();

            }
        });
        Home_Group_Search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Searchdata = s;

                kryptoDAO.getGroupNames(Home_RecyclerView,getApplicationContext(),alert,true,Searchdata);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }

    private void HomeConnectDTO() {//서버와통신해 데이터를 가져올 메서드
        searchstart = false;
        kryptoDAO.getGroupNames(Home_RecyclerView,getApplicationContext(),alert,searchstart,Searchdata);
    }///////////

    private void createComponent() {
        Main_Bottom_MyPageButton = findViewById(R.id.Main_Bottom_MyPage_Button);
        Main_Bottom_Notification_Button = findViewById(R.id.Main_Bottom_Notification_Button);
        Home_Bottom_SearchButton = findViewById(R.id.Main_Bottom_SearchButton);
        Home_Bottom_HomeButton = findViewById(R.id.Main_Bottom_Home_Button);
        Home_RecyclerView = findViewById(R.id.Home_RecyclerView);
        Home_Group_Search = findViewById(R.id.Home_Group_Search);
        kryptoDTO = new KryptoDTO();
        kryptoDAO = new KryptoDAO();
        alert = new AlertDialog.Builder(this);
    }//////////////////
}///////////////class
