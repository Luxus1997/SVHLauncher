package com.haoyue.svhlauncher.bean;

public class UploadBean {

    //{"code":"success","data":"140722","error":false,"msg":"成功","success":true,"systemSwitch":0}

    private String code;
    private String data;
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

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
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

}
