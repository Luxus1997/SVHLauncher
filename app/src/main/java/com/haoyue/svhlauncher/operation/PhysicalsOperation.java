package com.haoyue.svhlauncher.operation;

import com.haoyue.svhlauncher.SVHApplication;
import com.haoyue.svhlauncher.daobean.Physicals;

import java.util.List;

public class PhysicalsOperation {

    //清除所有
    public static void deleteAll() {
        SVHApplication.getDaoInstant().getPhysicalsDao().deleteAll();
    }

    //获取
    public static Physicals queryPhysicals() {
        List<Physicals> list = (SVHApplication.getDaoInstant().getPhysicalsDao()).queryBuilder().list();
        if (list != null && list.size() != 0) {
            return list.get(0);
        }
        return new Physicals();
    }

    //添加
    public static void insertPhysicals(Physicals physicals) {
        SVHApplication.getDaoInstant().getPhysicalsDao().deleteAll();
        (SVHApplication.getDaoInstant().getPhysicalsDao()).insertOrReplace(physicals);
    }

}
