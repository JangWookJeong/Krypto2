package com.codename.krypto;

public class KryptoDTO {
    /////////////////회원가입DTO
    private String Username ;//유저이름
    private String BackGround_UrI;///유저 배경이미지
    private String Profile_Url ;///유저 프로필이미지
    private String BirthDay;//유저 생일
    private String NickName;//유저 닉네임
    private String Gender;////유저 성별

    /////////////////그룹만들기 DTO

    private String Create_Group_Name;//그룹이름
    private String Create_Group_Content;////그룹소개
    private String Create_GroupProfile_image;///그룹프로필
    private String Group_Password;//그룹비밀번호
    private String Group_create_Time;///그룹만든시간

    ///////////////////////////////////////////////////////////////////////////유저회원가입DTO
    private String UserUID;///////////모임가입설정할 변수
    ///////////////////////////////////////////////////////////////////////////모임 입출금 및 리스트  내역

    private String Group_Total_Dues;//회비총잔고
    private String Group_Depoit;///입금리스트
    private String Group_Withdraw;//출금리스트
    private String Group_Month_Total_Depoit;//월별 토탈 입금
    private String Group_Month_Total_Withdraw;//월별 토탈 출금
    private String Group_Month_Depoit;//월별입금
    private String Group_Month_Withdraw;//월별 출금




    ///////////////////////////////////////////////////////////////////////////


    public String getGroup_Total_Dues() {
        return Group_Total_Dues;
    }

    public void setGroup_Total_Dues(String group_Total_Dues) {
        Group_Total_Dues = group_Total_Dues;
    }

    public String getGroup_Depoit() {
        return Group_Depoit;
    }

    public void setGroup_Depoit(String group_Depoit) {
        Group_Depoit = group_Depoit;
    }

    public String getGroup_Withdraw() {
        return Group_Withdraw;
    }

    public void setGroup_Withdraw(String group_Withdraw) {
        Group_Withdraw = group_Withdraw;
    }

    public String getGroup_Month_Total_Depoit() {
        return Group_Month_Total_Depoit;
    }

    public void setGroup_Month_Total_Depoit(String group_Month_Total_Depoit) {
        Group_Month_Total_Depoit = group_Month_Total_Depoit;
    }

    public String getGroup_Month_Total_Withdraw() {
        return Group_Month_Total_Withdraw;
    }

    public void setGroup_Month_Total_Withdraw(String group_Month_Total_Withdraw) {
        Group_Month_Total_Withdraw = group_Month_Total_Withdraw;
    }

    public String getGroup_Month_Depoit() {
        return Group_Month_Depoit;
    }

    public void setGroup_Month_Depoit(String group_Month_Depoit) {
        Group_Month_Depoit = group_Month_Depoit;
    }

    public String getGroup_Month_Withdraw() {
        return Group_Month_Withdraw;
    }

    public void setGroup_Month_Withdraw(String group_Month_Withdraw) {
        Group_Month_Withdraw = group_Month_Withdraw;
    }

    public String getBirthDay() {
        return BirthDay;
    }

    public void setBirthDay(String birthDay) {
        BirthDay = birthDay;
    }

    public String getUserUID() {
        return UserUID;
    }

    public void setUserUID(String userUID) {
        UserUID = userUID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getProfile_Url() {
        return Profile_Url;
    }

    public void setProfile_Url(String profile_Url) {
        Profile_Url = profile_Url;
    }

///////////////////////////////////////////////////////////////////////////////////////////Create Group DTO
    public String getCreate_Group_Name() {
        return Create_Group_Name;
    }

    public void setCreate_Group_Name(String create_Group_Name) {
        Create_Group_Name = create_Group_Name;
    }

    public String getCreate_Group_Content() {
        return Create_Group_Content;
    }

    public void setCreate_Group_Content(String create_Group_Content) {
        Create_Group_Content = create_Group_Content;
    }

    public String getCreate_GroupProfile_image() {
        return Create_GroupProfile_image;
    }

    public void setCreate_GroupProfile_image(String create_GroupProfile_image) {
        Create_GroupProfile_image = create_GroupProfile_image;
    }

    public String getGroup_Password() {
        return Group_Password;
    }

    public void setGroup_Password(String group_Password) {
        Group_Password = group_Password;
    }

    public String getGroup_create_Time() {
        return Group_create_Time;
    }

    public void setGroup_create_Time(String group_create_Time) {
        Group_create_Time = group_create_Time;
    }

    public String getBackGround_UrI() {
        return BackGround_UrI;
    }

    public void setBackGround_UrI(String backGround_UrI) {
        BackGround_UrI = backGround_UrI;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////



}
