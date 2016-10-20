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
import org.jooq.impl.TableImpl;

import top.zbeboy.isy.domain.Isy;
import top.zbeboy.isy.domain.Keys;
import top.zbeboy.isy.domain.tables.records.CollegeApplicationRecord;


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
public class CollegeApplication extends TableImpl<CollegeApplicationRecord> {

	private static final long serialVersionUID = -1874196719;

	/**
	 * The reference instance of <code>isy.college_application</code>
	 */
	public static final CollegeApplication COLLEGE_APPLICATION = new CollegeApplication();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<CollegeApplicationRecord> getRecordType() {
		return CollegeApplicationRecord.class;
	}

	/**
	 * The column <code>isy.college_application.application_id</code>.
	 */
	public final TableField<CollegeApplicationRecord, Integer> APPLICATION_ID = createField("application_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>isy.college_application.college_id</code>.
	 */
	public final TableField<CollegeApplicationRecord, Integer> COLLEGE_ID = createField("college_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * Create a <code>isy.college_application</code> table reference
	 */
	public CollegeApplication() {
		this("college_application", null);
	}

	/**
	 * Create an aliased <code>isy.college_application</code> table reference
	 */
	public CollegeApplication(String alias) {
		this(alias, COLLEGE_APPLICATION);
	}

	private CollegeApplication(String alias, Table<CollegeApplicationRecord> aliased) {
		this(alias, aliased, null);
	}

	private CollegeApplication(String alias, Table<CollegeApplicationRecord> aliased, Field<?>[] parameters) {
		super(alias, Isy.ISY, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<CollegeApplicationRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<CollegeApplicationRecord, ?>>asList(Keys.COLLEGE_APPLICATION_IBFK_1, Keys.COLLEGE_APPLICATION_IBFK_2);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CollegeApplication as(String alias) {
		return new CollegeApplication(alias, this);
	}

	/**
	 * Rename this table
	 */
	public CollegeApplication rename(String name) {
		return new CollegeApplication(name, null);
	}
}
