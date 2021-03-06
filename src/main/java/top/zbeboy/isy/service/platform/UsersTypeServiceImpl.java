package top.zbeboy.isy.service.platform;

import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.zbeboy.isy.domain.tables.daos.UsersTypeDao;
import top.zbeboy.isy.domain.tables.pojos.Users;
import top.zbeboy.isy.domain.tables.records.UsersTypeRecord;

import javax.annotation.Resource;

import static top.zbeboy.isy.domain.tables.UsersType.USERS_TYPE;

/**
 * Created by lenovo on 2016-08-24.
 */
@Slf4j
@Service("usersTypeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UsersTypeServiceImpl implements UsersTypeService {

    private final DSLContext create;

    @Resource
    private UsersTypeDao usersTypeDao;

    @Resource
    private UsersService usersService;

    @Autowired
    public UsersTypeServiceImpl(DSLContext dslContext) {
        this.create = dslContext;
    }

    @Override
    public Result<UsersTypeRecord> findAll() {
        return create.selectFrom(USERS_TYPE).fetch();
    }

    @Override
    public boolean isCurrentUsersTypeName(String usersTypeName) {
        Users users = usersService.getUserFromSession();
        String usersType = usersTypeDao.fetchOneByUsersTypeId(users.getUsersTypeId()).getUsersTypeName();
        return usersTypeName.equals(usersType);
    }
}
