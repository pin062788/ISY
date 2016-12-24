/**
 * This class is generated by jOOQ
 */
package top.zbeboy.isy.domain.tables.daos;


import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;

import top.zbeboy.isy.domain.tables.SystemAlertType;
import top.zbeboy.isy.domain.tables.records.SystemAlertTypeRecord;


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
public class SystemAlertTypeDao extends DAOImpl<SystemAlertTypeRecord, top.zbeboy.isy.domain.tables.pojos.SystemAlertType, Integer> {

	/**
	 * Create a new SystemAlertTypeDao without any configuration
	 */
	public SystemAlertTypeDao() {
		super(SystemAlertType.SYSTEM_ALERT_TYPE, top.zbeboy.isy.domain.tables.pojos.SystemAlertType.class);
	}

	/**
	 * Create a new SystemAlertTypeDao with an attached configuration
	 */
	public SystemAlertTypeDao(Configuration configuration) {
		super(SystemAlertType.SYSTEM_ALERT_TYPE, top.zbeboy.isy.domain.tables.pojos.SystemAlertType.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(top.zbeboy.isy.domain.tables.pojos.SystemAlertType object) {
		return object.getSystemAlertTypeId();
	}

	/**
	 * Fetch records that have <code>system_alert_type_id IN (values)</code>
	 */
	public List<top.zbeboy.isy.domain.tables.pojos.SystemAlertType> fetchBySystemAlertTypeId(Integer... values) {
		return fetch(SystemAlertType.SYSTEM_ALERT_TYPE.SYSTEM_ALERT_TYPE_ID, values);
	}

	/**
	 * Fetch a unique record that has <code>system_alert_type_id = value</code>
	 */
	public top.zbeboy.isy.domain.tables.pojos.SystemAlertType fetchOneBySystemAlertTypeId(Integer value) {
		return fetchOne(SystemAlertType.SYSTEM_ALERT_TYPE.SYSTEM_ALERT_TYPE_ID, value);
	}

	/**
	 * Fetch records that have <code>name IN (values)</code>
	 */
	public List<top.zbeboy.isy.domain.tables.pojos.SystemAlertType> fetchByName(String... values) {
		return fetch(SystemAlertType.SYSTEM_ALERT_TYPE.NAME, values);
	}

	/**
	 * Fetch records that have <code>icon IN (values)</code>
	 */
	public List<top.zbeboy.isy.domain.tables.pojos.SystemAlertType> fetchByIcon(String... values) {
		return fetch(SystemAlertType.SYSTEM_ALERT_TYPE.ICON, values);
	}
}
