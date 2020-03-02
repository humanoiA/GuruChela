package com.example.guru_chela;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class addPost extends Fragment implements AdapterView.OnItemSelectedListener {
    String[] dept= { "CSE", "IT", "ECE", "CE", "EE","ME","AEIE","BBA","BHM","BBM"};
    Spinner spin;
    RadioButton male;
    RadioButton female;

    public addPost() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.addpost_fragm, container, false);
        ButterKnife.bind(this,v);
        final TimePicker time = v.findViewById(R.id.time);
        final DatePicker date=v.findViewById(R.id.date);
        Button register = v.findViewById(R.id.button);
        EditText subject=v.findViewById(R.id.subject);
        EditText venue =v.findViewById(R.id.venue);
        EditText prerequisite=v.findViewById(R.id.prerequisite);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour=time.getHour();
                int min=time.getMinute();
                int month=date.getMonth();
                int d=date.getDayOfMonth();
                int year=date.getYear();
                String timestamp=String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(d)+ " "+String.valueOf(hour)+":"+String.valueOf(min)+":00";

            }
        });
        // Inflate the layout for this fragment
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

    }
