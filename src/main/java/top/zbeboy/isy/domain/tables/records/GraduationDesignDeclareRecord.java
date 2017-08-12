/*
 * This file is generated by jOOQ.
*/
package top.zbeboy.isy.domain.tables.records;


import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record15;
import org.jooq.Row15;
import org.jooq.impl.TableRecordImpl;

import top.zbeboy.isy.domain.tables.GraduationDesignDeclare;


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
public class GraduationDesignDeclareRecord extends TableRecordImpl<GraduationDesignDeclareRecord> implements Record15<Integer, Integer, Byte, Byte, Byte, Byte, Integer, String, String, String, String, Integer, Integer, Byte, String> {

    private static final long serialVersionUID = 859136333;

    /**
     * Setter for <code>isy.graduation_design_declare.subject_type_id</code>.
     */
    public void setSubjectTypeId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>isy.graduation_design_declare.subject_type_id</code>.
     */
    public Integer getSubjectTypeId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>isy.graduation_design_declare.origin_type_id</code>.
     */
    public void setOriginTypeId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>isy.graduation_design_declare.origin_type_id</code>.
     */
    public Integer getOriginTypeId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>isy.graduation_design_declare.is_new_subject</code>.
     */
    public void setIsNewSubject(Byte value) {
        set(2, value);
    }

    /**
     * Getter for <code>isy.graduation_design_declare.is_new_subject</code>.
     */
    public Byte getIsNewSubject() {
        return (Byte) get(2);
    }

    /**
     * Setter for <code>isy.graduation_design_declare.is_new_teacher_make</code>.
     */
    public void setIsNewTeacherMake(Byte value) {
        set(3, value);
    }

    /**
     * Getter for <code>isy.graduation_design_declare.is_new_teacher_make</code>.
     */
    public Byte getIsNewTeacherMake() {
        return (Byte) get(3);
    }

    /**
     * Setter for <code>isy.graduation_design_declare.is_new_subject_make</code>.
     */
    public void setIsNewSubjectMake(Byte value) {
        set(4, value);
    }

    /**
     * Getter for <code>isy.graduation_design_declare.is_new_subject_make</code>.
     */
    public Byte getIsNewSubjectMake() {
        return (Byte) get(4);
    }

    /**
     * Setter for <code>isy.graduation_design_declare.is_old_subject_change</code>.
     */
    public void setIsOldSubjectChange(Byte value) {
        set(5, value);
    }

    /**
     * Getter for <code>isy.graduation_design_declare.is_old_subject_change</code>.
     */
    public Byte getIsOldSubjectChange() {
        return (Byte) get(5);
    }

