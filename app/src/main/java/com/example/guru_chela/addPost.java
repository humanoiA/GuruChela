package com.example.guru_chela;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class addPost extends Fragment implements AdapterView.OnItemSelectedListener {
    String[] dept = {"CSE", "IT", "ECE", "CE", "EE", "ME", "AEIE", "BBA", "BHM", "BBM"};
    Spinner spin;
    RadioButton male;
    RadioButton female;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String MyPREF = "Uid";

    public addPost() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.addpost_fragm, container, false);
        ButterKnife.bind(this, v);
        final TimePicker time = v.findViewById(R.id.time);
        final DatePicker date = v.findViewById(R.id.date);
        Button register = v.findViewById(R.id.button);
        final EditText subject = v.findViewById(R.id.subject);
        final EditText venue = v.findViewById(R.id.value);
        final EditText prerequisite = v.findViewById(R.id.prerequisite);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour = time.getHour();
                int min = time.getMinute();
                int month = date.getMonth() + 1;
                int d = date.getDayOfMonth();
                int year = date.getYear();
                String timestamp = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(d) + " " + String.valueOf(hour) + ":" + String.valueOf(min) + ":00";
                Log.d("TSTAMP", timestamp);
                downloadJSON("https://guruchela.herokuapp.com/api/" + String.valueOf(subject.getText()) + "/" + String.valueOf(prerequisite.getText()) + "/" + String.valueOf(venue.getText()) + "/" + String.valueOf(0) + "/" + String.valueOf(1) + "/" + Integer.parseInt(getContext().getSharedPreferences(TeacherDashboardFrag.MyPREF, Context.MODE_PRIVATE).getString("UserId", "2")) + "/" + String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(d) + "/" + String.valueOf(hour) + ":" + String.valueOf(min) + ":00");
                // Inflate the layout for this fragment
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //  view.getRootView();


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
                //Log.d("ERRR", s);
                Toast.makeText(getContext(), "Subject Added Successfully", Toast.LENGTH_SHORT).show();
                getActivity().finish();
                getActivity().overridePendingTransition(0, 0);
                startActivity(getActivity().getIntent());
                getActivity().overridePendingTransition(0, 0);
                try {

                    Log.d("post", "onPostExecute: invokedPost");
                } catch (Exception e) {
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
}