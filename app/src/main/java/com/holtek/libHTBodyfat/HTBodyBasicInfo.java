package com.holtek.libHTBodyfat;

public class HTBodyBasicInfo {

    public static final int ErrorAge = 1;
    public static final int ErrorHeight = 4;
    public static final int ErrorImpedance = 16;
    public static final int ErrorImpedanceLeftArm = 128;
    public static final int ErrorImpedanceLeftLeg = 32;
    public static final int ErrorImpedanceRightArm = 256;
    public static final int ErrorImpedanceRightLeg = 64;
    public static final int ErrorNone = 0;
    public static final int ErrorSex = 8;
    public static final int ErrorWeight = 2;
    public static final int HTBodyTypeLFatMuscle = 7;
    public static final int HTBodyTypeLThinMuscle = 1;
    public static final int HTBodyTypeLackofexercise = 3;
    public static final int HTBodyTypeMuscleFat = 8;
    public static final int HTBodyTypeMuscular = 2;
    public static final int HTBodyTypeObesFat = 6;
    public static final int HTBodyTypeStandard = 4;
    public static final int HTBodyTypeStandardMuscle = 5;
    public static final int HTBodyTypeThin = 0;
    public static final String PlannerAerobic = "aerobic";
    public static final String PlannerBadminton = "badminton";
    public static final String PlannerBasketball = "basketball";
    public static final String PlannerBicycle = "bicycle";
    public static final String PlannerFootball = "football";
    public static final String PlannerGateball = "gateball";
    public static final String PlannerGolf = "golf";
    public static final String PlannerJogging = "jogging";
    public static final String PlannerMountainClimbing = "mountain_climbing";
    public static final String PlannerOrientalFencing = "oriental_fencing";
    public static final String PlannerRacketball = "racketball";
    public static final String PlannerRopejumping = "ropejumping";
    public static final String PlannerSquash = "squash";
    public static final String PlannerSwim = "swim";
    public static final String PlannerTabletennis = "tabletennis";
    public static final String PlannerTaeKwonDo = "tae_kwon_do";
    public static final String PlannerTennis = "tennis";
    public static final String PlannerWalking = "walking";
    public static final int SexTypeFemale = 0;
    public static final int SexTypeMale = 1;
    public static final String Standard1LevelA = "??????-??????";
    public static final String Standard2aLevelA = "??????-??????";
    public static final String Standard2aLevelB = "??????-??????";
    public static final String Standard2bLevelA = "??????-??????";
    public static final String Standard2bLevelB = "??????-??????";
    public static final String Standard3LevelA = "???-??????";
    public static final String Standard3LevelB = "??????-??????";
    public static final String Standard3LevelC = "??????-??????";
    public static final String Standard4LevelA = "??????-??????";
    public static final String Standard4LevelB = "??????-??????";
    public static final String Standard4LevelC = "??????-??????";
    public static final String Standard4LevelD = "??????-??????";
    public static final String Standard5LevelA = "???-??????";
    public static final String Standard5LevelB = "??????-???";
    public int htAge;
    public double htHeightCm;
    public int htSex;
    public int htTwoArmsImpedance;
    public int htTwoLegsImpedance;
    public double htWeightKg;
    public int htZAllBodyImpedance;
    public int htZLeftArmImpedance;
    public int htZLeftLegImpedance;
    public int htZRightArmImpedance;
    public int htZRightLegImpedance;

    public HTBodyBasicInfo(int htSex, int htHeightCm, double htWeightKg, int htAge) {
        this.htSex = htSex;
        this.htHeightCm = htHeightCm;
        this.htWeightKg = htWeightKg;
        this.htAge = htAge;
    }
}