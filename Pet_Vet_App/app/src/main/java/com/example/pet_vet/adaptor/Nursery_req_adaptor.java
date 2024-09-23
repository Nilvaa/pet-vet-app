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

import com.example.pet_vet.PetParent.Pet_parent_req_dog_walker;
import com.example.pet_vet.PetParent.Pet_parent_view_dog_walker;
import com.example.pet_vet.R;
import com.example.pet_vet.VeterinaryClinic.Vet_schedule_appointment;
import com.example.pet_vet.common.Base64;
import com.example.pet_vet.common.Petvet_model;
import com.example.pet_vet.petnursery.Petnursery_sent_pic;

import java.io.IOException;
import java.util.List;

public class Nursery_req_adaptor extends ArrayAdapter<Petvet_model> {

    Activity context;
    List<Petvet_model> d_list;
    Button request;
    String onam,oid,rid,uid;

    public Nursery_req_adaptor(Activity context, List<Petvet_model> d_list) {
        super(context, R.layout.activity_nursery_req_adaptor, d_list);
        this.d_list = d_list;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_nursery_req_adaptor, null, true);

        TextView na=view.findViewById(R.id.nam);
        TextView ge=view.findViewById(R.id.gen);
        TextView p=view.findViewById(R.id.ph);
        TextView d=view.findViewById(R.id.date);
        TextView f=view.findViewById(R.id.food);
        TextView w=view.findViewById(R.id.wa);
        TextView de=view.findViewById(R.id.de);
        ImageView im=view.findViewById(R.id.imge);
        request=view.findViewById(R.id.btnpic);
        na.setText("OWNER'S NAME: "+d_list.get(position).getOw_nam());
        ge.setText("PET'S GENDER: "+d_list.get(position).getPet_gen());
        p.setText("CONTACT: "+d_list.get(position).getO_phone());
        d.setText("DATE: "+d_list.get(position).getN_date());
        f.setText("FOOD MENU: "+d_list.get(position).getN_food());
        w.setText("WATER MENU: "+d_list.get(position).getN_water());
        de.setText("SPECIAL INTSRUCTIONS: "+d_list.get(position).getN_des());

        request.setOnClickListener(v -> {
            onam=d_list.get(position).getOw_nam();
            oid=d_list.get(position).getOw_id();
            rid=d_list.get(position).getN_reid();
            uid=d_list.get(position).getN_uid();
            Intent intent=new Intent(getContext(), Petnursery_sent_pic.class);
            Bundle bundle=new Bundle();
            bundle.putString("o_id",oid);
            bundle.putString("o_nam",onam);
            bundle.putString("rid",rid);
            bundle.putString("uid",uid);
            intent.putExtras(bundle);
            getContext().startActivity(intent);
        });

        String image=d_list.get(position).getPet_pic();
        try {
            byte[] imageAsBytes = Base64.decode(image.getBytes());

            im.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        } catch (IOException e) {

            e.printStackTrace();
        }
        return view;
    }
}