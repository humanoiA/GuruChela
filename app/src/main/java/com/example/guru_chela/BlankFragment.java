package com.example.guru_chela;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
                Intent intent2 = new Intent(getActivity(),TeacherDashboard.class);
                (getActivity()).startActivity(intent2);
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

}
