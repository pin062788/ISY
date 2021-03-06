/*
 * This file is generated by jOOQ.
*/
package top.zbeboy.isy.domain.tables.records;


import java.sql.Date;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record12;
import org.jooq.Row12;
import org.jooq.impl.UpdatableRecordImpl;

import top.zbeboy.isy.domain.tables.Staff;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class StaffRecord extends UpdatableRecordImpl<StaffRecord> implements Record12<Integer, String, Date, String, String, String, Integer, Integer, String, Integer, Integer, String> {

    private static final long serialVersionUID = -248810557;

    /**
     * Setter for <code>isy.staff.staff_id</code>.
     */
    public void setStaffId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>isy.staff.staff_id</code>.
     */
    @NotNull
    public Integer getStaffId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>isy.staff.staff_number</code>.
     */
    public void setStaffNumber(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>isy.staff.staff_number</code>.
     */
    @NotNull
    @Size(max = 20)
    public String getStaffNumber() {
        return (String) get(1);
    }

    /**
     * Setter for <code>isy.staff.birthday</code>.
     */
    public void setBirthday(Date value) {
        set(2, value);
    }

    /**
     * Getter for <code>isy.staff.birthday</code>.
     */
    public Date getBirthday() {
        return (Date) get(2);
    }

    /**
     * Setter for <code>isy.staff.sex</code>.
     */
    public void setSex(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>isy.staff.sex</code>.
     */
    @Size(max = 2)
    public String getSex() {
        return (String) get(3);
    }

    /**
     * Setter for <code>isy.staff.id_card</code>.
     */
    public void setIdCard(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>isy.staff.id_card</code>.
     */
    @Size(max = 20)
    public String getIdCard() {
        return (String) get(4);
    }

    /**
     * Setter for <code>isy.staff.family_residence</code>.
     */
    public void setFamilyResidence(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>isy.staff.family_residence</code>.
     */
    @Size(max = 600)
    public String getFamilyResidence() {
        return (String) get(5);
    }

    /**
     * Setter for <code>isy.staff.political_landscape_id</code>.
     */
    public void setPoliticalLandscapeId(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>isy.staff.political_landscape_id</code>.
     */
    public Integer getPoliticalLandscapeId() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>isy.staff.nation_id</code>.
     */
    public void setNationId(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>isy.staff.nation_id</code>.
     */
    public Integer getNationId() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>isy.staff.post</code>.
     */
    public void setPost(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>isy.staff.post</code>.
     */
    @Size(max = 500)
    public String getPost() {
        return (String) get(8);
    }

    /**
     * Setter for <code>isy.staff.academic_title_id</code>.
     */
    public void setAcademicTitleId(Integer value) {
        set(9, value);
    }

    /**
     * Getter for <code>isy.staff.academic_title_id</code>.
     */
    public Integer getAcademicTitleId() {
        return (Integer) get(9);
    }

    /**
     * Setter for <code>isy.staff.department_id</code>.
     */
    public void setDepartmentId(Integer value) {
        set(10, value);
    }

    /**
     * Getter for <code>isy.staff.department_id</code>.
     */
    @NotNull
    public Integer getDepartmentId() {
        return (Integer) get(10);
    }

    /**
     * Setter for <code>isy.staff.username</code>.
     */
    public void setUsername(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>isy.staff.username</code>.
     */
    @NotNull
    @Size(max = 64)
    public String getUsername() {
        return (String) get(11);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record12 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row12<Integer, String, Date, String, String, String, Integer, Integer, String, Integer, Integer, String> fieldsRow() {
        return (Row12) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row12<Integer, String, Date, String, String, String, Integer, Integer, String, Integer, Integer, String> valuesRow() {
        return (Row12) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Staff.STAFF.STAFF_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Staff.STAFF.STAFF_NUMBER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field3() {
        return Staff.STAFF.BIRTHDAY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Staff.STAFF.SEX;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return Staff.STAFF.ID_CARD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return Staff.STAFF.FAMILY_RESIDENCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return Staff.STAFF.POLITICAL_LANDSCAPE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return Staff.STAFF.NATION_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return Staff.STAFF.POST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field10() {
        return Staff.STAFF.ACADEMIC_TITLE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field11() {
        return Staff.STAFF.DEPARTMENT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field12() {
        return Staff.STAFF.USERNAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getStaffId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getStaffNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date value3() {
        return getBirthday();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getSex();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getIdCard();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getFamilyResidence();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getPoliticalLandscapeId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getNationId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getPost();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value10() {
        return getAcademicTitleId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value11() {
        return getDepartmentId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value12() {
        return getUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StaffRecord value1(Integer value) {
        setStaffId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StaffRecord value2(String value) {
        setStaffNumber(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StaffRecord value3(Date value) {
        setBirthday(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StaffRecord value4(String value) {
        setSex(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StaffRecord value5(String value) {
        setIdCard(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StaffRecord value6(String value) {
        setFamilyResidence(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StaffRecord value7(Integer value) {
        setPoliticalLandscapeId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StaffRecord value8(Integer value) {
        setNationId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StaffRecord value9(String value) {
        setPost(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StaffRecord value10(Integer value) {
        setAcademicTitleId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StaffRecord value11(Integer value) {
        setDepartmentId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StaffRecord value12(String value) {
        setUsername(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StaffRecord values(Integer value1, String value2, Date value3, String value4, String value5, String value6, Integer value7, Integer value8, String value9, Integer value10, Integer value11, String value12) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached StaffRecord
     */
    public StaffRecord() {
        super(Staff.STAFF);
    }

    /**
     * Create a detached, initialised StaffRecord
     */
    public StaffRecord(Integer staffId, String staffNumber, Date birthday, String sex, String idCard, String familyResidence, Integer politicalLandscapeId, Integer nationId, String post, Integer academicTitleId, Integer departmentId, String username) {
        super(Staff.STAFF);

        set(0, staffId);
        set(1, staffNumber);
        set(2, birthday);
        set(3, sex);
        set(4, idCard);
        set(5, familyResidence);
        set(6, politicalLandscapeId);
        set(7, nationId);
        set(8, post);
        set(9, academicTitleId);
        set(10, departmentId);
        set(11, username);
    }
}
