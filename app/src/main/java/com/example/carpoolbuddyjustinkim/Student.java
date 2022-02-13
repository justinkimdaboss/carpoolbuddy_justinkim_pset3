package com.example.carpoolbuddyjustinkim;

import java.util.ArrayList;

public class Student extends User
{

    public Student(String uid, String name, String email, String userType, double priceMultiplier,String graduatingYear)
    {
        super(uid, name,email, userType, priceMultiplier );
        this.graduatingYear = graduatingYear;
    }
    private String graduatingYear;
    private ArrayList<String> parentUIDs = new ArrayList<String>();

    public String getGraduatingYear() {
        return graduatingYear;
    }

    public void setGraduatingYear(String graduatingYear) {
        this.graduatingYear = graduatingYear;
    }

    public ArrayList<String> getParentUIDs() {
        return parentUIDs;
    }

    public void setParentUIDs(ArrayList<String> parentUIDs) {
        this.parentUIDs = parentUIDs;
    }
}
