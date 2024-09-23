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
import com.example.pet_vet.PetParent.Pet_parent_req_adoption;
import com.example.pet_vet.PetParent.Pet_parent_view_doc;
import com.example.pet_vet.R;
import com.example.pet_vet.VeterinaryClinic.Vet_schedule_appointment;
import com.example.pet_vet.common.Base64;
import com.example.pet_vet.common.Petvet_model;

import java.io.IOException;
import java.util.List;

public class Owner_view_adoption_adaptor extends ArrayAdapter<Petvet_model> {

    Activity context;
    List<Petvet_model> ad_list;
    Button request;
    String adop_id,shop_id,type,fees,bre,docnam,cl_id;

    public Owner_view_adoption_adaptor(Activity context, List<Petvet_model> ad_list) {
        super(context, R.layout.activity_owner_view_adoption_adaptor, ad_list);
        this.ad_list = ad_list;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_owner_view_adoption_adaptor, null, true);

        TextView ge=view.findViewById(R.id.gen);
        TextView ag=view.findViewById(R.id.age);
        TextView br=view.findViewById(R.id.bre);
        TextView si=view.findViewById(R.id.siz);
        TextView he=view.findViewById(R.id.heal);
        TextView fe=view.findViewById(R.id.fee);
        ImageView im=view.findViewById(R.id.imgei);
        request=view.findViewById(R.id.btnreq);
        request.setOnClickListener(v -> {
            adop_id=ad_list.get(position).getAdop_id();
            shop_id=ad_list.get(position).getAdop_shop_id();
            type=ad_list.get(position).getAdop_typ();
            bre=ad_list.get(position).getAdop_bre();
            fees=ad_list.get(position).getAdop_fees();
            Intent intent=new Intent(getContext(), Pet_parent_req_adoption.class);
            Bundle bundle=new Bundle();
            bundle.putString("adop_id",adop_id);
            bundle.putString("shop_id",shop_id);
            bundle.putString("breed",bre);
            bundle.putString("type",type);
            bundle.putString("fees",fees);
            intent.putExtras(bundle);
            getContext().startActivity(intent);
        });
        ge.setText("GENDER: "+ad_list.get(position).getAdop_gen());
        ag.setText("AGE: "+ad_list.get(position).getAdop_age());
        br.setText("BREED: "+ad_list.get(position).getAdop_bre());
        si.setText("SIZE: "+ad_list.get(position).getAdop_size());
        he.setText("HEALTH CONDITION: "+ad_list.get(position).getAdop_heal());
        fe.setText("ADOPTION FEES: Rs."+ad_list.get(position).getAdop_fees());
        String image=ad_list.get(position).getAdop_pic();
        try {
            byte[] imageAsBytes = Base64.decode(image.getBytes());

            im.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        } catch (IOException e) {

            e.printStackTrace();
        }
        return view;
    }
}