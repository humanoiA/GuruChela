package com.example.guru_chela;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;
public class PageAdap extends FragmentPagerAdapter {
    private int tabnum;

    public PageAdap(FragmentManager fm,int tabnum) {
        super(fm);
        this.tabnum=tabnum;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new BlankFragment();
            case 1:
                return new BlankFragment2();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabnum;
    }
}