package com.example.pet_vet.PetParent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;  // Add this import statement
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
import com.example.pet_vet.R;
import com.example.pet_vet.common.Utility;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Pet_parent_buy_product extends AppCompatActivity {
    EditText nam, price, quan, total, card_no, CVV, acc_nam, acc_num, exp_date;
    Button order;
    String pid, shid, na, pr, qu, tot, card, cv, acc, acnam, exp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_parent_buy_product);
        nam = findViewById(R.id.pdt);
        price = findViewById(R.id.price);
        quan = findViewById(R.id.qua);
        total = findViewById(R.id.tot);
        card_no = findViewById(R.id.car);
        CVV = findViewById(R.id.cvv);
        acc_nam = findViewById(R.id.accnam);
        acc_num = findViewById(R.id.acc);
        exp_date = findViewById(R.id.exp);
        order = findViewById(R.id.btnor);
        Intent i = getIntent();
        pid = i.getExtras().getString("p_id");
        shid = i.getExtras().getString("shop_id");
        na = i.getExtras().getString("name");
        pr = i.getExtras().getString("price");
        nam.setText(na);
        price.setText(pr);

        quan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Not needed for this case
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                updateTotalAmount();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Not needed for this case
            }
        });

        exp_date.setOnClickListener(new View.OnClickListener() {
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
                                exp = selectedMonth + "/" + selectedDay + "/" + selectedYear;
                                exp_date.setText(exp);
                            }
                        },
                        year, month, day
                );

                mDatePicker.setTitle("Select Expiry Date");
                mDatePicker.show();
            }
        });

        order.setOnClickListener(v -> {
            acnam = acc_nam.getText().toString();
            acc = acc_num.getText().toString();
            qu = quan.getText().toString();
            cv = CVV.getText().toString();
            card = card_no.getText().toString();

            // Update total amount before validating
            updateTotalAmount();


            if (acnam.isEmpty()) {
                Snackbar.make(acc_nam, "Please Enter Account Holder Name", Snackbar.LENGTH_LONG)
                        .setAction("dimiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
            }else if (acc.isEmpty()) {
                Snackbar.make(acc_num, "Please Enter Account Number", Snackbar.LENGTH_LONG)
                        .setAction("dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();

            }else if (qu.isEmpty()) {
                Snackbar.make(quan, "Please Enter Quantity", Snackbar.LENGTH_LONG)
                        .setAction("dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();

            } else if (card.isEmpty()) {
                Snackbar.make(card_no, "Please Enter Card Number", Snackbar.LENGTH_LONG)
                        .setAction("dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
            }else if (card.length() != 16) {

                Snackbar.make(card_no, "Please Enter Correct Card Number", Snackbar.LENGTH_LONG)
                        .setAction("dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();

            }else if (cv.isEmpty()) {
                Snackbar.make(CVV, "Please Enter CVV", Snackbar.LENGTH_LONG)
                        .setAction("dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
            }else if (cv.length() != 3) {

                Snackbar.make(CVV, "Please Enter Correct CVV", Snackbar.LENGTH_LONG)
                        .setAction("dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();

            }else {
                pet_parent_buy_product();
            }

        });

    }

    private void updateTotalAmount() {
        try {
            int quantity = Integer.parseInt(quan.getText().toString());
            double priceValue = Double.parseDouble(pr);
            double totalValue = quantity * priceValue;
            tot = String.valueOf(totalValue);
            total.setText(tot);
        } catch (NumberFormatException e) {
            Log.e("NumberFormatException", e.getMessage());
        }
    }

    void pet_parent_buy_product()  {
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {

                if (!response.trim().equals("failed")) {
                    Toast.makeText(Pet_parent_buy_product.this, "Ordered Successfully", Toast.LENGTH_SHORT).show();

                    // Pass userID to the next activity
                    SharedPreferences sharedPreferences = getSharedPreferences("profileprefer", Context.MODE_PRIVATE);
                    String p_id = sharedPreferences.getString("UID", "");
                    Intent i=new Intent(getApplicationContext(), Pet_parent_buy_product.class);
                    i.putExtra("pid",p_id);
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
                String o_id = sharedPreferences.getString("UID", "");
                map.put("requestType", "pet_parent_buy_product");
                map.put("own_id", o_id);
                map.put("pdt_id", pid);
                map.put("shop_id", shid);
                map.put("pdt_nam", na);
                map.put("price", pr);
                map.put("quantity", qu);
                map.put("total", tot);
                map.put("cvv", cv);
                map.put("cardno", card);
                map.put("acc_num", acc);
                map.put("acc_nam", acnam);
                map.put("exp_date", exp);
                return map;
            }
        };
        queue.add(request);
    }
}
