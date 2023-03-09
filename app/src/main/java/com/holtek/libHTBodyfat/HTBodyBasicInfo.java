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
    public static final String Standard1LevelA = "偏低-达标";
    public static final String Standard2aLevelA = "不足-标准";
    public static final String Standard2aLevelB = "标准-优秀";
    public static final String Standard2bLevelA = "标准-警惕";
    public static final String Standard2bLevelB = "警惕-危险";
    public static final String Standard3LevelA = "瘦-普通";
    public static final String Standard3LevelB = "普通-偏胖";
    public static final String Standard3LevelC = "偏胖-肥胖";
    public static final String Standard4LevelA = "偏瘦-标准";
    public static final String Standard4LevelB = "标准-警惕";
    public static final String Standard4LevelC = "警惕-偏胖";
    public static final String Standard4LevelD = "偏胖-肥胖";
    public static final String Standard5LevelA = "低-标准";
    public static final String Standard5LevelB = "标准-高";
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