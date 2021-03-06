package top.zbeboy.isy.service.graduate.design;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import top.zbeboy.isy.domain.tables.daos.GraduationDesignReleaseDao;
import top.zbeboy.isy.domain.tables.pojos.GraduationDesignRelease;
import top.zbeboy.isy.domain.tables.records.GraduationDesignReleaseRecord;
import top.zbeboy.isy.service.util.DateTimeUtils;
import top.zbeboy.isy.service.util.SQLQueryUtils;
import top.zbeboy.isy.web.bean.error.ErrorBean;
import top.zbeboy.isy.web.bean.graduate.design.release.GraduationDesignReleaseBean;
import top.zbeboy.isy.web.util.PaginationUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static top.zbeboy.isy.domain.Tables.*;

/**
 * Created by zbeboy on 2017/5/5.
 */
@Slf4j
@Service("graduationDesignReleaseService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GraduationDesignReleaseServiceImpl implements GraduationDesignReleaseService {

    private final DSLContext create;

    @Resource
    private GraduationDesignReleaseDao graduationDesignReleaseDao;

    @Autowired
    public GraduationDesignReleaseServiceImpl(DSLContext dslContext) {
        this.create = dslContext;
    }

    @Override
    public GraduationDesignRelease findById(String id) {
        return graduationDesignReleaseDao.findById(id);
    }

    @Override
    public Optional<Record> findByIdRelation(String id) {
        return create.select()
                .from(GRADUATION_DESIGN_RELEASE)
                .join(SCIENCE)
                .on(GRADUATION_DESIGN_RELEASE.SCIENCE_ID.eq(SCIENCE.SCIENCE_ID))
                .join(DEPARTMENT)
                .on(SCIENCE.DEPARTMENT_ID.eq(DEPARTMENT.DEPARTMENT_ID))
                .join(COLLEGE)
                .on(DEPARTMENT.COLLEGE_ID.eq(COLLEGE.COLLEGE_ID))
                .join(SCHOOL)
                .on(COLLEGE.COLLEGE_ID.eq(SCHOOL.SCHOOL_ID))
                .where(GRADUATION_DESIGN_RELEASE.GRADUATION_DESIGN_RELEASE_ID.eq(id))
                .fetchOptional();
    }

    @Override
    public List<GraduationDesignRelease> findByGraduationDesignTitle(String graduationDesignTitle) {
        return graduationDesignReleaseDao.fetchByGraduationDesignTitle(graduationDesignTitle);
    }

    @Override
    public Result<GraduationDesignReleaseRecord> findByGraduationDesignTitleNeGraduationDesignReleaseId(String graduationDesignTitle, String graduationDesignReleaseId) {
        return create.selectFrom(GRADUATION_DESIGN_RELEASE)
                .where(GRADUATION_DESIGN_RELEASE.GRADUATION_DESIGN_TITLE.eq(graduationDesignTitle).and(GRADUATION_DESIGN_RELEASE.GRADUATION_DESIGN_RELEASE_ID.ne(graduationDesignReleaseId)))
                .fetch();
    }

    @Override
    public Result<Record> findAllByPage(PaginationUtils paginationUtils, GraduationDesignReleaseBean graduationDesignReleaseBean) {
        int pageNum = paginationUtils.getPageNum();
        int pageSize = paginationUtils.getPageSize();
        Condition a = searchCondition(paginationUtils);
        a = otherCondition(a, graduationDesignReleaseBean);
        return create.select()
                .from(GRADUATION_DESIGN_RELEASE)
                .join(USERS)
                .on(GRADUATION_DESIGN_RELEASE.USERNAME.eq(USERS.USERNAME))
                .join(SCIENCE)
                .on(GRADUATION_DESIGN_RELEASE.SCIENCE_ID.eq(SCIENCE.SCIENCE_ID))
                .join(DEPARTMENT)
                .on(SCIENCE.DEPARTMENT_ID.eq(DEPARTMENT.DEPARTMENT_ID))
                .join(COLLEGE)
                .on(DEPARTMENT.COLLEGE_ID.eq(COLLEGE.COLLEGE_ID))
                .join(SCHOOL)
                .on(COLLEGE.COLLEGE_ID.eq(SCHOOL.SCHOOL_ID))
                .where(a)
                .orderBy(GRADUATION_DESIGN_RELEASE.RELEASE_TIME.desc())
                .limit((pageNum - 1) * pageSize, pageSize)
                .fetch();
    }

    @Override
    public List<GraduationDesignReleaseBean> dealData(PaginationUtils paginationUtils, Result<Record> records, GraduationDesignReleaseBean graduationDesignReleaseBean) {
        List<GraduationDesignReleaseBean> graduationDesignReleaseBeans = new ArrayList<>();
        if (records.isNotEmpty()) {
            graduationDesignReleaseBeans = records.into(GraduationDesignReleaseBean.class);
            String format = "yyyy-MM-dd HH:mm:ss";
            graduationDesignReleaseBeans.forEach(i -> {
                i.setFillTeacherStartTimeStr(DateTimeUtils.timestampToString(i.getFillTeacherStartTime(), format));
                i.setFillTeacherEndTimeStr(DateTimeUtils.timestampToString(i.getFillTeacherEndTime(), format));
                i.setStartTimeStr(DateTimeUtils.timestampToString(i.getStartTime(), format));
                i.setEndTimeStr(DateTimeUtils.timestampToString(i.getEndTime(), format));
                i.setReleaseTimeStr(DateTimeUtils.timestampToString(i.getReleaseTime(), format));
            });
            paginationUtils.setTotalDatas(countByCondition(paginationUtils, graduationDesignReleaseBean));
        }
        return graduationDesignReleaseBeans;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(GraduationDesignRelease graduationDesignRelease) {
        graduationDesignReleaseDao.insert(graduationDesignRelease);
    }

    @Override
    public void update(GraduationDesignRelease graduationDesignRelease) {
        graduationDesignReleaseDao.update(graduationDesignRelease);
    }

    @Override
    public ErrorBean<GraduationDesignRelease> basicCondition(String graduationDesignReleaseId) {
        ErrorBean<GraduationDesignRelease> errorBean = ErrorBean.of();
        GraduationDesignRelease graduationDesignRelease = findById(graduationDesignReleaseId);
        if (!ObjectUtils.isEmpty(graduationDesignRelease)) {
            errorBean.setData(graduationDesignRelease);
            if (graduationDesignRelease.getGraduationDesignIsDel() == 1) {
                errorBean.setHasError(true);
                errorBean.setErrorMsg("该毕业设计已被注销");
            } else {
                errorBean.setHasError(false);
            }
        } else {
            errorBean.setHasError(true);
            errorBean.setErrorMsg("未查询到相关毕业设计信息");
        }
        return errorBean;
    }

    public int countByCondition(PaginationUtils paginationUtils, GraduationDesignReleaseBean graduationDesignReleaseBean) {
        Record1<Integer> count;
        Condition a = searchCondition(paginationUtils);
        a = otherCondition(a, graduationDesignReleaseBean);
        if (ObjectUtils.isEmpty(a)) {
            SelectJoinStep<Record1<Integer>> selectJoinStep = create.selectCount()
                    .from(GRADUATION_DESIGN_RELEASE);
            count = selectJoinStep.fetchOne();
        } else {
            SelectConditionStep<Record1<Integer>> selectConditionStep = create.selectCount()
                    .from(GRADUATION_DESIGN_RELEASE)
                    .join(USERS)
                    .on(GRADUATION_DESIGN_RELEASE.USERNAME.eq(USERS.USERNAME))
                    .join(SCIENCE)
                    .on(GRADUATION_DESIGN_RELEASE.SCIENCE_ID.eq(SCIENCE.SCIENCE_ID))
                    .join(DEPARTMENT)
                    .on(SCIENCE.DEPARTMENT_ID.eq(DEPARTMENT.DEPARTMENT_ID))
                    .join(COLLEGE)
                    .on(DEPARTMENT.COLLEGE_ID.eq(COLLEGE.COLLEGE_ID))
                    .join(SCHOOL)
                    .on(COLLEGE.COLLEGE_ID.eq(SCHOOL.SCHOOL_ID))
                    .where(a);
            count = selectConditionStep.fetchOne();
        }
        return count.value1();
    }

    /**
     * 搜索条件
     *
     * @param paginationUtils 分页工具
     * @return 条件
     */
    public Condition searchCondition(PaginationUtils paginationUtils) {
        Condition a = null;
        JSONObject search = JSON.parseObject(paginationUtils.getSearchParams());
        if (!ObjectUtils.isEmpty(search)) {
            String graduationDesignTitle = StringUtils.trimWhitespace(search.getString("graduationDesignTitle"));
            if (StringUtils.hasLength(graduationDesignTitle)) {
                a = GRADUATION_DESIGN_RELEASE.GRADUATION_DESIGN_TITLE.like(SQLQueryUtils.likeAllParam(graduationDesignTitle));
            }
        }
        return a;
    }

    /**
     * 其它条件参数
     *
     * @param a                           搜索条件
     * @param graduationDesignReleaseBean 额外参数
     * @return 条件
     */
    private Condition otherCondition(Condition a, GraduationDesignReleaseBean graduationDesignReleaseBean) {
        if (!ObjectUtils.isEmpty(graduationDesignReleaseBean)) {
            if (!ObjectUtils.isEmpty(graduationDesignReleaseBean.getDepartmentId()) && graduationDesignReleaseBean.getDepartmentId() > 0) {
                if (!ObjectUtils.isEmpty(a)) {
                    a = a.and(GRADUATION_DESIGN_RELEASE.DEPARTMENT_ID.eq(graduationDesignReleaseBean.getDepartmentId()));
                } else {
                    a = GRADUATION_DESIGN_RELEASE.DEPARTMENT_ID.eq(graduationDesignReleaseBean.getDepartmentId());
                }
            }

            if (!ObjectUtils.isEmpty(graduationDesignReleaseBean.getCollegeId()) && graduationDesignReleaseBean.getCollegeId() > 0) {
                if (!ObjectUtils.isEmpty(a)) {
                    a = a.and(COLLEGE.COLLEGE_ID.eq(graduationDesignReleaseBean.getCollegeId()));
                } else {
                    a = COLLEGE.COLLEGE_ID.eq(graduationDesignReleaseBean.getCollegeId());
                }
            }

            if (!ObjectUtils.isEmpty(graduationDesignReleaseBean.getGraduationDesignIsDel())) {
                if (!ObjectUtils.isEmpty(a)) {
                    a = a.and(GRADUATION_DESIGN_RELEASE.GRADUATION_DESIGN_IS_DEL.eq(graduationDesignReleaseBean.getGraduationDesignIsDel()));
                } else {
                    a = GRADUATION_DESIGN_RELEASE.GRADUATION_DESIGN_IS_DEL.eq(graduationDesignReleaseBean.getGraduationDesignIsDel());
                }
            }

            if (!ObjectUtils.isEmpty(graduationDesignReleaseBean.getAllowGrade())) {
                if (!ObjectUtils.isEmpty(a)) {
                    a = a.and(GRADUATION_DESIGN_RELEASE.ALLOW_GRADE.eq(graduationDesignReleaseBean.getAllowGrade()));
                } else {
                    a = GRADUATION_DESIGN_RELEASE.ALLOW_GRADE.eq(graduationDesignReleaseBean.getAllowGrade());
                }
            }
        }
        return a;
    }
}
