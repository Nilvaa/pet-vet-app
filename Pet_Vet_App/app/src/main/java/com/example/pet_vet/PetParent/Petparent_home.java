package com.example.pet_vet.PetParent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pet_vet.Login;
import com.example.pet_vet.R;
import com.example.pet_vet.admin.Admin_view_centers;

public class Petparent_home extends AppCompatActivity {
    CardView user,center,dogwalker,feed;
    ImageView log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petparent_home);
        center=findViewById(R.id.cen);
        dogwalker=findViewById(R.id.walk);
        log=findViewById(R.id.log);

        center.setOnClickListener(v -> {
            Intent i=new Intent(getApplicationContext(), Pet_parent_view_cen.class);
            startActivity(i);
        });
        dogwalker.setOnClickListener(v -> {
            Intent i=new Intent(getApplicationContext(), Pet_parent_view_dog_walker.class);
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