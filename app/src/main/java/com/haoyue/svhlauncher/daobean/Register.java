package com.haoyue.svhlauncher.daobean;

public class Register {

    private Long id;
    private String recordid;
    private String devicernd;
    private String name;
    private String gender;
    private String age;
    private String formname;

    public Register() {
        this.recordid = "";
        this.devicernd = "";
        this.name = "";
        this.gender = "";
        this.age = "";
        this.formname = "";
    }

    public Register(Long id, String recordid, String devicernd, String name, String gender, String age, String formname) {
        this.recordid = "";
        this.devicernd = "";
        this.name = "";
        this.gender = "";
        this.age = "";
        this.formname = "";
        this.id = id;
        this.recordid = recordid;
        this.devicernd = devicernd;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.formname = formname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecord_id() {
        return recordid;
    }

    public void setRecord_id(String recordid) {
        this.recordid = recordid;
    }

    public String getDevicernd() {
        return devicernd;
    }

    public void setDevicernd(String devicernd) {
        this.devicernd = devicernd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getFormname() {
        return formname;
    }

    public void setFormname(String formname) {
        this.formname = formname;
    }
}
