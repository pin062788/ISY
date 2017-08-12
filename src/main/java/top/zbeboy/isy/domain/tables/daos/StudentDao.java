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

import top.zbeboy.isy.domain.tables.Student;
import top.zbeboy.isy.domain.tables.records.StudentRecord;


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
public class StudentDao extends DAOImpl<StudentRecord, top.zbeboy.isy.domain.tables.pojos.Student, Integer> {

    /**
     * Create a new StudentDao without any configuration
     */
    public StudentDao() {
        super(Student.STUDENT, top.zbeboy.isy.domain.tables.pojos.Student.class);
    }

    /**
     * Create a new StudentDao with an attached configuration
     */
    @Autowired
    public StudentDao(Configuration configuration) {
        super(Student.STUDENT, top.zbeboy.isy.domain.tables.pojos.Student.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(top.zbeboy.isy.domain.tables.pojos.Student object) {
        return object.getStudentId();
    }

    /**
     * Fetch records that have <code>student_id IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.Student> fetchByStudentId(Integer... values) {
        return fetch(Student.STUDENT.STUDENT_ID, values);
    }

    /**
     * Fetch a unique record that has <code>student_id = value</code>
     */
    public top.zbeboy.isy.domain.tables.pojos.Student fetchOneByStudentId(Integer value) {
        return fetchOne(Student.STUDENT.STUDENT_ID, value);
    }

    /**
     * Fetch records that have <code>student_number IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.Student> fetchByStudentNumber(String... values) {
        return fetch(Student.STUDENT.STUDENT_NUMBER, values);
    }

    /**
     * Fetch a unique record that has <code>student_number = value</code>
     */
    public top.zbeboy.isy.domain.tables.pojos.Student fetchOneByStudentNumber(String value) {
        return fetchOne(Student.STUDENT.STUDENT_NUMBER, value);
    }

    /**
     * Fetch records that have <code>birthday IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.Student> fetchByBirthday(Date... values) {
        return fetch(Student.STUDENT.BIRTHDAY, values);
    }

    /**
     * Fetch records that have <code>sex IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.Student> fetchBySex(String... values) {
        return fetch(Student.STUDENT.SEX, values);
    }

    /**
     * Fetch records that have <code>id_card IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.Student> fetchByIdCard(String... values) {
        return fetch(Student.STUDENT.ID_CARD, values);
    }

    /**
     * Fetch a unique record that has <code>id_card = value</code>
     */
    public top.zbeboy.isy.domain.tables.pojos.Student fetchOneByIdCard(String value) {
        return fetchOne(Student.STUDENT.ID_CARD, value);
    }

    /**
     * Fetch records that have <code>family_residence IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.Student> fetchByFamilyResidence(String... values) {
        return fetch(Student.STUDENT.FAMILY_RESIDENCE, values);
    }

    /**
     * Fetch records that have <code>political_landscape_id IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.Student> fetchByPoliticalLandscapeId(Integer... values) {
        return fetch(Student.STUDENT.POLITICAL_LANDSCAPE_ID, values);
    }

    /**
     * Fetch records that have <code>nation_id IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.Student> fetchByNationId(Integer... values) {
        return fetch(Student.STUDENT.NATION_ID, values);
    }

    /**
     * Fetch records that have <code>dormitory_number IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.Student> fetchByDormitoryNumber(String... values) {
        return fetch(Student.STUDENT.DORMITORY_NUMBER, values);
    }

    /**
     * Fetch records that have <code>parent_name IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.Student> fetchByParentName(String... values) {
        return fetch(Student.STUDENT.PARENT_NAME, values);
    }

    /**
     * Fetch records that have <code>parent_contact_phone IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.Student> fetchByParentContactPhone(String... values) {
        return fetch(Student.STUDENT.PARENT_CONTACT_PHONE, values);
    }

    /**
     * Fetch records that have <code>place_origin IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.Student> fetchByPlaceOrigin(String... values) {
        return fetch(Student.STUDENT.PLACE_ORIGIN, values);
    }

    /**
     * Fetch records that have <code>organize_id IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.Student> fetchByOrganizeId(Integer... values) {
        return fetch(Student.STUDENT.ORGANIZE_ID, values);
    }

    /**
     * Fetch records that have <code>username IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.Student> fetchByUsername(String... values) {
        return fetch(Student.STUDENT.USERNAME, values);
    }
}
