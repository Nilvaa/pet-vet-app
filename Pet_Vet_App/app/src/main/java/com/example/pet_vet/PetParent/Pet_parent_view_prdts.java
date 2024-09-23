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

public class Pet_parent_view_prdts extends AppCompatActivity {
    ListView pdtlist;
    List<Petvet_model> arraylist;
    public Products_adaptor adap;
    String cl_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_parent_view_prdts);
        pdtlist=findViewById(R.id.o_view_pdts);
        Intent i = getIntent();
        cl_id=i.getExtras().getString("cl_id");
        arraylist=new ArrayList<Petvet_model>();
        pdtlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String pid=arraylist.get(position).getPdt_id();
                String sh_id=arraylist.get(position).getPdt_shop_id();
                String na=arraylist.get(position).getPdt_name();
                String de=arraylist.get(position).getPdt_des();
                String cat=arraylist.get(position).getPdt_cate();
                String pr=arraylist.get(position).getPdt_price();
                String pic=arraylist.get(position).getPdt_pic();
                Intent intent=new Intent(getApplicationContext(), Pet_parent_view_pdt_details.class);
                Bundle bundle=new Bundle();
                bundle.putString("pdt_id", pid);
                bundle.putString("shop_id", sh_id);
                bundle.putString("name", na);
                bundle.putString("des", de);
                bundle.putString("catergory", cat);
                bundle.putString("price", pr);
                bundle.putString("pic", pic);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        view_products();
    }


    public void view_products(){
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
                    adap = new Products_adaptor(Pet_parent_view_prdts.this, arraylist);
                    pdtlist.setAdapter(adap);
                    registerForContextMenu(pdtlist);

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
                map.put("requestType", "pet_parent_view_product");
                map.put("shop_id", cl_id);
                return map;
            }
        };
        queue.add(request);
    }
}