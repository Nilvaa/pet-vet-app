package com.example.pet_vet.PetParent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pet_vet.Center_reg;
import com.example.pet_vet.Login;
import com.example.pet_vet.R;
import com.example.pet_vet.common.Base64;
import com.example.pet_vet.common.Utility;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PetParent_reg extends AppCompatActivity {
    EditText o_nam, add, email,phone, pass,pet_nam, breed,dob, des;
    Spinner type,gen;
    String nam,ad,em,ph,pas,pe_nam,ty,Dob,de,bre,ge;
    TextView Tv;
    Button signUp;
    private static final int PICK_IMAGE = 1;
    private static final int PICK_Camera_IMAGE = 2;
    private Bitmap bitmap;
    String bal = "", MAX;
    Uri imageUri;
    ImageView img, camera, gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_parent_reg);
        o_nam=findViewById(R.id.o_nam);
        add=findViewById(R.id.o_add);
        email=findViewById(R.id.o_em);
        phone=findViewById(R.id.o_phone);
        pass=findViewById(R.id.pass);
        pet_nam=findViewById(R.id.p_nam);
        breed=findViewById(R.id.breed);
        dob=findViewById(R.id.dob);
        des=findViewById(R.id.concern);
        Tv=findViewById(R.id.tv_reg);
        type=findViewById(R.id.type);
        gen=findViewById(R.id.gen);
        signUp = findViewById(R.id.btnreg);
        img = findViewById(R.id.add_image);
        camera = findViewById(R.id.cam);
        gallery = findViewById(R.id.gal);
        String pet_type[]={"My pet is a ","Dog","Cat","Birds"};

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, pet_type) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                if (position == 0) {
                    Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.paprika);
                    textView.setTypeface(typeface);
                    textView.setTextColor(Color.GRAY);
                    textView.setBackgroundColor(Color.LTGRAY);
                } else {
                    textView.setTextColor(Color.MAGENTA);
                    Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.paprika);
                    textView.setTypeface(typeface);
                }
                return view;
            }
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view;
                Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.paprika);
                textView.setTypeface(typeface);
                return view;
            }
        };
        arrayAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        type.setAdapter(arrayAdapter);

        String gender[]={"Gender of pet","Female","Male"};

        ArrayAdapter arrayAdapter2 = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, gender) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                if (position == 0) {
                    Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.paprika);
                    textView.setTypeface(typeface);
                    textView.setTextColor(Color.GRAY);
                    textView.setBackgroundColor(Color.LTGRAY);
                } else {
                    textView.setTextColor(Color.MAGENTA);
                    Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.paprika);
                    textView.setTypeface(typeface);
                }
                return view;
            }
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view;
                Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.paprika);
                textView.setTypeface(typeface);
                return view;
            }
        };
        arrayAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        gen.setAdapter(arrayAdapter2);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                String fileName = "new-photo-name.jpg";
                //create parameters for Intent with filename
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, fileName);
                values.put(MediaStore.Images.Media.DESCRIPTION, "Image captured by camera");
                //imageUri is the current activity attribute, define and save it for later usage (also in onSaveInstanceState)\Context.
                imageUri = getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                //create new Intent
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                startActivityForResult(intent, PICK_Camera_IMAGE);
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                // TODO Auto-generated method stub
                try {
                    Intent gintent = new Intent();
                    gintent.setType("image/*");
                    gintent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(gintent, "Select Picture"), PICK_IMAGE);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e(e.getClass().getName(), e.getMessage(), e);
                }
            }
        });

        Tv.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
        });
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                int year = mcurrentDate.get(Calendar.YEAR);
                int month = mcurrentDate.get(Calendar.MONTH);
                int day = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(
                        v.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {

                                selectedMonth += 1;

                                Dob = selectedMonth + "/" + selectedDay + "/" + selectedYear;
                                dob.setText(Dob);
                            }
                        },
                        year, month, day
                );

                mDatePicker.setTitle("Select Date");

                // Show the date picker dialog
                mDatePicker.show();
            }
        });

        signUp.setOnClickListener(v -> {
            nam = o_nam.getText().toString();
            em = email.getText().toString();
            ph = phone.getText().toString();
            pas = pass.getText().toString();
            pe_nam = pet_nam.getText().toString();
            ad = add.getText().toString();
            bre = breed.getText().toString();
            de=des.getText().toString();
            ty=type.getSelectedItem().toString();
            ge=gen.getSelectedItem().toString();
            if (nam.isEmpty()) {
                Snackbar.make(o_nam, "Please Enter Owner's Name", Snackbar.LENGTH_LONG)
                        .setAction("dimiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
            } else if (em.isEmpty()) {
                Snackbar.make(email, "Please Enter Email", Snackbar.LENGTH_LONG)
                        .setAction("dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();

            } else if (!em.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
                Snackbar.make(email, "Please Check Email Format", Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();

            } else if (ph.isEmpty()) {
                Snackbar.make(phone, "Please Enter Contact Number", Snackbar.LENGTH_LONG)
                        .setAction("dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
            } else if (ph.length() != 10) {

                Snackbar.make(phone, "Please Enter Correct Contact Number", Snackbar.LENGTH_LONG)
                        .setAction("dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();

            } else if (pas.isEmpty()) {
                Snackbar.make(pass, "Please Enter Password", Snackbar.LENGTH_LONG)
                        .setAction("dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
            } else if (ad.isEmpty()) {
                Snackbar.make(add, "Please Enter Address", Snackbar.LENGTH_LONG)
                        .setAction("dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
            } else if (pe_nam.isEmpty()) {
                Snackbar.make(pet_nam, "Please Enter Pet's Name", Snackbar.LENGTH_LONG)
                        .setAction("dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
            } else if (bre.isEmpty()) {
                Snackbar.make(breed, "Please Enter Pet's Breed", Snackbar.LENGTH_LONG)
                        .setAction("dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
            }else if (ge.isEmpty()) {
                Snackbar.make(gen, "Please Enter Pet's Gender", Snackbar.LENGTH_LONG)
                        .setAction("dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
            }
            else if (ty.isEmpty()) {
                Snackbar.make(type, "Please Enter Pet's Type", Snackbar.LENGTH_LONG)
                        .setAction("dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
            }
            else if (de.isEmpty()) {
                Snackbar.make(des, "Please Enter a Description", Snackbar.LENGTH_LONG)
                        .setAction("dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
            }  else {
                owner_reg();
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedImageUri = null;
        String filePath = null;
        switch (requestCode) {
            case PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    selectedImageUri = data.getData();
                }
                break;
            case PICK_Camera_IMAGE:
                if (resultCode == RESULT_OK) {
                    //use imageUri here to access the image

                    selectedImageUri = imageUri;
		 		    	/*Bitmap mPic = (Bitmap) data.getExtras().get("data");
						selectedImageUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), mPic, getResources().getString(R.string.app_name), Long.toString(System.currentTimeMillis())));*/
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(getApplicationContext(), "Picture was not taken", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Picture was not taken", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        if (selectedImageUri != null) {
            try {
                // OI FILE Manager
                String filemanagerstring = selectedImageUri.getPath();

                // MEDIA GALLERY
                String selectedImagePath = getPath(selectedImageUri);

                if (selectedImagePath != null) {
                    filePath = selectedImagePath;
                } else if (filemanagerstring != null) {
                    filePath = filemanagerstring;
                } else {
                    Toast.makeText(getApplicationContext(), "Unknown path",
                            Toast.LENGTH_LONG).show();
                    Log.e("Bitmap", "Unknown path");
                }

                if (filePath != null) {
                    decodeFile(filePath);
                } else {
                    bitmap = null;
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Internal error",
                        Toast.LENGTH_LONG).show();
                Log.e(e.getClass().getName(), e.getMessage(), e);
            }
        }
    }

    public void decodeFile(String filePath) {
        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 1024;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        bitmap = BitmapFactory.decodeFile(filePath, o2);

        img.setImageBitmap(bitmap);

        //...
        Base64.InputStream is;
        BitmapFactory.Options bfo;
        Bitmap bitmapOrg;
        ByteArrayOutputStream bao;

        bfo = new BitmapFactory.Options();
        bfo.inSampleSize = 2;
        //bitmapOrg = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/" + customImage, bfo);

        bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
        byte[] ba = bao.toByteArray();
        bal = Base64.encodeBytes(ba);


        //..

    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    void owner_reg() {
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.url, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {

                if (!response.trim().equals("failed")) {
                    Toast.makeText(PetParent_reg.this, "Sign Up successful ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Failed" + response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "my Error :" + error, Toast.LENGTH_SHORT).show();
                Log.i("My Error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("requestType", "owner_reg");
                map.put("name", nam);
                map.put("address", ad);
                map.put("phone", ph);
                map.put("email", em);
                map.put("passwd", pas);
                map.put("petname", pe_nam);
                map.put("type", ty);
                map.put("breed", bre);
                map.put("gender", ge);
                map.put("dob", Dob);
                map.put("des", de);
                map.put("image",bal);
                return map;
            }
        };
        queue.add(request);
    }
}