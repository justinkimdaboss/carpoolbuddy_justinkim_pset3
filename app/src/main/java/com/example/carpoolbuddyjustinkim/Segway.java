package com.example.carpoolbuddyjustinkim;

public class Segway extends Vehicles
{

    public Segway(String owner, String model, int capacity, int rating, String maker, String type , boolean open)
    {
        super(owner, model, capacity, rating, maker, type, open);
    }
    public int range;
    public int weightCapacity;

    public int getRange()
    {
        return range;
    }

    public void setRange(int range)
    {
        this.range = range;
    }

    public int getWeightCapacity()
    {
        return weightCapacity;
    }

    public void setWeightCapacity(int weightCapacity)
    {
        this.weightCapacity = weightCapacity;
    }
}

