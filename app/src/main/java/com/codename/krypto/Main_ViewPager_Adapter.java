package com.codename.krypto;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.TimePicker;

public class Main_ViewPager_Adapter extends FragmentStatePagerAdapter {
    public static final int VIEWPAGER_NUMBER = 4;
    String GroupName;
    String Time;
    /*메인 탭을 뷰페이저로 연결하는 어댑터*/
    public Main_ViewPager_Adapter(FragmentManager fm) {

        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return Main_Fragment.newInstance();
            case 1:
                return Main_Fragment.newInstance();
            case 2:
                return Main_Fragment.newInstance();
            case 3:
                return Main_Fragment.newInstance();
            default:
                return null;

        }
    }

    public void addData(String group,String name){

    }


    @Override
    public int getCount() {
        return VIEWPAGER_NUMBER;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "메인";
            case 1:
                return "동영상";
            case 2:
                return "사진";
            case 3:
                return "입출금내역";
            default:
                return null;
        }
    }




}//////////////////////Main_ViewPager_Adapter
