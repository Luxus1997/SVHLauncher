package com.haoyue.svhlauncher.operation;

import com.haoyue.svhlauncher.SVHApplication;
import com.haoyue.svhlauncher.daobean.Register;

import java.util.List;

public class RegisterOperation {

    //清除所有
    public static void deleteAll() {
        SVHApplication.getDaoInstant().getRegisterDao().deleteAll();
    }

    //获取
    public static Register queryRegister() {
        List<Register> list = (SVHApplication.getDaoInstant().getRegisterDao()).queryBuilder().list();
        if (list != null && list.size() != 0) {
            return list.get(0);
        }
        return new Register();
    }

    //添加
    public static void insertRegister(Register register) {
        SVHApplication.getDaoInstant().getRegisterDao().deleteAll();
        (SVHApplication.getDaoInstant().getRegisterDao()).insertOrReplace(register);
    }

}
