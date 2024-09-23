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

public class Dog_walker_request_adapotor extends ArrayAdapter<Petvet_model> {

    Activity context;
    List<Petvet_model> pdtlist;

    public Dog_walker_request_adapotor(Activity context, List<Petvet_model> pdtlist) {
        super(context, R.layout.activity_dog_walker_request_adapotor, pdtlist);
        this.pdtlist = pdtlist;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_dog_walker_request_adapotor, null, true);


        TextView na=view.findViewById(R.id.onam);
        TextView ty=view.findViewById(R.id.type);
        TextView da=view.findViewById(R.id.date);
        TextView ti=view.findViewById(R.id.ti);
        ImageView im=view.findViewById(R.id.imgei);
        na.setText("REQUESTED BY: "+pdtlist.get(position).getOw_nam());
        ty.setText("PET: "+pdtlist.get(position).getPet_typ());
        da.setText("DATE: "+pdtlist.get(position).getDw_date());
        ti.setText("TIME: "+pdtlist.get(position).getDw_time());


        String image=pdtlist.get(position).getPet_pic();
        try {
            byte[] imageAsBytes = Base64.decode(image.getBytes());

            im.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        } catch (IOException e) {

            e.printStackTrace();
        }
        return view;
    }
}