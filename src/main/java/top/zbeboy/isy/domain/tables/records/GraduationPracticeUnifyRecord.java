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

import top.zbeboy.isy.domain.tables.GraduationPracticeUnify;


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
public class GraduationPracticeUnifyRecord extends UpdatableRecordImpl<GraduationPracticeUnifyRecord> implements Record3<String, Integer, String> {

	private static final long serialVersionUID = 600320418;

	/**
	 * Setter for <code>isy.graduation_practice_unify.graduation_practice_unify_id</code>.
	 */
	public void setGraduationPracticeUnifyId(String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>isy.graduation_practice_unify.graduation_practice_unify_id</code>.
	 */
	@NotNull
	@Size(max = 100)
	public String getGraduationPracticeUnifyId() {
		return (String) getValue(0);
	}

	/**
	 * Setter for <code>isy.graduation_practice_unify.student_id</code>.
	 */
	public void setStudentId(Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>isy.graduation_practice_unify.student_id</code>.
	 */
	@NotNull
	public Integer getStudentId() {
		return (Integer) getValue(1);
	}

	/**
	 * Setter for <code>isy.graduation_practice_unify.internship_release_id</code>.
	 */
	public void setInternshipReleaseId(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>isy.graduation_practice_unify.internship_release_id</code>.
	 */
	@NotNull
	@Size(max = 100)
	public String getInternshipReleaseId() {
		return (String) getValue(2);
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
	// Record3 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row3<String, Integer, String> fieldsRow() {
		return (Row3) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row3<String, Integer, String> valuesRow() {
		return (Row3) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field1() {
		return GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY.GRADUATION_PRACTICE_UNIFY_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field2() {
		return GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY.STUDENT_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY.INTERNSHIP_RELEASE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value1() {
		return getGraduationPracticeUnifyId();
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
	public GraduationPracticeUnifyRecord value1(String value) {
		setGraduationPracticeUnifyId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GraduationPracticeUnifyRecord value2(Integer value) {
		setStudentId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GraduationPracticeUnifyRecord value3(String value) {
		setInternshipReleaseId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GraduationPracticeUnifyRecord values(String value1, Integer value2, String value3) {
		value1(value1);
		value2(value2);
		value3(value3);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached GraduationPracticeUnifyRecord
	 */
	public GraduationPracticeUnifyRecord() {
		super(GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY);
	}

	/**
	 * Create a detached, initialised GraduationPracticeUnifyRecord
	 */
	public GraduationPracticeUnifyRecord(String graduationPracticeUnifyId, Integer studentId, String internshipReleaseId) {
		super(GraduationPracticeUnify.GRADUATION_PRACTICE_UNIFY);

		setValue(0, graduationPracticeUnifyId);
		setValue(1, studentId);
		setValue(2, internshipReleaseId);
	}
}