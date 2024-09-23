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
import com.example.pet_vet.adaptor.Admin_view_centerAdaptor;
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

public class Admin_view_centers extends AppCompatActivity {
    ListView cenlist;
    List<Petvet_model> arraylist;
    String cen_id;
    public Admin_view_centerAdaptor adap;
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_centers);
        cenlist=findViewById(R.id.ad_view_cen);
        arraylist=new ArrayList<Petvet_model>();
        cenlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Petvet_model selectedcenter = arraylist.get(position);
                cen_id = selectedcenter.getCen_id();

                if ("1".equals(selectedcenter.getL_status())) {
                    showAlreadyAcceptedDialog(selectedcenter);
                } else {
                    showAcceptDialog(selectedcenter);
                }
            
            }

            private void showAcceptDialog(Petvet_model selectedcenter) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Admin_view_centers.this);
                dialog.setCancelable(false);
                dialog.setTitle("Permission");
//                dialog.setIcon(R.drawable.launch_icon);
                dialog.setMessage("Do you want to Accept? " + selectedcenter.getCen_nam());

                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        accept_center();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Handle the neutral button click (if needed)
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alert = dialog.create();
                alert.show();
            }

            private void showAlreadyAcceptedDialog(Petvet_model selectedcenter) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Admin_view_centers.this);
                dialog.setCancelable(false);
                dialog.setTitle("Already Accepted");
//                dialog.setIcon(R.drawable.launch_icon);
                dialog.setMessage(selectedcenter.getCen_nam() + " has already been accepted.");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = dialog.create();
                alert.show();
            }
        });

        view_centers();
    }
    private void updatecenterStatus(String cenId, String status) {
        // Update the status of the priest in the local list
        for (Petvet_model pojo : arraylist) {
            if (pojo.getCen_id().equals(cenId)) {
                pojo.setL_status(status);
                break;
            }
        }

        // Notify the adapter of the data change
        if (adap != null) {
            adap.notifyDataSetChanged();
        }
    }
    private void accept_center() {
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("******", response);
                System.out.println("EEE" + response);
                if (!response.trim().equals("failed")) {
                    Toast.makeText(getApplicationContext(), "Accepted", Toast.LENGTH_LONG).show();

                    // Update the status of the priest after accepting
                    updatecenterStatus(cen_id, "1");

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
                Map<String, String> map = new HashMap<>();
                map.put("requestType", "acceptcenter");
                map.put("cid", cen_id);
                return map;
            }
        };
        queue.add(request);
    }


    public void view_centers(){
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
                    adap = new Admin_view_centerAdaptor(Admin_view_centers.this, arraylist);
                    cenlist.setAdapter(adap);
                    registerForContextMenu(cenlist);

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
                map.put("requestType", "admin_view_cen");
                return map;
            }
        };
        queue.add(request);
    }
}