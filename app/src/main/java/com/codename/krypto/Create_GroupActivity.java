package com.codename.krypto;

import android.Manifest;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Create_GroupActivity extends AppCompatActivity {
    public static final int RESULT_CODE = 011;/////////////////그룹생성시 그룹이름을 반환할 코드
    public static final int GALLERY_CODE = 106;
    private EditText Create_GroupName,Create_GroupContent,Create_Group_Time,Create_Group_Password;
    private Button Create_MakeGroup,Create_GroupProfileImage_Change,Create_GroupName_Check;
    private TextView ifGroup_exist;
    private CircleImageView Create_GroupProfile_image;
    private KryptoDTO kryptoDTO;
    private KryptoDAO kryptoDAO;
    private SimpleDateFormat times;
    private String time,GroupImagePath;
    private File file;
    private ArrayList<String> isHaveNameList;
    private ImageButton ImageButton;
    private boolean isGroupNamecheck,isGroupnameoverlap;
    private Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__group);
        createComponent();
        addListener();
    }


    private void createComponent() {

        times = new SimpleDateFormat("yyyy-MM-dd");
        time =  times.format(new Date());
        kryptoDTO = new KryptoDTO();
        kryptoDAO = new KryptoDAO();
        isHaveNameList = new ArrayList<String>();
        Create_GroupName= findViewById(R.id.Create_GroupName);
        Create_GroupContent= findViewById(R.id.Create_GroupContent);
        Create_Group_Time= findViewById(R.id.Create_Group_Time);
        Create_Group_Time.setText(time);
        times = new SimpleDateFormat("MM");
        time =  times.format(new Date());
        System.out.println(time+"월입니다.");
        Create_Group_Password= findViewById(R.id.Create_Group_Password);
        Create_MakeGroup= findViewById(R.id.Create_MakeGroup);
        Create_GroupProfile_image= findViewById(R.id.Create_GroupProfile_image);
        Create_GroupProfileImage_Change = findViewById(R.id.Create_GroupProfileImage_Change);
        Create_GroupName_Check = findViewById(R.id.Create_GroupName_Check);
        ImageButton = findViewById(R.id.MyPage_ProfileSetting_TopLayout_BackSpace);
        ifGroup_exist = findViewById(R.id.ifGroup_exist);
    }//////////////////////////////crerateComponent

    private void addListener() {

        ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MyPage_Profile_Activity.class));
                finish();
            }
        });
        Create_MakeGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectDTO();
            }///////CreateMakeGroup

        });///////////////////CreateMakeGroup

        Create_GroupProfileImage_Change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent,GALLERY_CODE);
            }
        });

        Create_GroupName_Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isHaveNameList.clear();
                if(Create_GroupName.getText().length() != 0) {////////모임명을 입력한경우
                    kryptoDAO.GroupNameCheck(ifGroup_exist,Create_GroupName.getText().toString(),Create_GroupName);
                    isGroupNamecheck= true;
                }else{////////////////////////////모임명을 입력하지 않은 경우
                    Toast.makeText(Create_GroupActivity.this, "모임명을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }//////////////addListener

    public void ConnectDTO(){
        if(GroupImagePath != null) {
            if (isGroupNamecheck) {
                kryptoDTO.setCreate_GroupProfile_image(GroupImagePath);
                kryptoDTO.setCreate_Group_Name(Create_GroupName.getText().toString().trim());
                kryptoDTO.setCreate_Group_Content(Create_GroupContent.getText().toString().trim());
                kryptoDTO.setGroup_create_Time(Create_Group_Time.getText().toString().trim());
                kryptoDTO.setGroup_Password(Create_Group_Password.getText().toString().trim());
                kryptoDAO.CreateGroup(kryptoDTO, Create_GroupName.getText().toString(), GroupImagePath, getApplicationContext(),time);

                Create_GroupName.setText("");
                Create_GroupContent.setText("");
                Create_Group_Password.setText("");
                Toast.makeText(getApplicationContext(), "모임생성이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),MainMyGroupHome.class);
                startActivity(intent);
                finish();



            } else {
                Toast.makeText(this, "모임명 중복체크하세요", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "프로필사진을 등록하세요", Toast.LENGTH_SHORT).show();
        }

    }////////////////////////////DAO에 그룹 생성후 데이터베이스 전송

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE) {
            try {
                if(data.getData() != null) {
                    GroupImagePath = getPath(data.getData());
                    file = new File(GroupImagePath);
                    /*Create_GroupProfile_image.setImageURI(Uri.fromFile(file));*/
                    Create_GroupProfile_image.setImageURI(Uri.fromFile(file));
                }else {
                    Toast.makeText(this, "사진을 선택해주세요.", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){

            }
        }
    }

    public String getPath(Uri uri){

        String [] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this,uri,proj,null,null,null);
        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(index);
    }///////////////////////////getPath


}///////////class
