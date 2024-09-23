package com.example.pet_vet.PetParent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pet_vet.Login;
import com.example.pet_vet.R;
import com.example.pet_vet.common.Utility;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Pet_parent_req_nursery extends AppCompatActivity {
    EditText nname,date, food,wat,other;
    Button send;
    String cnam,cl_id,da,fo,wa,oth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_parent_req_nursery);
        Intent i = getIntent();
        cl_id=i.getExtras().getString("cl_id");
        cnam=i.getExtras().getString("cl_nam");
        nname=findViewById(R.id.nam);
        date=findViewById(R.id.date);
        food=findViewById(R.id.food);
        wat=findViewById(R.id.wat);
        other=findViewById(R.id.oth);
        send=findViewById(R.id.btnre);
        nname.setText(cnam);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                int year = mcurrentDate.get(Calendar.YEAR);
                int month = mcurrentDate.get(Calendar.MONTH);
                int day = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(
                        v.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {

                                selectedMonth += 1;

                                da = selectedMonth + "/" + selectedDay + "/" + selectedYear;
                                date.setText(da);
                            }
                        },
                        year, month, day
                );

                mDatePicker.setTitle("Select Date");

                // Show the date picker dialog
                mDatePicker.show();
            }
        });
        send.setOnClickListener(v -> {
            fo=food.getText().toString();
            wa=wat.getText().toString();
            oth=other.getText().toString();

            if (fo.isEmpty()) {
                Snackbar.make(food, "Please Enter Food Menu", Snackbar.LENGTH_LONG)
                        .setAction("dimiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
            } else if (wa.isEmpty()) {
                Snackbar.make(wat, "Please Enter Water Menu", Snackbar.LENGTH_LONG)
                        .setAction("dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();

            }else if (oth.isEmpty()) {
                Snackbar.make(other, "Please Enter Description", Snackbar.LENGTH_LONG)
                        .setAction("dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();

            } else {
                owner_request_nursery();
            }

        });
    }

    void owner_request_nursery() {
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {

                if (!response.trim().equals("failed")) {
                    Toast.makeText(Pet_parent_req_nursery.this, "Request sent successful ", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(getApplicationContext(), Login.class));
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
                SharedPreferences sharedPreferences = getSharedPreferences("profileprefer", Context.MODE_PRIVATE);
                String uid = sharedPreferences.getString("UID", "");
                Map<String, String> map = new HashMap<String, String>();
                map.put("requestType", "owner_request_nursery");
                map.put("cid", cl_id);
                map.put("uid", uid);
                map.put("c_nam", cnam);
                map.put("food", fo);
                map.put("water", wa);
                map.put("date", da);
                map.put("des",oth);
                return map;
            }
        };
        queue.add(request);
    }
}