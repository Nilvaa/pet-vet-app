package com.example.pet_vet.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pet_vet.Login;
import com.example.pet_vet.R;

public class Admin_home extends AppCompatActivity {
    CardView user,center,dogwalker,feed;
    ImageView log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        user=findViewById(R.id.parent);
        center=findViewById(R.id.cen);
        dogwalker=findViewById(R.id.walk);
        feed=findViewById(R.id.feed);
        log=findViewById(R.id.log);

        center.setOnClickListener(v -> {
            Intent i=new Intent(getApplicationContext(), Admin_view_centers.class);
            startActivity(i);
        });
        dogwalker.setOnClickListener(v -> {
            Intent i=new Intent(getApplicationContext(), Admin_view_dogwalkers.class);
            startActivity(i);
        });
        user.setOnClickListener(v -> {
            Intent i=new Intent(getApplicationContext(), Admin_view_owners.class);
            startActivity(i);
        });
        feed.setOnClickListener(v -> {
            Intent i=new Intent(getApplicationContext(), Admin_view_feedback.class);
            startActivity(i);
        });
        log.setOnClickListener(v -> {
            Intent i=new Intent(getApplicationContext(), Login.class);
            startActivity(i);
        });


    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Please logout", Toast.LENGTH_SHORT).show();
    }
}