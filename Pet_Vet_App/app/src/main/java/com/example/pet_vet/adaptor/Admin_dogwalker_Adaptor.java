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

public class Admin_dogwalker_Adaptor extends ArrayAdapter<Petvet_model> {

    Activity context;
    List<Petvet_model> d_list;

    public Admin_dogwalker_Adaptor(Activity context, List<Petvet_model> d_list) {
        super(context, R.layout.activity_admin_dogwalker_adaptor, d_list);
        this.d_list = d_list;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_admin_dogwalker_adaptor, null, true);

        TextView na=view.findViewById(R.id.nam);
        TextView em=view.findViewById(R.id.em);
        TextView ex=view.findViewById(R.id.ex);
        TextView ad=view.findViewById(R.id.add);
        TextView l=view.findViewById(R.id.lic);
        TextView s=view.findViewById(R.id.sp);
        TextView ph=view.findViewById(R.id.ph);
        ImageView im=view.findViewById(R.id.imge);
        na.setText("NAME: "+d_list.get(position).getWa_nam());
        l.setText("LICENSE NUMBER: "+d_list.get(position).getWa_lic());
        em.setText("EMAIL: "+d_list.get(position).getWa_em());
        ph.setText("CONTACT: "+d_list.get(position).getWa_ph());
        ad.setText("ADDRESS: "+d_list.get(position).getWa_add());
        ex.setText("EXPERIENCE: "+d_list.get(position).getWa_ex()+ " year");
        s.setText("SPECIALIZATIONS: "+d_list.get(position).getWa_sp());

        String image=d_list.get(position).getWa_pic();
        try {
            byte[] imageAsBytes = Base64.decode(image.getBytes());

            im.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        } catch (IOException e) {

            e.printStackTrace();
        }
        return view;
    }
}