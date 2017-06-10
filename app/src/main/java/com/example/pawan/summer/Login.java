package com.example.pawan.summer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextusername,editTextPassword;
     private Button loginbutton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(SharedPrefManager.getInstance(this).isLoggedin())
        {
            finish();
            startActivity(new Intent(this , profile.class));

            return;
        }

        editTextusername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        loginbutton = (Button) findViewById(R.id.loginbutton);
        loginbutton.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");

    }

    private void userlogin()
    {
        final String username=editTextusername.getText().toString().trim();
        final String password=editTextPassword.getText().toString().trim();
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                         progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(!jsonObject.getBoolean("error")){
                             SharedPrefManager.getInstance(getApplicationContext())
                                     .userlogin(
                                     jsonObject.getInt("id"),
                                     jsonObject.getString("username"),
                                     jsonObject.getString("email"));
                                finish();
startActivity(new Intent(getApplicationContext() , profile.class));
                                finish();


                            }else {
                                Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError error)
            {
                  progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),error.getMessage( ),Toast.LENGTH_LONG).show();

            }
        }


        ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put("username" , username);
                params.put("password", password);
                return params;
            }
        } ;
RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }


    @Override
    public void onClick(View view) {
        if(view == loginbutton)
        {

        userlogin();

    }
    }
}
