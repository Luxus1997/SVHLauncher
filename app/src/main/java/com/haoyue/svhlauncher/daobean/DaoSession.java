package com.haoyue.svhlauncher.daobean;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import java.util.Map;

public class DaoSession extends AbstractDaoSession {

    private final PhysicalsDao physicalsDao;
    private final DaoConfig physicalsDaoConfig;
    private final RegisterDao registerDao;
    private final DaoConfig registerDaoConfig;

    public DaoSession(Database database, IdentityScopeType identityScopeType, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> map) {
        super(database);

        (physicalsDaoConfig = map.get(PhysicalsDao.class).clone()).initIdentityScope(identityScopeType);
        (registerDaoConfig = map.get(RegisterDao.class).clone()).initIdentityScope(identityScopeType);

        physicalsDao = new PhysicalsDao(physicalsDaoConfig, this);
        registerDao = new RegisterDao(registerDaoConfig, this);

        registerDao(Physicals.class, physicalsDao);
        registerDao(Register.class, registerDao);
    }

    public void clear() {
        physicalsDaoConfig.clearIdentityScope();
        registerDaoConfig.clearIdentityScope();
    }

    public PhysicalsDao getPhysicalsDao() {
        return physicalsDao;
    }

    public RegisterDao getRegisterDao() {
        return registerDao;
    }

}