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
import com.example.pet_vet.common.Base64;
import com.example.pet_vet.common.Petvet_model;

import java.io.IOException;
import java.util.List;

public class Owner_view_doc_adaptor extends ArrayAdapter<Petvet_model> {

    Activity context;
    List<Petvet_model> do_list;
    Button consult;
    String doc_id,clid,dnam;

    public Owner_view_doc_adaptor(Activity context, List<Petvet_model> do_list) {
        super(context, R.layout.activity_owner_view_doc_adaptor, do_list);
        this.do_list = do_list;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_owner_view_doc_adaptor, null, true);

        TextView na=view.findViewById(R.id.nam);
        TextView em=view.findViewById(R.id.em);
        TextView ad=view.findViewById(R.id.add);
        TextView l=view.findViewById(R.id.lic);
        TextView ph=view.findViewById(R.id.ph);
        TextView ex=view.findViewById(R.id.ex);
        TextView d=view.findViewById(R.id.des);
        ImageView im=view.findViewById(R.id.imge);
        consult=view.findViewById(R.id.btncon);
        consult.setOnClickListener(v -> {
            doc_id=do_list.get(position).getDo_id();
            dnam=do_list.get(position).getDnam();
            clid=do_list.get(position).getDo_cl_id();
            Intent intent=new Intent(getContext(), Pet_parent_consult_doc.class);
            Bundle bundle=new Bundle();
            bundle.putString("d_id",doc_id);
            bundle.putString("d_nam",dnam);
            bundle.putString("cl_id",clid);
            intent.putExtras(bundle);
            getContext().startActivity(intent);
        });
        na.setText("NAME: "+do_list.get(position).getDnam());
        em.setText("EMAIL: "+do_list.get(position).getD_em());
        ph.setText("CONTACT: "+do_list.get(position).getDph());
        ad.setText("ADDRESS: "+do_list.get(position).getDaddr());
        l.setText("LICENSE NUMBER: "+do_list.get(position).getD_lic());
        ex.setText("EXPERIENCE: "+do_list.get(position).getD_ex()+ "years");
        d.setText("ABOUT: "+do_list.get(position).getD_des());

        String image=do_list.get(position).getD_pic();
        try {
            byte[] imageAsBytes = Base64.decode(image.getBytes());

            im.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        } catch (IOException e) {

            e.printStackTrace();
        }
        return view;
    }
}