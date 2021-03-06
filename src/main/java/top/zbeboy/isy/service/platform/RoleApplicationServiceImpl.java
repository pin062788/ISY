package top.zbeboy.isy.service.platform;

import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.zbeboy.isy.domain.tables.daos.RoleApplicationDao;
import top.zbeboy.isy.domain.tables.pojos.RoleApplication;
import top.zbeboy.isy.domain.tables.records.RoleApplicationRecord;
import top.zbeboy.isy.web.util.SmallPropsUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static top.zbeboy.isy.domain.Tables.ROLE_APPLICATION;

/**
 * Created by lenovo on 2016/9/29.
 */
@Slf4j
@Service("roleApplicationService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RoleApplicationServiceImpl implements RoleApplicationService {

    private final DSLContext create;

    @Resource
    private RoleApplicationDao roleApplicationDao;

    @Autowired
    public RoleApplicationServiceImpl(DSLContext dslContext) {
        this.create = dslContext;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(RoleApplication roleApplication) {
        create.insertInto(ROLE_APPLICATION)
                .set(ROLE_APPLICATION.ROLE_ID, roleApplication.getRoleId())
                .set(ROLE_APPLICATION.APPLICATION_ID, roleApplication.getApplicationId())
                .execute();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(List<RoleApplication> roleApplication) {
        roleApplicationDao.insert(roleApplication);
    }

    @Override
    public void deleteByApplicationId(String applicationId) {
        create.deleteFrom(ROLE_APPLICATION)
                .where(ROLE_APPLICATION.APPLICATION_ID.eq(applicationId))
                .execute();
    }

    @Override
    public void deleteByRoleId(String roleId) {
        create.deleteFrom(ROLE_APPLICATION)
                .where(ROLE_APPLICATION.ROLE_ID.in(roleId))
                .execute();
    }

    @Override
    public Result<RoleApplicationRecord> findByRoleId(String roleId) {
        return create.selectFrom(ROLE_APPLICATION)
                .where(ROLE_APPLICATION.ROLE_ID.eq(roleId))
                .fetch();
    }

    @Override
    public void batchSaveRoleApplication(String applicationIds, String roleId) {
        if (StringUtils.hasLength(applicationIds)) {
            List<String> ids = SmallPropsUtils.StringIdsToStringList(applicationIds);
            List<RoleApplication> roleApplications = new ArrayList<>();
            ids.forEach(id -> roleApplications.add(new RoleApplication(roleId, id)));
            save(roleApplications);
        }
    }
}
