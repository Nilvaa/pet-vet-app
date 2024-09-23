package com.example.pet_vet.PetParent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pet_vet.R;
import com.example.pet_vet.common.Base64;

import java.io.IOException;

public class Pet_parent_view_pdt_details extends AppCompatActivity {

    TextView name, price, des, cate;
    ImageView pdt_img;
    Button buy;
    String pid, sh_id, na, de, cat, pr, pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_parent_view_pdt_details);
        name = findViewById(R.id.pnam);
        pdt_img = findViewById(R.id.imgei);
        price = findViewById(R.id.price);
        des = findViewById(R.id.des);
        cate = findViewById(R.id.ca);
        buy = findViewById(R.id.btnby);

        Intent i = getIntent();
        pid = i.getExtras().getString("pdt_id");
        sh_id = i.getExtras().getString("shop_id");
        na = i.getExtras().getString("name");
        de = i.getExtras().getString("des");
        cat = i.getExtras().getString("catergory");
        pr = i.getExtras().getString("price");
        pic = i.getExtras().getString("pic");
        name.setText("NAME: " + na);
        price.setText("PRICE: Rs." + pr);
        des.setText("DESCRIPTION: " + de);
        cate.setText("CATEGORY: " + cat);

        buy.setOnClickListener(v -> {
        Intent intent= new Intent(getApplicationContext(), Pet_parent_buy_product.class);
        intent.putExtra("p_id",pid);
        intent.putExtra("shop_id",sh_id);
        intent.putExtra("name",na);
        intent.putExtra("price",pr);
        startActivity(intent);

        });



        try {
            byte[] imageAsBytes = Base64.decode(pic.getBytes());

            pdt_img.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}