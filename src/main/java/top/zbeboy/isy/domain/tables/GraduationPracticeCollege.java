/**
 * This class is generated by jOOQ
 */
package top.zbeboy.isy.domain.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;

import top.zbeboy.isy.domain.Isy;
import top.zbeboy.isy.domain.Keys;
import top.zbeboy.isy.domain.tables.records.GraduationPracticeCollegeRecord;


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
public class GraduationPracticeCollege extends TableImpl<GraduationPracticeCollegeRecord> {

	private static final long serialVersionUID = -284748604;

	/**
	 * The reference instance of <code>isy.graduation_practice_college</code>
	 */
	public static final GraduationPracticeCollege GRADUATION_PRACTICE_COLLEGE = new GraduationPracticeCollege();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<GraduationPracticeCollegeRecord> getRecordType() {
		return GraduationPracticeCollegeRecord.class;
	}

	/**
	 * The column <code>isy.graduation_practice_college.graduation_practice_college_id</code>.
	 */
	public final TableField<GraduationPracticeCollegeRecord, String> GRADUATION_PRACTICE_COLLEGE_ID = createField("graduation_practice_college_id", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "");

	/**
	 * The column <code>isy.graduation_practice_college.student_id</code>.
	 */
	public final TableField<GraduationPracticeCollegeRecord, Integer> STUDENT_ID = createField("student_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>isy.graduation_practice_college.internship_release_id</code>.
	 */
	public final TableField<GraduationPracticeCollegeRecord, String> INTERNSHIP_RELEASE_ID = createField("internship_release_id", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "");

	/**
	 * Create a <code>isy.graduation_practice_college</code> table reference
	 */
	public GraduationPracticeCollege() {
		this("graduation_practice_college", null);
	}

	/**
	 * Create an aliased <code>isy.graduation_practice_college</code> table reference
	 */
	public GraduationPracticeCollege(String alias) {
		this(alias, GRADUATION_PRACTICE_COLLEGE);
	}

	private GraduationPracticeCollege(String alias, Table<GraduationPracticeCollegeRecord> aliased) {
		this(alias, aliased, null);
	}

	private GraduationPracticeCollege(String alias, Table<GraduationPracticeCollegeRecord> aliased, Field<?>[] parameters) {
		super(alias, Isy.ISY, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<GraduationPracticeCollegeRecord> getPrimaryKey() {
		return Keys.KEY_GRADUATION_PRACTICE_COLLEGE_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<GraduationPracticeCollegeRecord>> getKeys() {
		return Arrays.<UniqueKey<GraduationPracticeCollegeRecord>>asList(Keys.KEY_GRADUATION_PRACTICE_COLLEGE_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<GraduationPracticeCollegeRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<GraduationPracticeCollegeRecord, ?>>asList(Keys.GRADUATION_PRACTICE_COLLEGE_IBFK_1, Keys.GRADUATION_PRACTICE_COLLEGE_IBFK_2);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GraduationPracticeCollege as(String alias) {
		return new GraduationPracticeCollege(alias, this);
	}

	/**
	 * Rename this table
	 */
	public GraduationPracticeCollege rename(String name) {
		return new GraduationPracticeCollege(name, null);
	}
}