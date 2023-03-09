package com.haoyue.svhlauncher.eventtask;

public class PressureTask {

    public int high_pressure;
    public int low_pressure;
    public int heart_rate;
    public boolean FinalData;

    public PressureTask(int high_pressure, int low_pressure, int heart_rate, boolean finalData) {
        this.high_pressure = high_pressure;
        this.low_pressure = low_pressure;
        this.heart_rate = heart_rate;
        FinalData = finalData;
    }
}
