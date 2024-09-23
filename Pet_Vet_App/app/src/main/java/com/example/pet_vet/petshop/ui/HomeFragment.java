package com.example.pet_vet.petshop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pet_vet.PetParent.Pet_parent_view_cen;
import com.example.pet_vet.R;
import com.example.pet_vet.databinding.FragmentHomeBinding;
import com.example.pet_vet.petshop.Pet_shop_view_orders;
import com.example.pet_vet.petshop.Petshop_view_adoptions;

public class HomeFragment extends Fragment {
    CardView products, adopts;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        products=root.findViewById(R.id.prod);
        adopts=root.findViewById(R.id.adop);

        products.setOnClickListener(v -> {
            Intent i=new Intent(getContext(), Pet_shop_view_orders.class);
            startActivity(i);
        });

        adopts.setOnClickListener(v -> {
            Intent i=new Intent(getContext(), Petshop_view_adoptions.class);
            startActivity(i);
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}