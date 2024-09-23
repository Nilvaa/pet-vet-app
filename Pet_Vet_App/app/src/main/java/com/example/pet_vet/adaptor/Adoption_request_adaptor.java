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

public class Adoption_request_adaptor extends ArrayAdapter<Petvet_model> {

    Activity context;
    List<Petvet_model> d_list;

    public Adoption_request_adaptor(Activity context, List<Petvet_model> d_list) {
        super(context, R.layout.activity_adoption_request_adaptor, d_list);
        this.d_list = d_list;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_adoption_request_adaptor, null, true);

        TextView na=view.findViewById(R.id.pnam);
        TextView am=view.findViewById(R.id.amt);
        TextView bu=view.findViewById(R.id.buyr);
        ImageView im=view.findViewById(R.id.imge);
        na.setText("BREED: "+d_list.get(position).getAd_re_breed());
        am.setText("AMOUNT PAID: Rs."+d_list.get(position).getAd_re_tot());
        bu.setText("ADOPTED BY: "+d_list.get(position).getAd_re_accnam());

        String image=d_list.get(position).getAdop_pic();
        try {
            byte[] imageAsBytes = Base64.decode(image.getBytes());

            im.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        } catch (IOException e) {

            e.printStackTrace();
        }
        return view;
    }
}