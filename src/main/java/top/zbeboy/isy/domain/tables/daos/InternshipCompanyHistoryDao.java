/**
 * This class is generated by jOOQ
 */
package top.zbeboy.isy.domain.tables.daos;


import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;

import top.zbeboy.isy.domain.tables.InternshipCompanyHistory;
import top.zbeboy.isy.domain.tables.records.InternshipCompanyHistoryRecord;


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
public class InternshipCompanyHistoryDao extends DAOImpl<InternshipCompanyHistoryRecord, top.zbeboy.isy.domain.tables.pojos.InternshipCompanyHistory, Integer> {

	/**
	 * Create a new InternshipCompanyHistoryDao without any configuration
	 */
	public InternshipCompanyHistoryDao() {
		super(InternshipCompanyHistory.INTERNSHIP_COMPANY_HISTORY, top.zbeboy.isy.domain.tables.pojos.InternshipCompanyHistory.class);
	}

	/**
	 * Create a new InternshipCompanyHistoryDao with an attached configuration
	 */
	public InternshipCompanyHistoryDao(Configuration configuration) {
		super(InternshipCompanyHistory.INTERNSHIP_COMPANY_HISTORY, top.zbeboy.isy.domain.tables.pojos.InternshipCompanyHistory.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Integer getId(top.zbeboy.isy.domain.tables.pojos.InternshipCompanyHistory object) {
		return object.getInternshipCompanyHistoryId();
	}

	/**
	 * Fetch records that have <code>internship_company_history_id IN (values)</code>
	 */
	public List<top.zbeboy.isy.domain.tables.pojos.InternshipCompanyHistory> fetchByInternshipCompanyHistoryId(Integer... values) {
		return fetch(InternshipCompanyHistory.INTERNSHIP_COMPANY_HISTORY.INTERNSHIP_COMPANY_HISTORY_ID, values);
	}

	/**
	 * Fetch a unique record that has <code>internship_company_history_id = value</code>
	 */
	public top.zbeboy.isy.domain.tables.pojos.InternshipCompanyHistory fetchOneByInternshipCompanyHistoryId(Integer value) {
		return fetchOne(InternshipCompanyHistory.INTERNSHIP_COMPANY_HISTORY.INTERNSHIP_COMPANY_HISTORY_ID, value);
	}

	/**
	 * Fetch records that have <code>internship_company_name IN (values)</code>
	 */
	public List<top.zbeboy.isy.domain.tables.pojos.InternshipCompanyHistory> fetchByInternshipCompanyName(String... values) {
		return fetch(InternshipCompanyHistory.INTERNSHIP_COMPANY_HISTORY.INTERNSHIP_COMPANY_NAME, values);
	}

	/**
	 * Fetch records that have <code>internship_company_address IN (values)</code>
	 */
	public List<top.zbeboy.isy.domain.tables.pojos.InternshipCompanyHistory> fetchByInternshipCompanyAddress(String... values) {
		return fetch(InternshipCompanyHistory.INTERNSHIP_COMPANY_HISTORY.INTERNSHIP_COMPANY_ADDRESS, values);
	}

	/**
	 * Fetch records that have <code>internship_company_contacts IN (values)</code>
	 */
	public List<top.zbeboy.isy.domain.tables.pojos.InternshipCompanyHistory> fetchByInternshipCompanyContacts(String... values) {
		return fetch(InternshipCompanyHistory.INTERNSHIP_COMPANY_HISTORY.INTERNSHIP_COMPANY_CONTACTS, values);
	}

	/**
	 * Fetch records that have <code>internship_company_tel IN (values)</code>
	 */
	public List<top.zbeboy.isy.domain.tables.pojos.InternshipCompanyHistory> fetchByInternshipCompanyTel(String... values) {
		return fetch(InternshipCompanyHistory.INTERNSHIP_COMPANY_HISTORY.INTERNSHIP_COMPANY_TEL, values);
	}

	/**
	 * Fetch records that have <code>student_id IN (values)</code>
	 */
	public List<top.zbeboy.isy.domain.tables.pojos.InternshipCompanyHistory> fetchByStudentId(Integer... values) {
		return fetch(InternshipCompanyHistory.INTERNSHIP_COMPANY_HISTORY.STUDENT_ID, values);
	}

	/**
	 * Fetch records that have <code>internship_release_id IN (values)</code>
	 */
	public List<top.zbeboy.isy.domain.tables.pojos.InternshipCompanyHistory> fetchByInternshipReleaseId(String... values) {
		return fetch(InternshipCompanyHistory.INTERNSHIP_COMPANY_HISTORY.INTERNSHIP_RELEASE_ID, values);
	}
}
