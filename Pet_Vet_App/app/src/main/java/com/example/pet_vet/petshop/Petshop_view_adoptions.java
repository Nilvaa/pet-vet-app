package com.example.pet_vet.petshop;

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
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pet_vet.PetParent.Pet_parent_view_pdt_details;
import com.example.pet_vet.R;
import com.example.pet_vet.adaptor.Adoption_request_adaptor;
import com.example.pet_vet.adaptor.Product_order_adaptor;
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

public class Petshop_view_adoptions extends AppCompatActivity {
    ListView d_list;
    List<Petvet_model> arraylist;
    public Adoption_request_adaptor adap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petshop_view_adoptions);
        d_list=findViewById(R.id.petshop_view_ad);

        arraylist=new ArrayList<Petvet_model>();
        d_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String rid=arraylist.get(position).getAd_req_id();
                String pid=arraylist.get(position).getAd_requid();
                String typ=arraylist.get(position).getAd_reqtyp();
                String br=arraylist.get(position).getAd_re_breed();
                String rea=arraylist.get(position).getAd_re_reason();
                String tot=arraylist.get(position).getAd_re_tot();
                String accna=arraylist.get(position).getAd_re_accnam();
                String accnu=arraylist.get(position).getAd_re_accnum();
                String pic=arraylist.get(position).getAdop_pic();
                Intent intent=new Intent(getApplicationContext(), Petshop_view_adopt_details.class);
                Bundle bundle=new Bundle();
                bundle.putString("r_id", rid);
                bundle.putString("buyr_id", pid);
                bundle.putString("type", typ);
                bundle.putString("breed", br);
                bundle.putString("reason", rea);
                bundle.putString("total", tot);
                bundle.putString("accna", accna);
                bundle.putString("accnu", accnu);
                bundle.putString("pic", pic);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        view_adopts();
    }


    public void view_adopts(){
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
                    adap = new Adoption_request_adaptor(Petshop_view_adoptions.this, arraylist);
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
                SharedPreferences sh = getSharedPreferences("profileprefer", Context.MODE_PRIVATE);
                String p_id=sh.getString("UID","not_data");
                Map<String, String> map = new HashMap<String, String>();
                map.put("requestType", "pet_shop_view_adoptions");
                map.put("petshop_id", p_id);
                return map;
            }
        };
        queue.add(request);
    }
}