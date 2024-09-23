package com.example.pet_vet.adaptor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.pet_vet.PetParent.Pet_parent_addfeedback;
import com.example.pet_vet.R;
import com.example.pet_vet.common.Base64;
import com.example.pet_vet.common.Petvet_model;

import java.io.IOException;
import java.util.List;

public class Photo_adaptor extends ArrayAdapter<Petvet_model> {

    Activity context;
    List<Petvet_model> pdtlist;
    Button feed;
String clid,cnam;
    public Photo_adaptor(Activity context, List<Petvet_model> pdtlist) {
        super(context, R.layout.activity_photo_adaptor, pdtlist);
        this.pdtlist = pdtlist;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_photo_adaptor, null, true);


        ImageView im=view.findViewById(R.id.imgei);

feed=view.findViewById(R.id.btnfee);
        feed.setOnClickListener(v -> {

            clid=pdtlist.get(position).getNp_nid();
            cnam=pdtlist.get(position).getCen_nam();
            Intent intent=new Intent(getContext(), Pet_parent_addfeedback.class);
            Bundle bundle=new Bundle();
            bundle.putString("clname",cnam);
            bundle.putString("cl_id",clid);
            intent.putExtras(bundle);
            getContext().startActivity(intent);
        });

        String image=pdtlist.get(position).getNp_pic();
        try {
            byte[] imageAsBytes = Base64.decode(image.getBytes());

            im.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        } catch (IOException e) {

            e.printStackTrace();
        }
        return view;
    }
}