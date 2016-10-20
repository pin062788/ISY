/**
 * This class is generated by jOOQ
 */
package top.zbeboy.isy.domain.tables.records;


import java.sql.Date;
import java.sql.Timestamp;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record14;
import org.jooq.Row14;
import org.jooq.impl.UpdatableRecordImpl;

import top.zbeboy.isy.domain.tables.InternshipRegulate;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.4"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class InternshipRegulateRecord extends UpdatableRecordImpl<InternshipRegulateRecord> implements Record14<String, String, String, String, String, String, String, Date, String, String, Timestamp, Integer, String, Integer> {

	private static final long serialVersionUID = -873377417;

	/**
	 * Setter for <code>isy.internship_regulate.internship_regulate_id</code>.
	 */
	public void setInternshipRegulateId(String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>isy.internship_regulate.internship_regulate_id</code>.
	 */
	@NotNull
	@Size(max = 100)
	public String getInternshipRegulateId() {
		return (String) getValue(0);
	}

	/**
	 * Setter for <code>isy.internship_regulate.student_name</code>.
	 */
	public void setStudentName(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>isy.internship_regulate.student_name</code>.
	 */
	@NotNull
	@Size(max = 10)
	public String getStudentName() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>isy.internship_regulate.student_number</code>.
	 */
	public void setStudentNumber(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>isy.internship_regulate.student_number</code>.
	 */
	@NotNull
	@Size(max = 20)
	public String getStudentNumber() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>isy.internship_regulate.student_tel</code>.
	 */
	public void setStudentTel(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>isy.internship_regulate.student_tel</code>.
	 */
	@NotNull
	@Size(max = 15)
	public String getStudentTel() {
		return (String) getValue(3);
	}

	/**
	 * Setter for <code>isy.internship_regulate.internship_content</code>.
	 */
	public void setInternshipContent(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>isy.internship_regulate.internship_content</code>.
	 */
	@NotNull
	@Size(max = 200)
	public String getInternshipContent() {
		return (String) getValue(4);
	}

	/**
	 * Setter for <code>isy.internship_regulate.internship_progress</code>.
	 */
	public void setInternshipProgress(String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>isy.internship_regulate.internship_progress</code>.
	 */
	@NotNull
	@Size(max = 200)
	public String getInternshipProgress() {
		return (String) getValue(5);
	}

	/**
	 * Setter for <code>isy.internship_regulate.report_way</code>.
	 */
	public void setReportWay(String value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>isy.internship_regulate.report_way</code>.
	 */
	@NotNull
	@Size(max = 20)
	public String getReportWay() {
		return (String) getValue(6);
	}

	/**
	 * Setter for <code>isy.internship_regulate.report_date</code>.
	 */
	public void setReportDate(Date value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>isy.internship_regulate.report_date</code>.
	 */
	@NotNull
	public Date getReportDate() {
		return (Date) getValue(7);
	}

	/**
	 * Setter for <code>isy.internship_regulate.school_guidance_teacher</code>.
	 */
	public void setSchoolGuidanceTeacher(String value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>isy.internship_regulate.school_guidance_teacher</code>.
	 */
	@NotNull
	@Size(max = 10)
	public String getSchoolGuidanceTeacher() {
		return (String) getValue(8);
	}

	/**
	 * Setter for <code>isy.internship_regulate.tliy</code>.
	 */
	public void setTliy(String value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>isy.internship_regulate.tliy</code>.
	 */
	@NotNull
	@Size(max = 200)
	public String getTliy() {
		return (String) getValue(9);
	}

	/**
	 * Setter for <code>isy.internship_regulate.create_date</code>.
	 */
	public void setCreateDate(Timestamp value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>isy.internship_regulate.create_date</code>.
	 */
	@NotNull
	public Timestamp getCreateDate() {
		return (Timestamp) getValue(10);
	}

	/**
	 * Setter for <code>isy.internship_regulate.student_id</code>.
	 */
	public void setStudentId(Integer value) {
		setValue(11, value);
	}

	/**
	 * Getter for <code>isy.internship_regulate.student_id</code>.
	 */
	@NotNull
	public Integer getStudentId() {
		return (Integer) getValue(11);
	}

	/**
	 * Setter for <code>isy.internship_regulate.internship_release_id</code>.
	 */
	public void setInternshipReleaseId(String value) {
		setValue(12, value);
	}

	/**
	 * Getter for <code>isy.internship_regulate.internship_release_id</code>.
	 */
	@NotNull
	@Size(max = 100)
	public String getInternshipReleaseId() {
		return (String) getValue(12);
	}

	/**
	 * Setter for <code>isy.internship_regulate.staff_id</code>.
	 */
	public void setStaffId(Integer value) {
		setValue(13, value);
	}

	/**
	 * Getter for <code>isy.internship_regulate.staff_id</code>.
	 */
	@NotNull
	public Integer getStaffId() {
		return (Integer) getValue(13);
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
	// Record14 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row14<String, String, String, String, String, String, String, Date, String, String, Timestamp, Integer, String, Integer> fieldsRow() {
		return (Row14) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row14<String, String, String, String, String, String, String, Date, String, String, Timestamp, Integer, String, Integer> valuesRow() {
		return (Row14) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field1() {
		return InternshipRegulate.INTERNSHIP_REGULATE.INTERNSHIP_REGULATE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return InternshipRegulate.INTERNSHIP_REGULATE.STUDENT_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return InternshipRegulate.INTERNSHIP_REGULATE.STUDENT_NUMBER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return InternshipRegulate.INTERNSHIP_REGULATE.STUDENT_TEL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field5() {
		return InternshipRegulate.INTERNSHIP_REGULATE.INTERNSHIP_CONTENT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field6() {
		return InternshipRegulate.INTERNSHIP_REGULATE.INTERNSHIP_PROGRESS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field7() {
		return InternshipRegulate.INTERNSHIP_REGULATE.REPORT_WAY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Date> field8() {
		return InternshipRegulate.INTERNSHIP_REGULATE.REPORT_DATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field9() {
		return InternshipRegulate.INTERNSHIP_REGULATE.SCHOOL_GUIDANCE_TEACHER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field10() {
		return InternshipRegulate.INTERNSHIP_REGULATE.TLIY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field11() {
		return InternshipRegulate.INTERNSHIP_REGULATE.CREATE_DATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field12() {
		return InternshipRegulate.INTERNSHIP_REGULATE.STUDENT_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field13() {
		return InternshipRegulate.INTERNSHIP_REGULATE.INTERNSHIP_RELEASE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field14() {
		return InternshipRegulate.INTERNSHIP_REGULATE.STAFF_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value1() {
		return getInternshipRegulateId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getStudentName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getStudentNumber();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getStudentTel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value5() {
		return getInternshipContent();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value6() {
		return getInternshipProgress();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value7() {
		return getReportWay();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Date value8() {
		return getReportDate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value9() {
		return getSchoolGuidanceTeacher();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value10() {
		return getTliy();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value11() {
		return getCreateDate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value12() {
		return getStudentId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value13() {
		return getInternshipReleaseId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value14() {
		return getStaffId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InternshipRegulateRecord value1(String value) {
		setInternshipRegulateId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InternshipRegulateRecord value2(String value) {
		setStudentName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InternshipRegulateRecord value3(String value) {
		setStudentNumber(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InternshipRegulateRecord value4(String value) {
		setStudentTel(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InternshipRegulateRecord value5(String value) {
		setInternshipContent(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InternshipRegulateRecord value6(String value) {
		setInternshipProgress(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InternshipRegulateRecord value7(String value) {
		setReportWay(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InternshipRegulateRecord value8(Date value) {
		setReportDate(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InternshipRegulateRecord value9(String value) {
		setSchoolGuidanceTeacher(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InternshipRegulateRecord value10(String value) {
		setTliy(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InternshipRegulateRecord value11(Timestamp value) {
		setCreateDate(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InternshipRegulateRecord value12(Integer value) {
		setStudentId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InternshipRegulateRecord value13(String value) {
		setInternshipReleaseId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InternshipRegulateRecord value14(Integer value) {
		setStaffId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InternshipRegulateRecord values(String value1, String value2, String value3, String value4, String value5, String value6, String value7, Date value8, String value9, String value10, Timestamp value11, Integer value12, String value13, Integer value14) {
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
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached InternshipRegulateRecord
	 */
	public InternshipRegulateRecord() {
		super(InternshipRegulate.INTERNSHIP_REGULATE);
	}

	/**
	 * Create a detached, initialised InternshipRegulateRecord
	 */
	public InternshipRegulateRecord(String internshipRegulateId, String studentName, String studentNumber, String studentTel, String internshipContent, String internshipProgress, String reportWay, Date reportDate, String schoolGuidanceTeacher, String tliy, Timestamp createDate, Integer studentId, String internshipReleaseId, Integer staffId) {
		super(InternshipRegulate.INTERNSHIP_REGULATE);

		setValue(0, internshipRegulateId);
		setValue(1, studentName);
		setValue(2, studentNumber);
		setValue(3, studentTel);
		setValue(4, internshipContent);
		setValue(5, internshipProgress);
		setValue(6, reportWay);
		setValue(7, reportDate);
		setValue(8, schoolGuidanceTeacher);
		setValue(9, tliy);
		setValue(10, createDate);
		setValue(11, studentId);
		setValue(12, internshipReleaseId);
		setValue(13, staffId);
	}
}
