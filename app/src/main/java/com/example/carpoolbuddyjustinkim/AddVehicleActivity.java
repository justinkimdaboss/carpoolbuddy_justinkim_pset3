package com.example.carpoolbuddyjustinkim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AddVehicleActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{

    // establishing the essential EditTexts + Firebase properties//
    private EditText carModel;
    private EditText capacity;
    private EditText rating;
    private FirebaseUser currUser;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private EditText owner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currUser = mAuth.getCurrentUser();
        owner = (EditText) findViewById(R.id.nameYessir);
        carModel = (EditText) findViewById(R.id.carModelYessir);
        capacity = (EditText) findViewById(R.id.capacityCarYessir);
        rating = (EditText) findViewById(R.id.ratingVehicleYessir);

        // code for spinner - direct reference to spinner can be found in strings.xml in values folder//
        Spinner spinner = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    /**
     *
     * @param v parameter represents the, in this case, button that has received the click event.
     *         this will execute the exportation of all relevant data to VehicleInfoActivity
     *          the relevant data includes the type of the vehicle, the owner's ID, the capacity, the rating, and the open/closed status
     */

    public void addVehicle(View v)
    {
        Spinner mySpinner = (Spinner)findViewById(R.id.spinner2);
        String text = mySpinner.getSelectedItem().toString();
        System.out.println("Button Clicked");
        String ownerInput = owner.getText().toString();
        String carModelInput = carModel.getText().toString();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String maker = userID;
        String capacityInput = capacity.getText().toString();
        String ratingInput = rating.getText().toString();
        boolean openInput = true;
        int ratingConverter = 0;
        int capacityConverter = 0;

        // make all users' inputs strings, but convert ratingInput and capacityInput into integers with try and catch//
        // purpose of converting to int types is to comply with the type it was assigned in the Vehicles class//

        try
        {
            ratingConverter = Integer.parseInt(ratingInput);
            capacityConverter = Integer.parseInt(capacityInput);
        }
        catch(Exception e)
        {
            Toast.makeText(AddVehicleActivity.this, "Enter a number for the capacity and rating", Toast.LENGTH_SHORT).show();
            return;
        }


        // outline possibilities for each vehicle type and input all information (including type-specific information) into the Firebase database//
        // after input is inside the database, go to VehicleInfoActivity class//
        if(text.equals("Car"))
        {
            Car carObject = new Car(ownerInput, carModelInput, capacityConverter, ratingConverter, maker, text, openInput);
            firestore.collection("Vehicles").document(ownerInput).set(carObject);
            Toast.makeText(AddVehicleActivity.this, "Success!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, VehicleInfoActivity.class);
            startActivity(intent);
        }

        if(text.equals("Bicycle"))
        {
            Bicycle bicycleObject = new Bicycle(ownerInput, carModelInput, capacityConverter, ratingConverter, maker, text, openInput);
            firestore.collection("Vehicles").document(ownerInput).set(bicycleObject);
            Toast.makeText(AddVehicleActivity.this, "Success!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, VehicleInfoActivity.class);
            startActivity(intent);
        }

        if(text.equals("Helicopter"))
        {
            Helicopter helicopterObject = new Helicopter(ownerInput, carModelInput, capacityConverter, ratingConverter, maker, text, openInput);
            firestore.collection("Vehicles").document(ownerInput).set(helicopterObject);
            Toast.makeText(AddVehicleActivity.this, "Success!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, VehicleInfoActivity.class);
            startActivity(intent);
        }
        if(text.equals("Segway"))
        {
            Segway segwayObject = new Segway(ownerInput, carModelInput, capacityConverter, ratingConverter, maker, text, openInput);
            firestore.collection("Vehicles").document(ownerInput).set(segwayObject);
            Toast.makeText(AddVehicleActivity.this, "Success!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, VehicleInfoActivity.class);
            startActivity(intent);
        }

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }

}

