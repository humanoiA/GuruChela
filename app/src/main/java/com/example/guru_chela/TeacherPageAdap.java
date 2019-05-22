package com.example.guru_chela;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;
public class TeacherPageAdap extends FragmentPagerAdapter {
    private int tabnum;

    public TeacherPageAdap(FragmentManager fm,int tabnum) {
        super(fm);
        this.tabnum=tabnum;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new TeacherDashboardFrag();
            case 1:
                return new addPost();
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return tabnum;
    }
}