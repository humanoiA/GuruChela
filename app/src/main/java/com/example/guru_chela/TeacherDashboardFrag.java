package com.example.guru_chela;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherDashboardFrag extends Fragment {
    SharedPreferences sharedPref;
    SwipeRefreshLayout mSwipeRefreshLayout;
    SharedPreferences.Editor edit;
    public static final String MyPREF = "Uid";
    ListView listView;
    boolean flag = true;
    public TeacherDashboardFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.teacherdashboardposts, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeToRefresh);
        listView =  v.findViewById(R.id.listView);
        ButterKnife.bind(this,v);
       // BlankFragment.getInstance();
        Toast.makeText(getContext(), getContext().getSharedPreferences(BlankFragment.MyPREFERENCES,Context.MODE_PRIVATE).getString("EMAIL",""),Toast.LENGTH_LONG).show();
    //   Log.d("oo","https://guruchela.herokuapp.com/"+getContext().getSharedPreferences(BlankFragment.MyPREFERENCES,Context.MODE_PRIVATE).getString("EMAIL",""))
;
        try {
            String s = getJSONObjectFromURL("https://guruchela.herokuapp.com/api/search/" + getContext().getSharedPreferences(BlankFragment.MyPREFERENCES, Context.MODE_PRIVATE).getString("EMAIL", "IDK"));
            if (s.indexOf('1') == -1) {
                Toast.makeText(getContext(), "USER DOES NOT EXIST", Toast.LENGTH_LONG).show();
                flag = false;
            }
        } catch (Exception e) {
            Log.d("try", String.valueOf(e));
        }
        if (flag) {
            downloadJSON("https://guruchela.herokuapp.com/api/" + getContext().getSharedPreferences(BlankFragment.MyPREFERENCES, Context.MODE_PRIVATE).getString("EMAIL", ""));
        } else {
            getActivity().finish();
        }
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2500);
            }
        });
        // Inflate the layout for this fragment
        return v;
    }
    class MyAdapter extends ArrayAdapter<String> {
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
            LayoutInflater layinflater= (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            venue.setText("Charge Amount(in ₹): " + Currency[position]);
            curren.setText("Venue: "+venuee[position]);
            time.setText("Time: "+timee[position]);
            tname.setText("Teacher's Name: "+tnamee[position]);
            tmail.setText("Teacher's Email: "+temaill[position]);
            tdept.setText("Teacher's Department: "+tdepte[position]);
            tgen.setText("Teacher's Gender: "+tgene[position]);
            return row;
        }
    }

    public static String getJSONObjectFromURL(String urlString) throws IOException, JSONException {
        HttpURLConnection urlConnection = null;
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */);
        urlConnection.setConnectTimeout(15000 /* milliseconds */);
        urlConnection.setDoOutput(true);
        urlConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();

        String jsonString = sb.toString();
        System.out.println("JSON: " + jsonString);

        return jsonString;
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
                Log.d("ERRR",s);
                 Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
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
        JSONArray Jarray = object.getJSONArray("ALPHA");
        String[] stocks = new String[Jarray.length()];
        for (int i = 0; i < Jarray.length(); i++) {
            // JSONObject obj = jsonArray.getJSONObject(0);
            stocks[i] = Jarray.getString(i);
        }
        String fin[][]=new String[9][Jarray.length()];
        String Uid = "";
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
            Uid = o[14].replace("\"", "").replace("]", "");
            //fin[i][5]=o[5];

        }
        //  Log.d("----", "loadIntoListView: invoked");
        //  ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fin[1]);
        MyAdapter adap=new MyAdapter(getContext(),fin[0],fin[1],fin[2],fin[3],fin[4],fin[5],fin[6],fin[7],fin[8]);
        sharedPref = getContext().getSharedPreferences(MyPREF, Context.MODE_PRIVATE);
        edit = sharedPref.edit();
        edit.putString("UserId", Uid);
        edit.apply();
        final String name= fin[5][0];
        final TextView loadText = getActivity().findViewById(R.id.UserName);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadText.setText("Welcome, "+name);
            }
        });

        listView.setAdapter(adap);
    }

}
