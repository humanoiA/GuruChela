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
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;

import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment2 extends Fragment implements AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {
    String[] dept= { "CSE", "IT", "ECE", "CE", "EE","ME","AEIE","BBA","BHM","BBM"};
    Spinner spin;
    RadioButton male;
    RadioButton female;

    public BlankFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_blank_fragment2, container, false);
        ButterKnife.bind(this,v);
        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      //  view.getRootView();
        spin=view.findViewById(R.id.spin);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter aa =  new ArrayAdapter(view.getContext(),R.layout.support_simple_spinner_dropdown_item,dept);
        aa.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        male=view.findViewById(R.id.male);
        female=view.findViewById(R.id.female);
        //male.setOnCheckedChangeListener(view.getContext());
        male.setOnCheckedChangeListener(this);
        female.setOnCheckedChangeListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            if (compoundButton.getId() == R.id.male) {
                female.setChecked(false);
                //r3.setChecked(false);
            }
            if (compoundButton.getId() == R.id.female) {
                male.setChecked(false);
               // r3.setChecked(false);
            }
            /*if (buttonView.getId() == R.id.rB3) {
                r1.setChecked(false);
                r2.setChecked(false);
            }*/
        }
    }
    }
