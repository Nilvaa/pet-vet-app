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
import com.example.pet_vet.PetParent.Pet_parent_consult_doc;
import com.example.pet_vet.PetParent.Pet_parent_view_doc;
import com.example.pet_vet.R;
import com.example.pet_vet.common.Base64;
import com.example.pet_vet.common.Petvet_model;

import java.io.IOException;
import java.util.List;

public class Owner_view_appointments_adaptor extends ArrayAdapter<Petvet_model> {

    Activity context;
    List<Petvet_model> dlist;
    Button feed;
    String doc_id,clid,dnam,cnam;

    public Owner_view_appointments_adaptor(Activity context, List<Petvet_model> dlist) {
        super(context, R.layout.activity_owner_view_appointments_adaptor, dlist);
        this.dlist = dlist;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_owner_view_appointments_adaptor, null, true);

        TextView na=view.findViewById(R.id.nam);
        TextView is=view.findViewById(R.id.iss);
        TextView da=view.findViewById(R.id.date);
        TextView t=view.findViewById(R.id.time);
        feed=view.findViewById(R.id.btnfeed);
        feed.setOnClickListener(v -> {

            clid=dlist.get(position).getAp_clid();
            cnam=dlist.get(position).getCen_nam();
            Intent intent=new Intent(getContext(), Pet_parent_addfeedback.class);
            Bundle bundle=new Bundle();
            bundle.putString("clname",cnam);
            bundle.putString("cl_id",clid);
            intent.putExtras(bundle);
            getContext().startActivity(intent);
        });
        na.setText("DOCTOR'S NAME: "+dlist.get(position).getAp_dnam());
        is.setText("ISSUE: "+dlist.get(position).getAp_issue());
        da.setText("APPOINTMENT DATE: "+dlist.get(position).getAp_date());
        t.setText("APPOINTMENT TIME: "+dlist.get(position).getAp_time());



        return view;
    }
}