/**
 * This class is generated by jOOQ
 */
package top.zbeboy.isy.domain.tables.records;


import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;

import top.zbeboy.isy.domain.tables.School;


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
public class SchoolRecord extends UpdatableRecordImpl<SchoolRecord> implements Record3<Integer, String, Byte> {

	private static final long serialVersionUID = -1593075239;

	/**
	 * Setter for <code>isy.school.school_id</code>.
	 */
	public void setSchoolId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>isy.school.school_id</code>.
	 */
	@NotNull
	public Integer getSchoolId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>isy.school.school_name</code>.
	 */
	public void setSchoolName(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>isy.school.school_name</code>.
	 */
	@NotNull
	@Size(max = 200)
	public String getSchoolName() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>isy.school.school_is_del</code>.
	 */
	public void setSchoolIsDel(Byte value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>isy.school.school_is_del</code>.
	 */
	public Byte getSchoolIsDel() {
		return (Byte) getValue(2);
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
	// Record3 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row3<Integer, String, Byte> fieldsRow() {
		return (Row3) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row3<Integer, String, Byte> valuesRow() {
		return (Row3) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return School.SCHOOL.SCHOOL_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return School.SCHOOL.SCHOOL_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Byte> field3() {
		return School.SCHOOL.SCHOOL_IS_DEL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getSchoolId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getSchoolName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Byte value3() {
		return getSchoolIsDel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SchoolRecord value1(Integer value) {
		setSchoolId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SchoolRecord value2(String value) {
		setSchoolName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SchoolRecord value3(Byte value) {
		setSchoolIsDel(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SchoolRecord values(Integer value1, String value2, Byte value3) {
		value1(value1);
		value2(value2);
		value3(value3);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached SchoolRecord
	 */
	public SchoolRecord() {
		super(School.SCHOOL);
	}

	/**
	 * Create a detached, initialised SchoolRecord
	 */
	public SchoolRecord(Integer schoolId, String schoolName, Byte schoolIsDel) {
		super(School.SCHOOL);

		setValue(0, schoolId);
		setValue(1, schoolName);
		setValue(2, schoolIsDel);
	}
}
