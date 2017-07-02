/*
 * This file is generated by jOOQ.
*/
package top.zbeboy.isy.domain.tables.daos;


import java.sql.Date;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import top.zbeboy.isy.domain.tables.GraduationPracticeUnify;
import top.zbeboy.isy.domain.tables.records.GraduationPracticeUnifyRecord;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Repository
public class GraduationPracticeUnifyDao extends DAOImpl<GraduationPracticeUnifyRecord, top.zbeboy.isy.domain.tables.pojos.GraduationPracticeUnify, String> {

    /**
     * Create a new GraduationPracticeUnifyDao without any configuration
     */
    public GraduationPracticeUnifyDao() {
        super(GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY, top.zbeboy.isy.domain.tables.pojos.GraduationPracticeUnify.class);
    }

    /**
     * Create a new GraduationPracticeUnifyDao with an attached configuration
     */
    @Autowired
    public GraduationPracticeUnifyDao(Configuration configuration) {
        super(GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY, top.zbeboy.isy.domain.tables.pojos.GraduationPracticeUnify.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getId(top.zbeboy.isy.domain.tables.pojos.GraduationPracticeUnify object) {
        return object.getGraduationPracticeUnifyId();
    }

    /**
     * Fetch records that have <code>graduation_practice_unify_id IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.GraduationPracticeUnify> fetchByGraduationPracticeUnifyId(String... values) {
        return fetch(GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY.GRADUATION_PRACTICE_UNIFY_ID, values);
    }

    /**
     * Fetch a unique record that has <code>graduation_practice_unify_id = value</code>
     */
    public top.zbeboy.isy.domain.tables.pojos.GraduationPracticeUnify fetchOneByGraduationPracticeUnifyId(String value) {
        return fetchOne(GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY.GRADUATION_PRACTICE_UNIFY_ID, value);
    }

    /**
     * Fetch records that have <code>student_name IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.GraduationPracticeUnify> fetchByStudentName(String... values) {
        return fetch(GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY.STUDENT_NAME, values);
    }

    /**
     * Fetch records that have <code>college_class IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.GraduationPracticeUnify> fetchByCollegeClass(String... values) {
        return fetch(GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY.COLLEGE_CLASS, values);
    }

    /**
     * Fetch records that have <code>student_sex IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.GraduationPracticeUnify> fetchByStudentSex(String... values) {
        return fetch(GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY.STUDENT_SEX, values);
    }

    /**
     * Fetch records that have <code>student_number IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.GraduationPracticeUnify> fetchByStudentNumber(String... values) {
        return fetch(GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY.STUDENT_NUMBER, values);
    }

    /**
     * Fetch records that have <code>phone_number IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.GraduationPracticeUnify> fetchByPhoneNumber(String... values) {
        return fetch(GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY.PHONE_NUMBER, values);
    }

    /**
     * Fetch records that have <code>qq_mailbox IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.GraduationPracticeUnify> fetchByQqMailbox(String... values) {
        return fetch(GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY.QQ_MAILBOX, values);
    }

    /**
     * Fetch records that have <code>parental_contact IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.GraduationPracticeUnify> fetchByParentalContact(String... values) {
        return fetch(GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY.PARENTAL_CONTACT, values);
    }

    /**
     * Fetch records that have <code>headmaster IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.GraduationPracticeUnify> fetchByHeadmaster(String... values) {
        return fetch(GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY.HEADMASTER, values);
    }

    /**
     * Fetch records that have <code>headmaster_contact IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.GraduationPracticeUnify> fetchByHeadmasterContact(String... values) {
        return fetch(GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY.HEADMASTER_CONTACT, values);
    }

    /**
     * Fetch records that have <code>graduation_practice_unify_name IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.GraduationPracticeUnify> fetchByGraduationPracticeUnifyName(String... values) {
        return fetch(GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY.GRADUATION_PRACTICE_UNIFY_NAME, values);
    }

    /**
     * Fetch records that have <code>graduation_practice_unify_address IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.GraduationPracticeUnify> fetchByGraduationPracticeUnifyAddress(String... values) {
        return fetch(GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY.GRADUATION_PRACTICE_UNIFY_ADDRESS, values);
    }

    /**
     * Fetch records that have <code>graduation_practice_unify_contacts IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.GraduationPracticeUnify> fetchByGraduationPracticeUnifyContacts(String... values) {
        return fetch(GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY.GRADUATION_PRACTICE_UNIFY_CONTACTS, values);
    }

    /**
     * Fetch records that have <code>graduation_practice_unify_tel IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.GraduationPracticeUnify> fetchByGraduationPracticeUnifyTel(String... values) {
        return fetch(GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY.GRADUATION_PRACTICE_UNIFY_TEL, values);
    }

    /**
     * Fetch records that have <code>school_guidance_teacher IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.GraduationPracticeUnify> fetchBySchoolGuidanceTeacher(String... values) {
        return fetch(GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY.SCHOOL_GUIDANCE_TEACHER, values);
    }

    /**
     * Fetch records that have <code>school_guidance_teacher_tel IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.GraduationPracticeUnify> fetchBySchoolGuidanceTeacherTel(String... values) {
        return fetch(GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY.SCHOOL_GUIDANCE_TEACHER_TEL, values);
    }

    /**
     * Fetch records that have <code>start_time IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.GraduationPracticeUnify> fetchByStartTime(Date... values) {
        return fetch(GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY.START_TIME, values);
    }

    /**
     * Fetch records that have <code>end_time IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.GraduationPracticeUnify> fetchByEndTime(Date... values) {
        return fetch(GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY.END_TIME, values);
    }

    /**
     * Fetch records that have <code>commitment_book IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.GraduationPracticeUnify> fetchByCommitmentBook(Byte... values) {
        return fetch(GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY.COMMITMENT_BOOK, values);
    }

    /**
     * Fetch records that have <code>safety_responsibility_book IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.GraduationPracticeUnify> fetchBySafetyResponsibilityBook(Byte... values) {
        return fetch(GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY.SAFETY_RESPONSIBILITY_BOOK, values);
    }

    /**
     * Fetch records that have <code>practice_agreement IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.GraduationPracticeUnify> fetchByPracticeAgreement(Byte... values) {
        return fetch(GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY.PRACTICE_AGREEMENT, values);
    }

    /**
     * Fetch records that have <code>internship_application IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.GraduationPracticeUnify> fetchByInternshipApplication(Byte... values) {
        return fetch(GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY.INTERNSHIP_APPLICATION, values);
    }

    /**
     * Fetch records that have <code>practice_receiving IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.GraduationPracticeUnify> fetchByPracticeReceiving(Byte... values) {
        return fetch(GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY.PRACTICE_RECEIVING, values);
    }

    /**
     * Fetch records that have <code>security_education_agreement IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.GraduationPracticeUnify> fetchBySecurityEducationAgreement(Byte... values) {
        return fetch(GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY.SECURITY_EDUCATION_AGREEMENT, values);
    }

    /**
     * Fetch records that have <code>parental_consent IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.GraduationPracticeUnify> fetchByParentalConsent(Byte... values) {
        return fetch(GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY.PARENTAL_CONSENT, values);
    }

    /**
     * Fetch records that have <code>student_id IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.GraduationPracticeUnify> fetchByStudentId(Integer... values) {
        return fetch(GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY.STUDENT_ID, values);
    }

    /**
     * Fetch records that have <code>internship_release_id IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.GraduationPracticeUnify> fetchByInternshipReleaseId(String... values) {
        return fetch(GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY.INTERNSHIP_RELEASE_ID, values);
    }
}
