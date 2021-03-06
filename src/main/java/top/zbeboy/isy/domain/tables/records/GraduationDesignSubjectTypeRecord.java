/*
 * This file is generated by jOOQ.
*/
package top.zbeboy.isy.domain.tables.records;


import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;

import top.zbeboy.isy.domain.tables.GraduationDesignSubjectType;


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
public class GraduationDesignSubjectTypeRecord extends UpdatableRecordImpl<GraduationDesignSubjectTypeRecord> implements Record2<Integer, String> {

    private static final long serialVersionUID = 569043368;

    /**
     * Setter for <code>isy.graduation_design_subject_type.subject_type_id</code>.
     */
    public void setSubjectTypeId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>isy.graduation_design_subject_type.subject_type_id</code>.
     */
    @NotNull
    public Integer getSubjectTypeId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>isy.graduation_design_subject_type.subject_type_name</code>.
     */
    public void setSubjectTypeName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>isy.graduation_design_subject_type.subject_type_name</code>.
     */
    @NotNull
    @Size(max = 30)
    public String getSubjectTypeName() {
        return (String) get(1);
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
    // Record2 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<Integer, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<Integer, String> valuesRow() {
        return (Row2) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return GraduationDesignSubjectType.GRADUATION_DESIGN_SUBJECT_TYPE.SUBJECT_TYPE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return GraduationDesignSubjectType.GRADUATION_DESIGN_SUBJECT_TYPE.SUBJECT_TYPE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getSubjectTypeId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getSubjectTypeName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GraduationDesignSubjectTypeRecord value1(Integer value) {
        setSubjectTypeId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GraduationDesignSubjectTypeRecord value2(String value) {
        setSubjectTypeName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GraduationDesignSubjectTypeRecord values(Integer value1, String value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached GraduationDesignSubjectTypeRecord
     */
    public GraduationDesignSubjectTypeRecord() {
        super(GraduationDesignSubjectType.GRADUATION_DESIGN_SUBJECT_TYPE);
    }

    /**
     * Create a detached, initialised GraduationDesignSubjectTypeRecord
     */
    public GraduationDesignSubjectTypeRecord(Integer subjectTypeId, String subjectTypeName) {
        super(GraduationDesignSubjectType.GRADUATION_DESIGN_SUBJECT_TYPE);

        set(0, subjectTypeId);
        set(1, subjectTypeName);
    }
}
