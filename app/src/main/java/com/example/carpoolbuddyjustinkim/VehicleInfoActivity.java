package com.example.carpoolbuddyjustinkim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VehicleInfoActivity extends AppCompatActivity implements MyRecylerViewAdapter.ItemClickListener
{

    MyRecylerViewAdapter adapter;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private HashMap<String, Object> information;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_info);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        ArrayList<String> nameData = new ArrayList<>();
        ArrayList<String> capacityData = new ArrayList<>();
        ArrayList<String>makerData = new ArrayList<>();
        ArrayList<String>typeData = new ArrayList<>();

        VehicleInfoActivity context = this;
        firestore = FirebaseFirestore.getInstance();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getApplicationContext(),VehicleProfileActivity.class);
                        final String vehicleOwner = nameData.get(position);
                        intent.putExtra("owner", vehicleOwner);
                        final String vehicleCapacity = capacityData.get(position);
                        intent.putExtra("capacity", vehicleCapacity);
                        final String vehicleMakerID = makerData.get(position);
                        intent.putExtra("maker", vehicleMakerID);
                        final String vehicleType = typeData.get(position);
                        intent.putExtra("type", vehicleType);

                        intent.putExtra("userID", mUser.getUid());
                        startActivity(intent);
                        finish();

//Get the clicked properties (owner, capacity, owner ID, and type) from the ArrayLists containing all the properties
                        // intent.putExtra to pass these clicked properties to the VehicleProfileActivity class
                            // once this is done, go to VehicleProfileActivity Class

                    }

                    @Override public void onLongItemClick(View view, int position)
                    {
                    }
                })
        );
        firestore.collection("Vehicles").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    // Get the objects of the owner, type, capacity, and maker categories of a vehicle
                        // convert the objects to strings and store them in the string Arraylists nameData, capacityData, makerData, and typeData respectively
                    List<DocumentSnapshot> ds = task.getResult().getDocuments();
                    for(DocumentSnapshot obj: ds) {
                        Map<String, Object> objData = obj.getData();
                        String owner = (String) objData.get("owner");
                        String type = (String)objData.get("type");
                        long capacity = (long) objData.get("capacity");
                        String maker = (String)objData.get("maker");
                        String capacityConverted = String.valueOf(capacity);
                        nameData.add(owner);
                        capacityData.add(capacityConverted);
                        makerData.add(maker);
                        typeData.add(type);
                    }
                    // update the recyclerview with nameData, capacityData, makerData, and typeData

                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        adapter = new MyRecylerViewAdapter(context, nameData);
                        adapter.updateInfo(nameData, capacityData, makerData, typeData);
                        adapter.setClickListener(context);
                        recyclerView.setAdapter(adapter);

                }


            }
        });


    }

    /**
     *
     * @param v represents a clicked event in the form of a button
     *          when clicked, it will go to the AddVehicleActivity Class
     */
    public void goToAddVehicle (View v)
    {
        Intent intent = new Intent(this, AddVehicleActivity.class);
        startActivity(intent);
        finish();

    }


    @Override
    public void onItemClick(View view, int position)
    {

    }
}
