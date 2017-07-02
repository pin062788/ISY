/*
 * This file is generated by jOOQ.
*/
package top.zbeboy.isy.domain.tables.records;


import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;

import top.zbeboy.isy.domain.tables.GraduationDesignDeclareData;


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
public class GraduationDesignDeclareDataRecord extends UpdatableRecordImpl<GraduationDesignDeclareDataRecord> implements Record7<String, String, String, String, String, String, String> {

    private static final long serialVersionUID = 1696565037;

    /**
     * Setter for <code>isy.graduation_design_declare_data.graduation_design_declare_data_id</code>.
     */
    public void setGraduationDesignDeclareDataId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>isy.graduation_design_declare_data.graduation_design_declare_data_id</code>.
     */
    @NotNull
    @Size(max = 64)
    public String getGraduationDesignDeclareDataId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>isy.graduation_design_declare_data.graduation_date</code>.
     */
    public void setGraduationDate(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>isy.graduation_design_declare_data.graduation_date</code>.
     */
    @Size(max = 30)
    public String getGraduationDate() {
        return (String) get(1);
    }

    /**
     * Setter for <code>isy.graduation_design_declare_data.department_name</code>.
     */
    public void setDepartmentName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>isy.graduation_design_declare_data.department_name</code>.
     */
    @Size(max = 200)
    public String getDepartmentName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>isy.graduation_design_declare_data.science_name</code>.
     */
    public void setScienceName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>isy.graduation_design_declare_data.science_name</code>.
     */
    @Size(max = 200)
    public String getScienceName() {
        return (String) get(3);
    }

    /**
     * Setter for <code>isy.graduation_design_declare_data.organize_names</code>.
     */
    public void setOrganizeNames(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>isy.graduation_design_declare_data.organize_names</code>.
     */
    @Size(max = 150)
    public String getOrganizeNames() {
        return (String) get(4);
    }

    /**
     * Setter for <code>isy.graduation_design_declare_data.organize_peoples</code>.
     */
    public void setOrganizePeoples(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>isy.graduation_design_declare_data.organize_peoples</code>.
     */
    @Size(max = 150)
    public String getOrganizePeoples() {
        return (String) get(5);
    }

    /**
     * Setter for <code>isy.graduation_design_declare_data.graduation_design_release_id</code>.
     */
    public void setGraduationDesignReleaseId(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>isy.graduation_design_declare_data.graduation_design_release_id</code>.
     */
    @NotNull
    @Size(max = 64)
    public String getGraduationDesignReleaseId() {
        return (String) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<String, String, String, String, String, String, String> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<String, String, String, String, String, String, String> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return GraduationDesignDeclareData.GRADUATION_DESIGN_DECLARE_DATA.GRADUATION_DESIGN_DECLARE_DATA_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return GraduationDesignDeclareData.GRADUATION_DESIGN_DECLARE_DATA.GRADUATION_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return GraduationDesignDeclareData.GRADUATION_DESIGN_DECLARE_DATA.DEPARTMENT_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return GraduationDesignDeclareData.GRADUATION_DESIGN_DECLARE_DATA.SCIENCE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return GraduationDesignDeclareData.GRADUATION_DESIGN_DECLARE_DATA.ORGANIZE_NAMES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return GraduationDesignDeclareData.GRADUATION_DESIGN_DECLARE_DATA.ORGANIZE_PEOPLES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return GraduationDesignDeclareData.GRADUATION_DESIGN_DECLARE_DATA.GRADUATION_DESIGN_RELEASE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getGraduationDesignDeclareDataId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getGraduationDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getDepartmentName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getScienceName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getOrganizeNames();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getOrganizePeoples();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getGraduationDesignReleaseId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GraduationDesignDeclareDataRecord value1(String value) {
        setGraduationDesignDeclareDataId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GraduationDesignDeclareDataRecord value2(String value) {
        setGraduationDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GraduationDesignDeclareDataRecord value3(String value) {
        setDepartmentName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GraduationDesignDeclareDataRecord value4(String value) {
        setScienceName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GraduationDesignDeclareDataRecord value5(String value) {
        setOrganizeNames(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GraduationDesignDeclareDataRecord value6(String value) {
        setOrganizePeoples(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GraduationDesignDeclareDataRecord value7(String value) {
        setGraduationDesignReleaseId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GraduationDesignDeclareDataRecord values(String value1, String value2, String value3, String value4, String value5, String value6, String value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached GraduationDesignDeclareDataRecord
     */
    public GraduationDesignDeclareDataRecord() {
        super(GraduationDesignDeclareData.GRADUATION_DESIGN_DECLARE_DATA);
    }

    /**
     * Create a detached, initialised GraduationDesignDeclareDataRecord
     */
    public GraduationDesignDeclareDataRecord(String graduationDesignDeclareDataId, String graduationDate, String departmentName, String scienceName, String organizeNames, String organizePeoples, String graduationDesignReleaseId) {
        super(GraduationDesignDeclareData.GRADUATION_DESIGN_DECLARE_DATA);

        set(0, graduationDesignDeclareDataId);
        set(1, graduationDate);
        set(2, departmentName);
        set(3, scienceName);
        set(4, organizeNames);
        set(5, organizePeoples);
        set(6, graduationDesignReleaseId);
    }
}
