package com.codename.krypto;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class Main_Total_List_Adapter extends BaseAdapter {
    ArrayList<Main_Total_ListDTO> items = new ArrayList<Main_Total_ListDTO>();
    Context context;
    Main_Total_List_items view;
    @Override
    public int getCount() {
        return items.size();
    }
    public void additems(Main_Total_ListDTO dto, Context context){
        items.add(dto);
        this.context = context;
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup viewGroup) {
        if(convertview != null) {
            view = (Main_Total_List_items)convertview;
        }else {
            view = new Main_Total_List_items(context);
        }
        Main_Total_ListDTO Main_List_DTO = items.get(position);

        view.setGetProfile_Image_Url(Main_List_DTO.getList_Profile_Image());
        view.setList_Item_Name(Main_List_DTO.getList_Item_Name());
        view.setList_Item_Date(Main_List_DTO.getList_Item_Date());
        view.setList_item_Depoit(Main_List_DTO.getList_item_Depoit());
        view.setList_Item_PersonerTotal_Money(Main_List_DTO.getList_Item_PersonerTotal_Money());

        return view;
    }
}
