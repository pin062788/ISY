/**
 * This class is generated by jOOQ
 */
package top.zbeboy.isy.domain.tables.records;


import java.sql.Timestamp;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Row10;
import org.jooq.impl.UpdatableRecordImpl;

import top.zbeboy.isy.domain.tables.InternshipRelease;


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
public class InternshipReleaseRecord extends UpdatableRecordImpl<InternshipReleaseRecord> implements Record10<String, String, Timestamp, String, String, Timestamp, Timestamp, Byte, Integer, Integer> {

	private static final long serialVersionUID = 1902011273;

	/**
	 * Setter for <code>isy.internship_release.internship_release_id</code>.
	 */
	public void setInternshipReleaseId(String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>isy.internship_release.internship_release_id</code>.
	 */
	@NotNull
	@Size(max = 100)
	public String getInternshipReleaseId() {
		return (String) getValue(0);
	}

	/**
	 * Setter for <code>isy.internship_release.internship_title</code>.
	 */
	public void setInternshipTitle(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>isy.internship_release.internship_title</code>.
	 */
	@NotNull
	@Size(max = 100)
	public String getInternshipTitle() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>isy.internship_release.release_time</code>.
	 */
	public void setReleaseTime(Timestamp value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>isy.internship_release.release_time</code>.
	 */
	public Timestamp getReleaseTime() {
		return (Timestamp) getValue(2);
	}

	/**
	 * Setter for <code>isy.internship_release.username</code>.
	 */
	public void setUsername(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>isy.internship_release.username</code>.
	 */
	@NotNull
	@Size(max = 200)
	public String getUsername() {
		return (String) getValue(3);
	}

	/**
	 * Setter for <code>isy.internship_release.allow_grade</code>.
	 */
	public void setAllowGrade(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>isy.internship_release.allow_grade</code>.
	 */
	@NotNull
	@Size(max = 5)
	public String getAllowGrade() {
		return (String) getValue(4);
	}

	/**
	 * Setter for <code>isy.internship_release.start_time</code>.
	 */
	public void setStartTime(Timestamp value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>isy.internship_release.start_time</code>.
	 */
	@NotNull
	public Timestamp getStartTime() {
		return (Timestamp) getValue(5);
	}

	/**
	 * Setter for <code>isy.internship_release.end_time</code>.
	 */
	public void setEndTime(Timestamp value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>isy.internship_release.end_time</code>.
	 */
	@NotNull
	public Timestamp getEndTime() {
		return (Timestamp) getValue(6);
	}

	/**
	 * Setter for <code>isy.internship_release.internship_release_is_del</code>.
	 */
	public void setInternshipReleaseIsDel(Byte value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>isy.internship_release.internship_release_is_del</code>.
	 */
	@NotNull
	public Byte getInternshipReleaseIsDel() {
		return (Byte) getValue(7);
	}

	/**
	 * Setter for <code>isy.internship_release.department_id</code>.
	 */
	public void setDepartmentId(Integer value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>isy.internship_release.department_id</code>.
	 */
	@NotNull
	public Integer getDepartmentId() {
		return (Integer) getValue(8);
	}

	/**
	 * Setter for <code>isy.internship_release.internship_type_id</code>.
	 */
	public void setInternshipTypeId(Integer value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>isy.internship_release.internship_type_id</code>.
	 */
	@NotNull
	public Integer getInternshipTypeId() {
		return (Integer) getValue(9);
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
	// Record10 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row10<String, String, Timestamp, String, String, Timestamp, Timestamp, Byte, Integer, Integer> fieldsRow() {
		return (Row10) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row10<String, String, Timestamp, String, String, Timestamp, Timestamp, Byte, Integer, Integer> valuesRow() {
		return (Row10) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field1() {
		return InternshipRelease.INTERNSHIP_RELEASE.INTERNSHIP_RELEASE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return InternshipRelease.INTERNSHIP_RELEASE.INTERNSHIP_TITLE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field3() {
		return InternshipRelease.INTERNSHIP_RELEASE.RELEASE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return InternshipRelease.INTERNSHIP_RELEASE.USERNAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field5() {
		return InternshipRelease.INTERNSHIP_RELEASE.ALLOW_GRADE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field6() {
		return InternshipRelease.INTERNSHIP_RELEASE.START_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field7() {
		return InternshipRelease.INTERNSHIP_RELEASE.END_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Byte> field8() {
		return InternshipRelease.INTERNSHIP_RELEASE.INTERNSHIP_RELEASE_IS_DEL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field9() {
		return InternshipRelease.INTERNSHIP_RELEASE.DEPARTMENT_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field10() {
		return InternshipRelease.INTERNSHIP_RELEASE.INTERNSHIP_TYPE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value1() {
		return getInternshipReleaseId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getInternshipTitle();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value3() {
		return getReleaseTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getUsername();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value5() {
		return getAllowGrade();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value6() {
		return getStartTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value7() {
		return getEndTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Byte value8() {
		return getInternshipReleaseIsDel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value9() {
		return getDepartmentId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value10() {
		return getInternshipTypeId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InternshipReleaseRecord value1(String value) {
		setInternshipReleaseId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InternshipReleaseRecord value2(String value) {
		setInternshipTitle(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InternshipReleaseRecord value3(Timestamp value) {
		setReleaseTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InternshipReleaseRecord value4(String value) {
		setUsername(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InternshipReleaseRecord value5(String value) {
		setAllowGrade(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InternshipReleaseRecord value6(Timestamp value) {
		setStartTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InternshipReleaseRecord value7(Timestamp value) {
		setEndTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InternshipReleaseRecord value8(Byte value) {
		setInternshipReleaseIsDel(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InternshipReleaseRecord value9(Integer value) {
		setDepartmentId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InternshipReleaseRecord value10(Integer value) {
		setInternshipTypeId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InternshipReleaseRecord values(String value1, String value2, Timestamp value3, String value4, String value5, Timestamp value6, Timestamp value7, Byte value8, Integer value9, Integer value10) {
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
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached InternshipReleaseRecord
	 */
	public InternshipReleaseRecord() {
		super(InternshipRelease.INTERNSHIP_RELEASE);
	}

	/**
	 * Create a detached, initialised InternshipReleaseRecord
	 */
	public InternshipReleaseRecord(String internshipReleaseId, String internshipTitle, Timestamp releaseTime, String username, String allowGrade, Timestamp startTime, Timestamp endTime, Byte internshipReleaseIsDel, Integer departmentId, Integer internshipTypeId) {
		super(InternshipRelease.INTERNSHIP_RELEASE);

		setValue(0, internshipReleaseId);
		setValue(1, internshipTitle);
		setValue(2, releaseTime);
		setValue(3, username);
		setValue(4, allowGrade);
		setValue(5, startTime);
		setValue(6, endTime);
		setValue(7, internshipReleaseIsDel);
		setValue(8, departmentId);
		setValue(9, internshipTypeId);
	}
}
