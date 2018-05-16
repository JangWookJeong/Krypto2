package com.codename.krypto;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InputActivity extends AppCompatActivity {
    public static final String KRYPTO_LOG = "KRYPTO_LOG:";
    public Spinner Check_Spinner;
    private String[] Spinner_item ={"입 금","출 금"};
    private TextView Input_Name,Input_Money;
    private EditText Input_Content_Text,Input_Date_Text;
    private ImageButton Input_Top_Backspace;
    private SimpleDateFormat dateFormat;
    private String time;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        createcomponent();
    }////////////onCreate

    public void createcomponent(){
        KryptoDAO dao = new KryptoDAO();
        dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        time = dateFormat.format(new Date());

        Input_Content_Text = findViewById(R.id.Input_Content_Text);
        Input_Content_Text.setSelection(Input_Content_Text.length());
        Input_Date_Text = findViewById(R.id.Input_Date_Text);
        Input_Date_Text.setText(time);
        Input_Top_Backspace = findViewById(R.id.Input_Top_Backspace);
        Input_Top_Backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
        Input_Name = findViewById(R.id.Input_Name);
        Input_Money = findViewById(R.id.Input_money);
        Check_Spinner = findViewById(R.id.Input_Spinner);
        adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.custom_simple_dropdown_item_1line,Spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Check_Spinner.setAdapter(adapter);
        Check_Spinner.setSelection(0);

        Check_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Input_Name.setText(Spinner_item[position]+"자명:");
                Input_Name.setHint(Spinner_item[position]+"자명을 입력해주세요");
                Input_Money.setText(Spinner_item[position]+"액:");
                Input_Money.setHint(Spinner_item[position]+"액을 입력해주세요");
                Check_Spinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });//////////////////////////Check_Spinner_Listener


    }
}
