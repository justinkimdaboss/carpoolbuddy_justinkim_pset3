package com.example.carpoolbuddyjustinkim;

public class Teacher extends User
{

    public Teacher(String uid, String name, String email, String userType, double priceMultiplier, String inSchoolTitle)
    {
        super(uid, name, email, userType, priceMultiplier);
        this.inSchoolTitle = inSchoolTitle;
    }


    private String inSchoolTitle;

    public String getInSchoolTitle() {
        return inSchoolTitle;
    }

    public void setInSchoolTitle(String inSchoolTitle) {
        this.inSchoolTitle = inSchoolTitle;
    }
}
