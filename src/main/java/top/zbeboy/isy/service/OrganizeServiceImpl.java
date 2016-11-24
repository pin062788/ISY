package top.zbeboy.isy.service;

import com.alibaba.fastjson.JSONObject;
import org.jooq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import top.zbeboy.isy.config.Workbook;
import top.zbeboy.isy.domain.tables.daos.OrganizeDao;
import top.zbeboy.isy.domain.tables.pojos.Organize;
import top.zbeboy.isy.domain.tables.pojos.Users;
import top.zbeboy.isy.domain.tables.records.OrganizeRecord;
import top.zbeboy.isy.service.plugin.DataTablesPlugin;
import top.zbeboy.isy.service.util.SQLQueryUtils;
import top.zbeboy.isy.web.bean.data.organize.OrganizeBean;
import top.zbeboy.isy.web.util.DataTablesUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static top.zbeboy.isy.domain.Tables.COLLEGE;
import static top.zbeboy.isy.domain.Tables.DEPARTMENT;
import static top.zbeboy.isy.domain.Tables.ORGANIZE;
import static top.zbeboy.isy.domain.Tables.SCHOOL;
import static top.zbeboy.isy.domain.Tables.SCIENCE;

/**
 * Created by lenovo on 2016-08-21.
 */
@Service("organizeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class OrganizeServiceImpl extends DataTablesPlugin<OrganizeBean> implements OrganizeService {

    private final Logger log = LoggerFactory.getLogger(OrganizeServiceImpl.class);

    private final DSLContext create;

    private OrganizeDao organizeDao;

    @Resource
    private AuthoritiesService authoritiesService;

    @Resource
    private UsersService usersService;

    @Autowired
    public OrganizeServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.organizeDao = new OrganizeDao(configuration);
    }

    @Override
    public Result<Record1<String>> findByScienceIdAndDistinctGrade(int scienceId) {
        Byte isDel = 0;
        return create.selectDistinct(ORGANIZE.GRADE)
                .from(ORGANIZE)
                .where(ORGANIZE.SCIENCE_ID.eq(scienceId).and(ORGANIZE.ORGANIZE_IS_DEL.eq(isDel)))
                .orderBy(ORGANIZE.ORGANIZE_ID.desc())
                .limit(0, 6)
                .fetch();
    }

    @Override
    public Result<OrganizeRecord> findInScienceIdsAndGrade(List<Integer> scienceIds,String grade) {
        return create.selectFrom(ORGANIZE)
                .where(ORGANIZE.SCIENCE_ID.in(scienceIds).and(ORGANIZE.GRADE.eq(grade)))
                .fetch();
    }

    @Override
    public Result<Record1<String>> findByDepartmentIdAndDistinctGrade(int departmentId) {
        Byte isDel = 0;
        return create.selectDistinct(ORGANIZE.GRADE)
                .from(ORGANIZE)
                .join(SCIENCE)
                .on(ORGANIZE.SCIENCE_ID.eq(SCIENCE.SCIENCE_ID))
                .join(DEPARTMENT)
                .on(SCIENCE.DEPARTMENT_ID.eq(DEPARTMENT.DEPARTMENT_ID))
                .where(DEPARTMENT.DEPARTMENT_ID.eq(departmentId).and(ORGANIZE.ORGANIZE_IS_DEL.eq(isDel)))
                .orderBy(ORGANIZE.GRADE.desc())
                .limit(0, 6)
                .fetch();
    }

    @Override
    public Result<OrganizeRecord> findByOrganizeNameAndScienceIdNeOrganizeId(String organizeName, int organizeId, int scienceId) {
        return create.selectFrom(ORGANIZE)
                .where(ORGANIZE.ORGANIZE_NAME.eq(organizeName).and(ORGANIZE.SCIENCE_ID.eq(scienceId)).and(ORGANIZE.ORGANIZE_ID.ne(organizeId)))
                .fetch();
    }

    @Override
    public Result<OrganizeRecord> findByGrade(String grade) {
        Byte isDel = 0;
        return create.selectFrom(ORGANIZE)
                .where(ORGANIZE.ORGANIZE_IS_DEL.eq(isDel).and(ORGANIZE.GRADE.eq(grade)))
                .fetch();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(Organize organize) {
        organizeDao.insert(organize);
    }

    @Override
    public void update(Organize organize) {
        organizeDao.update(organize);
    }

    @Override
    public void updateIsDel(List<Integer> ids, Byte isDel) {
        for (int id : ids) {
            create.update(ORGANIZE).set(ORGANIZE.ORGANIZE_IS_DEL, isDel).where(ORGANIZE.ORGANIZE_ID.eq(id)).execute();
        }
    }

    @Override
    public Optional<Record> findByIdRelation(int id) {
        return create.select()
                .from(ORGANIZE)
                .join(SCIENCE)
                .on(ORGANIZE.SCIENCE_ID.eq(SCIENCE.SCIENCE_ID))
                .join(DEPARTMENT)
                .on(SCIENCE.DEPARTMENT_ID.eq(DEPARTMENT.DEPARTMENT_ID))
                .join(COLLEGE)
                .on(DEPARTMENT.COLLEGE_ID.eq(COLLEGE.COLLEGE_ID))
                .join(SCHOOL)
                .on(COLLEGE.SCHOOL_ID.eq(SCHOOL.SCHOOL_ID))
                .where(ORGANIZE.ORGANIZE_ID.eq(id))
                .fetchOptional();
    }

    @Override
    public Organize findById(int id) {
        return organizeDao.findById(id);
    }

    @Override
    public Result<Record> findAllByPage(DataTablesUtils<OrganizeBean> dataTablesUtils) {
        Result<Record> records = null;
        Condition a = searchCondition(dataTablesUtils);
        if (ObjectUtils.isEmpty(a)) {
            // 分权限显示用户数据
            if (authoritiesService.isCurrentUserInRole(Workbook.SYSTEM_AUTHORITIES)) { // 系统
                SelectJoinStep<Record> selectJoinStep = create.select()
                        .from(ORGANIZE)
                        .join(SCIENCE)
                        .on(ORGANIZE.SCIENCE_ID.eq(SCIENCE.SCIENCE_ID))
                        .join(DEPARTMENT)
                        .on(SCIENCE.DEPARTMENT_ID.eq(DEPARTMENT.DEPARTMENT_ID))
                        .join(COLLEGE)
                        .on(DEPARTMENT.COLLEGE_ID.eq(COLLEGE.COLLEGE_ID))
                        .join(SCHOOL)
                        .on(COLLEGE.SCHOOL_ID.eq(SCHOOL.SCHOOL_ID));
                sortCondition(dataTablesUtils, null, selectJoinStep, JOIN_TYPE);
                pagination(dataTablesUtils, null, selectJoinStep, JOIN_TYPE);
                records = selectJoinStep.fetch();
            } else if (authoritiesService.isCurrentUserInRole(Workbook.ADMIN_AUTHORITIES)) { // 管理员
                Users users = usersService.getUserFromSession();
                Optional<Record> record = usersService.findUserSchoolInfo(users);
                int collegeId = authoritiesService.getRoleCollegeId(record);
                SelectConditionStep<Record> selectConditionStep = create.select()
                        .from(ORGANIZE)
                        .join(SCIENCE)
                        .on(ORGANIZE.SCIENCE_ID.eq(SCIENCE.SCIENCE_ID))
                        .join(DEPARTMENT)
                        .on(SCIENCE.DEPARTMENT_ID.eq(DEPARTMENT.DEPARTMENT_ID))
                        .join(COLLEGE)
                        .on(DEPARTMENT.COLLEGE_ID.eq(COLLEGE.COLLEGE_ID))
                        .join(SCHOOL)
                        .on(COLLEGE.SCHOOL_ID.eq(SCHOOL.SCHOOL_ID))
                        .where(COLLEGE.COLLEGE_ID.eq(collegeId));
                sortCondition(dataTablesUtils, selectConditionStep, null, CONDITION_TYPE);
                pagination(dataTablesUtils, selectConditionStep, null, CONDITION_TYPE);
                records = selectConditionStep.fetch();
            }
        } else {
            // 分权限显示用户数据
            if (authoritiesService.isCurrentUserInRole(Workbook.SYSTEM_AUTHORITIES)) { // 系统
                SelectConditionStep<Record> selectConditionStep = create.select()
                        .from(ORGANIZE)
                        .join(SCIENCE)
                        .on(ORGANIZE.SCIENCE_ID.eq(SCIENCE.SCIENCE_ID))
                        .join(DEPARTMENT)
                        .on(SCIENCE.DEPARTMENT_ID.eq(DEPARTMENT.DEPARTMENT_ID))
                        .join(COLLEGE)
                        .on(DEPARTMENT.COLLEGE_ID.eq(COLLEGE.COLLEGE_ID))
                        .join(SCHOOL)
                        .on(COLLEGE.SCHOOL_ID.eq(SCHOOL.SCHOOL_ID))
                        .where(a);
                sortCondition(dataTablesUtils, selectConditionStep, null, CONDITION_TYPE);
                pagination(dataTablesUtils, selectConditionStep, null, CONDITION_TYPE);
                records = selectConditionStep.fetch();
            } else if (authoritiesService.isCurrentUserInRole(Workbook.ADMIN_AUTHORITIES)) { // 管理员
                Users users = usersService.getUserFromSession();
                Optional<Record> record = usersService.findUserSchoolInfo(users);
                int collegeId = authoritiesService.getRoleCollegeId(record);
                SelectConditionStep<Record> selectConditionStep = create.select()
                        .from(ORGANIZE)
                        .join(SCIENCE)
                        .on(ORGANIZE.SCIENCE_ID.eq(SCIENCE.SCIENCE_ID))
                        .join(DEPARTMENT)
                        .on(SCIENCE.DEPARTMENT_ID.eq(DEPARTMENT.DEPARTMENT_ID))
                        .join(COLLEGE)
                        .on(DEPARTMENT.COLLEGE_ID.eq(COLLEGE.COLLEGE_ID))
                        .join(SCHOOL)
                        .on(COLLEGE.SCHOOL_ID.eq(SCHOOL.SCHOOL_ID))
                        .where(COLLEGE.COLLEGE_ID.eq(collegeId).and(a));
                sortCondition(dataTablesUtils, selectConditionStep, null, CONDITION_TYPE);
                pagination(dataTablesUtils, selectConditionStep, null, CONDITION_TYPE);
                records = selectConditionStep.fetch();
            }
        }
        return records;
    }

    @Override
    public int countAll() {
        // 分权限显示用户数据
        if (authoritiesService.isCurrentUserInRole(Workbook.SYSTEM_AUTHORITIES)) { // 系统
            return statisticsAll(create, ORGANIZE);
        } else if (authoritiesService.isCurrentUserInRole(Workbook.ADMIN_AUTHORITIES)) { // 管理员
            Users users = usersService.getUserFromSession();
            Optional<Record> record = usersService.findUserSchoolInfo(users);
            int collegeId = authoritiesService.getRoleCollegeId(record);
            Record1<Integer> count = create.selectCount()
                    .from(ORGANIZE)
                    .join(SCIENCE)
                    .on(ORGANIZE.SCIENCE_ID.eq(SCIENCE.SCIENCE_ID))
                    .join(DEPARTMENT)
                    .on(SCIENCE.DEPARTMENT_ID.eq(DEPARTMENT.DEPARTMENT_ID))
                    .join(COLLEGE)
                    .on(DEPARTMENT.COLLEGE_ID.eq(COLLEGE.COLLEGE_ID))
                    .where(COLLEGE.COLLEGE_ID.eq(collegeId))
                    .fetchOne();
            return count.value1();
        }
        return 0;
    }

    @Override
    public int countByCondition(DataTablesUtils<OrganizeBean> dataTablesUtils) {
        Record1<Integer> count = null;
        Condition a = searchCondition(dataTablesUtils);
        if (ObjectUtils.isEmpty(a)) {
            // 分权限显示用户数据
            if (authoritiesService.isCurrentUserInRole(Workbook.SYSTEM_AUTHORITIES)) { // 系统
                SelectJoinStep<Record1<Integer>> selectJoinStep = create.selectCount()
                        .from(ORGANIZE);
                count = selectJoinStep.fetchOne();
            } else if (authoritiesService.isCurrentUserInRole(Workbook.ADMIN_AUTHORITIES)) { // 管理员
                Users users = usersService.getUserFromSession();
                Optional<Record> record = usersService.findUserSchoolInfo(users);
                int collegeId = authoritiesService.getRoleCollegeId(record);
                SelectConditionStep<Record1<Integer>> selectConditionStep = create.selectCount()
                        .from(ORGANIZE)
                        .join(SCIENCE)
                        .on(ORGANIZE.SCIENCE_ID.eq(SCIENCE.SCIENCE_ID))
                        .join(DEPARTMENT)
                        .on(SCIENCE.DEPARTMENT_ID.eq(DEPARTMENT.DEPARTMENT_ID))
                        .join(COLLEGE)
                        .on(DEPARTMENT.COLLEGE_ID.eq(COLLEGE.COLLEGE_ID))
                        .where(COLLEGE.COLLEGE_ID.eq(collegeId));
                count = selectConditionStep.fetchOne();
            }

        } else {
            // 分权限显示用户数据
            if (authoritiesService.isCurrentUserInRole(Workbook.SYSTEM_AUTHORITIES)) { // 系统
                SelectConditionStep<Record1<Integer>> selectConditionStep = create.selectCount()
                        .from(ORGANIZE)
                        .join(SCIENCE)
                        .on(ORGANIZE.SCIENCE_ID.eq(SCIENCE.SCIENCE_ID))
                        .join(DEPARTMENT)
                        .on(SCIENCE.DEPARTMENT_ID.eq(DEPARTMENT.DEPARTMENT_ID))
                        .join(COLLEGE)
                        .on(DEPARTMENT.COLLEGE_ID.eq(COLLEGE.COLLEGE_ID))
                        .join(SCHOOL)
                        .on(COLLEGE.SCHOOL_ID.eq(SCHOOL.SCHOOL_ID))
                        .where(a);
                count = selectConditionStep.fetchOne();
            } else if (authoritiesService.isCurrentUserInRole(Workbook.ADMIN_AUTHORITIES)) { // 管理员
                Users users = usersService.getUserFromSession();
                Optional<Record> record = usersService.findUserSchoolInfo(users);
                int collegeId = authoritiesService.getRoleCollegeId(record);
                SelectConditionStep<Record1<Integer>> selectConditionStep = create.selectCount()
                        .from(ORGANIZE)
                        .join(SCIENCE)
                        .on(ORGANIZE.SCIENCE_ID.eq(SCIENCE.SCIENCE_ID))
                        .join(DEPARTMENT)
                        .on(SCIENCE.DEPARTMENT_ID.eq(DEPARTMENT.DEPARTMENT_ID))
                        .join(COLLEGE)
                        .on(DEPARTMENT.COLLEGE_ID.eq(COLLEGE.COLLEGE_ID))
                        .join(SCHOOL)
                        .on(COLLEGE.SCHOOL_ID.eq(SCHOOL.SCHOOL_ID))
                        .where(COLLEGE.COLLEGE_ID.eq(collegeId).and(a));
                count = selectConditionStep.fetchOne();
            }

        }
        if (!ObjectUtils.isEmpty(count)) {
            return count.value1();
        }
        return 0;
    }

    @Override
    public Result<OrganizeRecord> findByOrganizeNameAndScienceId(String organizeName, int scienceId) {
        return create.selectFrom(ORGANIZE)
                .where(ORGANIZE.ORGANIZE_NAME.eq(organizeName).and(ORGANIZE.SCIENCE_ID.eq(scienceId)))
                .fetch();
    }

    /**
     * 班级数据全局搜索条件
     *
     * @param dataTablesUtils
     * @return 搜索条件
     */
    @Override
    public Condition searchCondition(DataTablesUtils<OrganizeBean> dataTablesUtils) {
        Condition a = null;

        JSONObject search = dataTablesUtils.getSearch();
        if (!ObjectUtils.isEmpty(search)) {
            String schoolName = StringUtils.trimWhitespace(search.getString("schoolName"));
            String collegeName = StringUtils.trimWhitespace(search.getString("collegeName"));
            String departmentName = StringUtils.trimWhitespace(search.getString("departmentName"));
            String scienceName = StringUtils.trimWhitespace(search.getString("scienceName"));
            String grade = StringUtils.trimWhitespace(search.getString("grade"));
            String organizeName = StringUtils.trimWhitespace(search.getString("organizeName"));
            if (StringUtils.hasLength(schoolName)) {
                a = SCHOOL.SCHOOL_NAME.like(SQLQueryUtils.likeAllParam(schoolName));
            }

            if (StringUtils.hasLength(collegeName)) {
                if (ObjectUtils.isEmpty(a)) {
                    a = COLLEGE.COLLEGE_NAME.like(SQLQueryUtils.likeAllParam(collegeName));
                } else {
                    a = a.and(COLLEGE.COLLEGE_NAME.like(SQLQueryUtils.likeAllParam(collegeName)));
                }
            }

            if (StringUtils.hasLength(departmentName)) {
                if (ObjectUtils.isEmpty(a)) {
                    a = DEPARTMENT.DEPARTMENT_NAME.like(SQLQueryUtils.likeAllParam(departmentName));
                } else {
                    a = a.and(DEPARTMENT.DEPARTMENT_NAME.like(SQLQueryUtils.likeAllParam(departmentName)));
                }
            }

            if (StringUtils.hasLength(scienceName)) {
                if (ObjectUtils.isEmpty(a)) {
                    a = SCIENCE.SCIENCE_NAME.like(SQLQueryUtils.likeAllParam(scienceName));
                } else {
                    a = a.and(SCIENCE.SCIENCE_NAME.like(SQLQueryUtils.likeAllParam(scienceName)));
                }
            }

            if (StringUtils.hasLength(grade)) {
                if (ObjectUtils.isEmpty(a)) {
                    a = ORGANIZE.GRADE.like(SQLQueryUtils.likeAllParam(grade));
                } else {
                    a = a.and(ORGANIZE.GRADE.like(SQLQueryUtils.likeAllParam(grade)));
                }
            }

            if (StringUtils.hasLength(organizeName)) {
                if (ObjectUtils.isEmpty(a)) {
                    a = ORGANIZE.ORGANIZE_NAME.like(SQLQueryUtils.likeAllParam(organizeName));
                } else {
                    a = a.and(ORGANIZE.ORGANIZE_NAME.like(SQLQueryUtils.likeAllParam(organizeName)));
                }
            }

        }
        return a;
    }

    /**
     * 班级数据排序
     *
     * @param dataTablesUtils
     * @param selectConditionStep
     */
    @Override
    public void sortCondition(DataTablesUtils<OrganizeBean> dataTablesUtils, SelectConditionStep<Record> selectConditionStep, SelectJoinStep<Record> selectJoinStep, int type) {
        String orderColumnName = dataTablesUtils.getOrderColumnName();
        String orderDir = dataTablesUtils.getOrderDir();
        boolean isAsc = "asc".equalsIgnoreCase(orderDir);
        SortField<Integer> a = null;
        SortField<String> b = null;
        SortField<Byte> c = null;
        if (StringUtils.hasLength(orderColumnName)) {
            if ("organize_id".equalsIgnoreCase(orderColumnName)) {
                if (isAsc) {
                    a = ORGANIZE.ORGANIZE_ID.asc();
                } else {
                    a = ORGANIZE.ORGANIZE_ID.desc();
                }
            }

            if ("school_name".equalsIgnoreCase(orderColumnName)) {
                if (isAsc) {
                    b = SCHOOL.SCHOOL_NAME.asc();
                } else {
                    b = SCHOOL.SCHOOL_NAME.desc();
                }
            }

            if ("college_name".equalsIgnoreCase(orderColumnName)) {
                if (isAsc) {
                    b = COLLEGE.COLLEGE_NAME.asc();
                } else {
                    b = COLLEGE.COLLEGE_NAME.desc();
                }
            }

            if ("department_name".equalsIgnoreCase(orderColumnName)) {
                if (isAsc) {
                    b = DEPARTMENT.DEPARTMENT_NAME.asc();
                } else {
                    b = DEPARTMENT.DEPARTMENT_NAME.desc();
                }
            }

            if ("science_name".equalsIgnoreCase(orderColumnName)) {
                if (isAsc) {
                    b = SCIENCE.SCIENCE_NAME.asc();
                } else {
                    b = SCIENCE.SCIENCE_NAME.desc();
                }
            }

            if ("grade".equalsIgnoreCase(orderColumnName)) {
                if (isAsc) {
                    b = ORGANIZE.GRADE.asc();
                } else {
                    b = ORGANIZE.GRADE.desc();
                }
            }

            if ("organize_name".equalsIgnoreCase(orderColumnName)) {
                if (isAsc) {
                    b = ORGANIZE.ORGANIZE_NAME.asc();
                } else {
                    b = ORGANIZE.ORGANIZE_NAME.desc();
                }
            }

            if ("organize_is_del".equalsIgnoreCase(orderColumnName)) {
                if (isAsc) {
                    c = ORGANIZE.ORGANIZE_IS_DEL.asc();
                } else {
                    c = ORGANIZE.ORGANIZE_IS_DEL.desc();
                }
            }

        }

        if (!ObjectUtils.isEmpty(a)) {
            if (type == CONDITION_TYPE) {
                selectConditionStep.orderBy(a);
            }

            if (type == JOIN_TYPE) {
                selectJoinStep.orderBy(a);
            }

        } else if (!ObjectUtils.isEmpty(b)) {
            if (type == CONDITION_TYPE) {
                selectConditionStep.orderBy(b);
            }

            if (type == JOIN_TYPE) {
                selectJoinStep.orderBy(b);
            }
        } else if (!ObjectUtils.isEmpty(c)) {
            if (type == CONDITION_TYPE) {
                selectConditionStep.orderBy(c);
            }

            if (type == JOIN_TYPE) {
                selectJoinStep.orderBy(c);
            }
        } else {
            if (type == CONDITION_TYPE) {
                selectConditionStep.orderBy(ORGANIZE.ORGANIZE_ID.desc());
            }

            if (type == JOIN_TYPE) {
                selectJoinStep.orderBy(ORGANIZE.ORGANIZE_ID.desc());
            }
        }
    }
}
