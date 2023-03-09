package com.holtek.libHTBodyfat;

import java.util.Hashtable;

public class HTBodyResultTwoLegs extends HTBodyResult {
    
    public double htZTwoLegs;

    static {
        System.loadLibrary("Bodyfat_SDK");
    }

    private native double native_bmi();

    private native double[] native_bmi_ratinglist();

    private native int native_body_age();

    private native int native_body_score();

    private native int native_body_type();

    private native double native_bodyfat_freemass();

    private native double native_bodyfat_kg();

    private native double native_bodyfat_percentage();

    private native double[] native_bodyfat_ratinglist();

    private native double native_bodyfat_subcut();

    private native double[] native_bodyfat_subcut_ratinglist();

    private native double native_bodyfat_subcutkg();

    private native double native_bone_kg();

    private native double[] native_bone_ratinglist();

    private native int native_checkbody(double htWeightKg, double htHeightCm, int htAge, int htSex);

    private native int[] native_exercise_plannerlist();

    private native int native_getbodyfat(int htTwoLegsImpedance, boolean b);

    private native double native_ideal_weight();

    private native double native_muscle_kg();

    private native double native_muscle_percentage();

    private native double[] native_muscle_ratinglist();

    private native double native_protein_percentage();

    private native double[] native_protein_ratinglist();

    private native int native_vfal();

    private native int[] native_vfal_ratinglist();

    private native double native_water_percentage();

    private native double[] native_water_ratinglist();

    private native double native_z_twolegs();

