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

import top.zbeboy.isy.domain.tables.Organize;
import top.zbeboy.isy.domain.tables.records.OrganizeRecord;


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
public class OrganizeDao extends DAOImpl<OrganizeRecord, top.zbeboy.isy.domain.tables.pojos.Organize, Integer> {

    /**
     * Create a new OrganizeDao without any configuration
     */
    public OrganizeDao() {
        super(Organize.ORGANIZE, top.zbeboy.isy.domain.tables.pojos.Organize.class);
    }

    /**
     * Create a new OrganizeDao with an attached configuration
     */
    @Autowired
    public OrganizeDao(Configuration configuration) {
        super(Organize.ORGANIZE, top.zbeboy.isy.domain.tables.pojos.Organize.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(top.zbeboy.isy.domain.tables.pojos.Organize object) {
        return object.getOrganizeId();
    }

    /**
     * Fetch records that have <code>organize_id IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.Organize> fetchByOrganizeId(Integer... values) {
        return fetch(Organize.ORGANIZE.ORGANIZE_ID, values);
    }

    /**
     * Fetch a unique record that has <code>organize_id = value</code>
     */
    public top.zbeboy.isy.domain.tables.pojos.Organize fetchOneByOrganizeId(Integer value) {
        return fetchOne(Organize.ORGANIZE.ORGANIZE_ID, value);
    }

    /**
     * Fetch records that have <code>organize_name IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.Organize> fetchByOrganizeName(String... values) {
        return fetch(Organize.ORGANIZE.ORGANIZE_NAME, values);
    }

    /**
     * Fetch records that have <code>organize_is_del IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.Organize> fetchByOrganizeIsDel(Byte... values) {
        return fetch(Organize.ORGANIZE.ORGANIZE_IS_DEL, values);
    }

    /**
     * Fetch records that have <code>science_id IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.Organize> fetchByScienceId(Integer... values) {
        return fetch(Organize.ORGANIZE.SCIENCE_ID, values);
    }

    /**
     * Fetch records that have <code>grade IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.Organize> fetchByGrade(String... values) {
        return fetch(Organize.ORGANIZE.GRADE, values);
    }
}
