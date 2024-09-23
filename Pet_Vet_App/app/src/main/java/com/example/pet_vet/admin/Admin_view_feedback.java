package com.example.pet_vet.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pet_vet.R;
import com.example.pet_vet.adaptor.Admin_dogwalker_Adaptor;
import com.example.pet_vet.adaptor.Admin_owner_adaptor;
import com.example.pet_vet.adaptor.Admin_view_centerAdaptor;
import com.example.pet_vet.adaptor.Feedback_adaptor;
import com.example.pet_vet.common.Petvet_model;
import com.example.pet_vet.common.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.pet_vet.adaptor.Admin_view_centerAdaptor;
import com.example.pet_vet.common.Petvet_model;
import com.google.gson.Gson;

public class Admin_view_feedback extends AppCompatActivity {
    ListView feedlist;
    List<Petvet_model> arraylist;

    public Feedback_adaptor adap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_feedback);
        feedlist=findViewById(R.id.ad_view_feed);
        arraylist=new ArrayList<Petvet_model>();


        feedback();
    }



    public void feedback(){
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("******", response);
                System.out.println("EEE" + response);
                if (!response.trim().equals("failed")) {
                    Gson gson = new Gson();
                    String data = response.trim();
                    arraylist = Arrays.asList(gson.fromJson(data, Petvet_model[].class));
                    adap = new Feedback_adaptor(Admin_view_feedback.this, arraylist);
                    feedlist.setAdapter(adap);
                    registerForContextMenu(feedlist);

                } else {
                    Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "my error :" + error, Toast.LENGTH_LONG).show();
                Log.i("My error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("requestType", "admin_view_feedback");
                return map;
            }
        };
        queue.add(request);
    }
}