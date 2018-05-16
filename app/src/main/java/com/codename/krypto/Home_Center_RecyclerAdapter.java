package com.codename.krypto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public class Home_Center_RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<KryptoDTO> items ;
    private Context Context;
    private EditText InputPassword;
    private String UserInputPassword,getGroupPassword,getGroupName;
    private AlertDialog.Builder Alert;
    ViewGroup  parentViewGroup;
    InputMethodManager immhide;
    KryptoDAO kryptoDAO;


    public Home_Center_RecyclerAdapter(List<KryptoDTO> item, final Context context, AlertDialog.Builder alert){
        this.Context = context;
        this.items = item;
        this.Alert = alert;
        kryptoDAO = new KryptoDAO();
        InputPassword = new EditText(context);
        InputPassword.setTextColor(Color.BLACK);
        InputPassword.setTransformationMethod(new PasswordTransformationMethod());
       /* InputPassword.setFocusable(true);*/
        InputPassword.requestFocus();
        Alert.setMessage("모임가입을 하시겠습니까?\r\n" +
                "\r\n비밀번호를 입력해주세요");
        Alert.setView(InputPassword);

        Alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                View view = InputPassword;
                parentViewGroup= (ViewGroup)view.getParent();
                parentViewGroup.removeView(InputPassword);
                CloseKeypad(false);
                return;
            }
        });///////////////////////setPositiveButton
        Alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {//////////////유저가 확인버튼을 눌럿을경우
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.out.println("그룹비밀번호 :"+getGroupPassword+" 입력한 비밀번호"+InputPassword.getText());
                if(InputPassword.length() != 0) {//////////유저가 입력한비밀번호가 0이 아닐경우
                    if (getGroupPassword.equals(InputPassword.getText().toString())) {
                        kryptoDAO.JoiningUser(Context,getGroupName);
                        /*((Home_Group_ListActivity)Context).finish();*/
                    } else {//////////유저가 입력한 비밀번호가 일치하지 않을경우
                        Toast.makeText(Context, "비밀번호가 일치하지않습니다..", Toast.LENGTH_SHORT).show();
                    }
                }else{//////////////아무것도 입력하지 않았을경우
                    Toast.makeText(Context, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                InputPassword.setText("");
                //자동키패드 닫기

                CloseKeypad(false);
                View view = InputPassword;
                parentViewGroup= (ViewGroup)view.getParent();
                parentViewGroup.removeView(InputPassword);
            }
        });///////////////////////setPositiveButton
    }///////////////////////////////////////생성자

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //xml아이템을 정의
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_group_search_item,parent,false);
        System.out.println("로그체크 : 여기는 xml아이템을 정의 : onCreateViewHolder 안입니다.");
        return new RowCell(view);
    }///////////////////////////////////////////

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {////////////데이터를 넣는 부분
        System.out.println("로그체크 : 여기는 데이터를 넣는 부분 정의 : onBindViewHolder 안입니다.");
        ((RowCell)holder).Home_Group_Item_GroupName.setText(items.get(position).getCreate_Group_Name());
        ((RowCell)holder).Home_Group_Item_GroupContent.setText(items.get(position).getCreate_Group_Content());
        Glide.with(Context.getApplicationContext()).load(items.get(position).getCreate_GroupProfile_image()).into(((RowCell)holder).Home_Group_Item_TopImage);

        ((RowCell)holder).Home_Group_Joining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = Alert.create();
                dialog.show();
                LayoutClicked(items.get(position).getGroup_Password(),items.get(position).getCreate_Group_Name());
                CloseKeypad(true);
            }
        });

    }//////////////onBind

    public void LayoutClicked(String GroupPassword, String GroupName){/////////그룹비밀번호 및 그룹명을 를 받아서 초기화
        this.getGroupPassword = GroupPassword;
        this.getGroupName = GroupName;
    }/////////////////////////////////////////

    @Override
    public int getItemCount() {/////////////// 아템개수 카운트
        System.out.println("로그체크 : 아템개수 카운트 : getItemCount 안입니다. 아이템 사이즈는"+ items.size());
        return items.size();
    }

    private class RowCell extends RecyclerView.ViewHolder {
        ImageView Home_Group_Item_TopImage;
        TextView Home_Group_Item_GroupName,Home_Group_Item_GroupContent;
        LinearLayout Home_Move_Group;
        Button Home_Group_Joining;

        public RowCell(View view) {

            super(view);
            Home_Group_Item_TopImage = view.findViewById(R.id.Home_Group_Item_TopImage);
            GradientDrawable drawable=
                    (GradientDrawable)Context.getDrawable(R.drawable.image_round);
            Home_Group_Item_TopImage.setBackground(drawable);
            Home_Group_Item_TopImage.setClipToOutline(true);
            Home_Group_Item_GroupName = view.findViewById(R.id.Home_Group_Item_GroupName);
            Home_Group_Item_GroupContent = view.findViewById(R.id.Home_Group_Item_GroupContent);
            Home_Move_Group = view.findViewById(R.id.Home_Move_Group);
            Home_Group_Joining =view.findViewById(R.id.Home_Group_Joining);
            System.out.println("로그체크 : create하는중 어댑터에서 : RowCell 안입니다.");


        }
    }

    public void CloseKeypad(boolean check){

        if(check){//자동키패드 뜨게하기
            InputMethodManager imm = (InputMethodManager) Context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }else{//자동키패드 닫기
            immhide = (InputMethodManager) Context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }

    }
}////////////////////////////////////
