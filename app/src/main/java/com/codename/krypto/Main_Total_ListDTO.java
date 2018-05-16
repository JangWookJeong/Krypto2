package com.codename.krypto;

import android.widget.TextView;

public class Main_Total_ListDTO {
    private int List_Profile_Image;
    private String List_Item_Name,List_Item_Date,List_item_Depoit,List_Item_PersonerTotal_Money;
    //기본생성자
    public Main_Total_ListDTO(){}
    //인자생성자

    public Main_Total_ListDTO(int list_Profile_Image, String list_Item_Name, String list_Item_Date, String list_item_Depoit, String list_Item_PersonerTotal_Money) {
        List_Profile_Image = list_Profile_Image;
        List_Item_Name = list_Item_Name;
        List_Item_Date = list_Item_Date;
        List_item_Depoit = list_item_Depoit;
        List_Item_PersonerTotal_Money = list_Item_PersonerTotal_Money;
    }

    public int getList_Profile_Image() {
        return List_Profile_Image;
    }

     public String getList_Item_Name() {
        return List_Item_Name;
    }

    public String getList_Item_Date() {
        return List_Item_Date;
    }

    public String getList_item_Depoit() {
        return List_item_Depoit;
    }

    public String getList_Item_PersonerTotal_Money() {
        return List_Item_PersonerTotal_Money;
    }

    public void setList_Item_PersonerTotal_Money(String list_Item_PersonerTotal_Money) {
        List_Item_PersonerTotal_Money = list_Item_PersonerTotal_Money;
    }
}
