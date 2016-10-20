/**
 * This class is generated by jOOQ
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

import top.zbeboy.isy.domain.tables.College;


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
public class CollegeRecord extends UpdatableRecordImpl<CollegeRecord> implements Record4<Integer, String, Byte, Integer> {

	private static final long serialVersionUID = -971031917;

	/**
	 * Setter for <code>isy.college.college_id</code>.
	 */
	public void setCollegeId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>isy.college.college_id</code>.
	 */
	@NotNull
	public Integer getCollegeId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>isy.college.college_name</code>.
	 */
	public void setCollegeName(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>isy.college.college_name</code>.
	 */
	@NotNull
	@Size(max = 200)
	public String getCollegeName() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>isy.college.college_is_del</code>.
	 */
	public void setCollegeIsDel(Byte value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>isy.college.college_is_del</code>.
	 */
	public Byte getCollegeIsDel() {
		return (Byte) getValue(2);
	}

	/**
	 * Setter for <code>isy.college.school_id</code>.
	 */
	public void setSchoolId(Integer value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>isy.college.school_id</code>.
	 */
	@NotNull
	public Integer getSchoolId() {
		return (Integer) getValue(3);
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
	public Row4<Integer, String, Byte, Integer> fieldsRow() {
		return (Row4) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<Integer, String, Byte, Integer> valuesRow() {
		return (Row4) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return College.COLLEGE.COLLEGE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return College.COLLEGE.COLLEGE_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Byte> field3() {
		return College.COLLEGE.COLLEGE_IS_DEL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field4() {
		return College.COLLEGE.SCHOOL_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getCollegeId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getCollegeName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Byte value3() {
		return getCollegeIsDel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value4() {
		return getSchoolId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CollegeRecord value1(Integer value) {
		setCollegeId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CollegeRecord value2(String value) {
		setCollegeName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CollegeRecord value3(Byte value) {
		setCollegeIsDel(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CollegeRecord value4(Integer value) {
		setSchoolId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CollegeRecord values(Integer value1, String value2, Byte value3, Integer value4) {
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
	 * Create a detached CollegeRecord
	 */
	public CollegeRecord() {
		super(College.COLLEGE);
	}

	/**
	 * Create a detached, initialised CollegeRecord
	 */
	public CollegeRecord(Integer collegeId, String collegeName, Byte collegeIsDel, Integer schoolId) {
		super(College.COLLEGE);

		setValue(0, collegeId);
		setValue(1, collegeName);
		setValue(2, collegeIsDel);
		setValue(3, schoolId);
	}
}