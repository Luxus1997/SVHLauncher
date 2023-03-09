package com.haoyue.svhlauncher.bean;

import java.util.Date;

public class RandomBean {

    private String code;
    private RandomData data;
    private boolean error;
    private String msg;
    private boolean success;
    private int systemSwitch;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setData(RandomData data) {
        this.data = data;
    }

    public RandomData getData() {
        return data;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public boolean getError() {
        return error;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSystemSwitch(int systemSwitch) {
        this.systemSwitch = systemSwitch;
    }

    public int getSystemSwitch() {
        return systemSwitch;
    }

    public class RandomData {
        private Record record;
        private People people;

        public void setRecord(Record record) {
            this.record = record;
        }

        public Record getRecord() {
            return record;
        }

        public void setPeople(People people) {
            this.people = people;
        }

        public People getPeople() {
            return people;
        }

        public class Record {
            private int amount;
            private Date createDt;
            private String createUid;
            private long deviceCode;
            private String deviceRnd;
            private String formId;
            private String formName;
            private String formType;
            private String id;
            private String peopleGender;
            private String peopleId;
            private String peopleName;
            private int status;
            private Date updateDt;
            private int value;

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public int getAmount() {
                return amount;
            }

            public void setCreateDt(Date createDt) {
                this.createDt = createDt;
            }

            public Date getCreateDt() {
                return createDt;
            }

            public void setCreateUid(String createUid) {
                this.createUid = createUid;
            }

            public String getCreateUid() {
                return createUid;
            }

            public void setDeviceCode(long deviceCode) {
                this.deviceCode = deviceCode;
            }

            public long getDeviceCode() {
                return deviceCode;
            }

            public void setDeviceRnd(String deviceRnd) {
                this.deviceRnd = deviceRnd;
            }

            public String getDeviceRnd() {
                return deviceRnd;
            }

            public void setFormId(String formId) {
                this.formId = formId;
            }

            public String getFormId() {
                return formId;
            }

            public void setFormName(String formName) {
                this.formName = formName;
            }

            public String getFormName() {
                return formName;
            }

            public void setFormType(String formType) {
                this.formType = formType;
            }

            public String getFormType() {
                return formType;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getId() {
                return id;
            }

            public void setPeopleGender(String peopleGender) {
                this.peopleGender = peopleGender;
            }

            public String getPeopleGender() {
                return peopleGender;
            }

            public void setPeopleId(String peopleId) {
                this.peopleId = peopleId;
            }

            public String getPeopleId() {
                return peopleId;
            }

            public void setPeopleName(String peopleName) {
                this.peopleName = peopleName;
            }

            public String getPeopleName() {
                return peopleName;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getStatus() {
                return status;
            }

            public void setUpdateDt(Date updateDt) {
                this.updateDt = updateDt;
            }

            public Date getUpdateDt() {
                return updateDt;
            }

            public void setValue(int value) {
                this.value = value;
            }

            public int getValue() {
                return value;
            }
        }

        public class People {
            private int age;
            private Date createDt;
            private String createUid;
            private int gender;
            private int height;
            private String id;
            private String name;
            private int weight;

            public void setAge(int age) {
                this.age = age;
            }

            public int getAge() {
                return age;
            }

            public void setCreateDt(Date createDt) {
                this.createDt = createDt;
            }

            public Date getCreateDt() {
                return createDt;
            }

            public void setCreateUid(String createUid) {
                this.createUid = createUid;
            }

            public String getCreateUid() {
                return createUid;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public int getGender() {
                return gender;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public int getHeight() {
                return height;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getId() {
                return id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }

            public void setWeight(int weight) {
                this.weight = weight;
            }

            public int getWeight() {
                return weight;
            }
        }
    }

}
