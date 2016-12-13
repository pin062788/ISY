/**
 * This class is generated by jOOQ
 */
package top.zbeboy.isy.domain.tables.daos;


import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.Record2;
import org.jooq.impl.DAOImpl;

import top.zbeboy.isy.domain.tables.Authorities;
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
public class AuthoritiesDao extends DAOImpl<AuthoritiesRecord, top.zbeboy.isy.domain.tables.pojos.Authorities, Record2<String, String>> {

	/**
	 * Create a new AuthoritiesDao without any configuration
	 */
	public AuthoritiesDao() {
		super(Authorities.AUTHORITIES, top.zbeboy.isy.domain.tables.pojos.Authorities.class);
	}

	/**
	 * Create a new AuthoritiesDao with an attached configuration
	 */
	public AuthoritiesDao(Configuration configuration) {
		super(Authorities.AUTHORITIES, top.zbeboy.isy.domain.tables.pojos.Authorities.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Record2<String, String> getId(top.zbeboy.isy.domain.tables.pojos.Authorities object) {
		return compositeKeyRecord(object.getUsername(), object.getAuthority());
	}

	/**
	 * Fetch records that have <code>username IN (values)</code>
	 */
	public List<top.zbeboy.isy.domain.tables.pojos.Authorities> fetchByUsername(String... values) {
		return fetch(Authorities.AUTHORITIES.USERNAME, values);
	}

	/**
	 * Fetch records that have <code>authority IN (values)</code>
	 */
	public List<top.zbeboy.isy.domain.tables.pojos.Authorities> fetchByAuthority(String... values) {
		return fetch(Authorities.AUTHORITIES.AUTHORITY, values);
	}
}
