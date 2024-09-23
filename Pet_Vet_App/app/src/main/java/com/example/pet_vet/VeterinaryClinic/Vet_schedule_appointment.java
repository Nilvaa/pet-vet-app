package com.example.pet_vet.VeterinaryClinic;

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
import com.example.pet_vet.Login;
import com.example.pet_vet.PetParent.Pet_parent_consult_doc;
import com.example.pet_vet.R;
import com.example.pet_vet.VeterinaryClinic.ui.Vet_HomeFragment;
import com.example.pet_vet.common.Utility;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Vet_schedule_appointment extends AppCompatActivity {
    EditText nam,dnam,issue,date,time;
    Button sched;
    String pn,dn,is,da,ti,d_id,co_id,uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet_schedule_appointment);
        nam=findViewById(R.id.onam);
        dnam=findViewById(R.id.dnam);
        issue=findViewById(R.id.iss);
        date=findViewById(R.id.date);
        time=findViewById(R.id.time);
        sched=findViewById(R.id.btnsc);
        Intent i = getIntent();
        co_id=i.getExtras().getString("consid");
        pn=i.getExtras().getString("nam");
        dn=i.getExtras().getString("dnam");
        is=i.getExtras().getString("issue");
        d_id=i.getExtras().getString("d_id");
        uid=i.getExtras().getString("uid");
        nam.setText(pn);
        dnam.setText(dn);
        issue.setText(is);

        sched.setOnClickListener(v -> {
            pn=nam.getText().toString();
            dn=dnam.getText().toString();
            is=issue.getText().toString();

            if (da.isEmpty()) {
                Snackbar.make(date, "Please Enter Appointment Date", Snackbar.LENGTH_LONG)
                        .setAction("dimiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
            } else if (ti.isEmpty()) {
                Snackbar.make(time, "Please Enter Appointment time", Snackbar.LENGTH_LONG)
                        .setAction("dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();

            } else {
                vet_schedule_appointment();
            }
        });

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

    }

    void vet_schedule_appointment() {
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {

                if (!response.trim().equals("failed")) {
                    Toast.makeText(Vet_schedule_appointment.this, "Appointment scheduled successful ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Vet_HomeFragment.class));
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
                String cid = sharedPreferences.getString("UID", "");
                Map<String, String> map = new HashMap<String, String>();
                map.put("requestType", "vet_schedule_appointment");
                map.put("cid", co_id);
                map.put("did", d_id);
                map.put("uid", uid);
                map.put("clid", cid);
                map.put("d_nam", dn);
                map.put("o_nam", pn);
                map.put("issue", is);
                map.put("date", da);
                map.put("time", ti);
                return map;
            }
        };
        queue.add(request);
    }
}