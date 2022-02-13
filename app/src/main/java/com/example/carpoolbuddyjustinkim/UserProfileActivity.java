package com.example.carpoolbuddyjustinkim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class UserProfileActivity extends AppCompatActivity
{

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
    }

    /**
     *
     * @param v represents the clicked event in the form of a button
     *          when this method is activated, the current user will get taken to the AuthActivity page
     */
    public void signOut(View v)
    {
        // take users back to homepage
        mAuth.signOut();
        Log.d("Sign Out!", "Success!");
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     *
     * @param v represents the clicked event in the form of a button
     *          when this method is activated, it will first check if the current user is null or not
     *          if the current user is null, they will be shown an error prompting them to try again
     *          if the current user is not null, they will be taken to the VehicleInfoActivity page
     */
    public void seeVehicles(View v)
    {
        if (mUser != null)
        {
            // go to VehicleInfoActivity Page

            Intent intent = new Intent(this, VehicleInfoActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            Toast.makeText(UserProfileActivity.this, "Error, try again", Toast.LENGTH_SHORT ).show();
        }
    }

}