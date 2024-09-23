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

import com.example.pet_vet.PetParent.Pet_parent_consult_doc;
import com.example.pet_vet.PetParent.Pet_parent_view_doc;
import com.example.pet_vet.R;
import com.example.pet_vet.VeterinaryClinic.Vet_schedule_appointment;
import com.example.pet_vet.common.Base64;
import com.example.pet_vet.common.Petvet_model;

import java.io.IOException;
import java.util.List;

public class Clinic_view_req_adaptor extends ArrayAdapter<Petvet_model> {

    Activity context;
    List<Petvet_model> do_list;
    Button schedule;
    String cons_id,doc_id,uid,iss,o_na,docnam,cl_id;

    public Clinic_view_req_adaptor(Activity context, List<Petvet_model> do_list) {
        super(context, R.layout.activity_clinic_view_req_adaptor, do_list);
        this.do_list = do_list;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_clinic_view_req_adaptor, null, true);

        TextView pn=view.findViewById(R.id.pnam);
        TextView dn=view.findViewById(R.id.dnam);
        TextView i=view.findViewById(R.id.iss);
        TextView d=view.findViewById(R.id.date);
        TextView de=view.findViewById(R.id.des);
        ImageView im=view.findViewById(R.id.imgei);
        schedule=view.findViewById(R.id.btnsch);
        schedule.setOnClickListener(v -> {
            cons_id=do_list.get(position).getConsu_id();
            cl_id=do_list.get(position).getCons_cid();
            doc_id=do_list.get(position).getCons_did();
            docnam=do_list.get(position).getCons_dnam();
            uid=do_list.get(position).getCons_uid();
            iss=do_list.get(position).getCons_issue();
            o_na=do_list.get(position).getOw_nam();
            Intent intent=new Intent(getContext(), Vet_schedule_appointment.class);
            Bundle bundle=new Bundle();
            bundle.putString("d_id",doc_id);
            bundle.putString("clid",cl_id);
            bundle.putString("dnam",docnam);
            bundle.putString("consid",cons_id);
            bundle.putString("uid",uid);
            bundle.putString("issue",iss);
            bundle.putString("nam",o_na);
            intent.putExtras(bundle);
            getContext().startActivity(intent);
        });
        pn.setText("REQUEST FROM: "+do_list.get(position).getOw_nam());
        dn.setText("TO: "+do_list.get(position).getCons_dnam());
        i.setText("ISSUE: "+do_list.get(position).getCons_issue());
        de.setText("STARTED FROM: "+do_list.get(position).getCons_date());
        d.setText("DESCRPTION: "+do_list.get(position).getCons_des());

        String image=do_list.get(position).getCons_pic();
        try {
            byte[] imageAsBytes = Base64.decode(image.getBytes());

            im.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        } catch (IOException e) {

            e.printStackTrace();
        }
        return view;
    }
}