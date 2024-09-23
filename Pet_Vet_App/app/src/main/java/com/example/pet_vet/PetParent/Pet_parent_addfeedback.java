package com.example.pet_vet.PetParent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import com.example.pet_vet.common.Utility;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pet_vet.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

public class Pet_parent_addfeedback extends AppCompatActivity {
    EditText subject,des,cen_nam;
    String sub,de,cl_id,cnam,rate;
    Button feed;
    RatingBar star;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_parent_addfeedback);
        cen_nam=findViewById(R.id.nam);
        Intent i = getIntent();
        cl_id = i.getExtras().getString("cl_id");
        cnam = i.getExtras().getString("clname");
        cen_nam.setText(cnam);
        subject=findViewById(R.id.sub);
        des=findViewById(R.id.des);
        star=(RatingBar) findViewById(R.id.startid);
        feed=findViewById(R.id.btnfeed);
        feed.setOnClickListener(v -> {
    sub=subject.getText().toString();
    de=des.getText().toString();
    rate= String.valueOf(star.getRating());


    if(sub.isEmpty()) {
        Snackbar.make(subject, "Please Enter Subject", Snackbar.LENGTH_LONG)
                .setAction("dimiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
    }else if(de.isEmpty()) {
        Snackbar.make(des, "Please Enter Description", Snackbar.LENGTH_LONG)
                .setAction("dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
    } else if (rate.isEmpty()) {
        Snackbar.make(star, "Please Enter Rating", Snackbar.LENGTH_LONG)
                .setAction("dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
    } else{
        send_feedback();
    }
});
    }

    public void send_feedback(){
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {

                if (!response.trim().equals("failed")) {
                    System.out.println("getlabel"+response);
                    Toast.makeText(Pet_parent_addfeedback.this, "Feedback sent successful ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed" + response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "my Error :" + error, Toast.LENGTH_SHORT).show();
                Log.i("My Error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>();
                SharedPreferences sharedPreferences = getSharedPreferences("profileprefer", MODE_PRIVATE);
                String uid=sharedPreferences.getString("UID","");
                map.put("requestType", "owner_send_feedback");
                map.put("descr", de);
                map.put("rating", rate);
                map.put("subject", sub);
                map.put("clinic_id", cl_id);
                map.put("uid", uid);
                map.put("cen_nam", cnam);
                return map;
            }
        };
        queue.add(request);
    }
}