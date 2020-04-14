package com.example.guru_chela;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {

Button Student;
EditText email;
EditText pass;
Button Teacher;
public static String mail;
public static String passw;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String MyPREFERENCES = "MyPrefs" ;
    private static BlankFragment mInstance= null;

    public int someValueIWantToKeep;

    public static synchronized BlankFragment getInstance() {
        if(null == mInstance){
            mInstance = new BlankFragment();
        }
        return mInstance;
    }
    public BlankFragment() {
        // Required empty public constructor
    }
    public String getValue(String code) {
        return sharedPreferences.getString("EMAIL", "");
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        View v = inflater.inflate(R.layout.fragment_blank, container, false);
        ButterKnife.bind(this,v);
        Student= v.findViewById(R.id.buttonStudent);
        email=v.findViewById(R.id.teachermail);
        pass=v.findViewById(R.id.teacherpass);
       // passw= String.valueOf(pass.getText());
        Student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mail= email.getText().toString();
                sharedPreferences=getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                editor=sharedPreferences.edit();
                editor.putString("EMAIL",mail);
                editor.apply();
                Log.d("PREFS", "onCreateView: "+getContext().getSharedPreferences(MyPREFERENCES,0).getString("EMAIL","IDK"));
                Log.d("EDtext", "onCreateView: "+mail);
                Intent intent = new Intent(getActivity(),StudentView.class);
                (getActivity()).startActivity(intent);
            }
        });
        Teacher= v.findViewById(R.id.buttonTeacherlogin);
        Teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mail= email.getText().toString();
                sharedPreferences=getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                editor=sharedPreferences.edit();
                editor.putString("EMAIL",mail);
                editor.apply();
                Log.d("PREFS", "onCreateView: "+getContext().getSharedPreferences(MyPREFERENCES,0).getString("EMAIL","IDK"));
                /*try {
                   //Log.d("METHOD",getJSONObjectFromURL("https://guruchela.herokuapp.com/api/all" ));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            //Your code goes here
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                if (isNetworkAvailable(getContext())) {
                    try {
                        Log.d("METHOD", getJSONObjectFromURL("https://guruchela.herokuapp.com/api/" + getContext().getSharedPreferences(MyPREFERENCES, 0).getString("EMAIL", "IDK")));
                        if (getJSONObjectFromURL("https://guruchela.herokuapp.com/api/" + getContext().getSharedPreferences(MyPREFERENCES, 0).getString("EMAIL", "IDK")).length() > 10) {

                            Intent intent2 = new Intent(getActivity(), TeacherDashboard.class);
                            (getActivity()).startActivity(intent2);
                        } else {
                            Toast.makeText(getContext(), "Bad Credentials", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.d("err", String.valueOf(e));
                    }
                } else {
                    Toast.makeText(getContext(), "Check Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

}