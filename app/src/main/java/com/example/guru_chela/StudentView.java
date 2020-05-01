package com.example.guru_chela;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class StudentView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private TabLayout tablyout;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view);
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
        navigationView.getMenu().getItem(0).setChecked(false);
        downloadJSON("https://guruchela.herokuapp.com/api/all");

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.link:
                // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_comtainer, new bookings()).commit();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://smudfe.co.in"));
                startActivity(browserIntent);
                break;
            case R.id.home:
                finish();
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
            //    android.os.Process.killProcess(android.os.Process.myPid());
               // android.os.Process.sendSignal(android.os.Process.myPid(), android.os.Process.SIGNAL_KILL);
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                finish();
                System.exit(0);
                break;
                //return false;
            case R.id.notify:
                Toast.makeText(this, "No Notifications", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    class MyAdapter extends ArrayAdapter<String>{
        Context context;
        String SubName[];
        String Prerequi[];
        String Currency[];
        String venuee[];
        String timee[];
        String tnamee[];
        String temaill[];
        String tdepte[];
        String tgene[];
        MyAdapter(Context c,String subname[],String prerequi[],String Venue[],String Currency[],String Time[],String Tname[],String Tmail[],String Tdept[],String Tgen[]){
            super(c,R.layout.listrow,R.id.sub_name,subname);
            this.context=c;
            this.SubName=subname;
            this.Prerequi=prerequi;
            this.Currency=Currency;
            this.venuee=Venue;
            this.timee=Time;
            this.tnamee=Tname;
            this.temaill=Tmail;
            this.tdepte=Tdept;
            this.tgene=Tgen;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layinflater= (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=layinflater.inflate(R.layout.listrow,parent,false);
            TextView subname=row.findViewById(R.id.sub_name);
            TextView prereq=row.findViewById(R.id.prerequi);
            TextView venue=row.findViewById(R.id.venue);
            TextView curren=row.findViewById(R.id.currency);
            TextView time=row.findViewById(R.id.time);
            TextView tname=row.findViewById(R.id.tname);
            TextView tmail=row.findViewById(R.id.tcontact);
            TextView tdept=row.findViewById(R.id.tdept);
            TextView tgen=row.findViewById(R.id.tgen);
            subname.setText("Subject: "+SubName[position]);
            prereq.setText("Pre-Requisite: "+Prerequi[position]);
            venue.setText("Currency: "+Currency[position]);
            curren.setText("Venue: "+venuee[position]);
            time.setText("Time: "+timee[position]);
            tname.setText("Teacher's Name: "+tnamee[position]);
            tmail.setText("Teacher's Email: "+temaill[position]);
            tdept.setText("Teacher's Department: "+tdepte[position]);
            tgen.setText("Teacher's Gender: "+tgene[position]);
            return row;
        }
    }

    private void downloadJSON(final String urlWebService) {

        class DownloadJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
               // Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView(s);
                    Log.d("post", "onPostExecute: invokedPost");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        DownloadJSON getJSON = new DownloadJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        JSONArray Jarray = object.getJSONArray("a");
        String[] stocks = new String[Jarray.length()];
        for (int i = 0; i < Jarray.length(); i++) {
            // JSONObject obj = jsonArray.getJSONObject(0);
            stocks[i] = Jarray.getString(i);
        }
        String fin[][]=new String[9][Jarray.length()];
        for (int i = 0; i < Jarray.length(); i++) {
            // JSONObject obj = jsonArray.getJSONObject(0);
            //stocks[i] = Jarray.getString(i);
            String o[]=stocks[i].split(",");
            fin[0][i]=o[0].replace("[","").replace("\"","");
            fin[1][i]=o[1].replace("\"","");
            fin[2][i]=o[2].replace("\"","");
            fin[3][i]=o[3];
            fin[4][i]=o[6].replace("\"","")+o[7].replace("\"","").replace("]","");
            fin[5][i]=o[9].replace("\"","");
            fin[6][i]=o[10].replace("\"","");
            fin[7][i]=o[12].replace("\"","");
            fin[8][i]=o[13].replace("\"","").replace("]","");
            //fin[i][5]=o[5];

        }
        //  Log.d("----", "loadIntoListView: invoked");
      //  ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fin[1]);
        listView =  findViewById(R.id.listView);
        MyAdapter adap=new MyAdapter(this,fin[0],fin[1],fin[2],fin[3],fin[4],fin[5],fin[6],fin[7],fin[8]);
        listView.setAdapter(adap);
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

}