    @Override
    public int getBodyfatWithBasicInfo(HTBodyBasicInfo htBodyBasicInfo) {
        native_checkbody(htBodyBasicInfo.htWeightKg, htBodyBasicInfo.htHeightCm, htBodyBasicInfo.htAge, htBodyBasicInfo.htSex);
        int native_getbodyfat = native_getbodyfat(htBodyBasicInfo.htTwoLegsImpedance, true);
        if (native_getbodyfat == 0) {
            htBodyfatPercentage = native_bodyfat_percentage();
            htWaterPercentage = native_water_percentage();
            htBoneKg = native_bone_kg();
            htMuscleKg = native_muscle_kg();
            htVFAL = native_vfal();
            htBMR = native_bmr();
            htBMI = native_bmi();
            htIdealWeightKg = native_ideal_weight();
            htBodyAge = native_body_age();
            htProteinPercentage = native_protein_percentage();
            htBodyfatSubcut = native_bodyfat_subcut();
            htBodyfatSubcutKg = native_bodyfat_subcutkg();
            int[] native_exercise_plannerlist = native_exercise_plannerlist();
            htExercisePlannerList.put("walking", String.valueOf(native_exercise_plannerlist[0]));
            htExercisePlannerList.put("jogging", String.valueOf(native_exercise_plannerlist[1]));
            htExercisePlannerList.put("bicycle", String.valueOf(native_exercise_plannerlist[2]));
            htExercisePlannerList.put("swim", String.valueOf(native_exercise_plannerlist[3]));
            htExercisePlannerList.put("mountain_climbing", String.valueOf(native_exercise_plannerlist[4]));
            htExercisePlannerList.put("aerobic", String.valueOf(native_exercise_plannerlist[5]));
            htExercisePlannerList.put("tabletennis", String.valueOf(native_exercise_plannerlist[6]));
            htExercisePlannerList.put("tennis", String.valueOf(native_exercise_plannerlist[7]));
            htExercisePlannerList.put("football", String.valueOf(native_exercise_plannerlist[8]));
            htExercisePlannerList.put("oriental_fencing", String.valueOf(native_exercise_plannerlist[9]));
            htExercisePlannerList.put("gateball", String.valueOf(native_exercise_plannerlist[10]));
            htExercisePlannerList.put("badminton", String.valueOf(native_exercise_plannerlist[11]));
            htExercisePlannerList.put("racketball", String.valueOf(native_exercise_plannerlist[12]));
            htExercisePlannerList.put("tae_kwon_do", String.valueOf(native_exercise_plannerlist[13]));
            htExercisePlannerList.put("squash", String.valueOf(native_exercise_plannerlist[14]));
            htExercisePlannerList.put("basketball", String.valueOf(native_exercise_plannerlist[15]));
            htExercisePlannerList.put("ropejumping", String.valueOf(native_exercise_plannerlist[16]));
            htExercisePlannerList.put("golf", String.valueOf(native_exercise_plannerlist[17]));
            double[] native_bmi_ratinglist = native_bmi_ratinglist();
            htBMIRatingList.put("瘦-普通", String.valueOf(native_bmi_ratinglist[0]));
            htBMIRatingList.put("普通-偏胖", String.valueOf(native_bmi_ratinglist[1]));
            htBMIRatingList.put("偏胖-肥胖", String.valueOf(native_bmi_ratinglist[2]));
            (htBMRRatingList = new Hashtable<>()).put("偏低-达标", String.valueOf(native_bmr_ratinglist()[0]));
            double[] native_bodyfat_ratinglist = native_bodyfat_ratinglist();
            htBodyfatRatingList.put("偏瘦-标准", String.valueOf(native_bodyfat_ratinglist[0]));
            htBodyfatRatingList.put("标准-警惕", String.valueOf(native_bodyfat_ratinglist[1]));
            htBodyfatRatingList.put("警惕-偏胖", String.valueOf(native_bodyfat_ratinglist[2]));
            htBodyfatRatingList.put("偏胖-肥胖", String.valueOf(native_bodyfat_ratinglist[3]));
            double[] native_bone_ratinglist = native_bone_ratinglist();
            htBoneRatingList.put("不足-标准", String.valueOf(native_bone_ratinglist[0]));
            htBoneRatingList.put("标准-优秀", String.valueOf(native_bone_ratinglist[1]));
            double[] native_muscle_ratinglist = native_muscle_ratinglist();
            htMuscleRatingList.put("不足-标准", String.valueOf(native_muscle_ratinglist[0]));
            htMuscleRatingList.put("标准-优秀", String.valueOf(native_muscle_ratinglist[1]));
            int[] native_vfal_ratinglist = native_vfal_ratinglist();
            htVFALRatingList.put("标准-警惕", String.valueOf(native_vfal_ratinglist[0]));
            htVFALRatingList.put("警惕-危险", String.valueOf(native_vfal_ratinglist[1]));
            double[] native_water_ratinglist = native_water_ratinglist();
            htWaterRatingList.put("不足-标准", String.valueOf(native_water_ratinglist[0]));
            htWaterRatingList.put("标准-优秀", String.valueOf(native_water_ratinglist[1]));
            double[] native_bodyfat_subcut_ratinglist = native_bodyfat_subcut_ratinglist();
            htBodyfatSubcutList.put("低-标准", String.valueOf(native_bodyfat_subcut_ratinglist[0]));
            htBodyfatSubcutList.put("标准-高", String.valueOf(native_bodyfat_subcut_ratinglist[1]));
            double[] native_protein_ratinglist = native_protein_ratinglist();
            htProteinRatingList.put("不足-标准", String.valueOf(native_protein_ratinglist[0]));
            htProteinRatingList.put("标准-优秀", String.valueOf(native_protein_ratinglist[1]));
            htBodyType = native_body_type();
            htBodyScore = native_body_score();
            htMusclePercentage = native_muscle_percentage();
            htBodyfatKg = native_bodyfat_kg();
            htBodyfatFreeMass = native_bodyfat_freemass();
        } else {
            if ((native_getbodyfat & 0x4) != 0x4 && (native_getbodyfat & 0x2) != 0x2) {
                htBMI = native_bmi();
                htIdealWeightKg = native_ideal_weight();
            } else {
                htBMI = 0.0;
                htIdealWeightKg = 0.0;
            }
            htVFAL = 0;
            htBodyfatPercentage = 0.0;
            htWaterPercentage = 0.0;
            htMuscleKg = 0.0;
            htBoneKg = 0.0;
            htBMR = 0;
            htBodyAge = 0;
            htProteinPercentage = 0.0;
            htBodyType = 0;
            htBodyScore = 0;
            htMusclePercentage = 0.0;
            htBodyfatKg = 0.0;
            htBodyfatFreeMass = 0.0;
            htBodyfatSubcut = 0.0;
            htBodyfatSubcutKg = 0.0;
        }
        htZTwoLegs = native_z_twolegs();
        return htErrorType = native_getbodyfat;
    }

    public native int native_bmr();

    public native int[] native_bmr_ratinglist();
}