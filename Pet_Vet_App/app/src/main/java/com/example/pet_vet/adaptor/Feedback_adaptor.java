package com.example.pet_vet.adaptor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.pet_vet.R;
import com.example.pet_vet.common.Petvet_model;

import java.util.List;

public class Feedback_adaptor extends ArrayAdapter<Petvet_model> {

    Activity context;
    List<Petvet_model> feedlist;


    public Feedback_adaptor(Activity context, List<Petvet_model> feedlist) {
        super(context, R.layout.activity_feedback_adaptor, feedlist);
        this.feedlist = feedlist;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_feedback_adaptor, null, true);

        TextView su=view.findViewById(R.id.sub);
        TextView na=view.findViewById(R.id.nam);
        TextView de=view.findViewById(R.id.des);
        RatingBar ra=view.findViewById(R.id.rate);
        na.setText(feedlist.get(position).getF_cen_nam());
        su.setText(feedlist.get(position).getF_sub());
        de.setText(feedlist.get(position).getF_des());
        ra.setRating(Float.valueOf(feedlist.get(position).getF_rate()));

        return view;
    }
}