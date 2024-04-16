package model;

import java.io.Serializable;

public class VitalSigns implements Serializable {
    
    private int heartRate;
    private int breathRate;
    private int bloodPressure;
    private float bloodOxygenRate;
    private float bodyTemperature;
    private int glucoseRate;

    public int getHeartRate() {
        return heartRate;
    }
    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }
    public int getBreathRate() {
        return breathRate;
    }
    public void setBreathRate(int breathRate) {
        this.breathRate = breathRate;
    }
    public int getBloodPressure() {
        return bloodPressure;
    }
    public void setBloodPressure(int bloodPressure) {
        this.bloodPressure = bloodPressure;
    }
    public float getBloodOxygenRate() {
        return bloodOxygenRate;
    }
    public void setBloodOxygenRate(float bloodOxygenRate) {
        this.bloodOxygenRate = bloodOxygenRate;
    }
    public float getBodyTemperature() {
        return bodyTemperature;
    }
    public void setBodyTemperature(float bodyTemperature) {
        this.bodyTemperature = bodyTemperature;
    }
    public int getGlucoseRate() {
        return glucoseRate;
    }
    public void setGlucoseRate(int glucoseRate) {
        this.glucoseRate = glucoseRate;
    }

}
