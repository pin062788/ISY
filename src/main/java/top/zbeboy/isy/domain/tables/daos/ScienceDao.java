/**
 * This class is generated by jOOQ
 */
package top.zbeboy.isy.domain.tables.daos;


import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;

import top.zbeboy.isy.domain.tables.Science;
import top.zbeboy.isy.domain.tables.records.ScienceRecord;


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
public class ScienceDao extends DAOImpl<ScienceRecord, top.zbeboy.isy.domain.tables.pojos.Science, Integer> {

	/**
	 * Create a new ScienceDao without any configuration
	 */
	public ScienceDao() {
		super(Science.SCIENCE, top.zbeboy.isy.domain.tables.pojos.Science.class);
	}

	/**
	 * Create a new ScienceDao with an attached configuration
	 */
	public ScienceDao(Configuration configuration) {
		super(Science.SCIENCE, top.zbeboy.isy.domain.tables.pojos.Science.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(top.zbeboy.isy.domain.tables.pojos.Science object) {
		return object.getScienceId();
	}

	/**
	 * Fetch records that have <code>science_id IN (values)</code>
	 */
	public List<top.zbeboy.isy.domain.tables.pojos.Science> fetchByScienceId(Integer... values) {
		return fetch(Science.SCIENCE.SCIENCE_ID, values);
	}

	/**
	 * Fetch a unique record that has <code>science_id = value</code>
	 */
	public top.zbeboy.isy.domain.tables.pojos.Science fetchOneByScienceId(Integer value) {
		return fetchOne(Science.SCIENCE.SCIENCE_ID, value);
	}

	/**
	 * Fetch records that have <code>science_name IN (values)</code>
	 */
	public List<top.zbeboy.isy.domain.tables.pojos.Science> fetchByScienceName(String... values) {
		return fetch(Science.SCIENCE.SCIENCE_NAME, values);
	}

	/**
	 * Fetch records that have <code>science_is_del IN (values)</code>
	 */
	public List<top.zbeboy.isy.domain.tables.pojos.Science> fetchByScienceIsDel(Byte... values) {
		return fetch(Science.SCIENCE.SCIENCE_IS_DEL, values);
	}

	/**
	 * Fetch records that have <code>department_id IN (values)</code>
	 */
	public List<top.zbeboy.isy.domain.tables.pojos.Science> fetchByDepartmentId(Integer... values) {
		return fetch(Science.SCIENCE.DEPARTMENT_ID, values);
	}
}