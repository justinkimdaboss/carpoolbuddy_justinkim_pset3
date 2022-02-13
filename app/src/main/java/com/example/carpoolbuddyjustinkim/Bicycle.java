package com.example.carpoolbuddyjustinkim;

public class Bicycle extends Vehicles{

    public Bicycle(String owner, String model, int capacity, int rating, String maker, String type, boolean open)
    {
        super(owner, model, capacity, rating, maker, type, open);
    }

    public String bicycleType;
    public int weight;
    public int weightCapacity;

    public String getBicycleType() {
        return bicycleType;
    }

    public void setBicycleType(String bicycleType) {
        this.bicycleType = bicycleType;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeightCapacity() {
        return weightCapacity;
    }

    public void setWeightCapacity(int weightCapacity) {
        this.weightCapacity = weightCapacity;
    }
}
