package com.codename.krypto;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TotalListActivity extends AppCompatActivity {
    public static final String KRYPTO_LOG = "KRYPTO_LOG:";
    ImageButton Main_Bottom_Home_Button,Main_Bottom_List_Button,Main_Bottom_Graph_Button,Main_Bottom_MyPage_Button;
    ListView Depoit_List;
    SimpleDateFormat format;
    private String[] Spinner_item ={"입 금","출 금"};
    Spinner List_Total_Spiner;
    Main_Total_List_items items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_list);
        oncreate();

        Main_Bottom_Home_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(KRYPTO_LOG,"TotalListActivity : Main_Bottom_Home_Button : handling");
                startActivity(new Intent(getApplicationContext(),MainMyGroupHome.class));
                finish();

            }
        });//////////////////////Main_Botton_Home_Button Listener
        Main_Bottom_List_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TotalListActivity.this, "현재 화면 입니다.", Toast.LENGTH_SHORT).show();

            }
        });/////////////Main_Bottom_List_Button Listenter
        Main_Bottom_Graph_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(KRYPTO_LOG,"TotalListActivity : Main_Bottom_Home_Button : handling ");
                Snackbar.make(Main_Bottom_Graph_Button,"잠금모드 사용중입니다.",Snackbar.LENGTH_LONG).show();
            }
        });///////////////Main_Bottom_Graph_Button Listener
        Main_Bottom_MyPage_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(KRYPTO_LOG,"TotalListActivity : Main_Bottom_MyPage_Button : handling");
                startActivity(new Intent(getApplicationContext(),MyPageActivity.class));
                finish();

            }
        });////////////////Main_Bottom_MyPage_Button Listener

    }

    private void oncreate() {

        try{
            format = new SimpleDateFormat("yyyy.MM.dd");
            String time = format.format(new Date());
            Depoit_List = findViewById(R.id.Depoit_List);
            Main_Bottom_Home_Button = (ImageButton)findViewById(R.id.Main_Bottom_Home_Button);
            Main_Bottom_List_Button = (ImageButton)findViewById(R.id.Main_Bottom_List_Button);
            Main_Bottom_Graph_Button = (ImageButton)findViewById(R.id.Main_Bottom_Graph_Button);
            Main_Bottom_MyPage_Button = (ImageButton)findViewById(R.id.Main_Bottom_MyPage_Button);
            List_Total_Spiner = findViewById(R.id.List_Total_Spinner);
            List_Total_Spiner.setSelection(0);
            ArrayAdapter Spinner_adapter = new ArrayAdapter(getApplicationContext(),R.layout.custom_simple_dropdown_item_2line,Spinner_item);
            List_Total_Spiner.setAdapter(Spinner_adapter);
            List_Total_Spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    if(position == 1){

                    }
                }//////////////

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });////////////List_Total_Spiner Listener

            Main_Total_List_Adapter adapter = new Main_Total_List_Adapter();
            adapter.additems(new Main_Total_ListDTO(R.drawable.bin1,"장욱정",time,"15000원","1345234원"),getApplicationContext());
            adapter.additems(new Main_Total_ListDTO(R.drawable.bin1,"장동건",time,"15000원","2345234원"),getApplicationContext());
            adapter.additems(new Main_Total_ListDTO(R.drawable.bin1,"조인성",time,"15000원","75234원"),getApplicationContext());
            adapter.additems(new Main_Total_ListDTO(R.drawable.bin1,"김태희",time,"15000원","43234원"),getApplicationContext());
            adapter.additems(new Main_Total_ListDTO(R.drawable.bin1,"이나영",time,"15000원","233441원"),getApplicationContext());
            adapter.additems(new Main_Total_ListDTO(R.drawable.bin1,"원빈",time,"15000원","985000원"),getApplicationContext());
            adapter.additems(new Main_Total_ListDTO(R.drawable.bin1,"효도르",time,"15000원","78454원"),getApplicationContext());
            adapter.additems(new Main_Total_ListDTO(R.drawable.bin1,"토르",time,"15000원","345234원"),getApplicationContext());
            adapter.additems(new Main_Total_ListDTO(R.drawable.bin1,"헐크",time,"15000원","233441원"),getApplicationContext());
            adapter.additems(new Main_Total_ListDTO(R.drawable.bin1,"아이언맨",time,"15000원","43234원"),getApplicationContext());
            adapter.additems(new Main_Total_ListDTO(R.drawable.bin1,"자비스",time,"15000원","2345234원"),getApplicationContext());
            adapter.additems(new Main_Total_ListDTO(R.drawable.bin1,"도비스",time,"15000원","1345234원"),getApplicationContext());
            adapter.additems(new Main_Total_ListDTO(R.drawable.bin1,"장욱정",time,"15000원","985000원"),getApplicationContext());
            adapter.additems(new Main_Total_ListDTO(R.drawable.bin1,"정우성",time,"15000원","43234원"),getApplicationContext());
            adapter.additems(new Main_Total_ListDTO(R.drawable.bin1,"테스트",time,"15000원","233441원"),getApplicationContext());
            adapter.additems(new Main_Total_ListDTO(R.drawable.bin1,"테스트",time,"15000원","78454원"),getApplicationContext());
            adapter.additems(new Main_Total_ListDTO(R.drawable.bin1,"테스트",time,"15000원","345234원"),getApplicationContext());
            adapter.additems(new Main_Total_ListDTO(R.drawable.bin1,"테스트",time,"15000원","233441원"),getApplicationContext());
            adapter.additems(new Main_Total_ListDTO(R.drawable.bin1,"테스트",time,"15000원","345234원"),getApplicationContext());
            adapter.additems(new Main_Total_ListDTO(R.drawable.bin1,"테스트",time,"15000원","78454원"),getApplicationContext());
            adapter.additems(new Main_Total_ListDTO(R.drawable.bin1,"테스트",time,"15000원","345234원"),getApplicationContext());
            Depoit_List.setAdapter(adapter);

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Totaal_List_createMethod Error", Toast.LENGTH_SHORT).show();
        }
    }////////////onCreate

}
