/*
 * This file is generated by jOOQ.
*/
package top.zbeboy.isy.domain.tables.records;


import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;

import top.zbeboy.isy.domain.tables.Schoolroom;


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
public class SchoolroomRecord extends UpdatableRecordImpl<SchoolroomRecord> implements Record4<Integer, Integer, String, Byte> {

    private static final long serialVersionUID = 175673258;

    /**
     * Setter for <code>isy.schoolroom.schoolroom_id</code>.
     */
    public void setSchoolroomId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>isy.schoolroom.schoolroom_id</code>.
     */
    @NotNull
    public Integer getSchoolroomId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>isy.schoolroom.building_id</code>.
     */
    public void setBuildingId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>isy.schoolroom.building_id</code>.
     */
    @NotNull
    public Integer getBuildingId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>isy.schoolroom.building_code</code>.
     */
    public void setBuildingCode(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>isy.schoolroom.building_code</code>.
     */
    @NotNull
    @Size(max = 10)
    public String getBuildingCode() {
        return (String) get(2);
    }

    /**
     * Setter for <code>isy.schoolroom.schoolroom_is_del</code>.
     */
    public void setSchoolroomIsDel(Byte value) {
        set(3, value);
    }

    /**
     * Getter for <code>isy.schoolroom.schoolroom_is_del</code>.
     */
    public Byte getSchoolroomIsDel() {
        return (Byte) get(3);
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
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Integer, Integer, String, Byte> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Integer, Integer, String, Byte> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Schoolroom.SCHOOLROOM.SCHOOLROOM_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return Schoolroom.SCHOOLROOM.BUILDING_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Schoolroom.SCHOOLROOM.BUILDING_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field4() {
        return Schoolroom.SCHOOLROOM.SCHOOLROOM_IS_DEL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getSchoolroomId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getBuildingId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getBuildingCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value4() {
        return getSchoolroomIsDel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SchoolroomRecord value1(Integer value) {
        setSchoolroomId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SchoolroomRecord value2(Integer value) {
        setBuildingId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SchoolroomRecord value3(String value) {
        setBuildingCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SchoolroomRecord value4(Byte value) {
        setSchoolroomIsDel(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SchoolroomRecord values(Integer value1, Integer value2, String value3, Byte value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached SchoolroomRecord
     */
    public SchoolroomRecord() {
        super(Schoolroom.SCHOOLROOM);
    }

    /**
     * Create a detached, initialised SchoolroomRecord
     */
    public SchoolroomRecord(Integer schoolroomId, Integer buildingId, String buildingCode, Byte schoolroomIsDel) {
        super(Schoolroom.SCHOOLROOM);

        set(0, schoolroomId);
        set(1, buildingId);
        set(2, buildingCode);
        set(3, schoolroomIsDel);
    }
}
