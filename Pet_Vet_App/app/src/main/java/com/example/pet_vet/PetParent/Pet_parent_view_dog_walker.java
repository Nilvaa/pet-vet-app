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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pet_vet.R;
import com.example.pet_vet.adaptor.Admin_dogwalker_Adaptor;
import com.example.pet_vet.adaptor.Admin_view_centerAdaptor;
import com.example.pet_vet.adaptor.Dog_walker_adaptor;
import com.example.pet_vet.adaptor.Product_order_adaptor;
import com.example.pet_vet.common.Petvet_model;
import com.example.pet_vet.common.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.pet_vet.adaptor.Admin_view_centerAdaptor;
import com.example.pet_vet.common.Petvet_model;
import com.example.pet_vet.petshop.Pet_shop_view_orders;
import com.google.gson.Gson;

public class Pet_parent_view_dog_walker extends AppCompatActivity {
    ListView dlist;
    List<Petvet_model> arraylist;
    String searchText;
    ImageView searchimg;
    EditText search;

    public Dog_walker_adaptor adap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_parent_view_dog_walker);
        dlist=findViewById(R.id.o_view_wal);
        search=findViewById(R.id.searchPackage);
        searchimg=findViewById(R.id.searchIcon);

        arraylist=new ArrayList<Petvet_model>();
        searchimg.setOnClickListener(v -> {
            validate();
        });
        view_walkers();
    }
    private void validate()
    {
        searchText=search.getText().toString();
        if(searchText.isEmpty())
        {
            view_walkers();

        }
        else
        {
            getSearchResult();
        }
    }
    private void getSearchResult()
    {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.trim().equals("failed")) {
                    Gson gson = new Gson();
                    String data = response.trim();
                    arraylist = Arrays.asList(gson.fromJson(data, Petvet_model[].class));
                    adap = new Dog_walker_adaptor(Pet_parent_view_dog_walker.this, arraylist);
                    dlist.setAdapter(adap);
                    registerForContextMenu(dlist);

                } else {
                    Toast.makeText(getApplicationContext(), "NO_DATA", Toast.LENGTH_SHORT).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_SHORT).show();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences prefs = getSharedPreferences("sharedData", MODE_PRIVATE);
                final String reg_id = prefs.getString("reg_id", "No_id");
                final String type = prefs.getString("type", "NO-Type");
                Map<String, String> map = new HashMap<String, String>();
                map.put("requestType", "getSearchResult");
                map.put("reg_id", reg_id);
                map.put("searchValue",  searchText);
                return map;
            }

        };
        queue.add(request);
    }



    public void view_walkers(){
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
                    adap = new Dog_walker_adaptor(Pet_parent_view_dog_walker.this, arraylist);
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
                map.put("requestType", "petparent_view_walker");
                return map;
            }
        };
        queue.add(request);
    }
}