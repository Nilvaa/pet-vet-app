package com.example.pet_vet.PetParent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pet_vet.R;
import com.example.pet_vet.common.Utility;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Pet_parent_req_dog_walker extends AppCompatActivity {

    EditText name, date,time;
    String did,nam,da,ti;
    Button Request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_parent_req_dog_walker);
        name=findViewById(R.id.nam);
        date=findViewById(R.id.date);
        time=findViewById(R.id.time);
        Request=findViewById(R.id.btnre);
        Intent i = getIntent();
        did = i.getExtras().getString("d_id");
        nam = i.getExtras().getString("d_nam");
        name.setText(nam);
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

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mCurrentTime = Calendar.getInstance();
                int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mCurrentTime.get(Calendar.MINUTE);

                // TimePickerDialog for selecting time
                TimePickerDialog mTimePicker = new TimePickerDialog(
                        v.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                // Format the selected time
                                String amPm = (selectedHour < 12) ? "AM" : "PM";
                                selectedHour = (selectedHour > 12) ? selectedHour - 12 : selectedHour;
                                ti = String.format("%02d:%02d %s", selectedHour, selectedMinute, amPm);

                                time.setText(ti);
                            }
                        },
                        hour, minute, false // 24-hour format
                );

                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        Request.setOnClickListener(v -> {
            nam=name.getText().toString();
            pet_parent_req_DW();
        });
    }

    void pet_parent_req_DW() {
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(com.android.volley.Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {

                if (!response.trim().equals("failed")) {
                    SharedPreferences sharedPreferences = getSharedPreferences("profileprefer", Context.MODE_PRIVATE);
                    String doc_id = sharedPreferences.getString("UID", "");
                    Intent i=new Intent(getApplicationContext(), Pet_parent_req_dog_walker.class);
                    i.putExtra("did",doc_id);
                    startActivity(i);
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
                SharedPreferences sharedPreferences = getSharedPreferences("profileprefer", Context.MODE_PRIVATE);
                String u_id = sharedPreferences.getString("UID", "");
                Intent i = getIntent();

                did = i.getExtras().getString("d_id");
                nam = i.getExtras().getString("d_nam");
                map.put("requestType", "req_dog_walker");
                map.put("do_id",did );
                map.put("u_id",u_id );
                map.put("d_name", nam);
                map.put("date", da);
                map.put("time", ti);
                return map;
            }
        };
        queue.add(request);
    }
}