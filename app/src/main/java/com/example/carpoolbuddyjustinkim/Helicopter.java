package com.example.carpoolbuddyjustinkim;

public class Helicopter extends Vehicles
{
    private int maxAltitude;
    private int maxAirSpeed;

    public Helicopter(String owner, String model, int rating, int capacity,String maker, String type, boolean open) {
        super(owner, model,rating, capacity, maker, type, open);
    }

    public int getMaxAltitude() {
        return maxAltitude;
    }

    public void setMaxAltitude(int maxAltitude) {
        this.maxAltitude = maxAltitude;
    }

    public int getMaxAirSpeed() {
        return maxAirSpeed;
    }

    public void setMaxAirSpeed(int maxAirSpeed) {
        this.maxAirSpeed = maxAirSpeed;
    }
}
