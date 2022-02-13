package com.example.carpoolbuddyjustinkim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import android.widget.ImageView;

import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VehicleProfileActivity extends AppCompatActivity {

    private FirebaseUser currUser;
    private View button;
    private String vehicleOwner;
    private String capacityOwner;
    private String vehicleType;
    private FirebaseAuth mAuth;
    private String userID;
    private String makerID;
    private int capacityInt;
    TextView id;
    TextView daOwner;
    TextView daCapacity;
        TextView daRiders;
    private ArrayList<String> riderUIDs = new ArrayList<>();
    public boolean open;


    private FirebaseFirestore firestore;

    ImageView myImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        mAuth = FirebaseAuth.getInstance();
        currUser = mAuth.getCurrentUser();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_profile);

        // SerializableExtra properties (passed from VehicleInfoActivity)
        vehicleType = (String) getIntent().getSerializableExtra("type");
        vehicleOwner = (String) getIntent().getSerializableExtra("owner");
        capacityOwner = (String) getIntent().getSerializableExtra("capacity");
        makerID = (String)getIntent().getSerializableExtra("maker");
        userID = (String)getIntent().getSerializableExtra("userID");

        // convert capacity to an integer
        capacityInt = Integer.parseInt(capacityOwner);

        // set the TextViews accordingly
         daRiders = findViewById(R.id.riderUIDs);
         id = findViewById(R.id.IDTextView);
         daOwner = findViewById(R.id.vehicleOwnerTextView);
         daCapacity = findViewById(R.id.vehicleCapacityTextView);
        daOwner.setText("vehicleOwner" + vehicleOwner);
        daCapacity.setText("Capacity:" +  capacityOwner);
        id.setText("Maker's ID" + makerID);
        button = findViewById(R.id.button);

        firestore = FirebaseFirestore.getInstance();

        //Image
        myImageView = findViewById(R.id.imageFilterView);


        // Extension - setting image according to type, so clients have a reference to what vehicles look like
        if(vehicleType.equals("Bicycle"))
        {
            myImageView.setImageDrawable(getResources().getDrawable(R.drawable.bike, getApplicationContext().getTheme()));
//            myImageView.setBackgroundResource(R.drawable.bike);
            Toast.makeText(VehicleProfileActivity.this, "Respect", Toast.LENGTH_LONG).show();

        }
        if(vehicleType.equals("Segway"))
        {
            myImageView.setImageResource(R.drawable.swegwayy);
            Toast.makeText(VehicleProfileActivity.this, "Respect!", Toast.LENGTH_LONG).show();
        }
        if(vehicleType.equals("Helicopter"))
        {
            myImageView.setImageResource(R.drawable.helicopter);
            Toast.makeText(VehicleProfileActivity.this, "Shame!", Toast.LENGTH_LONG).show();
        }
        if(vehicleType.equals("Car"))
        {
            myImageView.setImageResource(R.drawable.tesla);
            Toast.makeText(VehicleProfileActivity.this, "not bad! consider biking or using a swegway though!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     *
     * @param v represents the clicked event in the form of a button
     *          will check if the capacity of the clicked vehicle is greater than 0 and hence valid
     *          if so, it will check if the vehicle is open
     *          if it is open, it will update the capacity after getting booked and store the booking user's id in the ArrayList of riders' IDs
     *          if not, it will tell the current user the vehicle is closed, prompting them to look for another vehicle
     *          if there is no space because the capacity is less than or equal to zero, the current user will be taken back to the VehicleInfoActivity page
     */

    public void bookVehicle(View v)
    {
        int rideCapacity = capacityInt - 1;

        if (capacityInt > 0)
        {

            firestore.collection("Vehicles").document(vehicleOwner).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful())
                            {
                                open = task.getResult().toObject(Vehicles.class).isOpen();

                                if (open == true)
                                {
                                    capacityInt = rideCapacity;
                                    capacityOwner = String.valueOf(capacityInt);
                                    daCapacity.setText(capacityOwner);
                                    firestore.collection("Vehicles").document(vehicleOwner).update("capacity", capacityInt);

                                    riderUIDs.add(userID);
                                    for (String riderUID : riderUIDs)
                                    {
                                        daRiders.append(riderUID + ",");
                                        firestore.collection("Vehicles").document(vehicleOwner).update("ridersUIDs", FieldValue.arrayUnion(riderUID));
                                        Toast.makeText(VehicleProfileActivity.this, "BOOKED!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(VehicleProfileActivity.this, "IT'S CLOSED", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });

        }
        else
        {
            Intent intent = new Intent(getApplicationContext(),VehicleInfoActivity.class);
            Toast.makeText(this, "no more space!", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        }



//      if()
//        {
//
//        }

//        button.setText("booking complete");
    }

    public void closeVehicle(View v)
    {
        if (userID.equals(makerID))
        {
            firestore.collection("Vehicles").document(vehicleOwner).update("open", false);
            Toast.makeText(VehicleProfileActivity.this, "CLOSED!", Toast.LENGTH_SHORT).show();

        }

    }
    public void openVehicle(View v)
    {
        if (userID.equals(makerID))
        {
            firestore.collection("Vehicles").document(vehicleOwner).update("open", true);
            Toast.makeText(VehicleProfileActivity.this, "opened!", Toast.LENGTH_SHORT).show();

        }

    }
}