package com.haoyue.svhlauncher.bean;

import java.util.List;

public class PhysicalsBean {

    //项目参数-描述
    public String describe;
    //项目细分
    public List<PhysicalsData> physicalsData;
    //项目类型
    public Enum<PTYPE> project_type;

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public List<PhysicalsData> getPhysicalsData() {
        return physicalsData;
    }

    public void setPhysicalsData(List<PhysicalsData> physicalsData) {
        this.physicalsData = physicalsData;
    }

    public Enum<PTYPE> getProject_type() {
        return project_type;
    }

    public void setProject_type(Enum<PTYPE> project_type) {
        this.project_type = project_type;
    }

    public static class PhysicalsData {

        //项目名称
        public String name;
        //值
        public double value;
        //正常范围
        public String range;
        //状态
        public Enum<TYPE> type;

        public PhysicalsData(String name, double value, String range, Enum<TYPE> type) {
            this.name = name;
            this.value = value;
            this.range = range;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public String getRange() {
            return range;
        }

        public void setRange(String range) {
            this.range = range;
        }

        public Enum<TYPE> getType() {
            return type;
        }

        public void setType(Enum<TYPE> type) {
            this.type = type;
        }

        public enum TYPE {
            NORMAL, HIGH, LOW;
        }
    }

    public enum PTYPE {
        Stature, Pressure, Temp;
    }

}
