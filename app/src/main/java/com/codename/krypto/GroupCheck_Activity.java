package com.codename.krypto;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class GroupCheck_Activity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_group_check_,container,false);
        Button Group_Check_FindGroup = root.findViewById(R.id.Group_Check_FindGroup);
        Group_Check_FindGroup.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View view) {
                KryptoDAO kryptoDAO = new KryptoDAO();
             }
         });
        Button Group_Check_CreateGroup= root.findViewById(R.id.Group_Check_CreateGroup);

        return root;
    }

    public static GroupCheck_Activity newInstance() {

        Bundle args = new Bundle();

        GroupCheck_Activity fragment = new GroupCheck_Activity();
        fragment.setArguments(args);
        return fragment;
    }
}
