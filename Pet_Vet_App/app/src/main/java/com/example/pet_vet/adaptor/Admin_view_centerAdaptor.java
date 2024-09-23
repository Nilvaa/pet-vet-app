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

public class Admin_view_centerAdaptor extends ArrayAdapter<Petvet_model> {

    Activity context;
    List<Petvet_model> cen_list;

    public Admin_view_centerAdaptor(Activity context, List<Petvet_model> cen_list) {
        super(context, R.layout.activity_admin_view_center_adaptor, cen_list);
        this.cen_list = cen_list;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_admin_view_center_adaptor, null, true);

        TextView na=view.findViewById(R.id.nam);
        TextView em=view.findViewById(R.id.em);
        TextView ty=view.findViewById(R.id.typ);
        TextView ad=view.findViewById(R.id.add);
        TextView l=view.findViewById(R.id.lic);
        TextView wr=view.findViewById(R.id.wr);
        TextView ph=view.findViewById(R.id.ph);
        ImageView im=view.findViewById(R.id.imge);
        na.setText("NAME: "+cen_list.get(position).getCen_nam());
        ty.setText("TYPE: "+cen_list.get(position).getCen_typ());
        l.setText("LICENSE NUMBER: "+cen_list.get(position).getCen_lic());
        em.setText("EMAIL: "+cen_list.get(position).getCen_em());
        ph.setText("CONTACT: "+cen_list.get(position).getCen_ph());
        ad.setText("ADDRESS: "+cen_list.get(position).getCen_add());
        wr.setText("OPEN HOURS: "+cen_list.get(position).getCen_wrk());

        String image=cen_list.get(position).getCen_pic();
        try {
            byte[] imageAsBytes = Base64.decode(image.getBytes());

            im.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        } catch (IOException e) {

            e.printStackTrace();
        }
        return view;
    }
}