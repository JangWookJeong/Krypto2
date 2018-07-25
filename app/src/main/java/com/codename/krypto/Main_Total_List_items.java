package com.codename.krypto;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

/*
    부분화면을 만들어서 저장
*/
public class Main_Total_List_items extends LinearLayout {
    private CircleImageView List_Profile_Image;
    private int getProfile_Image_Url;
    private String List_Item_Name, List_Item_Date, List_item_Depoit, List_Item_PersonerTotal_Money;
    private TextView List_Item_Name1, List_Item_Date1, List_item_Depoit1, List_Item_PersonerTotal_Money1;
    String isWithdraw = "입금";

    public Main_Total_List_items(Context context) {
        super(context);

        init(context);
    }

    public Main_Total_List_items(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.total_list_items, this, true);

        List_Item_Name1 = findViewById(R.id.List_Item_Name);
        List_Item_Date1 = findViewById(R.id.List_Item_Date);
        List_item_Depoit1 = findViewById(R.id.List_item_Depoit);
        List_Item_PersonerTotal_Money1 = findViewById(R.id.List_Item_PersonerTotal_Money);
        List_Profile_Image = findViewById(R.id.List_Profile_Image);
    }

    public void setGetProfile_Image_Url(int getProfile_Image_Url) {
        List_Profile_Image.setImageResource(getProfile_Image_Url);

    }


    public void setList_Item_Name(String list_Item_Name) {
        List_Item_Name1.setText(list_Item_Name);

    }


    public void setList_Item_Date(String list_Item_Date) {
        List_Item_Date1.setText(list_Item_Date);
    }


    public void setList_item_Depoit(String list_item_Depoit) {

        List_item_Depoit1.setText(isWithdraw + " :" + list_item_Depoit);

    }

    public void setList_Item_PersonerTotal_Money(String list_Item_PersonerTotal_Money) {
        List_Item_PersonerTotal_Money1.setText("잔 액 :" + list_Item_PersonerTotal_Money);
    }
}
