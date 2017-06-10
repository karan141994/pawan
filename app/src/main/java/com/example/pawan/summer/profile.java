package com.example.pawan.summer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class profile extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        if (!SharedPrefManager.getInstance(this).isLoggedin()) {
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())

        {
            case R.id.logoutmenu:
                SharedPrefManager.getInstance(this).logout();
                break;
            case R.id.add:
           startActivity(new Intent(getApplicationContext(),add.class));




        }
        return true;
    }
}


