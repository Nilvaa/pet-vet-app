package com.example.pet_vet.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pet_vet.R;
import com.example.pet_vet.common.Base64;

import java.io.IOException;

public class Petshop_view_adopt_details extends AppCompatActivity {
    TextView type,breed,reason,total,accna,accnu;
    ImageView pdt_img;
    String ty,bre,rea,tot,acnu,acna,pic,rid,p_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petshop_view_adopt_details);
        type=findViewById(R.id.pnam);
        breed=findViewById(R.id.pr);
        reason=findViewById(R.id.qua);
        total=findViewById(R.id.tot);
        accna=findViewById(R.id.acna);
        accnu=findViewById(R.id.acnu);
        pdt_img=findViewById(R.id.imgei);
        Intent i = getIntent();
        rid = i.getExtras().getString("r_id");
        p_id = i.getExtras().getString("buyr_id");
        ty = i.getExtras().getString("type");
        bre = i.getExtras().getString("breed");
        rea = i.getExtras().getString("reason");
        tot = i.getExtras().getString("total");
        acnu = i.getExtras().getString("accnu");
        acna = i.getExtras().getString("accna");
        pic = i.getExtras().getString("pic");
        type.setText("TYPE: " + ty);
        breed.setText("BREED: " + bre);
        reason.setText("REASON: " + rea) ;
        total.setText("AMOUNT PAID: Rs." + tot);
        accna.setText("ORDERED BY: " + acna);
        accnu.setText("ACCOUNT NUMBER: " + acnu);

        try {
            byte[] imageAsBytes = Base64.decode(pic.getBytes());

            pdt_img.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}