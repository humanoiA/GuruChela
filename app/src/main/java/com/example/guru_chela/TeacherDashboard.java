package com.example.guru_chela;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TeacherDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {
    String[] dept= { "CSE", "IT", "ECE", "CE", "EE","ME","AEIE","BBA","BHM","BBM"};
    private  String TAG ="" ;
    private DrawerLayout drawer;
    private TabLayout tablyout;
    TeacherPageAdap pageadap;
    TabItem tablogin;
    Spinner spin;
   public TextView uname;
    TabItem tabreg;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toast.makeText(this,"Dashboard",Toast.LENGTH_LONG).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);
        tablyout=findViewById(R.id.tablay);
        // Log.d(TAG, "onCreate: "+tablyout.getTabCount());
        viewPager=findViewById(R.id.viewPage);
        tablogin=findViewById(R.id.login);
        tabreg=findViewById(R.id.reg);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tutor's Hub");
        changeToolbarFont(toolbar,this);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        pageadap=new TeacherPageAdap(getSupportFragmentManager(),tablyout.getTabCount());
        viewPager.setAdapter(pageadap);
        tablyout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==1){
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                        getWindow().setStatusBarColor(ContextCompat.getColor(TeacherDashboard.this,R.color.colorPrimary));
                    }
                }
                if(tab.getPosition()==2){
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                        getWindow().setStatusBarColor(ContextCompat.getColor(TeacherDashboard.this,R.color.colorPrimary));
                    }
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablyout));
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        View headerView = navigationView.getHeaderView(0);
        uname=headerView.findViewById(R.id.UserName);
     //   uname.setText("Welcome, "+this.getSharedPreferences(TeacherDashboardFrag.MyPREF, Context.MODE_PRIVATE).getString("UserName","IDK"));

    }
    @Override
    protected void onResume()
    {
        super.onResume();
    //    uname.setText("Welcome, "+this.getSharedPreferences(TeacherDashboardFrag.MyPREF, Context.MODE_PRIVATE).getString("UserName","IDK"));

    }
    public static void changeToolbarFont(Toolbar toolbar, Activity context) {
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View view = toolbar.getChildAt(i);
            if (view instanceof TextView) {
                TextView tv = (TextView) view;
                if (tv.getText().equals(toolbar.getTitle())) {
                    applyFont(tv, context);
                    break;
                }
            }
        }
    }

    public static void applyFont(TextView tv, Activity context) {
        tv.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Sofia-Regular.ttf"));
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.link:
                // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_comtainer, new bookings()).commit();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://smudfe.co.in"));
                startActivity(browserIntent);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.exit:
                // Log.d("debug","activity: action home has clicked");
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                finish();
                System.exit(0);
                break;
            case R.id.notify:
                Toast.makeText(this, "No Notifications", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
