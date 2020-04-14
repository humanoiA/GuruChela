package com.example.guru_chela;


import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
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
public class BlankFragment2 extends Fragment implements AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {
    String[] dept= { "CSE", "IT", "ECE", "CE", "EE","ME","AEIE","BBA","BHM","BBM"};
    Spinner spin;
    RadioButton male;
    RadioButton female;
    Button register;
    EditText name;
    EditText pass;
    EditText mail;
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
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        spin=view.findViewById(R.id.spin);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter aa =  new ArrayAdapter(view.getContext(),R.layout.support_simple_spinner_dropdown_item,dept);
        aa.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        male=view.findViewById(R.id.male);
        female=view.findViewById(R.id.female);
        register = view.findViewById(R.id.register_button);
        name = view.findViewById(R.id.name);
        pass = view.findViewById(R.id.pass);
        mail = view.findViewById(R.id.email);
        //male.setOnCheckedChangeListener(view.getContext());
        male.setOnCheckedChangeListener(this);
        female.setOnCheckedChangeListener(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable(getContext())) {
                    try {
                        String s = getJSONObjectFromURL("https://guruchela.herokuapp.com/api/search/" + String.valueOf(mail.getText()));
                        if (s.indexOf('0') == -1) {
                            Toast.makeText(getContext(), "USER ALREADY EXIST", Toast.LENGTH_LONG).show();
                            Log.d("BF", "USER EXIST");
                        }
                        //Log.d("skip","https://guruchela.herokuapp.com/api/register/" + String.valueOf(name.getText()) + "/" + String.valueOf(mail.getText()) + "/" + String.valueOf(pass.getText()) + "/" + spin.getSelectedItem().toString() + "/" + String.valueOf(male.isChecked()));
                        else {
                            String gender = "";
                            String flag = "";
                            if (male.isChecked()) {
                                gender = "M";
                                flag = getJSONObjectFromURL("https://guruchela.herokuapp.com/api/register/" + String.valueOf(name.getText()) + "/" + String.valueOf(mail.getText()) + "/" + String.valueOf(pass.getText()) + "/" + spin.getSelectedItem().toString() + "/" + gender);
                            } else if (female.isChecked()) {
                                gender = "F";
                                flag = getJSONObjectFromURL("https://guruchela.herokuapp.com/api/register/" + String.valueOf(name.getText()) + "/" + String.valueOf(mail.getText()) + "/" + String.valueOf(pass.getText()) + "/" + spin.getSelectedItem().toString() + "/" + gender);
                            } else {
                                Toast.makeText(getContext(), "Please Select Gender", Toast.LENGTH_LONG).show();
                            }
                            Log.d("flag", "--" + flag + "--");
                            if (flag.replaceAll("[\\n\\t ]", "").equals("true")) {
                                Toast.makeText(getContext(), "Account Created", Toast.LENGTH_LONG).show();
                                getActivity().finish();
                                getActivity().overridePendingTransition(0, 0);
                                startActivity(getActivity().getIntent());
                                getActivity().overridePendingTransition(0, 0);
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
        });
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
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
