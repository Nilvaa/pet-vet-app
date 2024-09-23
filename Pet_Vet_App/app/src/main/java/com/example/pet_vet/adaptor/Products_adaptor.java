package com.example.pet_vet.adaptor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.pet_vet.R;
import com.example.pet_vet.common.Base64;
import com.example.pet_vet.common.Petvet_model;

import java.io.IOException;
import java.util.List;

public class Products_adaptor extends ArrayAdapter<Petvet_model> {

    Activity context;
    List<Petvet_model> pdtlist;

    public Products_adaptor(Activity context, List<Petvet_model> pdtlist) {
        super(context, R.layout.activity_products_adaptor, pdtlist);
        this.pdtlist = pdtlist;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_products_adaptor, null, true);


        TextView na=view.findViewById(R.id.d_nam);
        TextView de=view.findViewById(R.id.d_em);
        TextView pr=view.findViewById(R.id.d_ph);
        ImageView im=view.findViewById(R.id.imge);
        na.setText("NAME: "+pdtlist.get(position).getPdt_name());
        de.setText("DESCRIPTION: "+pdtlist.get(position).getPdt_des());
        pr.setText("PRICE: Rs."+pdtlist.get(position).getPdt_price());


        String image=pdtlist.get(position).getPdt_pic();
        try {
            byte[] imageAsBytes = Base64.decode(image.getBytes());

            im.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        } catch (IOException e) {

            e.printStackTrace();
        }
        return view;
    }
}