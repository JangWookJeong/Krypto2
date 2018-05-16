package com.codename.krypto;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class KryptoDAO{
    public static final int UPDATEMEMBERCOUNT = 101;////////////회원가입할때 회원수 카운트
    public static final int UPDATEMEMBERLOGINCOUNT = 102;////////////로그인할때 회원수 카운트
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private FirebaseStorage storage;
    StorageReference storageRef;
    List<KryptoDTO> ListValue;
    List<String> ListKey;
    List<String> isGroupName;
    boolean isJoiningUser;
    boolean isSearchcheck = false;
    String getGroup_Total_Dues,depoit,withdraw;
    private Uri file1,file2;
    Create_GroupActivity group;
    int initChecked = 0;
    KryptoDTO kryptoDTO;
    RecyclerView.LayoutManager layoutmanager;
    Home_Center_RecyclerAdapter Search_home_adapter;
    MyHome_Group_ListAdapter MyHome_adapter;
    String username,Group_Member_size;
    String Create_Group_Name;
    Main_Fragment main_fragment;

    public KryptoDAO(){

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        ListValue = new ArrayList<KryptoDTO>();
        ListKey = new ArrayList<String>();
        group = new Create_GroupActivity();
        isGroupName = new ArrayList<String>();
        kryptoDTO = new KryptoDTO();
        main_fragment = new Main_Fragment();

    }//////////초기화를위한 생성자 설정

    public void JoinninandCheck(final Context LoginContext, KryptoDTO dto){

        database.getReference().child("관리자").child("회원관리").child("회원정보").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ListKey.clear();
                for(DataSnapshot JoiningCheck:dataSnapshot.getChildren()){
                    ListKey.add(JoiningCheck.getKey());
                }
                for(int i =0; i<ListKey.size();i++){
                    if(auth.getCurrentUser().getUid().equals(ListKey.get(i))){
                        Intent intent = new Intent(LoginContext,MainMyGroupHome.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        LoginContext.startActivity(intent);
                    }else{
                        Intent intent = new Intent(LoginContext,MyPage_Profile_Activity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        LoginContext.startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public KryptoDTO MemberListCheck(KryptoDTO dto) {/////////////회원정보확인



        return kryptoDTO;
    }////////////////////MemberListCheck
    public void createUser(final KryptoDTO kryptoDTO , final boolean isBackgroundimage,final boolean isProfileimage) {//////유저 회원가입

        storageRef = storage.getReferenceFromUrl("gs://krypto-39b18.appspot.com/");
        file1 = Uri.fromFile(new File(kryptoDTO.getBackGround_UrI()));
        file2 = Uri.fromFile(new File(kryptoDTO.getProfile_Url()));
        StorageReference riverRef1 =storageRef.child(  "유저/"+kryptoDTO.getUsername()+"/"+file1.getLastPathSegment());
        StorageReference riverRef2 =storageRef.child(  "유저/"+kryptoDTO.getUsername()+"/"+file2.getLastPathSegment());
        final UploadTask uploadTask1 = riverRef1.putFile(file1);
        final UploadTask uploadTask2 = riverRef2.putFile(file2);
        uploadTask1.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl1 = taskSnapshot.getDownloadUrl();
                kryptoDTO.setBackGround_UrI(downloadUrl1.toString());
                uploadTask2.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrI2 = taskSnapshot.getDownloadUrl();
                        kryptoDTO.setProfile_Url(downloadUrI2.toString());
                        database.getReference()
                                .child("관리자")
                                .child("회원관리")
                                .child("회원정보")
                                .child(auth.getCurrentUser().getUid())
                                .setValue(kryptoDTO).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }//////////onSuccess
                        });//////////////////addOnSuccess 리스너
                    }////성공시
                });//////////profile URI 저장과 동시에 데이터저장
            }////////backGround URL저장
        });////////////유저 정보를 입력

    }//////////createUser

    public void CreateGroup(final KryptoDTO CreateDTO, final String groupName, String GroupImageUrl, final Context applicationContext,final String month) {

        storageRef = storage.getReferenceFromUrl("gs://krypto-39b18.appspot.com/");
        file1 = Uri.fromFile(new File(GroupImageUrl));
        StorageReference riverRef = storageRef.child(groupName+"/"+file1.getLastPathSegment());
        UploadTask uploadTask = riverRef.putFile(file1);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Create_Group_Name =groupName;
                //다운로드Url 설정
                Uri downloadUrI = taskSnapshot.getDownloadUrl();
                CreateDTO.setCreate_GroupProfile_image(downloadUrI.toString());
                database.getReference().child("관리자").child("모임관리").child("모임명").child(Create_Group_Name).child("모임정보").child("프로필").child("기본프로필").setValue(CreateDTO).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        database.getReference().child("관리자").child("모임관리").child("모임명").child(Create_Group_Name).child("모임장").setValue(auth.getCurrentUser().getUid());
                        database.getReference().child("관리자").child("회원관리").child("회원정보").child(auth.getCurrentUser().getUid()).child("나의모임관리").child(Create_Group_Name).setValue(CreateDTO).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {///////////////////////////회원 나의모임관리에 모임명 및 모임프로필 넣기.
                                database.getReference().child("관리자").child("회원관리").child("회원정보").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {///////회원 프로필 가지고오기
                                        kryptoDTO = null;
                                        for(DataSnapshot getMyFrofile : dataSnapshot.getChildren()){
                                            kryptoDTO =  dataSnapshot.getValue(KryptoDTO.class);
                                        }
                                        kryptoDTO.setUserUID(auth.getCurrentUser().getUid());///////회원 UID 넣어서 모임 멤버에 넣기
                                        database.getReference().child("관리자").child("모임관리").child("모임명").child(Create_Group_Name).child("회원정보").child("멤버정보").child(auth.getCurrentUser().getUid()).setValue(kryptoDTO).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {//모임 멤버정보에다가 유저의 정보 및 프로필 넣기 <여기다가 출금 입금 생성하면됨>
                                                kryptoDTO = null;
                                                kryptoDTO = new KryptoDTO();
                                                kryptoDTO.setGroup_Total_Dues("0");
                                                database.getReference().child("관리자").child("모임관리").child("모임명").child(Create_Group_Name).child("모임정보").child("회비내역").child("총잔고").setValue(kryptoDTO).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        kryptoDTO = null;
                                                        kryptoDTO = new KryptoDTO();
                                                        kryptoDTO.setGroup_Month_Total_Depoit("0");
                                                        database.getReference().child("관리자").child("모임관리").child("모임명").child(Create_Group_Name).child("모임정보").child("회비내역").child("월별입금").child("전체입금잔고").setValue(kryptoDTO).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                kryptoDTO = null;
                                                                kryptoDTO = new KryptoDTO();
                                                                kryptoDTO.setGroup_Month_Depoit("0");
                                                                database.getReference().child("관리자").child("모임관리").child("모임명").child(Create_Group_Name).child("모임정보").child("회비내역").child("월별입금").child(month+"월").push().setValue(kryptoDTO).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        kryptoDTO = null;
                                                                        kryptoDTO = new KryptoDTO();
                                                                        kryptoDTO.setGroup_Month_Total_Withdraw("0");
                                                                        database.getReference().child("관리자").child("모임관리").child("모임명").child(Create_Group_Name).child("모임정보").child("회비내역").child("월별출금").child("전체출금잔고").push().setValue(kryptoDTO).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void aVoid) {
                                                                                kryptoDTO = null;
                                                                                kryptoDTO = new KryptoDTO();
                                                                                kryptoDTO.setGroup_Month_Withdraw("0");
                                                                                database.getReference().child("관리자").child("모임관리").child("모임명").child(Create_Group_Name).child("모임정보").child("회비내역").child("월별출금").child(month+"월").push().setValue(kryptoDTO).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                    @Override
                                                                                    public void onSuccess(Void aVoid) {
                                                                                        kryptoDTO=null;
                                                                                    }
                                                                                });
                                                                            }
                                                                        });
                                                                    }
                                                                });
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        });
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                        });
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }//////////////모임만들기에서 모임만들기 생성하여 데이터베이스에 저장

    public void GroupNameCheck(final TextView ifGroup_exist, final String GroupName, final EditText create_GroupName) {
        database.getReference().child("관리자").child("모임관리").child("모임명").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ListValue.clear();
                ListKey.clear();
                initChecked= 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(GroupName.contains(snapshot.getKey())){
                        initChecked = 1;
                    }
                    String key = snapshot.getKey();
                    ListKey.add(key);
                }//////////확장for문
                if(initChecked == 1){
                    ifGroup_exist.setTextColor(Color.RED);
                    ifGroup_exist.setText(create_GroupName.getText()+" 은(는) 존재하는 모임명입니다.");
                    create_GroupName.setText("");

                }else{
                    ifGroup_exist.setTextColor(Color.BLACK);
                    ifGroup_exist.setText(create_GroupName.getText()+"은(는) 사용할수있는 모임명입니다.");
                }
            }////////////////////////////////////////////////Ondata
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }////////////////모임만들기에 동일한 그룹명이 있는지  체크

    public void getUserInfo(final CircleImageView myPage_Profile_imageHave, final ImageView myPage_Mybackground_Imagehave, final TextView myPage_Top_Myname, final Context applicationContext) {////////유저정보를 가지고옴
        database.getReference().child("관리자").child("회원관리").child("회원정보").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                kryptoDTO = null;
                ListValue.clear();
                for(DataSnapshot Snapshot :  dataSnapshot.getChildren()) {
                    kryptoDTO = dataSnapshot.getValue(KryptoDTO.class);
                    ListValue.add(kryptoDTO);

                }/////////////////
                if (kryptoDTO != null) {
                    Glide.with(applicationContext.getApplicationContext()).load(kryptoDTO.getBackGround_UrI()).into(myPage_Mybackground_Imagehave);
                    Glide.with(applicationContext.getApplicationContext()).load(kryptoDTO.getProfile_Url()).into(myPage_Profile_imageHave);
                    myPage_Top_Myname.setText(kryptoDTO.getUsername().toString());
                }else{

                }

            }/////////////////////
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }//////////////////////getUserInfo

    public void initNicknameChecked(final String UserNickName,final TextView initChecked_TextView){////////닉네임 중복을체크를위한 메서드

        database.getReference().child("관리자").child("회원관리").child("회원정보").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ListValue.clear();
                ListKey.clear();
                initChecked = 0;
                for(DataSnapshot snapshotKey : dataSnapshot.getChildren()){
                    kryptoDTO = snapshotKey.getValue(KryptoDTO.class);
                    if(UserNickName.contains(kryptoDTO.getNickName())){
                        initChecked = 1;
                    }
                }////확장for문
                if(initChecked == 1){
                    initChecked_TextView.setTextColor(Color.RED);
                    initChecked_TextView.setText("사용중인 닉네임 입니다.");
                }else{
                    initChecked_TextView.setTextColor(Color.BLACK);
                    initChecked_TextView.setText("사용가능한 닉네임 입니다.");
                }
            }///////////////////onDataChange
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }////////////////initNicknameChecked

    public void getUserBeforeInfo(final ImageView myPage_mybackground_image, //////////이전에 유저가 정보를 가져와서 뿌려줌
                                  final CircleImageView myPage_profile_myImage,
                                  final EditText mypage_profile_nickName,
                                  final EditText mypage_profile_userName,
                                  final EditText mypage_profile_birthNumber,
                                  final Context applicationContext) {

        database.getReference().child("관리자").child("회원관리").child("회원정보").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ListKey.clear();
                ListValue.clear();
                for(DataSnapshot Snapshot :  dataSnapshot.getChildren()) {
                    ListKey.add(Snapshot.getKey());
                    kryptoDTO = dataSnapshot.getValue(KryptoDTO.class);
                    ListValue.add(kryptoDTO);

                }/////////////////
                if (kryptoDTO != null) {
                    for(int i =0; i < ListKey.size();i++){
                        if(auth.getCurrentUser().getUid().equals(ListKey.get(i))){
                            Glide.with(applicationContext.getApplicationContext()).load(kryptoDTO.getBackGround_UrI()).into(myPage_mybackground_image);
                            Glide.with(applicationContext.getApplicationContext()).load(kryptoDTO.getProfile_Url()).into(myPage_profile_myImage);
                            mypage_profile_userName.setText(kryptoDTO.getUsername().toString());
                            mypage_profile_nickName.setText(kryptoDTO.getNickName());
                            mypage_profile_birthNumber.setText(kryptoDTO.getBirthDay());
                        }
                        else{
                            Intent intent = new Intent(applicationContext,MyPage_Profile_Activity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            applicationContext.startActivity(intent);
                        }
                    }


                }else{
                    return;
                }
            }/////////////////////
            @Override
            public void onCancelled(DatabaseError databaseError) {Toast.makeText(applicationContext, "서버가 불안정합니다. 잠시후 다시 시도해주세요", Toast.LENGTH_SHORT).show();}});

    }//////////////////////getUserBeforeInfo

    public void getGroupNames(final RecyclerView home_recyclerView, final Context applicationContext, final android.app.AlertDialog.Builder alert, final boolean isSearch, final String Searchdata) {/////////그룹리스트를 가져와서뿌려주기.

        if(isSearch){
            database.getReference().child("관리자").child("모임관리").child("모임명").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    kryptoDTO = null;
                    ListValue.clear();
                    ListKey.clear();
                    isGroupName.clear();
                    for(DataSnapshot SearchData : dataSnapshot.getChildren()){//////////전체 키값을 가져오기위한 for문
                        ListKey.add(SearchData.getKey());
                        System.out.println(SearchData.getKey());
                    }
                    for(int i =0; i < ListKey.size(); i++){
                        if(Searchdata.equals(ListKey.get(i))){////////////입력데이터와 모임명 이 같은게 있는지 확인
                            isGroupName.add(ListKey.get(i)+"포함된값이 있는지확인");
                        }else{
                            Toast.makeText(applicationContext, "데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }////////////////////////////for
                    if(isSearch){
                        for(int i =0; i < isGroupName.size();i++){
                            database.getReference().child("관리자").child("모임관리").child("모임명").child(isGroupName.get(i)).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {///////////중복되는 모임명의 데이터를뿌려주기위해 정보수집
                                    for(DataSnapshot SameData : dataSnapshot.getChildren()){
                                        kryptoDTO =  dataSnapshot.getValue(KryptoDTO.class);
                                        ListValue.add(kryptoDTO);
                                    }
                                    layoutmanager = new LinearLayoutManager(applicationContext.getApplicationContext(),LinearLayoutManager.VERTICAL,false);
                                    Search_home_adapter = new Home_Center_RecyclerAdapter(ListValue, applicationContext, alert);
                                    home_recyclerView.setLayoutManager(layoutmanager);
                                    home_recyclerView.setAdapter(Search_home_adapter);
                                    Search_home_adapter.notifyDataSetChanged();

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(applicationContext, "서버가 불안정합니다. 잠시후 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }else{

                    }
                }/////////////////////
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(applicationContext, "서버가 불안정합니다. 잠시후 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            database.getReference().child("관리자").child("모임관리").child("모임명").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    kryptoDTO = null;
                    ListValue.clear();
                    ListKey.clear();
                    for (DataSnapshot keySnap : dataSnapshot.getChildren()) {
                        ListKey.add(keySnap.getKey().toString());
                    }

                    for (int i = 0; i < ListKey.size(); i++) {
                        System.out.println(ListKey.get(i)+"키는?"+i);
                        database.getReference().child("관리자").child("모임관리").child("모임명").child(ListKey.get(i)).child("모임정보").child("프로필").addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                kryptoDTO = null;
                                for (DataSnapshot ValueSnap : dataSnapshot.getChildren()) {
                                    ListKey.add(ValueSnap.getKey());
                                    kryptoDTO = ValueSnap.getValue(KryptoDTO.class);
                                    ListValue.add(kryptoDTO);
                                    System.out.println("ListVlaue사이즈" + ListValue.size());
                                }
                                layoutmanager = new LinearLayoutManager(applicationContext.getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                                Search_home_adapter = new Home_Center_RecyclerAdapter(ListValue, applicationContext, alert);
                                home_recyclerView.setLayoutManager(layoutmanager);
                                home_recyclerView.setAdapter(Search_home_adapter);
                                Search_home_adapter.notifyDataSetChanged();
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(applicationContext, "서버가 불안정합니다. 잠시후 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }///////////for문
                }///////////////////////////////////////////////////////////////////////////////////////
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(applicationContext, "서버가 불안정합니다. 잠시후 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }/////////////////////////////////////////////////////////////////////////////////////////////getGroupNames

    public void JoiningUser(final Context context, final String getGroupName) {///////////유저를 모임에 가입시키는 메서드

        database.getReference().child("관리자").child("모임관리").child("모임명").child(getGroupName).child("회원정보").child("멤버정보").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ListValue.clear();
                kryptoDTO = null;
                ListKey.clear();
                for (DataSnapshot UserUIdCheck : dataSnapshot.getChildren()) {
                    ListKey.add(UserUIdCheck.getKey());
                    kryptoDTO = UserUIdCheck.getValue(KryptoDTO.class);
                    ListValue.add(kryptoDTO);////////그룹 멤버의 숫자를 확인하기위한 Vlaue
                }
                if(ListValue.size() != 0){//////////가입한 사용자가 있는 경우

                    for (int i = 0; i < ListValue.size();i++) {//////////회원가입여부를 비교하기위한for문
                        System.out.println("for문에 들어왔어요  :" +isJoiningUser);
                        System.out.println("for문에 listValue값  :" +ListValue.get(i).getUserUID());
                        if (auth.getCurrentUser().getUid().equals(ListValue.get(i).getUserUID())) {///////////////회원가입이 되어있는 상태
                            isJoiningUser = true;
                            username =  ListValue.get(i).getUsername();
                        }
                    }/////////for문
                    System.out.println("for문에 지금막나왔어요   :" +isJoiningUser);
                    System.out.println("username  :" +username);
                    System.out.println("상태여부체크 :" +isJoiningUser);
                    if(isJoiningUser){///////회원가입이 되어있는 상태

                        Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("Groupname", getGroupName);
                        context.startActivity(intent);
                        Toast.makeText(context, username+ "님 환영합니다!", Toast.LENGTH_SHORT).show();
                        return;
                    }else{///////회원가입이 안되어있는 상태
                        System.out.println("상태여부체크 :" +isJoiningUser+" 회원가입이 안되어있는상태에 들어왓어요");
                        database.getReference().child("관리자").child("회원관리").child("회원정보").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {//유저정보확인
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                kryptoDTO = null;
                                for (DataSnapshot FindUserName_Sanpshot : dataSnapshot.getChildren()) {///////////유저이름을 가져옴 다이렉트로 가져올때는 for문에서 datasnapshot 으로 가져오면됨;
                                    try {
                                        kryptoDTO = dataSnapshot.getValue(KryptoDTO.class);
                                        kryptoDTO.setUserUID(auth.getCurrentUser().getUid());
                                    }catch (Exception e){return;}
                                    System.out.println("JoiningUser 로그 : " + ListValue.size());
                                }
                                if (kryptoDTO != null) {///////////유저가프로필을 설정한경우
                                    database.getReference().child("관리자").child("모임관리").child("모임명").child(getGroupName).child("회원정보").child("멤버정보").child(auth.getCurrentUser().getUid()).setValue(kryptoDTO).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            database.getReference().child("관리자").child("모임관리").child("모임명").child(getGroupName).child("모임정보").child("프로필").addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    kryptoDTO=null;
                                                    for(DataSnapshot getGroupInfo : dataSnapshot.getChildren()){
                                                        kryptoDTO = dataSnapshot.getValue(KryptoDTO.class);
                                                    }
                                                    database.getReference().child("관리자").child("회원관리").child("회원정보").child(auth.getCurrentUser().getUid()).child("나의모임관리").child(getGroupName).setValue(kryptoDTO).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
                                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                            intent.putExtra("Groupname", getGroupName);
                                                            context.startActivity(intent);
                                                            Toast.makeText(context, getGroupName + "에 가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                                            return;
                                                        }
                                                    });
                                                    return;
                                                }/////////////////

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {
                                                    Toast.makeText(context, "서버가 불안정합니다. 잠시후 다시 시도해주세요!", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    });
                                } else {///////////유저네임이 없는경우
                                    Toast.makeText(context, "프로필을 먼저 설정해주세요!", Toast.LENGTH_SHORT).show();
                                    /*Intent intent = new Intent(context.getApplicationContext(), MyPage_Profile_Activity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.putExtra("Groupname", getGroupName);
                                    context.startActivity(intent);*/
                                    return;
                                }
                            }/////////////////////////////////////////////////////////////////////////////////

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(context, "서버가 불안정합니다. 잠시후 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }else{/////////////////////////////한명도 가입을 하지않은상태 최초에 방을만드는사람이 한명있으니 거의 else 로 올일은 없음
                    Toast.makeText(context,"한명도 가입을 안했네", Toast.LENGTH_SHORT).show();
                }/////////else


            }//////////////////////////////////////

            @Override
            public void onCancelled(DatabaseError databaseError) { Toast.makeText(context, "서버가 불안정합니다. 잠시후 다시 시도해주세요", Toast.LENGTH_SHORT).show(); }//////////////
        });
    }///////////////////////////////////////////////////JoiningUser


    //////////자신이 가입한 모임의 데이터를 가지고와서 뿌려주기
    public void getjoiningGroup(final Context applicationContext, final RecyclerView myHome_recycleView,final TextView MyGroup_Count) {
        database.getReference().child("관리자").child("회원관리").child("회원정보").child(auth.getCurrentUser().getUid()).child("나의모임관리").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {//////////내가가입한 모임검색
                ListKey.clear();
                ListValue.clear();
                kryptoDTO= null;
                for(DataSnapshot getMyGroup : dataSnapshot.getChildren()){//////////키와데이터를 가지고옴
                    ListKey.add(getMyGroup.getKey());
                    ListValue.add(getMyGroup.getValue(KryptoDTO.class));
                }
                MyGroup_Count.setText(ListValue.size()+"개");
                layoutmanager = new LinearLayoutManager(applicationContext.getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                MyHome_adapter = new MyHome_Group_ListAdapter(applicationContext,ListValue);
                myHome_recycleView.setLayoutManager(layoutmanager);
                myHome_recycleView.setAdapter(MyHome_adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(applicationContext, "서버가 불안정합니다. 잠시후 다시 시도해주세요", Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void GetMainData(final Main_Fragment.OngetMaindataListener getMaindata, final String groupName) {

        database.getReference().child("관리자").child("모임관리").child("모임명").child(groupName).child("모임정보").child("회비내역").child("총잔고").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                kryptoDTO = null;
                ListValue.clear();
                for (DataSnapshot getdata : dataSnapshot.getChildren()){
                    kryptoDTO = dataSnapshot.getValue(KryptoDTO.class);
                    System.out.println("GetMainData DAO");
                 /*   getMaindata.onGetData(kryptoDTO.getGroup_Total_Dues());
                    main_fragment.setOngetMaindataListener(getMaindata);*/
                    getGroup_Total_Dues = kryptoDTO.getGroup_Total_Dues();
                    database.getReference().child("관리자").child("모임관리").child("모임명").child(groupName).child("모임정보").child("회비내역").child("월별입금").child("05월").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            kryptoDTO = null;
                            ListKey.clear();
                            for(DataSnapshot getdata: dataSnapshot.getChildren()){
                                ListKey.add(getdata.getKey());
                                kryptoDTO = getdata.getValue(KryptoDTO.class);
                            }
                            System.out.println(kryptoDTO.getGroup_Month_Depoit()+"????????");
                            depoit = kryptoDTO.getGroup_Month_Depoit();
                            database.getReference().child("관리자").child("모임관리").child("모임명").child(groupName).child("모임정보").child("회비내역").child("월별출금").child("05월").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    kryptoDTO = null;
                                    ListKey.clear();
                                    for(DataSnapshot getdata: dataSnapshot.getChildren()){
                                        ListKey.add(getdata.getKey());
                                        kryptoDTO = getdata.getValue(KryptoDTO.class);
                                    }
                                    withdraw = kryptoDTO.getGroup_Month_Withdraw();
                                    if(kryptoDTO != null) {
                                        getMaindata.onGetData(getGroup_Total_Dues, depoit,withdraw);
                                        main_fragment.setOngetMaindataListener(getMaindata);
                                    }else{
                                        System.out.println("DTO가 널입니다.");
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
      /*  System.out.println("kryptoDTO.getGroup_Total_Dues()"+kryptoDTO.getGroup_Total_Dues());
        getMaindata.onGetData(kryptoDTO.getGroup_Total_Dues());
        main_fragment.setOngetMaindataListener(getMaindata);*/
    }
}///////////////////KryptoDAO
