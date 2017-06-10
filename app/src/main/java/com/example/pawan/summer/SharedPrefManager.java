package com.example.pawan.summer;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private static final String SHARED_PREF_NAME="mysharedpref";
    private static final String KEY_USERNAME="username";
    private static final String KEY_USER_EMAIL="useremail";
    private static final String KEY_USER_ID="userid";



    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;


    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }
    public boolean userlogin(int id , String username, String email)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(KEY_USER_ID,id);
        editor .putString(KEY_USERNAME,username);
        editor.putString(KEY_USER_EMAIL,email);
        editor.apply();



     return true;
    }
public boolean isLoggedin()
{
SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    if(sharedPreferences.getString(KEY_USERNAME ,  null)!=null)
    {
        return true;

    }
    return false;

}
public boolean logout()
{
    SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.clear();
    editor.apply();
    return true;



}
public String getusername()
{
    SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    return sharedPreferences.getString(KEY_USERNAME , null);

}
public String getuseremail()
{
    SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    return sharedPreferences.getString(KEY_USER_EMAIL , null);

}




    }
