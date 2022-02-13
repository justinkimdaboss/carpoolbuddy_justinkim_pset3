package com.example.carpoolbuddyjustinkim;

import java.util.ArrayList;

public class Parent extends User
{
    private ArrayList<String> childrenUIDs = new ArrayList<String>();

    public Parent(String uid, String name, String email, String userType, double priceMultiplier)
    {
        super(uid,name, email, userType, priceMultiplier );
    }

    public ArrayList<String> getChildrenUIDs()
    {
        return childrenUIDs;
    }

    public void setChildrenUIDs(ArrayList<String> childrenUIDs)
    {
        this.childrenUIDs = childrenUIDs;
    }
}
