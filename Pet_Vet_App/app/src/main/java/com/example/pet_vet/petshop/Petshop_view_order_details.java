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

public class Petshop_view_order_details extends AppCompatActivity {
    TextView pdtnam,bunam,price,quan,total,accna,accnu;
    ImageView pdt_img;
    String pna,bna,pr,qu,tot,acnu,acna,pic,oid,sh_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petshop_view_order_details);
        pdtnam=findViewById(R.id.pnam);
        price=findViewById(R.id.pr);
        quan=findViewById(R.id.qua);
        total=findViewById(R.id.tot);
        accna=findViewById(R.id.acna);
        accnu=findViewById(R.id.acnu);
        pdt_img=findViewById(R.id.imgei);
        Intent i = getIntent();
        oid = i.getExtras().getString("or_id");
        sh_id = i.getExtras().getString("shop_id");
        pna = i.getExtras().getString("name");
        tot = i.getExtras().getString("total");
        pr = i.getExtras().getString("price");
        qu = i.getExtras().getString("quantity");
        acnu = i.getExtras().getString("accnu");
        acna = i.getExtras().getString("accna");
        pic = i.getExtras().getString("pic");
        pdtnam.setText("PRODUCT: " + pna);
        price.setText("PRICE: Rs." + pr);
        quan.setText("QUANTITY ORDERED: " + qu+" nos");
        total.setText("AMOUNT PAID: " + tot);
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