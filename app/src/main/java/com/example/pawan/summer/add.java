package com.example.pawan.summer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class add extends AppCompatActivity implements View.OnClickListener {
    private ProgressDialog progressDialog;
    private EditText nametext,citytext,addresstext,descriptiontext,featuretext;
    private ImageView image ;
    private Button buttonadd,imagetoupload;
    private static final int RESULT_LOAD_IMAGE=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        if(!SharedPrefManager.getInstance(this).isLoggedin())
        {


            finish();
            startActivity(new Intent(this , Login.class));
            return;
        }
          image = (ImageView) findViewById(R.id.image);
          imagetoupload = (Button) findViewById(R.id.imagetoupload);
          nametext=(EditText) findViewById(R.id.nametext);
        citytext=(EditText) findViewById(R.id.citytext);
        addresstext=(EditText) findViewById(R.id.addresstext);
        descriptiontext=(EditText) findViewById(R.id.descriptiontext);
        featuretext=(EditText) findViewById(R.id.featuretext);
        buttonadd = (Button) findViewById(R.id.addentry);
        buttonadd.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        imagetoupload.setOnClickListener(this);


    }
    private void addentry() {

        final String name = nametext.getText().toString().trim();
        final String city = citytext.getText().toString().trim();
        final String address = addresstext.getText().toString().trim();
        final String description = descriptiontext.getText().toString().trim();
        final String feature = featuretext.getText().toString().trim();

        progressDialog.setMessage("Adding Up");
        progressDialog.show();

        StringRequest stringRequestadd = new StringRequest(Request.Method.POST, Constants.URL_ADD
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                 progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name" , name);
                params.put("city" , city);
                params.put("address" , address);
                params.put("description" , description);
                params.put("feature" , feature);

                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequestadd);

    }



    @Override
    public void onClick(View view) {

       if(view == buttonadd)
       {
           addentry();

       }else if(view == imagetoupload)
       {

           Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
           startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE);

       }



        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RESULT_LOAD_IMAGE && resultCode==RESULT_OK && data!=null)
        {
            Uri selectedImage=data.getData();
            image.setImageURI(selectedImage);


        }



    }



    }

