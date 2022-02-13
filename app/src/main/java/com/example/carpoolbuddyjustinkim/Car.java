package com.example.carpoolbuddyjustinkim;

public class Car extends Vehicles
{


    public Car(String owner, String model, int capacity, int rating, String maker, String type, boolean open)
    {
        super(owner, model, capacity, rating, maker, type, open);
    }

    public int range;
    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }
}

