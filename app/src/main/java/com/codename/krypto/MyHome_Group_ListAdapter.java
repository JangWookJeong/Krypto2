package com.codename.krypto;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyHome_Group_ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<KryptoDTO> items ;
    Context context;
    KryptoDAO kryptoDAO;
    MainMyGroupHome home;

    public MyHome_Group_ListAdapter(Context getcontext ,List<KryptoDTO> item){
        this.context = getcontext;
        this.items = item;
        kryptoDAO= new KryptoDAO();
        home = new MainMyGroupHome();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {/////////뷰를 정의하는곳
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_group_mygroupview_item,parent,false);
        return new RowCell(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {////////////기능구현
        Glide.with(context.getApplicationContext()).load(items.get(position).getCreate_GroupProfile_image()).into(((RowCell)holder).MyHome_Group_Item_TopImage);
        ((RowCell)holder).MyHome_Group_Item_GroupName.setText(items.get(position).getCreate_Group_Name());
        ((RowCell)holder).MyHome_Group_Item_GroupContent.setText(items.get(position).getCreate_Group_Content());

        ((RowCell)holder).Home_Group_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Group_Name",items.get(position).getCreate_Group_Name());
                context.startActivity(intent);
            }
        });

    }//////////////////////////

    @Override
    public int getItemCount() {///////////카운트
        return items.size();
    }

    private class RowCell extends RecyclerView.ViewHolder {
        ImageView MyHome_Group_Item_TopImage,MyHome_Group_Item_BottomImage1,MyHome_Group_Item_BottomImage2,MyHome_Group_Item_BottomImage3;
        TextView MyHome_Group_Item_GroupName,MyHome_Group_Item_GroupContent;
        RelativeLayout Home_Group_layout;
        public RowCell(View view) {
            super(view);
            Home_Group_layout = view.findViewById(R.id.Home_Group_layout);
            MyHome_Group_Item_TopImage = view.findViewById(R.id.MyHome_Group_Item_TopImage);
            MyHome_Group_Item_GroupName = view.findViewById(R.id.MyHome_Group_Item_GroupName);
            MyHome_Group_Item_GroupContent = view.findViewById(R.id.MyHome_Group_Item_GroupContent);
            MyHome_Group_Item_BottomImage1 = view.findViewById(R.id.MyHome_Group_Item_BottomImage1);
            MyHome_Group_Item_BottomImage2 = view.findViewById(R.id.MyHome_Group_Item_BottomImage2);
            MyHome_Group_Item_BottomImage3 = view.findViewById(R.id.MyHome_Group_Item_BottomImage3);








        }

    }
}/////////////////////////////////////////////////////////////////////////////////////////
