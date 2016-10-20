/**
 * This class is generated by jOOQ
 */
package top.zbeboy.isy.domain.tables.records;


import javax.annotation.Generated;
import javax.validation.constraints.NotNull;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.TableRecordImpl;

import top.zbeboy.isy.domain.tables.CollegeRole;


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
public class CollegeRoleRecord extends TableRecordImpl<CollegeRoleRecord> implements Record2<Integer, Integer> {

	private static final long serialVersionUID = -1872502689;

	/**
	 * Setter for <code>isy.college_role.role_id</code>.
	 */
	public void setRoleId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>isy.college_role.role_id</code>.
	 */
	@NotNull
	public Integer getRoleId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>isy.college_role.college_id</code>.
	 */
	public void setCollegeId(Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>isy.college_role.college_id</code>.
	 */
	@NotNull
	public Integer getCollegeId() {
		return (Integer) getValue(1);
	}

	// -------------------------------------------------------------------------
	// Record2 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row2<Integer, Integer> fieldsRow() {
		return (Row2) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row2<Integer, Integer> valuesRow() {
		return (Row2) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return CollegeRole.COLLEGE_ROLE.ROLE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field2() {
		return CollegeRole.COLLEGE_ROLE.COLLEGE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getRoleId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value2() {
		return getCollegeId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CollegeRoleRecord value1(Integer value) {
		setRoleId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CollegeRoleRecord value2(Integer value) {
		setCollegeId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CollegeRoleRecord values(Integer value1, Integer value2) {
		value1(value1);
		value2(value2);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached CollegeRoleRecord
	 */
	public CollegeRoleRecord() {
		super(CollegeRole.COLLEGE_ROLE);
	}

	/**
	 * Create a detached, initialised CollegeRoleRecord
	 */
	public CollegeRoleRecord(Integer roleId, Integer collegeId) {
		super(CollegeRole.COLLEGE_ROLE);

		setValue(0, roleId);
		setValue(1, collegeId);
	}
}
