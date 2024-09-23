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

public class Admin_owner_adaptor extends ArrayAdapter<Petvet_model> {

    Activity context;
    List<Petvet_model> d_list;

    public Admin_owner_adaptor(Activity context, List<Petvet_model> d_list) {
        super(context, R.layout.activity_admin_owner_adaptor, d_list);
        this.d_list = d_list;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_admin_owner_adaptor, null, true);

        TextView na=view.findViewById(R.id.nam);
        TextView em=view.findViewById(R.id.em);
        TextView ad=view.findViewById(R.id.add);
        TextView pe=view.findViewById(R.id.pet);
        TextView ph=view.findViewById(R.id.ph);
        na.setText("NAME: "+d_list.get(position).getOw_nam());
        em.setText("EMAIL: "+d_list.get(position).getO_email());
        ph.setText("CONTACT: "+d_list.get(position).getO_phone());
        ad.setText("ADDRESS: "+d_list.get(position).getOwn_add());
        pe.setText("PET: "+d_list.get(position).getPet_typ());


        return view;
    }
}