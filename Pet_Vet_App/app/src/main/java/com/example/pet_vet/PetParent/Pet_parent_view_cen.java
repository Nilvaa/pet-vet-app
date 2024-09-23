package com.example.pet_vet.PetParent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;

import com.example.pet_vet.R;
import com.example.pet_vet.admin.Admin_view_centers;

public class Pet_parent_view_cen extends AppCompatActivity {
    CardView clinic,shop,nurs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_parent_view_cen);
        clinic=findViewById(R.id.cen);
        shop=findViewById(R.id.shop);
        nurs=findViewById(R.id.nur);
        clinic.setOnClickListener(v -> {
            Intent i=new Intent(getApplicationContext(), Pet_parent_view_clinic.class);
            startActivity(i);
        });

        shop.setOnClickListener(v -> {
            Intent i=new Intent(getApplicationContext(), Pet_parent_view_shop.class);
            startActivity(i);
        });

        nurs.setOnClickListener(v -> {
            Intent i=new Intent(getApplicationContext(), Pet_parent_view_nursery.class);
            startActivity(i);
        });
    }
}