package com.example.pet_vet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.pet_vet.PetParent.PetParent_reg;
import com.example.pet_vet.dog_walker.Dog_walker_reg;

public class Options extends AppCompatActivity {
    CardView user,dog_walker,center;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        user=findViewById(R.id.user);
        dog_walker=findViewById(R.id.walk);
        center=findViewById(R.id.cen);
        center.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), Center_reg.class);
            startActivity(i);

        });
        user.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), PetParent_reg.class);
            startActivity(i);

        });
        dog_walker.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), Dog_walker_reg.class);
            startActivity(i);

        });
        
    }
}