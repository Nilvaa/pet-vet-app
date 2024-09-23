package com.example.pet_vet.petnursery;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pet_vet.Login;
import com.example.pet_vet.R;
import com.example.pet_vet.adaptor.Dog_walker_request_adapotor;
import com.example.pet_vet.adaptor.Nursery_req_adaptor;
import com.example.pet_vet.adaptor.Products_adaptor;
import com.example.pet_vet.common.Petvet_model;
import com.example.pet_vet.common.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.pet_vet.adaptor.Products_adaptor;
import com.example.pet_vet.common.Petvet_model;
import com.google.gson.Gson;

public class Pet_nursery_home extends AppCompatActivity {
    ListView d_list;
    List<Petvet_model> arraylist;
    public Nursery_req_adaptor adap;
    String cl_id;
    ImageView log;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_nursery_home);
        d_list=findViewById(R.id.n_ad);
        log=findViewById(R.id.log);
        log.setOnClickListener(v -> {
            Intent i=new Intent(getApplicationContext(), Login.class);
            startActivity(i);
        });
        arraylist=new ArrayList<Petvet_model>();

        view_requests();
    }


    public void view_requests(){
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
                    adap = new Nursery_req_adaptor(Pet_nursery_home.this, arraylist);
                    d_list.setAdapter(adap);
                    registerForContextMenu(d_list);

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
                SharedPreferences sharedPreferences = getSharedPreferences("profileprefer", Context.MODE_PRIVATE);
                String n_id = sharedPreferences.getString("UID", "");
                Map<String, String> map = new HashMap<String, String>();
                map.put("requestType", "pet_nursery_view_admissions");
                map.put("uid", n_id);
                return map;
            }
        };
        queue.add(request);
    }
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Please logout", Toast.LENGTH_SHORT).show();
    }
}