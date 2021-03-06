package top.zbeboy.isy.service.internship;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import top.zbeboy.isy.domain.tables.daos.InternshipCollegeDao;
import top.zbeboy.isy.domain.tables.pojos.InternshipCollege;
import top.zbeboy.isy.service.plugin.DataTablesPlugin;
import top.zbeboy.isy.service.util.DateTimeUtils;
import top.zbeboy.isy.service.util.SQLQueryUtils;
import top.zbeboy.isy.service.util.UUIDUtils;
import top.zbeboy.isy.web.util.DataTablesUtils;
import top.zbeboy.isy.web.vo.internship.apply.InternshipCollegeVo;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.Clock;
import java.util.Optional;

import static top.zbeboy.isy.domain.Tables.*;

/**
 * Created by lenovo on 2016-11-27.
 */
@Slf4j
@Service("internshipCollegeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class InternshipCollegeServiceImpl extends DataTablesPlugin<InternshipCollege> implements InternshipCollegeService {

    private final DSLContext create;

    @Resource
    private InternshipCollegeDao internshipCollegeDao;

    @Autowired
    public InternshipCollegeServiceImpl(DSLContext dslContext) {
        this.create = dslContext;
    }

    @Override
    public InternshipCollege findById(String id) {
        return internshipCollegeDao.findById(id);
    }

    @Override
    public Optional<Record> findByInternshipReleaseIdAndStudentId(String internshipReleaseId, int studentId) {
        return create.select()
                .from(INTERNSHIP_COLLEGE)
                .where(INTERNSHIP_COLLEGE.INTERNSHIP_RELEASE_ID.eq(internshipReleaseId).and(INTERNSHIP_COLLEGE.STUDENT_ID.eq(studentId)))
                .fetchOptional();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(InternshipCollege internshipCollege) {
        internshipCollegeDao.insert(internshipCollege);
    }

    @Override
    public void saveWithTransaction(InternshipCollegeVo internshipCollegeVo) {
        create.transaction(configuration -> {
            Timestamp now = new Timestamp(Clock.systemDefaultZone().millis());
            int state = 0;
            DSL.using(configuration)
                    .insertInto(INTERNSHIP_APPLY)
                    .set(INTERNSHIP_APPLY.INTERNSHIP_APPLY_ID, UUIDUtils.getUUID())
                    .set(INTERNSHIP_APPLY.INTERNSHIP_RELEASE_ID, internshipCollegeVo.getInternshipReleaseId())
                    .set(INTERNSHIP_APPLY.STUDENT_ID, internshipCollegeVo.getStudentId())
                    .set(INTERNSHIP_APPLY.APPLY_TIME, now)
                    .set(INTERNSHIP_APPLY.INTERNSHIP_APPLY_STATE, state)
                    .execute();

            String[] headmasterArr = internshipCollegeVo.getHeadmaster().split(" ");
            if (headmasterArr.length >= 2) {
                internshipCollegeVo.setHeadmaster(headmasterArr[0]);
                internshipCollegeVo.setHeadmasterContact(headmasterArr[1]);
            }
            String[] schoolGuidanceTeacherArr = internshipCollegeVo.getSchoolGuidanceTeacher().split(" ");
            if (schoolGuidanceTeacherArr.length >= 2) {
                internshipCollegeVo.setSchoolGuidanceTeacher(schoolGuidanceTeacherArr[0]);
                internshipCollegeVo.setSchoolGuidanceTeacherTel(schoolGuidanceTeacherArr[1]);
            }

            DSL.using(configuration)
                    .insertInto(INTERNSHIP_COLLEGE)
                    .set(INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID, UUIDUtils.getUUID())
                    .set(INTERNSHIP_COLLEGE.STUDENT_ID, internshipCollegeVo.getStudentId())
                    .set(INTERNSHIP_COLLEGE.INTERNSHIP_RELEASE_ID, internshipCollegeVo.getInternshipReleaseId())
                    .set(INTERNSHIP_COLLEGE.STUDENT_NAME, internshipCollegeVo.getStudentName())
                    .set(INTERNSHIP_COLLEGE.COLLEGE_CLASS, internshipCollegeVo.getCollegeClass())
                    .set(INTERNSHIP_COLLEGE.STUDENT_SEX, internshipCollegeVo.getStudentSex())
                    .set(INTERNSHIP_COLLEGE.STUDENT_NUMBER, internshipCollegeVo.getStudentNumber())
                    .set(INTERNSHIP_COLLEGE.PHONE_NUMBER, internshipCollegeVo.getPhoneNumber())
                    .set(INTERNSHIP_COLLEGE.QQ_MAILBOX, internshipCollegeVo.getQqMailbox())
                    .set(INTERNSHIP_COLLEGE.PARENTAL_CONTACT, internshipCollegeVo.getParentalContact())
                    .set(INTERNSHIP_COLLEGE.HEADMASTER, internshipCollegeVo.getHeadmaster())
                    .set(INTERNSHIP_COLLEGE.HEADMASTER_CONTACT, internshipCollegeVo.getHeadmasterContact())
                    .set(INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_NAME, internshipCollegeVo.getInternshipCollegeName())
                    .set(INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ADDRESS, internshipCollegeVo.getInternshipCollegeAddress())
                    .set(INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_CONTACTS, internshipCollegeVo.getInternshipCollegeContacts())
                    .set(INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_TEL, internshipCollegeVo.getInternshipCollegeTel())
                    .set(INTERNSHIP_COLLEGE.SCHOOL_GUIDANCE_TEACHER, internshipCollegeVo.getSchoolGuidanceTeacher())
                    .set(INTERNSHIP_COLLEGE.SCHOOL_GUIDANCE_TEACHER_TEL, internshipCollegeVo.getSchoolGuidanceTeacherTel())
                    .set(INTERNSHIP_COLLEGE.START_TIME, DateTimeUtils.formatDate(internshipCollegeVo.getStartTime()))
                    .set(INTERNSHIP_COLLEGE.END_TIME, DateTimeUtils.formatDate(internshipCollegeVo.getEndTime()))
                    .execute();

            DSL.using(configuration)
                    .insertInto(INTERNSHIP_CHANGE_HISTORY)
                    .set(INTERNSHIP_CHANGE_HISTORY.INTERNSHIP_CHANGE_HISTORY_ID, UUIDUtils.getUUID())
                    .set(INTERNSHIP_CHANGE_HISTORY.INTERNSHIP_RELEASE_ID, internshipCollegeVo.getInternshipReleaseId())
                    .set(INTERNSHIP_CHANGE_HISTORY.STUDENT_ID, internshipCollegeVo.getStudentId())
                    .set(INTERNSHIP_CHANGE_HISTORY.STATE, state)
                    .set(INTERNSHIP_CHANGE_HISTORY.APPLY_TIME, now)
                    .execute();
        });
    }

    @Override
    public void update(InternshipCollege internshipCollege) {
        internshipCollegeDao.update(internshipCollege);
    }

    @Override
    public void deleteByInternshipReleaseIdAndStudentId(String internshipReleaseId, int studentId) {
        create.deleteFrom(INTERNSHIP_COLLEGE)
                .where(INTERNSHIP_COLLEGE.INTERNSHIP_RELEASE_ID.eq(internshipReleaseId).and(INTERNSHIP_COLLEGE.STUDENT_ID.eq(studentId)))
                .execute();
    }

    @Override
    public Result<Record> findAllByPage(DataTablesUtils<InternshipCollege> dataTablesUtils, InternshipCollege internshipCollege) {
        return dataPagingQueryAllWithCondition(dataTablesUtils, create, INTERNSHIP_COLLEGE, INTERNSHIP_COLLEGE.INTERNSHIP_RELEASE_ID.eq(internshipCollege.getInternshipReleaseId()));
    }

    @Override
    public int countAll(InternshipCollege internshipCollege) {
        return statisticsAllWithCondition(create, INTERNSHIP_COLLEGE, INTERNSHIP_COLLEGE.INTERNSHIP_RELEASE_ID.eq(internshipCollege.getInternshipReleaseId()));
    }

    @Override
    public int countByCondition(DataTablesUtils<InternshipCollege> dataTablesUtils, InternshipCollege internshipCollege) {
        return statisticsWithCondition(dataTablesUtils, create, INTERNSHIP_COLLEGE, INTERNSHIP_COLLEGE.INTERNSHIP_RELEASE_ID.eq(internshipCollege.getInternshipReleaseId()));
    }

    @Override
    public Result<Record> exportData(DataTablesUtils<InternshipCollege> dataTablesUtils, InternshipCollege internshipCollege) {
        return dataPagingQueryAllWithConditionNoPage(dataTablesUtils, create, INTERNSHIP_COLLEGE, INTERNSHIP_COLLEGE.INTERNSHIP_RELEASE_ID.eq(internshipCollege.getInternshipReleaseId()));
    }

    /**
     * 全局搜索条件
     *
     * @param dataTablesUtils datatables工具类
     * @return 搜索条件
     */
    @Override
    public Condition searchCondition(DataTablesUtils<InternshipCollege> dataTablesUtils) {
        Condition a = null;
        JSONObject search = dataTablesUtils.getSearch();
        if (!ObjectUtils.isEmpty(search)) {
            String studentName = StringUtils.trimWhitespace(search.getString("studentName"));
            String studentNumber = StringUtils.trimWhitespace(search.getString("studentNumber"));
            String collegeClass = StringUtils.trimWhitespace(search.getString("collegeClass"));
            String phoneNumber = StringUtils.trimWhitespace(search.getString("phoneNumber"));
            String headmaster = StringUtils.trimWhitespace(search.getString("headmaster"));
            String schoolGuidanceTeacher = StringUtils.trimWhitespace(search.getString("schoolGuidanceTeacher"));

            if (StringUtils.hasLength(studentName)) {
                a = INTERNSHIP_COLLEGE.STUDENT_NAME.like(SQLQueryUtils.likeAllParam(studentName));
            }

            if (StringUtils.hasLength(studentNumber)) {
                if (ObjectUtils.isEmpty(a)) {
                    a = INTERNSHIP_COLLEGE.STUDENT_NUMBER.like(SQLQueryUtils.likeAllParam(studentNumber));
                } else {
                    a = a.and(INTERNSHIP_COLLEGE.STUDENT_NUMBER.like(SQLQueryUtils.likeAllParam(studentNumber)));
                }
            }

            if (StringUtils.hasLength(collegeClass)) {
                if (ObjectUtils.isEmpty(a)) {
                    a = INTERNSHIP_COLLEGE.COLLEGE_CLASS.like(SQLQueryUtils.likeAllParam(collegeClass));
                } else {
                    a = a.and(INTERNSHIP_COLLEGE.COLLEGE_CLASS.like(SQLQueryUtils.likeAllParam(collegeClass)));
                }
            }

            if (StringUtils.hasLength(phoneNumber)) {
                if (ObjectUtils.isEmpty(a)) {
                    a = INTERNSHIP_COLLEGE.PHONE_NUMBER.like(SQLQueryUtils.likeAllParam(phoneNumber));
                } else {
                    a = a.and(INTERNSHIP_COLLEGE.PHONE_NUMBER.like(SQLQueryUtils.likeAllParam(phoneNumber)));
                }
            }

            if (StringUtils.hasLength(headmaster)) {
                if (ObjectUtils.isEmpty(a)) {
                    a = INTERNSHIP_COLLEGE.HEADMASTER.like(SQLQueryUtils.likeAllParam(headmaster));
                } else {
                    a = a.and(INTERNSHIP_COLLEGE.HEADMASTER.like(SQLQueryUtils.likeAllParam(headmaster)));
                }
            }

            if (StringUtils.hasLength(schoolGuidanceTeacher)) {
                if (ObjectUtils.isEmpty(a)) {
                    a = INTERNSHIP_COLLEGE.SCHOOL_GUIDANCE_TEACHER.like(SQLQueryUtils.likeAllParam(schoolGuidanceTeacher));
                } else {
                    a = a.and(INTERNSHIP_COLLEGE.SCHOOL_GUIDANCE_TEACHER.like(SQLQueryUtils.likeAllParam(schoolGuidanceTeacher)));
                }
            }
        }
        return a;
    }

    /**
     * 数据排序
     *
     * @param dataTablesUtils     datatables工具类
     * @param selectConditionStep 条件
     */
    @Override
    public void sortCondition(DataTablesUtils<InternshipCollege> dataTablesUtils, SelectConditionStep<Record> selectConditionStep, SelectJoinStep<Record> selectJoinStep, int type) {
        String orderColumnName = dataTablesUtils.getOrderColumnName();
        String orderDir = dataTablesUtils.getOrderDir();
        boolean isAsc = "asc".equalsIgnoreCase(orderDir);
        SortField[] sortField = null;
        if (StringUtils.hasLength(orderColumnName)) {
            if ("student_name".equalsIgnoreCase(orderColumnName)) {
                sortField = new SortField[2];
                if (isAsc) {
                    sortField[0] = INTERNSHIP_COLLEGE.STUDENT_NAME.asc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.asc();
                } else {
                    sortField[0] = INTERNSHIP_COLLEGE.STUDENT_NAME.desc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.desc();
                }
            }

            if ("student_number".equalsIgnoreCase(orderColumnName)) {
                sortField = new SortField[1];
                if (isAsc) {
                    sortField[0] = INTERNSHIP_COLLEGE.STUDENT_NUMBER.asc();
                } else {
                    sortField[0] = INTERNSHIP_COLLEGE.STUDENT_NUMBER.desc();
                }
            }

            if ("college_class".equalsIgnoreCase(orderColumnName)) {
                sortField = new SortField[2];
                if (isAsc) {
                    sortField[0] = INTERNSHIP_COLLEGE.COLLEGE_CLASS.asc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.asc();
                } else {
                    sortField[0] = INTERNSHIP_COLLEGE.COLLEGE_CLASS.desc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.desc();
                }
            }

            if ("student_sex".equalsIgnoreCase(orderColumnName)) {
                sortField = new SortField[2];
                if (isAsc) {
                    sortField[0] = INTERNSHIP_COLLEGE.STUDENT_SEX.asc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.asc();
                } else {
                    sortField[0] = INTERNSHIP_COLLEGE.STUDENT_SEX.desc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.desc();
                }
            }

            if ("phone_number".equalsIgnoreCase(orderColumnName)) {
                sortField = new SortField[1];
                if (isAsc) {
                    sortField[0] = INTERNSHIP_COLLEGE.PHONE_NUMBER.asc();
                } else {
                    sortField[0] = INTERNSHIP_COLLEGE.PHONE_NUMBER.desc();
                }
            }

            if ("qq_mailbox".equalsIgnoreCase(orderColumnName)) {
                sortField = new SortField[2];
                if (isAsc) {
                    sortField[0] = INTERNSHIP_COLLEGE.QQ_MAILBOX.asc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.asc();
                } else {
                    sortField[0] = INTERNSHIP_COLLEGE.QQ_MAILBOX.desc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.desc();
                }
            }

            if ("parental_contact".equalsIgnoreCase(orderColumnName)) {
                sortField = new SortField[2];
                if (isAsc) {
                    sortField[0] = INTERNSHIP_COLLEGE.PARENTAL_CONTACT.asc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.asc();
                } else {
                    sortField[0] = INTERNSHIP_COLLEGE.PARENTAL_CONTACT.desc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.desc();
                }
            }

            if ("headmaster".equalsIgnoreCase(orderColumnName)) {
                sortField = new SortField[2];
                if (isAsc) {
                    sortField[0] = INTERNSHIP_COLLEGE.HEADMASTER.asc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.asc();
                } else {
                    sortField[0] = INTERNSHIP_COLLEGE.HEADMASTER.desc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.desc();
                }
            }

            if ("headmaster_contact".equalsIgnoreCase(orderColumnName)) {
                sortField = new SortField[2];
                if (isAsc) {
                    sortField[0] = INTERNSHIP_COLLEGE.HEADMASTER_CONTACT.asc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.asc();
                } else {
                    sortField[0] = INTERNSHIP_COLLEGE.HEADMASTER_CONTACT.desc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.desc();
                }
            }

            if ("internship_college_name".equalsIgnoreCase(orderColumnName)) {
                sortField = new SortField[2];
                if (isAsc) {
                    sortField[0] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_NAME.asc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.asc();
                } else {
                    sortField[0] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_NAME.desc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.desc();
                }
            }

            if ("internship_college_address".equalsIgnoreCase(orderColumnName)) {
                sortField = new SortField[2];
                if (isAsc) {
                    sortField[0] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ADDRESS.asc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.asc();
                } else {
                    sortField[0] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ADDRESS.desc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.desc();
                }
            }

            if ("internship_college_contacts".equalsIgnoreCase(orderColumnName)) {
                sortField = new SortField[2];
                if (isAsc) {
                    sortField[0] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_CONTACTS.asc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.asc();
                } else {
                    sortField[0] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_CONTACTS.desc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.desc();
                }
            }

            if ("internship_college_tel".equalsIgnoreCase(orderColumnName)) {
                sortField = new SortField[2];
                if (isAsc) {
                    sortField[0] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_TEL.asc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.asc();
                } else {
                    sortField[0] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_TEL.desc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.desc();
                }
            }

            if ("school_guidance_teacher".equalsIgnoreCase(orderColumnName)) {
                sortField = new SortField[2];
                if (isAsc) {
                    sortField[0] = INTERNSHIP_COLLEGE.SCHOOL_GUIDANCE_TEACHER.asc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.asc();
                } else {
                    sortField[0] = INTERNSHIP_COLLEGE.SCHOOL_GUIDANCE_TEACHER.desc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.desc();
                }
            }

            if ("school_guidance_teacher_tel".equalsIgnoreCase(orderColumnName)) {
                sortField = new SortField[2];
                if (isAsc) {
                    sortField[0] = INTERNSHIP_COLLEGE.SCHOOL_GUIDANCE_TEACHER_TEL.asc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.asc();
                } else {
                    sortField[0] = INTERNSHIP_COLLEGE.SCHOOL_GUIDANCE_TEACHER_TEL.desc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.desc();
                }
            }

            if ("start_time".equalsIgnoreCase(orderColumnName)) {
                sortField = new SortField[2];
                if (isAsc) {
                    sortField[0] = INTERNSHIP_COLLEGE.START_TIME.asc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.asc();
                } else {
                    sortField[0] = INTERNSHIP_COLLEGE.START_TIME.desc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.desc();
                }
            }

            if ("end_time".equalsIgnoreCase(orderColumnName)) {
                sortField = new SortField[2];
                if (isAsc) {
                    sortField[0] = INTERNSHIP_COLLEGE.END_TIME.asc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.asc();
                } else {
                    sortField[0] = INTERNSHIP_COLLEGE.END_TIME.desc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.desc();
                }
            }

            if ("commitment_book".equalsIgnoreCase(orderColumnName)) {
                sortField = new SortField[2];
                if (isAsc) {
                    sortField[0] = INTERNSHIP_COLLEGE.COMMITMENT_BOOK.asc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.asc();
                } else {
                    sortField[0] = INTERNSHIP_COLLEGE.COMMITMENT_BOOK.desc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.desc();
                }
            }

            if ("safety_responsibility_book".equalsIgnoreCase(orderColumnName)) {
                sortField = new SortField[2];
                if (isAsc) {
                    sortField[0] = INTERNSHIP_COLLEGE.SAFETY_RESPONSIBILITY_BOOK.asc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.asc();
                } else {
                    sortField[0] = INTERNSHIP_COLLEGE.SAFETY_RESPONSIBILITY_BOOK.desc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.desc();
                }
            }

            if ("practice_agreement".equalsIgnoreCase(orderColumnName)) {
                sortField = new SortField[2];
                if (isAsc) {
                    sortField[0] = INTERNSHIP_COLLEGE.PRACTICE_AGREEMENT.asc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.asc();
                } else {
                    sortField[0] = INTERNSHIP_COLLEGE.PRACTICE_AGREEMENT.desc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.desc();
                }
            }

            if ("internship_application".equalsIgnoreCase(orderColumnName)) {
                sortField = new SortField[2];
                if (isAsc) {
                    sortField[0] = INTERNSHIP_COLLEGE.INTERNSHIP_APPLICATION.asc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.asc();
                } else {
                    sortField[0] = INTERNSHIP_COLLEGE.INTERNSHIP_APPLICATION.desc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.desc();
                }
            }

            if ("practice_receiving".equalsIgnoreCase(orderColumnName)) {
                sortField = new SortField[2];
                if (isAsc) {
                    sortField[0] = INTERNSHIP_COLLEGE.PRACTICE_RECEIVING.asc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.asc();
                } else {
                    sortField[0] = INTERNSHIP_COLLEGE.PRACTICE_RECEIVING.desc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.desc();
                }
            }

            if ("security_education_agreement".equalsIgnoreCase(orderColumnName)) {
                sortField = new SortField[2];
                if (isAsc) {
                    sortField[0] = INTERNSHIP_COLLEGE.SECURITY_EDUCATION_AGREEMENT.asc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.asc();
                } else {
                    sortField[0] = INTERNSHIP_COLLEGE.SECURITY_EDUCATION_AGREEMENT.desc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.desc();
                }
            }

            if ("parental_consent".equalsIgnoreCase(orderColumnName)) {
                sortField = new SortField[2];
                if (isAsc) {
                    sortField[0] = INTERNSHIP_COLLEGE.PARENTAL_CONSENT.asc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.asc();
                } else {
                    sortField[0] = INTERNSHIP_COLLEGE.PARENTAL_CONSENT.desc();
                    sortField[1] = INTERNSHIP_COLLEGE.INTERNSHIP_COLLEGE_ID.desc();
                }
            }

        }

        sortToFinish(selectConditionStep, selectJoinStep, type, sortField);
    }
}
