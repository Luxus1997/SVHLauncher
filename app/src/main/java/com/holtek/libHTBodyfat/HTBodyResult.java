package com.holtek.libHTBodyfat;

import java.util.Hashtable;

public class HTBodyResult {

    public double htBMI;
    public Hashtable<String, String> htBMIRatingList;
    public int htBMR;
    public Hashtable<String, String> htBMRRatingList;
    public int htBodyAge;
    public int htBodyScore;
    public int htBodyType;
    public double htBodyfatFreeMass;
    public double htBodyfatKg;
    public double htBodyfatPercentage;
    public Hashtable<String, String> htBodyfatRatingList;
    public double htBodyfatSubcut;
    public double htBodyfatSubcutKg;
    public Hashtable<String, String> htBodyfatSubcutList;
    public double htBoneKg;
    public Hashtable<String, String> htBoneRatingList;
    public int htErrorType;
    public Hashtable<String, String> htExercisePlannerList;
    public double htIdealWeightKg;
    public double htMuscleKg;
    public double htMusclePercentage;
    public Hashtable<String, String> htMuscleRatingList;
    public double htProteinPercentage;
    public Hashtable<String, String> htProteinRatingList;
    public int htVFAL;
    public Hashtable<String, String> htVFALRatingList;
    public double htWaterPercentage;
    public Hashtable<String, String> htWaterRatingList;

    public HTBodyResult() {
        htBMIRatingList = new Hashtable<>();
        htBMRRatingList = new Hashtable<>();
        htVFALRatingList = new Hashtable<>();
        htBoneRatingList = new Hashtable<>();
        htBodyfatRatingList = new Hashtable<>();
        htWaterRatingList = new Hashtable<>();
        htMuscleRatingList = new Hashtable<>();
        htProteinRatingList = new Hashtable<>();
        htBodyfatSubcutList = new Hashtable<>();
        htExercisePlannerList = new Hashtable<>();
    }

    public int getBodyfatWithBasicInfo(HTBodyBasicInfo htBodyBasicInfo) {
        return htBodyBasicInfo.ErrorNone;
    }

}