    /**
     * Setter for <code>isy.graduation_design_declare.old_subject_uses_times</code>.
     */
    public void setOldSubjectUsesTimes(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>isy.graduation_design_declare.old_subject_uses_times</code>.
     */
    public Integer getOldSubjectUsesTimes() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>isy.graduation_design_declare.plan_period</code>.
     */
    public void setPlanPeriod(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>isy.graduation_design_declare.plan_period</code>.
     */
    @Size(max = 10)
    public String getPlanPeriod() {
        return (String) get(7);
    }

    /**
     * Setter for <code>isy.graduation_design_declare.assistant_teacher</code>.
     */
    public void setAssistantTeacher(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>isy.graduation_design_declare.assistant_teacher</code>.
     */
    @Size(max = 30)
    public String getAssistantTeacher() {
        return (String) get(8);
    }

    /**
     * Setter for <code>isy.graduation_design_declare.assistant_teacher_academic</code>.
     */
    public void setAssistantTeacherAcademic(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>isy.graduation_design_declare.assistant_teacher_academic</code>.
     */
    @Size(max = 30)
    public String getAssistantTeacherAcademic() {
        return (String) get(9);
    }

    /**
     * Setter for <code>isy.graduation_design_declare.assistant_teacher_number</code>.
     */
    public void setAssistantTeacherNumber(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>isy.graduation_design_declare.assistant_teacher_number</code>.
     */
    @Size(max = 20)
    public String getAssistantTeacherNumber() {
        return (String) get(10);
    }

    /**
     * Setter for <code>isy.graduation_design_declare.guide_times</code>.
     */
    public void setGuideTimes(Integer value) {
        set(11, value);
    }

    /**
     * Getter for <code>isy.graduation_design_declare.guide_times</code>.
     */
    public Integer getGuideTimes() {
        return (Integer) get(11);
    }

    /**
     * Setter for <code>isy.graduation_design_declare.guide_peoples</code>.
     */
    public void setGuidePeoples(Integer value) {
        set(12, value);
    }

    /**
     * Getter for <code>isy.graduation_design_declare.guide_peoples</code>.
     */
    public Integer getGuidePeoples() {
        return (Integer) get(12);
    }

    /**
     * Setter for <code>isy.graduation_design_declare.is_ok_apply</code>.
     */
    public void setIsOkApply(Byte value) {
        set(13, value);
    }

    /**
     * Getter for <code>isy.graduation_design_declare.is_ok_apply</code>.
     */
    public Byte getIsOkApply() {
        return (Byte) get(13);
    }

    /**
     * Setter for <code>isy.graduation_design_declare.graduation_design_presubject_id</code>.
     */
    public void setGraduationDesignPresubjectId(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>isy.graduation_design_declare.graduation_design_presubject_id</code>.
     */
    @NotNull
    @Size(max = 64)
    public String getGraduationDesignPresubjectId() {
        return (String) get(14);
    }

    // -------------------------------------------------------------------------
    // Record15 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row15<Integer, Integer, Byte, Byte, Byte, Byte, Integer, String, String, String, String, Integer, Integer, Byte, String> fieldsRow() {
        return (Row15) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row15<Integer, Integer, Byte, Byte, Byte, Byte, Integer, String, String, String, String, Integer, Integer, Byte, String> valuesRow() {
        return (Row15) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return GraduationDesignDeclare.GRADUATION_DESIGN_DECLARE.SUBJECT_TYPE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return GraduationDesignDeclare.GRADUATION_DESIGN_DECLARE.ORIGIN_TYPE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field3() {
        return GraduationDesignDeclare.GRADUATION_DESIGN_DECLARE.IS_NEW_SUBJECT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field4() {
        return GraduationDesignDeclare.GRADUATION_DESIGN_DECLARE.IS_NEW_TEACHER_MAKE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field5() {
        return GraduationDesignDeclare.GRADUATION_DESIGN_DECLARE.IS_NEW_SUBJECT_MAKE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field6() {
        return GraduationDesignDeclare.GRADUATION_DESIGN_DECLARE.IS_OLD_SUBJECT_CHANGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return GraduationDesignDeclare.GRADUATION_DESIGN_DECLARE.OLD_SUBJECT_USES_TIMES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return GraduationDesignDeclare.GRADUATION_DESIGN_DECLARE.PLAN_PERIOD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return GraduationDesignDeclare.GRADUATION_DESIGN_DECLARE.ASSISTANT_TEACHER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return GraduationDesignDeclare.GRADUATION_DESIGN_DECLARE.ASSISTANT_TEACHER_ACADEMIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return GraduationDesignDeclare.GRADUATION_DESIGN_DECLARE.ASSISTANT_TEACHER_NUMBER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field12() {
        return GraduationDesignDeclare.GRADUATION_DESIGN_DECLARE.GUIDE_TIMES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field13() {
        return GraduationDesignDeclare.GRADUATION_DESIGN_DECLARE.GUIDE_PEOPLES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field14() {
        return GraduationDesignDeclare.GRADUATION_DESIGN_DECLARE.IS_OK_APPLY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field15() {
        return GraduationDesignDeclare.GRADUATION_DESIGN_DECLARE.GRADUATION_DESIGN_PRESUBJECT_ID;
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
    public Integer value2() {
        return getOriginTypeId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value3() {
        return getIsNewSubject();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value4() {
        return getIsNewTeacherMake();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value5() {
        return getIsNewSubjectMake();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value6() {
        return getIsOldSubjectChange();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getOldSubjectUsesTimes();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getPlanPeriod();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getAssistantTeacher();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getAssistantTeacherAcademic();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getAssistantTeacherNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value12() {
        return getGuideTimes();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value13() {
        return getGuidePeoples();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value14() {
        return getIsOkApply();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value15() {
        return getGraduationDesignPresubjectId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GraduationDesignDeclareRecord value1(Integer value) {
        setSubjectTypeId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GraduationDesignDeclareRecord value2(Integer value) {
        setOriginTypeId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GraduationDesignDeclareRecord value3(Byte value) {
        setIsNewSubject(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GraduationDesignDeclareRecord value4(Byte value) {
        setIsNewTeacherMake(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GraduationDesignDeclareRecord value5(Byte value) {
        setIsNewSubjectMake(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GraduationDesignDeclareRecord value6(Byte value) {
        setIsOldSubjectChange(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GraduationDesignDeclareRecord value7(Integer value) {
        setOldSubjectUsesTimes(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GraduationDesignDeclareRecord value8(String value) {
        setPlanPeriod(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GraduationDesignDeclareRecord value9(String value) {
        setAssistantTeacher(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GraduationDesignDeclareRecord value10(String value) {
        setAssistantTeacherAcademic(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GraduationDesignDeclareRecord value11(String value) {
        setAssistantTeacherNumber(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GraduationDesignDeclareRecord value12(Integer value) {
        setGuideTimes(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GraduationDesignDeclareRecord value13(Integer value) {
        setGuidePeoples(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GraduationDesignDeclareRecord value14(Byte value) {
        setIsOkApply(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GraduationDesignDeclareRecord value15(String value) {
        setGraduationDesignPresubjectId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GraduationDesignDeclareRecord values(Integer value1, Integer value2, Byte value3, Byte value4, Byte value5, Byte value6, Integer value7, String value8, String value9, String value10, String value11, Integer value12, Integer value13, Byte value14, String value15) {
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
        value13(value13);
        value14(value14);
        value15(value15);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached GraduationDesignDeclareRecord
     */
    public GraduationDesignDeclareRecord() {
        super(GraduationDesignDeclare.GRADUATION_DESIGN_DECLARE);
    }

    /**
     * Create a detached, initialised GraduationDesignDeclareRecord
     */
    public GraduationDesignDeclareRecord(Integer subjectTypeId, Integer originTypeId, Byte isNewSubject, Byte isNewTeacherMake, Byte isNewSubjectMake, Byte isOldSubjectChange, Integer oldSubjectUsesTimes, String planPeriod, String assistantTeacher, String assistantTeacherAcademic, String assistantTeacherNumber, Integer guideTimes, Integer guidePeoples, Byte isOkApply, String graduationDesignPresubjectId) {
        super(GraduationDesignDeclare.GRADUATION_DESIGN_DECLARE);

        set(0, subjectTypeId);
        set(1, originTypeId);
        set(2, isNewSubject);
        set(3, isNewTeacherMake);
        set(4, isNewSubjectMake);
        set(5, isOldSubjectChange);
        set(6, oldSubjectUsesTimes);
        set(7, planPeriod);
        set(8, assistantTeacher);
        set(9, assistantTeacherAcademic);
        set(10, assistantTeacherNumber);
        set(11, guideTimes);
        set(12, guidePeoples);
        set(13, isOkApply);
        set(14, graduationDesignPresubjectId);
    }
}
