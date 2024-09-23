package com.example.pet_vet.PetParent;

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
import com.example.pet_vet.adaptor.Owner_view_clinic_adaptor;
import com.example.pet_vet.adaptor.Owner_view_doc_adaptor;
import com.example.pet_vet.common.Petvet_model;
import com.example.pet_vet.common.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class Pet_parent_view_doc extends AppCompatActivity {
    ListView dlist;
    List<Petvet_model> arraylist;
    String cl_id;

    public Owner_view_doc_adaptor adap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_parent_view_doc);
        dlist=findViewById(R.id.o_view_doc);
        Intent i = getIntent();
        cl_id=i.getExtras().getString("cl_id");
        arraylist=new ArrayList<Petvet_model>();


        owners_view_doctors();
    }



    public void owners_view_doctors(){
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
                    adap = new Owner_view_doc_adaptor(Pet_parent_view_doc.this, arraylist);
                    dlist.setAdapter(adap);
                    registerForContextMenu(dlist);

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
                map.put("requestType", "owners_view_doc");
                map.put("clinic_id", cl_id);
                return map;
            }
        };
        queue.add(request);
    }
}