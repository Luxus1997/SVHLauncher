package com.haoyue.svhlauncher.daobean;

public class Physicals {

    private Long id;
    double heightCm;
    double weightKg;
    double htBMI;
    double high_pressure;
    double low_pressure;
    double heart_rate;

    double temp7;
    double temp8;
    double temp9;
    double temp10;
    double temp11;
    double temp12;

    public Physicals() {
        this.heightCm = 0;
        this.weightKg = 0;
        this.htBMI = 0;
        this.high_pressure = 0;
        this.low_pressure = 0;
        this.heart_rate = 0;
        this.temp7 = 0;
        this.temp8 = 0;
        this.temp9 = 0;
        this.temp10 = 0;
        this.temp11 = 0;
        this.temp12 = 0;
    }

    public Physicals(
            Long id, double heightCm, double weightKg, double htBMI,
            double high_pressure, double low_pressure, double heart_rate,
            double temp7, double temp8, double temp9, double temp10, double temp11, double temp12) {
        this.heightCm = 0;
        this.weightKg = 0;
        this.htBMI = 0;
        this.high_pressure = 0;
        this.low_pressure = 0;
        this.heart_rate = 0;
        this.temp7 = 0;
        this.temp8 = 0;
        this.temp9 = 0;
        this.temp10 = 0;
        this.temp11 = 0;
        this.temp12 = 0;

        this.id = id;
        this.heightCm = heightCm;
        this.weightKg = weightKg;
        this.htBMI = htBMI;
        this.high_pressure = high_pressure;
        this.low_pressure = low_pressure;
        this.heart_rate = heart_rate;
        this.temp7 = temp7;
        this.temp8 = temp8;
        this.temp9 = temp9;
        this.temp10 = temp10;
        this.temp11 = temp11;
        this.temp12 = temp12;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getHeightCm() {
        return heightCm;
    }

    public void setHeightCm(double heightCm) {
        this.heightCm = heightCm;
    }

    public double getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(double weightKg) {
        this.weightKg = weightKg;
    }

    public double getHtBMI() {
        return htBMI;
    }

    public void setHtBMI(double htBMI) {
        this.htBMI = htBMI;
    }

    public double getHigh_pressure() {
        return high_pressure;
    }

    public void setHigh_pressure(double high_pressure) {
        this.high_pressure = high_pressure;
    }

    public double getLow_pressure() {
        return low_pressure;
    }

    public void setLow_pressure(double low_pressure) {
        this.low_pressure = low_pressure;
    }

    public double getHeart_rate() {
        return heart_rate;
    }

    public void setHeart_rate(double heart_rate) {
        this.heart_rate = heart_rate;
    }

    public double getTemp7() {
        return temp7;
    }

    public void setTemp7(double temp7) {
        this.temp7 = temp7;
    }

    public double getTemp8() {
        return temp8;
    }

    public void setTemp8(double temp8) {
        this.temp8 = temp8;
    }

    public double getTemp9() {
        return temp9;
    }

    public void setTemp9(double temp9) {
        this.temp9 = temp9;
    }

    public double getTemp10() {
        return temp10;
    }

    public void setTemp10(double temp10) {
        this.temp10 = temp10;
    }

    public double getTemp11() {
        return temp11;
    }

    public void setTemp11(double temp11) {
        this.temp11 = temp11;
    }

    public double getTemp12() {
        return temp12;
    }

    public void setTemp12(double temp12) {
        this.temp12 = temp12;
    }

    public boolean isZero() {
        if (heightCm == 0 || weightKg == 0 || htBMI == 0 ||
                high_pressure == 0 || low_pressure == 0 || heart_rate == 0 ||
                temp7 == 0 || temp8 == 0 || temp9 == 0 || temp10 == 0 || temp11 == 0 || temp12 == 0) {
            return true;
        }else {
            return false;
        }
    }

    public boolean isAllZero() {
        if (heightCm == 0 && weightKg == 0 && htBMI == 0 &&
                high_pressure == 0 && low_pressure == 0 && heart_rate == 0 &&
                temp7 == 0 && temp8 == 0 && temp9 == 0 && temp10 == 0 && temp11 == 0 && temp12 == 0) {
            return true;
        }else {
            return false;
        }
    }

}
