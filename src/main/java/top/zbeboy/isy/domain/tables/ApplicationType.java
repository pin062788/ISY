/**
 * This class is generated by jOOQ
 */
package top.zbeboy.isy.domain.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;

import top.zbeboy.isy.domain.Isy;
import top.zbeboy.isy.domain.Keys;
import top.zbeboy.isy.domain.tables.records.ApplicationTypeRecord;


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
public class ApplicationType extends TableImpl<ApplicationTypeRecord> {

	private static final long serialVersionUID = 1151408425;

	/**
	 * The reference instance of <code>isy.application_type</code>
	 */
	public static final ApplicationType APPLICATION_TYPE = new ApplicationType();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<ApplicationTypeRecord> getRecordType() {
		return ApplicationTypeRecord.class;
	}

	/**
	 * The column <code>isy.application_type.application_type_id</code>.
	 */
	public final TableField<ApplicationTypeRecord, Integer> APPLICATION_TYPE_ID = createField("application_type_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>isy.application_type.application_type_name</code>.
	 */
	public final TableField<ApplicationTypeRecord, String> APPLICATION_TYPE_NAME = createField("application_type_name", org.jooq.impl.SQLDataType.VARCHAR.length(32).nullable(false), this, "");

	/**
	 * Create a <code>isy.application_type</code> table reference
	 */
	public ApplicationType() {
		this("application_type", null);
	}

	/**
	 * Create an aliased <code>isy.application_type</code> table reference
	 */
	public ApplicationType(String alias) {
		this(alias, APPLICATION_TYPE);
	}

	private ApplicationType(String alias, Table<ApplicationTypeRecord> aliased) {
		this(alias, aliased, null);
	}

	private ApplicationType(String alias, Table<ApplicationTypeRecord> aliased, Field<?>[] parameters) {
		super(alias, Isy.ISY, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<ApplicationTypeRecord, Integer> getIdentity() {
		return Keys.IDENTITY_APPLICATION_TYPE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<ApplicationTypeRecord> getPrimaryKey() {
		return Keys.KEY_APPLICATION_TYPE_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<ApplicationTypeRecord>> getKeys() {
		return Arrays.<UniqueKey<ApplicationTypeRecord>>asList(Keys.KEY_APPLICATION_TYPE_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ApplicationType as(String alias) {
		return new ApplicationType(alias, this);
	}

	/**
	 * Rename this table
	 */
	public ApplicationType rename(String name) {
		return new ApplicationType(name, null);
	}
}