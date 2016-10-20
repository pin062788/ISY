package top.zbeboy.isy.service;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.math.NumberUtils;
import org.jooq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import top.zbeboy.isy.config.Workbook;
import top.zbeboy.isy.domain.tables.daos.UsersDao;
import top.zbeboy.isy.domain.tables.pojos.Users;
import top.zbeboy.isy.domain.tables.pojos.UsersType;
import top.zbeboy.isy.domain.tables.records.AuthoritiesRecord;
import top.zbeboy.isy.security.MyUserImpl;
import top.zbeboy.isy.service.util.SQLQueryUtils;
import top.zbeboy.isy.web.bean.platform.users.UsersBean;
import top.zbeboy.isy.web.bean.data.staff.StaffBean;
import top.zbeboy.isy.web.bean.data.student.StudentBean;
import top.zbeboy.isy.web.util.DataTablesUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static top.zbeboy.isy.domain.Tables.*;

/**
 * Created by lenovo on 2016-01-05.
 */
@Service("usersService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UsersServiceImpl implements UsersService {

    private final Logger log = LoggerFactory.getLogger(UsersServiceImpl.class);

    private final DSLContext create;

    private UsersDao usersDao;

    @Resource
    private UsersTypeService usersTypeService;

    @Resource
    private StudentService studentService;

    @Resource
    private StaffService staffService;

    @Resource
    private AuthoritiesService authoritiesService;

    @Autowired
    public UsersServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.usersDao = new UsersDao(configuration);
    }

    @Override
    public Users findByUsername(String username) {
        return usersDao.findById(username);
    }

    @Override
    public Users getUserFromSession() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users users = null;
        if (!ObjectUtils.isEmpty(principal) && principal instanceof MyUserImpl) {
            users = (((MyUserImpl) principal).getUsers());
        }
        return users;
    }

    @Override
    public Optional<Record> findUserSchoolInfo(Users users) {
        Optional<Record> record = Optional.empty();
        if (!ObjectUtils.isEmpty(users)) {
            int userTypeId = users.getUsersTypeId();
            UsersType usersType = usersTypeService.findByUsersTypeId(userTypeId);
            if (usersType.getUsersTypeName().equals(Workbook.STUDENT_USERS_TYPE)) {// 学生
                record = studentService.findByUsernameRelation(users.getUsername());
            } else if (usersType.getUsersTypeName().equals(Workbook.STAFF_USERS_TYPE)) {// 教职工
                record = staffService.findByUsernameRelation(users.getUsername());
            }
        }
        return record;
    }

    @Override
    public List<Users> findByMobile(String mobile) {
        return usersDao.fetchByMobile(mobile);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(Users users) {
        usersDao.insert(users);
    }

    @Override
    public void update(Users users) {
        usersDao.update(users);
    }

    @Override
    public void updateEnabled(List<String> ids, Byte enabled) {
        ids.forEach(id -> {
            create.update(USERS).set(USERS.ENABLED, enabled).where(USERS.USERNAME.eq(id)).execute();
        });
    }

    @Override
    public void deleteById(String username) {
        usersDao.deleteById(username);
    }

    @Override
    public boolean validSCDSOIsDel(Users users) {
        // 检验学校等信息是否已被注销
        UsersType usersType = usersTypeService.findByUsersTypeId(users.getUsersTypeId());
        Byte isNotDel = 0;
        boolean isDel = true;
        if (!ObjectUtils.isEmpty(usersType)) {
            switch (usersType.getUsersTypeName()) {
                case Workbook.STUDENT_USERS_TYPE: // 学生
                    Optional<Record> studentRecord = studentService.findByUsernameRelation(users.getUsername());
                    if (studentRecord.isPresent()) {
                        StudentBean studentBean = studentRecord.get().into(StudentBean.class);
                        if (Objects.equals(studentBean.getOrganizeIsDel(), isNotDel) &&
                                Objects.equals(studentBean.getScienceIsDel(), isNotDel) &&
                                Objects.equals(studentBean.getDepartmentIsDel(), isNotDel) &&
                                Objects.equals(studentBean.getCollegeIsDel(), isNotDel) &&
                                Objects.equals(studentBean.getSchoolIsDel(), isNotDel)) {
                            isDel = false;
                        }
                    }
                    break;
                case Workbook.STAFF_USERS_TYPE: // 教职工
                    Optional<Record> staffRecord = staffService.findByUsernameRelation(users.getUsername());
                    if (staffRecord.isPresent()) {
                        StaffBean staffBean = staffRecord.get().into(StaffBean.class);
                        if (Objects.equals(staffBean.getDepartmentIsDel(), isNotDel) &&
                                Objects.equals(staffBean.getCollegeIsDel(), isNotDel) &&
                                Objects.equals(staffBean.getSchoolIsDel(), isNotDel)) {
                            isDel = false;
                        }
                    }
                    break;
                case Workbook.SYSTEM_USERS_TYPE: // 系统
                    isDel = false;
                    break;
            }
        }
        return isDel;
    }

    @Override
    public Result<Record> findByUsernameWithRole(String username) {
        return create.select()
                .from(USERS)
                .leftJoin(AUTHORITIES)
                .on(USERS.USERNAME.eq(AUTHORITIES.USERNAME))
                .leftJoin(ROLE)
                .on(AUTHORITIES.AUTHORITY.eq(ROLE.ROLE_EN_NAME))
                .where(USERS.USERNAME.eq(username))
                .fetch();
    }

    @Override
    public Result<Record1<String>> findByUsernameWithRoleNoCache(String username) {
        return create.select(ROLE.ROLE_NAME)
                .from(USERS)
                .leftJoin(AUTHORITIES)
                .on(USERS.USERNAME.eq(AUTHORITIES.USERNAME))
                .leftJoin(ROLE)
                .on(AUTHORITIES.AUTHORITY.eq(ROLE.ROLE_EN_NAME))
                .where(USERS.USERNAME.eq(username))
                .fetch();
    }

    @Override
    public Result<Record> findAllByPageExistsAuthorities(DataTablesUtils<UsersBean> dataTablesUtils) {
        Select<AuthoritiesRecord> select = existsAuthoritiesSelect();
        Result<Record> records = null;
        Condition a = searchCondition(dataTablesUtils);
        if (ObjectUtils.isEmpty(a)) {
            SelectConditionStep<Record> selectConditionStep = create.select()
                    .from(USERS)
                    .join(USERS_TYPE)
                    .on(USERS.USERS_TYPE_ID.eq(USERS_TYPE.USERS_TYPE_ID))
                    .whereExists(select);
            sortCondition(dataTablesUtils, selectConditionStep);
            pagination(dataTablesUtils, selectConditionStep);
            records = selectConditionStep.fetch();
        } else {
            SelectConditionStep<Record> selectConditionStep = create.select()
                    .from(USERS)
                    .join(USERS_TYPE)
                    .on(USERS.USERS_TYPE_ID.eq(USERS_TYPE.USERS_TYPE_ID))
                    .where(a).andExists(select);
            sortCondition(dataTablesUtils, selectConditionStep);
            pagination(dataTablesUtils, selectConditionStep);
            records = selectConditionStep.fetch();
        }
        return records;
    }

    @Override
    public Result<Record> findAllByPageNotExistsAuthorities(DataTablesUtils<UsersBean> dataTablesUtils) {
        Select<AuthoritiesRecord> select = create.selectFrom(AUTHORITIES)
                .where(AUTHORITIES.USERNAME.eq(USERS.USERNAME));
        Result<Record> records = null;
        Condition a = searchCondition(dataTablesUtils);
        if (ObjectUtils.isEmpty(a)) {
            SelectConditionStep<Record> selectConditionStep = create.select()
                    .from(USERS)
                    .join(USERS_TYPE)
                    .on(USERS.USERS_TYPE_ID.eq(USERS_TYPE.USERS_TYPE_ID))
                    .whereNotExists(select);
            sortCondition(dataTablesUtils, selectConditionStep);
            pagination(dataTablesUtils, selectConditionStep);
            records = selectConditionStep.fetch();
        } else {
            SelectConditionStep<Record> selectConditionStep = create.select()
                    .from(USERS)
                    .join(USERS_TYPE)
                    .on(USERS.USERS_TYPE_ID.eq(USERS_TYPE.USERS_TYPE_ID))
                    .where(a).andNotExists(select);
            sortCondition(dataTablesUtils, selectConditionStep);
            pagination(dataTablesUtils, selectConditionStep);
            records = selectConditionStep.fetch();
        }
        return records;
    }

    @Override
    public int countAllExistsAuthorities() {
        Select<AuthoritiesRecord> select = existsAuthoritiesSelect();
        Record1<Integer> count = create.selectCount()
                .from(USERS)
                .whereExists(select)
                .fetchOne();
        return count.value1();
    }

    @Override
    public int countAllNotExistsAuthorities() {
        Select<AuthoritiesRecord> select = create.selectFrom(AUTHORITIES)
                .where(AUTHORITIES.USERNAME.eq(USERS.USERNAME));
        Record1<Integer> count = create.selectCount()
                .from(USERS)
                .whereNotExists(select)
                .fetchOne();
        return count.value1();
    }

    @Override
    public int countByConditionExistsAuthorities(DataTablesUtils<UsersBean> dataTablesUtils) {
        Select<AuthoritiesRecord> select = existsAuthoritiesSelect();
        Record1<Integer> count = null;
        Condition a = searchCondition(dataTablesUtils);
        if (ObjectUtils.isEmpty(a)) {
            SelectConditionStep<Record1<Integer>> selectConditionStep = create.selectCount()
                    .from(USERS)
                    .whereExists(select);
            count = selectConditionStep.fetchOne();
        } else {
            SelectConditionStep<Record1<Integer>> selectConditionStep = create.selectCount()
                    .from(USERS)
                    .join(USERS_TYPE)
                    .on(USERS.USERS_TYPE_ID.eq(USERS_TYPE.USERS_TYPE_ID))
                    .where(a).andExists(select);
            count = selectConditionStep.fetchOne();
        }
        return count.value1();
    }

    @Override
    public int countByConditionNotExistsAuthorities(DataTablesUtils<UsersBean> dataTablesUtils) {
        Select<AuthoritiesRecord> select = create.selectFrom(AUTHORITIES)
                .where(AUTHORITIES.USERNAME.eq(USERS.USERNAME));
        Record1<Integer> count = null;
        Condition a = searchCondition(dataTablesUtils);
        if (ObjectUtils.isEmpty(a)) {
            SelectConditionStep<Record1<Integer>> selectConditionStep = create.selectCount()
                    .from(USERS)
                    .whereNotExists(select);
            count = selectConditionStep.fetchOne();
        } else {
            SelectConditionStep<Record1<Integer>> selectConditionStep = create.selectCount()
                    .from(USERS)
                    .join(USERS_TYPE)
                    .on(USERS.USERS_TYPE_ID.eq(USERS_TYPE.USERS_TYPE_ID))
                    .where(a).andNotExists(select);
            count = selectConditionStep.fetchOne();
        }
        return count.value1();

    }

    /**
     * 组装exists条件
     *
     * @return select
     */
    public Select<AuthoritiesRecord> existsAuthoritiesSelect() {
        if(authoritiesService.isCurrentUserInRole(Workbook.SYSTEM_AUTHORITIES)){
            return create.selectFrom(AUTHORITIES)
                    .where(AUTHORITIES.USERNAME.eq(USERS.USERNAME).and(AUTHORITIES.AUTHORITY.ne(Workbook.SYSTEM_AUTHORITIES)));
        } else {
            return create.selectFrom(AUTHORITIES)
                    .where(AUTHORITIES.USERNAME.eq(USERS.USERNAME).and(AUTHORITIES.AUTHORITY.ne(Workbook.SYSTEM_AUTHORITIES)).and(AUTHORITIES.AUTHORITY.ne(Workbook.ADMIN_AUTHORITIES)));
        }
    }

    /**
     * 全局搜索条件
     *
     * @param dataTablesUtils
     * @return 搜索条件
     */
    public Condition searchCondition(DataTablesUtils<UsersBean> dataTablesUtils) {
        Condition a = null;

        JSONObject search = dataTablesUtils.getSearch();
        if (!ObjectUtils.isEmpty(search)) {
            String username = StringUtils.trimWhitespace(search.getString("username"));
            String mobile = StringUtils.trimWhitespace(search.getString("mobile"));
            String usersType = StringUtils.trimWhitespace(search.getString("usersType"));
            if (StringUtils.hasLength(username)) {
                a = USERS.USERNAME.like(SQLQueryUtils.likeAllParam(username));
            }

            if (StringUtils.hasLength(mobile)) {
                if (ObjectUtils.isEmpty(a)) {
                    a = USERS.MOBILE.like(SQLQueryUtils.likeAllParam(mobile));
                } else {
                    a = a.and(USERS.MOBILE.like(SQLQueryUtils.likeAllParam(mobile)));
                }
            }

            if (StringUtils.hasLength(usersType)) {
                int usersTypeId = NumberUtils.toInt(usersType);
                if (usersTypeId > 0) {
                    if (ObjectUtils.isEmpty(a)) {
                        a = USERS_TYPE.USERS_TYPE_ID.eq(usersTypeId);
                    } else {
                        a = a.and(USERS_TYPE.USERS_TYPE_ID.eq(usersTypeId));
                    }
                }
            }
        }
        return a;
    }

    /**
     * 数据排序
     *
     * @param dataTablesUtils
     * @param selectConditionStep
     */
    public void sortCondition(DataTablesUtils<UsersBean> dataTablesUtils, SelectConditionStep<Record> selectConditionStep) {
        String orderColumnName = dataTablesUtils.getOrderColumnName();
        String orderDir = dataTablesUtils.getOrderDir();
        boolean isAsc = orderDir.equalsIgnoreCase("asc");
        SortField<Integer> a = null;
        SortField<String> b = null;
        SortField<Byte> c = null;
        SortField<java.sql.Date> d = null;
        if (StringUtils.hasLength(orderColumnName)) {
            if (orderColumnName.equalsIgnoreCase("username")) {
                if (isAsc) {
                    b = USERS.USERNAME.asc();
                } else {
                    b = USERS.USERNAME.desc();
                }
            }

            if (orderColumnName.equalsIgnoreCase("mobile")) {
                if (isAsc) {
                    b = USERS.MOBILE.asc();
                } else {
                    b = USERS.MOBILE.desc();
                }
            }

            if (orderColumnName.equalsIgnoreCase("real_name")) {
                if (isAsc) {
                    b = USERS.REAL_NAME.asc();
                } else {
                    b = USERS.REAL_NAME.desc();
                }
            }

            if (orderColumnName.equalsIgnoreCase("role_name")) {
                if (isAsc) {
                    b = ROLE.ROLE_NAME.asc();
                } else {
                    b = ROLE.ROLE_NAME.desc();
                }
            }

            if (orderColumnName.equalsIgnoreCase("users_type_name")) {
                if (isAsc) {
                    b = USERS_TYPE.USERS_TYPE_NAME.asc();
                } else {
                    b = USERS_TYPE.USERS_TYPE_NAME.desc();
                }
            }

            if (orderColumnName.equalsIgnoreCase("enabled")) {
                if (isAsc) {
                    c = USERS.ENABLED.asc();
                } else {
                    c = USERS.ENABLED.desc();
                }
            }

            if (orderColumnName.equalsIgnoreCase("lang_key")) {
                if (isAsc) {
                    b = USERS.LANG_KEY.asc();
                } else {
                    b = USERS.LANG_KEY.desc();
                }
            }

            if (orderColumnName.equalsIgnoreCase("join_date")) {
                if (isAsc) {
                    d = USERS.JOIN_DATE.asc();
                } else {
                    d = USERS.JOIN_DATE.desc();
                }
            }

        }

        if (!ObjectUtils.isEmpty(a)) {
            selectConditionStep.orderBy(a);
        } else if (!ObjectUtils.isEmpty(b)) {
            selectConditionStep.orderBy(b);
        } else if (!ObjectUtils.isEmpty(c)) {
            selectConditionStep.orderBy(c);
        } else if (!ObjectUtils.isEmpty(d)) {
            selectConditionStep.orderBy(d);
        } else {
            selectConditionStep.orderBy(USERS.JOIN_DATE.desc());
        }
    }

    /**
     * 分页方式
     *
     * @param dataTablesUtils
     * @param selectConditionStep
     */
    public void pagination(DataTablesUtils<UsersBean> dataTablesUtils, SelectConditionStep<Record> selectConditionStep) {
        int start = dataTablesUtils.getStart();
        int length = dataTablesUtils.getLength();
        selectConditionStep.limit(start, length);
    }
}