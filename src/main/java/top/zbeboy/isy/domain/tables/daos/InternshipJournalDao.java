/*
 * This file is generated by jOOQ.
*/
package top.zbeboy.isy.domain.tables.daos;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import top.zbeboy.isy.domain.tables.InternshipJournal;
import top.zbeboy.isy.domain.tables.records.InternshipJournalRecord;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Repository
public class InternshipJournalDao extends DAOImpl<InternshipJournalRecord, top.zbeboy.isy.domain.tables.pojos.InternshipJournal, String> {

    /**
     * Create a new InternshipJournalDao without any configuration
     */
    public InternshipJournalDao() {
        super(InternshipJournal.INTERNSHIP_JOURNAL, top.zbeboy.isy.domain.tables.pojos.InternshipJournal.class);
    }

    /**
     * Create a new InternshipJournalDao with an attached configuration
     */
    @Autowired
    public InternshipJournalDao(Configuration configuration) {
        super(InternshipJournal.INTERNSHIP_JOURNAL, top.zbeboy.isy.domain.tables.pojos.InternshipJournal.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getId(top.zbeboy.isy.domain.tables.pojos.InternshipJournal object) {
        return object.getInternshipJournalId();
    }

    /**
     * Fetch records that have <code>internship_journal_id IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.InternshipJournal> fetchByInternshipJournalId(String... values) {
        return fetch(InternshipJournal.INTERNSHIP_JOURNAL.INTERNSHIP_JOURNAL_ID, values);
    }

    /**
     * Fetch a unique record that has <code>internship_journal_id = value</code>
     */
    public top.zbeboy.isy.domain.tables.pojos.InternshipJournal fetchOneByInternshipJournalId(String value) {
        return fetchOne(InternshipJournal.INTERNSHIP_JOURNAL.INTERNSHIP_JOURNAL_ID, value);
    }

    /**
     * Fetch records that have <code>student_name IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.InternshipJournal> fetchByStudentName(String... values) {
        return fetch(InternshipJournal.INTERNSHIP_JOURNAL.STUDENT_NAME, values);
    }

    /**
     * Fetch records that have <code>student_number IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.InternshipJournal> fetchByStudentNumber(String... values) {
        return fetch(InternshipJournal.INTERNSHIP_JOURNAL.STUDENT_NUMBER, values);
    }

    /**
     * Fetch records that have <code>organize IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.InternshipJournal> fetchByOrganize(String... values) {
        return fetch(InternshipJournal.INTERNSHIP_JOURNAL.ORGANIZE, values);
    }

    /**
     * Fetch records that have <code>school_guidance_teacher IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.InternshipJournal> fetchBySchoolGuidanceTeacher(String... values) {
        return fetch(InternshipJournal.INTERNSHIP_JOURNAL.SCHOOL_GUIDANCE_TEACHER, values);
    }

    /**
     * Fetch records that have <code>graduation_practice_company_name IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.InternshipJournal> fetchByGraduationPracticeCompanyName(String... values) {
        return fetch(InternshipJournal.INTERNSHIP_JOURNAL.GRADUATION_PRACTICE_COMPANY_NAME, values);
    }

    /**
     * Fetch records that have <code>internship_journal_content IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.InternshipJournal> fetchByInternshipJournalContent(String... values) {
        return fetch(InternshipJournal.INTERNSHIP_JOURNAL.INTERNSHIP_JOURNAL_CONTENT, values);
    }

    /**
     * Fetch records that have <code>internship_journal_html IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.InternshipJournal> fetchByInternshipJournalHtml(String... values) {
        return fetch(InternshipJournal.INTERNSHIP_JOURNAL.INTERNSHIP_JOURNAL_HTML, values);
    }

    /**
     * Fetch records that have <code>internship_journal_date IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.InternshipJournal> fetchByInternshipJournalDate(Date... values) {
        return fetch(InternshipJournal.INTERNSHIP_JOURNAL.INTERNSHIP_JOURNAL_DATE, values);
    }

    /**
     * Fetch records that have <code>create_date IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.InternshipJournal> fetchByCreateDate(Timestamp... values) {
        return fetch(InternshipJournal.INTERNSHIP_JOURNAL.CREATE_DATE, values);
    }

    /**
     * Fetch records that have <code>student_id IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.InternshipJournal> fetchByStudentId(Integer... values) {
        return fetch(InternshipJournal.INTERNSHIP_JOURNAL.STUDENT_ID, values);
    }

    /**
     * Fetch records that have <code>internship_release_id IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.InternshipJournal> fetchByInternshipReleaseId(String... values) {
        return fetch(InternshipJournal.INTERNSHIP_JOURNAL.INTERNSHIP_RELEASE_ID, values);
    }

    /**
     * Fetch records that have <code>staff_id IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.InternshipJournal> fetchByStaffId(Integer... values) {
        return fetch(InternshipJournal.INTERNSHIP_JOURNAL.STAFF_ID, values);
    }

    /**
     * Fetch records that have <code>internship_journal_word IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.InternshipJournal> fetchByInternshipJournalWord(String... values) {
        return fetch(InternshipJournal.INTERNSHIP_JOURNAL.INTERNSHIP_JOURNAL_WORD, values);
    }

    /**
     * Fetch records that have <code>is_see_staff IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.InternshipJournal> fetchByIsSeeStaff(Byte... values) {
        return fetch(InternshipJournal.INTERNSHIP_JOURNAL.IS_SEE_STAFF, values);
    }
}
