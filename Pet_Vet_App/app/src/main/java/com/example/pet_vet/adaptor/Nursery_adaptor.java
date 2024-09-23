package com.example.pet_vet.adaptor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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

import com.example.pet_vet.PetParent.Pet_parent_req_nursery;
import com.example.pet_vet.PetParent.Pet_parent_view_appointment;
import com.example.pet_vet.PetParent.Pet_parent_view_doc;
import com.example.pet_vet.PetParent.Pet_parent_view_pic;
import com.example.pet_vet.R;
import com.example.pet_vet.common.Base64;
import com.example.pet_vet.common.Petvet_model;

import java.io.IOException;
import java.util.List;

public class Nursery_adaptor extends ArrayAdapter<Petvet_model> {

    Activity context;
    List<Petvet_model> cen_list;
    Button Req,pic;
    String clid,cnam;

    public Nursery_adaptor(Activity context, List<Petvet_model> cen_list) {
        super(context, R.layout.activity_nursery_adaptor, cen_list);
        this.cen_list = cen_list;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_nursery_adaptor, null, true);

        TextView na=view.findViewById(R.id.nam);
        TextView em=view.findViewById(R.id.em);
        TextView ad=view.findViewById(R.id.add);
        TextView l=view.findViewById(R.id.lic);
        TextView wr=view.findViewById(R.id.wr);
        TextView ph=view.findViewById(R.id.ph);
        TextView s=view.findViewById(R.id.se);
        TextView d=view.findViewById(R.id.des);
        ImageView im=view.findViewById(R.id.imge);
        pic=view.findViewById(R.id.photo);
        confirm();
        Req=view.findViewById(R.id.btnps);
        Req.setOnClickListener(v -> {

            clid=cen_list.get(position).getCen_id();
            cnam=cen_list.get(position).getCen_nam();
            Intent intent=new Intent(getContext(), Pet_parent_req_nursery.class);
            Bundle bundle=new Bundle();
            bundle.putString("cl_id",clid);
            bundle.putString("cl_nam",cnam);
            intent.putExtras(bundle);
            getContext().startActivity(intent);
        });

        pic.setOnClickListener(v -> {

            clid=cen_list.get(position).getCen_id();
            cnam=cen_list.get(position).getCen_nam();
            Intent intent=new Intent(getContext(), Pet_parent_view_pic.class);
            Bundle bundle=new Bundle();
            bundle.putString("cl_id",clid);
            bundle.putString("cl_nam",cnam);
            intent.putExtras(bundle);
            getContext().startActivity(intent);
        });

        na.setText("NAME: "+cen_list.get(position).getCen_nam());
        l.setText("LICENSE NUMBER: "+cen_list.get(position).getCen_lic());
        em.setText("EMAIL: "+cen_list.get(position).getCen_em());
        ph.setText("CONTACT: "+cen_list.get(position).getCen_ph());
        ad.setText("ADDRESS: "+cen_list.get(position).getCen_add());
        wr.setText("OPEN HOURS: "+cen_list.get(position).getCen_wrk());
        s.setText("SERVICES: "+cen_list.get(position).getCen_serv());
        d.setText("ABOUT: "+cen_list.get(position).getCen_des());

        String image=cen_list.get(position).getCen_pic();
        try {
            byte[] imageAsBytes = Base64.decode(image.getBytes());

            im.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        } catch (IOException e) {

            e.printStackTrace();
        }
        return view;
    }
    void confirm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Important");
        builder.setMessage(" Photos will be only visible if you are our customer");
        builder.setPositiveButton("Got it", (dialog, which) -> {
        });
        builder.show();
    }

}