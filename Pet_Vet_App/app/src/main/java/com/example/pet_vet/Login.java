package com.example.pet_vet;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pet_vet.PetParent.Petparent_home;
import com.example.pet_vet.VeterinaryClinic.Vet_nav;
import com.example.pet_vet.admin.Admin_home;
import com.example.pet_vet.common.Utility;
import com.example.pet_vet.dog_walker.Dog_walker_home;
import com.example.pet_vet.dog_walker.Dog_walker_notifictaion;
import com.example.pet_vet.petnursery.Pet_nursery_home;
import com.example.pet_vet.petshop.PetshopNavActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText unam,pass;
    Button signin;
    String un,pas;
    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tv=findViewById(R.id.tv_log);
        unam=findViewById(R.id.l_uname);
        pass=findViewById(R.id.l_pass);
        signin=findViewById(R.id.btnlog);

        tv.setOnClickListener(v -> {
            Intent i= new Intent (getApplicationContext(), Options.class);
            startActivity(i);
        });

        signin.setOnClickListener(v -> {
            un=unam.getText().toString();
            pas=pass.getText().toString();

            if (un.isEmpty()) {
                Snackbar.make(unam, "Please Enter Username", Snackbar.LENGTH_LONG)
                        .setAction("dimiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
            } else if (pas.isEmpty()) {
                Snackbar.make(pass, "Please Enter Password", Snackbar.LENGTH_LONG)
                        .setAction("dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
            }else {
                login();
            }

        });
    }

    void login() {

        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {

                if (!response.trim().equals("failed")) {
                    System.out.println("LOGIN_ID"+response);
                    String respArr[]= response.trim().split("#");
                    String uid=respArr[0];
                    String type=respArr[1];
                    System.out.println(uid);
                    System.out.println(type);
                    System.out.println("response"+respArr[1]);
                    SharedPreferences.Editor editor = getSharedPreferences("profileprefer", MODE_PRIVATE).edit();
                    editor.putString("UID",uid);
                    editor.putString("type",type);
                    editor.commit();
                    if(type.equals("admin")){
                        startActivity(new Intent(getApplicationContext(), Admin_home.class));

                    }else  if(type.equals("Veterinary Clinic")){
                        startActivity(new Intent(getApplicationContext(), Vet_nav.class));

                    }else  if(type.equals("Pet Shop")) {
                        startActivity(new Intent(getApplicationContext(), PetshopNavActivity.class));
                    }
                    else  if(type.equals("Pet Nursery")) {
                        startActivity(new Intent(getApplicationContext(), Pet_nursery_home.class));
                    }
                    else  if(type.equals("petparent")) {
                        startActivity(new Intent(getApplicationContext(), Petparent_home.class));
                    }
                    else  if(type.equals("dogwalker")) {
                        startActivity(new Intent(getApplicationContext(), Dog_walker_home.class));
                        sendLocalNotification("NEW REQUESTS🐾", "You may have request from your fur Friends, Thank you!");

                    }
                    else{
                        Toast.makeText(Login.this, "something went wrong", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Failed" , Toast.LENGTH_SHORT).show();
                }
            }
            private void sendLocalNotification(String title, String message) {
                // Create an intent to open the Stud_notification activity
                Intent intent = new Intent(getApplicationContext(), Dog_walker_notifictaion.class);
                PendingIntent pendingIntent = getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "channel_id")
                        .setSmallIcon(R.drawable.dog) // Set your notification icon
                        .setContentTitle(title)
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);

                NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    CharSequence name = "Your Channel Name"; // Change this to your desired channel name
                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                    NotificationChannel notificationChannel = new NotificationChannel("channel_id", name, importance);
                    notificationManager.createNotificationChannel(notificationChannel);
                }

                // Notify with a unique ID
                int notificationId = (int) System.currentTimeMillis();
                notificationManager.notify(notificationId, builder.build());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "My Error :" + error, Toast.LENGTH_SHORT).show();
                Log.i("My Error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("requestType", "login");
                map.put("uname", un);
                map.put("pass", pas);
                return map;
            }
        };
        queue.add(request);
    }
}