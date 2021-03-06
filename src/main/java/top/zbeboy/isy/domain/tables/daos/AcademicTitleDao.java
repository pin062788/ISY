/*
 * This file is generated by jOOQ.
*/
package top.zbeboy.isy.domain.tables.daos;


import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import top.zbeboy.isy.domain.tables.AcademicTitle;
import top.zbeboy.isy.domain.tables.records.AcademicTitleRecord;


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
public class AcademicTitleDao extends DAOImpl<AcademicTitleRecord, top.zbeboy.isy.domain.tables.pojos.AcademicTitle, Integer> {

    /**
     * Create a new AcademicTitleDao without any configuration
     */
    public AcademicTitleDao() {
        super(AcademicTitle.ACADEMIC_TITLE, top.zbeboy.isy.domain.tables.pojos.AcademicTitle.class);
    }

    /**
     * Create a new AcademicTitleDao with an attached configuration
     */
    @Autowired
    public AcademicTitleDao(Configuration configuration) {
        super(AcademicTitle.ACADEMIC_TITLE, top.zbeboy.isy.domain.tables.pojos.AcademicTitle.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(top.zbeboy.isy.domain.tables.pojos.AcademicTitle object) {
        return object.getAcademicTitleId();
    }

    /**
     * Fetch records that have <code>academic_title_id IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.AcademicTitle> fetchByAcademicTitleId(Integer... values) {
        return fetch(AcademicTitle.ACADEMIC_TITLE.ACADEMIC_TITLE_ID, values);
    }

    /**
     * Fetch a unique record that has <code>academic_title_id = value</code>
     */
    public top.zbeboy.isy.domain.tables.pojos.AcademicTitle fetchOneByAcademicTitleId(Integer value) {
        return fetchOne(AcademicTitle.ACADEMIC_TITLE.ACADEMIC_TITLE_ID, value);
    }

    /**
     * Fetch records that have <code>academic_title_name IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.AcademicTitle> fetchByAcademicTitleName(String... values) {
        return fetch(AcademicTitle.ACADEMIC_TITLE.ACADEMIC_TITLE_NAME, values);
    }
}
