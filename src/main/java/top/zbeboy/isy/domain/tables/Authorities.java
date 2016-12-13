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
import top.zbeboy.isy.domain.tables.records.AuthoritiesRecord;


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
public class Authorities extends TableImpl<AuthoritiesRecord> {

	private static final long serialVersionUID = 633315478;

	/**
	 * The reference instance of <code>isy.authorities</code>
	 */
	public static final Authorities AUTHORITIES = new Authorities();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<AuthoritiesRecord> getRecordType() {
		return AuthoritiesRecord.class;
	}

	/**
	 * The column <code>isy.authorities.username</code>.
	 */
	public final TableField<AuthoritiesRecord, String> USERNAME = createField("username", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "");

	/**
	 * The column <code>isy.authorities.authority</code>.
	 */
	public final TableField<AuthoritiesRecord, String> AUTHORITY = createField("authority", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false), this, "");

	/**
	 * Create a <code>isy.authorities</code> table reference
	 */
	public Authorities() {
		this("authorities", null);
	}

	/**
	 * Create an aliased <code>isy.authorities</code> table reference
	 */
	public Authorities(String alias) {
		this(alias, AUTHORITIES);
	}

	private Authorities(String alias, Table<AuthoritiesRecord> aliased) {
		this(alias, aliased, null);
	}

	private Authorities(String alias, Table<AuthoritiesRecord> aliased, Field<?>[] parameters) {
		super(alias, Isy.ISY, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<AuthoritiesRecord> getPrimaryKey() {
		return Keys.KEY_AUTHORITIES_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<AuthoritiesRecord>> getKeys() {
		return Arrays.<UniqueKey<AuthoritiesRecord>>asList(Keys.KEY_AUTHORITIES_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<AuthoritiesRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<AuthoritiesRecord, ?>>asList(Keys.AUTHORITIES_IBFK_1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Authorities as(String alias) {
		return new Authorities(alias, this);
	}

	/**
	 * Rename this table
	 */
	public Authorities rename(String name) {
		return new Authorities(name, null);
	}
}
