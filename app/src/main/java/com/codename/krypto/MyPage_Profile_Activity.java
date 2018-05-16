package com.codename.krypto;

import android.app.ProgressDialog;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyPage_Profile_Activity extends AppCompatActivity {
    public static final int PROFILE_GALLERY_CODE = 201;
    public static final int PROFILE_BACKGROUND_GALLERY_CODE = 202;
    private EditText Mypage_Profile_NickName,Mypage_Profile_UserName,Mypage_Profile_BirthNumber;
    private Button MyPage_Profile_OkButton,Mypage_Profile_CheckedButton;
    private RadioGroup Mypage_Profile_RadioGroup;
    private ImageView MyPage_Mybackground_Image;
    private ImageButton MyPage_Profile_MyImage;
    private TextView Mypage_profile_initNickName_Checked;
    private String RadioCheck;
    private KryptoDTO kryptoDTO;
    private KryptoDAO kryptoDAO;
    private String Profile_Image_Path;
    private String BackGround_Image_Path;
    private CircleImageView MyPage_Profile_AddMyimage;
    private File file;
    private ProgressDialog LodingDialog;
    private boolean isBackGroundCheck = false;
    private boolean isProfileCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page__profile_);
        createComponent();
        addListener();
        addConnect();


    }//////////onCreate

    private void addConnect() {////////////나의 저장된 프로필을 초기화
        kryptoDAO.getUserBeforeInfo(
                MyPage_Mybackground_Image,
                MyPage_Profile_AddMyimage
                ,Mypage_Profile_NickName
                ,Mypage_Profile_UserName
                ,Mypage_Profile_BirthNumber
                ,getApplicationContext());
    }/////////////////addConnect

    public void addListener(){

        MyPage_Mybackground_Image.setOnClickListener(new View.OnClickListener() {///////////////미디어 오픈 및 경로 얻기
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent,PROFILE_BACKGROUND_GALLERY_CODE);
            }
        });///////////////

        MyPage_Profile_MyImage.setOnClickListener(new View.OnClickListener() {///////////////미디어 오픈 및 경로 얻기
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent,PROFILE_GALLERY_CODE);

            }
        });/////////////////////////MyPage_Profile_MyImage

        Mypage_Profile_RadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {////////라디오버튼 데이터얻기
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int getid) {

                RadioButton radio = findViewById(getid);
                RadioCheck = radio.getText().toString();
            }
        });//////////////////Mypage_Profile_RadioGroup

        MyPage_Profile_OkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Mypage_Profile_NickName.getText().length() != 0) {
                    if(Mypage_Profile_UserName.getText().length() != 0) {
                        if(Mypage_Profile_BirthNumber.getText().length() != 0) {
                            ConnectDTO();
                        }else{
                            Toast.makeText(MyPage_Profile_Activity.this, "생일을 입력해주세요", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(MyPage_Profile_Activity.this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MyPage_Profile_Activity.this, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Mypage_Profile_CheckedButton.setOnClickListener(new View.OnClickListener() {///////////중복닉네임 체크용 버튼
            @Override
            public void onClick(View view) {
                kryptoDAO.initNicknameChecked(Mypage_Profile_NickName.getText().toString(),Mypage_profile_initNickName_Checked);
            }
        });/////////////////////Mypage_Profile_CheckedButton
    }//////////addListener

    public void ConnectDTO(){////////////데이터베이스연결을 위한 DTO

        kryptoDTO.setBackGround_UrI(BackGround_Image_Path);
        kryptoDTO.setProfile_Url(Profile_Image_Path);
        kryptoDTO.setNickName(Mypage_Profile_NickName.getText().toString());
        kryptoDTO.setUsername(Mypage_Profile_UserName.getText().toString());
        kryptoDTO.setGender(RadioCheck);
        kryptoDTO.setBirthDay(Mypage_Profile_BirthNumber.getText().toString());
        kryptoDAO.createUser(kryptoDTO,isBackGroundCheck,isProfileCheck);
        Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),MyPageActivity.class));
        finish();


    }///////// ////ConnectDTO
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {/////////갤러리오픈 및 스토리지 경로 얻기
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == PROFILE_GALLERY_CODE) {/////////////////////////PROFILE 이미지 클릭시 갤러리오픈및 스토리지 경로얻기
                if (data != null) {
                    Profile_Image_Path = getPath(data.getData());
                    file = new File(Profile_Image_Path);
                    MyPage_Profile_AddMyimage.setImageURI(Uri.fromFile(file));
                    isProfileCheck = true;
                } else {
                    return;
                }

            } else if (requestCode == PROFILE_BACKGROUND_GALLERY_CODE) {////////////////////BackGround 이미지 클릭시 갤러리오픈및 스트로지 경로얻기
                if (data != null) {
                    BackGround_Image_Path = getPath(data.getData());
                    file = new File(BackGround_Image_Path);
                    MyPage_Mybackground_Image.setImageURI(Uri.fromFile(file));
                    isBackGroundCheck = true;
                } else {
                    return;
                }

            }//////////////


        }catch (Exception e){
            e.printStackTrace();
        }
    }//////////////////////////onActivityResult

    private void createComponent() {////////////////////////////컴포넌트 객체화
        //DTO 연결을 위한 객체 생성
        kryptoDTO = new KryptoDTO();
        //DAO 연결을 위한 객체생성
        kryptoDAO = new KryptoDAO();
        LodingDialog = new ProgressDialog(getApplicationContext());
        ///////////그룹생성 버튼 얻기
        //회원가입 버튼 얻기
        MyPage_Profile_OkButton= findViewById(R.id.MyPage_Profile_OkButton);
        //Edittext 가져오기
        Mypage_Profile_NickName = findViewById(R.id.Mypage_Profile_NickName);
        Mypage_Profile_UserName = findViewById(R.id.Mypage_Profile_UserName);
        Mypage_Profile_BirthNumber = findViewById(R.id.Mypage_Profile_BirthNumber);
        Mypage_Profile_RadioGroup = findViewById(R.id.Mypage_Profile_RadioGroup);
        //라디오버튼해제
        Mypage_Profile_RadioGroup.clearCheck();
        //라디오버튼추가
        Mypage_Profile_RadioGroup.check(R.id.Myapge_Profile_Radio_Man);
        ////Background 이미지 얻기
        MyPage_Mybackground_Image = findViewById(R.id.MyPage_Mybackground_Image);
        MyPage_Profile_AddMyimage = findViewById(R.id.MyPage_Profile_AddMyimage);
        ///ProFile 이미지 얻기
        MyPage_Profile_MyImage = findViewById(R.id.MyPage_Profile_MyImage);
        //닉네임이 중복되는지 체크하기위한 버튼
        Mypage_Profile_CheckedButton = findViewById(R.id.Mypage_Profile_CheckedButton);

        //닉네임중복체크를 뿌려주기위한 이미지뷰
        Mypage_profile_initNickName_Checked = findViewById(R.id.Mypage_profile_initNickName_Checked);





    }///////////////////////////////////////////createComponent

    public void Backspace(View view) {

        switch (view.getId()){
            case R.id.MyPage_ProfileSetting_TopLayout_BackSpace :
                startActivity(new Intent(getApplicationContext(),MyPageActivity.class));
                finish();
                break;
            case R.id.MyPage_ProfileSetting_BackSpaceText:
                startActivity(new Intent(getApplicationContext(),MyPageActivity.class));
                finish();
                break;
            default:
                return;

        }
    }////////////////////뒤로가기 버튼 클릭시

    @Override
    protected void onStop() {
        super.onStop();
    }///////////////

    public String getPath(Uri uri){

        String [] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this,uri,proj,null,null,null);
        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(index);
    }///////////////////////////getPath



}/////////////class
