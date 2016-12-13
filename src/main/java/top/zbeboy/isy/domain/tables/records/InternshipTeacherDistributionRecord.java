/**
 * This class is generated by jOOQ
 */
package top.zbeboy.isy.domain.tables.records;


import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;

import top.zbeboy.isy.domain.tables.InternshipTeacherDistribution;


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
public class InternshipTeacherDistributionRecord extends UpdatableRecordImpl<InternshipTeacherDistributionRecord> implements Record4<Integer, Integer, String, String> {

	private static final long serialVersionUID = 1704355271;

	/**
	 * Setter for <code>isy.internship_teacher_distribution.staff_id</code>.
	 */
	public void setStaffId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>isy.internship_teacher_distribution.staff_id</code>.
	 */
	@NotNull
	public Integer getStaffId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>isy.internship_teacher_distribution.student_id</code>.
	 */
	public void setStudentId(Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>isy.internship_teacher_distribution.student_id</code>.
	 */
	@NotNull
	public Integer getStudentId() {
		return (Integer) getValue(1);
	}

	/**
	 * Setter for <code>isy.internship_teacher_distribution.internship_release_id</code>.
	 */
	public void setInternshipReleaseId(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>isy.internship_teacher_distribution.internship_release_id</code>.
	 */
	@NotNull
	@Size(max = 64)
	public String getInternshipReleaseId() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>isy.internship_teacher_distribution.username</code>.
	 */
	public void setUsername(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>isy.internship_teacher_distribution.username</code>.
	 */
	@NotNull
	@Size(max = 200)
	public String getUsername() {
		return (String) getValue(3);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record3<Integer, Integer, String> key() {
		return (Record3) super.key();
	}

	// -------------------------------------------------------------------------
	// Record4 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<Integer, Integer, String, String> fieldsRow() {
		return (Row4) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<Integer, Integer, String, String> valuesRow() {
		return (Row4) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return InternshipTeacherDistribution.INTERNSHIP_TEACHER_DISTRIBUTION.STAFF_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field2() {
		return InternshipTeacherDistribution.INTERNSHIP_TEACHER_DISTRIBUTION.STUDENT_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return InternshipTeacherDistribution.INTERNSHIP_TEACHER_DISTRIBUTION.INTERNSHIP_RELEASE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return InternshipTeacherDistribution.INTERNSHIP_TEACHER_DISTRIBUTION.USERNAME;
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
	public Integer value2() {
		return getStudentId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getInternshipReleaseId();
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
	public InternshipTeacherDistributionRecord value1(Integer value) {
		setStaffId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InternshipTeacherDistributionRecord value2(Integer value) {
		setStudentId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InternshipTeacherDistributionRecord value3(String value) {
		setInternshipReleaseId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InternshipTeacherDistributionRecord value4(String value) {
		setUsername(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InternshipTeacherDistributionRecord values(Integer value1, Integer value2, String value3, String value4) {
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
	 * Create a detached InternshipTeacherDistributionRecord
	 */
	public InternshipTeacherDistributionRecord() {
		super(InternshipTeacherDistribution.INTERNSHIP_TEACHER_DISTRIBUTION);
	}

	/**
	 * Create a detached, initialised InternshipTeacherDistributionRecord
	 */
	public InternshipTeacherDistributionRecord(Integer staffId, Integer studentId, String internshipReleaseId, String username) {
		super(InternshipTeacherDistribution.INTERNSHIP_TEACHER_DISTRIBUTION);

		setValue(0, staffId);
		setValue(1, studentId);
		setValue(2, internshipReleaseId);
		setValue(3, username);
	}
}
