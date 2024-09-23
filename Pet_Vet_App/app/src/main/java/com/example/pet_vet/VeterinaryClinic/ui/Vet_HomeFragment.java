package com.example.pet_vet.VeterinaryClinic.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pet_vet.adaptor.Clinic_view_req_adaptor;
import com.example.pet_vet.common.Petvet_model;
import com.example.pet_vet.databinding.FragmentVetHomeBinding;
import com.example.pet_vet.R;
import com.example.pet_vet.common.Petvet_model;
import com.example.pet_vet.common.Utility;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vet_HomeFragment extends Fragment {
    ListView cl_list;
    List<Petvet_model> arraylist;
    public Clinic_view_req_adaptor adap;
    private FragmentVetHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentVetHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        cl_list=root.findViewById(R.id.cl_view_req);
        arraylist=new ArrayList<Petvet_model>();
        clinic_view_req();
        return root;
    }
    public void clinic_view_req(){
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("******", response);
                System.out.println("EEE" + response);
                if (!response.trim().equals("failed")) {
                    Gson gson = new Gson();
                    String data = response.trim();
                    arraylist = Arrays.asList(gson.fromJson(data, Petvet_model[].class));
                    adap = new Clinic_view_req_adaptor(requireActivity(), arraylist);
                    cl_list.setAdapter(adap);
                    registerForContextMenu(cl_list);

                } else {
                    Toast.makeText(getContext(), "No data found", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "my error :" + error, Toast.LENGTH_LONG).show();
                Log.i("My error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sh = getContext().getSharedPreferences("profileprefer", Context.MODE_PRIVATE);
                String c_id=sh.getString("UID","not_data");
                Map<String, String> map = new HashMap<String, String>();
                map.put("requestType", "clinic_view_req");
                map.put("cid", c_id);
                return map;
            }
        };
        queue.add(request);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}