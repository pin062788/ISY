/*
 * This file is generated by jOOQ.
*/
package top.zbeboy.isy.domain.tables.daos;


import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import top.zbeboy.isy.domain.tables.DefenseGroup;
import top.zbeboy.isy.domain.tables.records.DefenseGroupRecord;


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
public class DefenseGroupDao extends DAOImpl<DefenseGroupRecord, top.zbeboy.isy.domain.tables.pojos.DefenseGroup, String> {

    /**
     * Create a new DefenseGroupDao without any configuration
     */
    public DefenseGroupDao() {
        super(DefenseGroup.DEFENSE_GROUP, top.zbeboy.isy.domain.tables.pojos.DefenseGroup.class);
    }

    /**
     * Create a new DefenseGroupDao with an attached configuration
     */
    @Autowired
    public DefenseGroupDao(Configuration configuration) {
        super(DefenseGroup.DEFENSE_GROUP, top.zbeboy.isy.domain.tables.pojos.DefenseGroup.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getId(top.zbeboy.isy.domain.tables.pojos.DefenseGroup object) {
        return object.getDefenseGroupId();
    }

    /**
     * Fetch records that have <code>defense_group_id IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.DefenseGroup> fetchByDefenseGroupId(String... values) {
        return fetch(DefenseGroup.DEFENSE_GROUP.DEFENSE_GROUP_ID, values);
    }

    /**
     * Fetch a unique record that has <code>defense_group_id = value</code>
     */
    public top.zbeboy.isy.domain.tables.pojos.DefenseGroup fetchOneByDefenseGroupId(String value) {
        return fetchOne(DefenseGroup.DEFENSE_GROUP.DEFENSE_GROUP_ID, value);
    }

    /**
     * Fetch records that have <code>defense_group_name IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.DefenseGroup> fetchByDefenseGroupName(String... values) {
        return fetch(DefenseGroup.DEFENSE_GROUP.DEFENSE_GROUP_NAME, values);
    }

    /**
     * Fetch records that have <code>schoolroom_id IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.DefenseGroup> fetchBySchoolroomId(Integer... values) {
        return fetch(DefenseGroup.DEFENSE_GROUP.SCHOOLROOM_ID, values);
    }

    /**
     * Fetch records that have <code>note IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.DefenseGroup> fetchByNote(String... values) {
        return fetch(DefenseGroup.DEFENSE_GROUP.NOTE, values);
    }

    /**
     * Fetch records that have <code>leader_id IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.DefenseGroup> fetchByLeaderId(String... values) {
        return fetch(DefenseGroup.DEFENSE_GROUP.LEADER_ID, values);
    }

    /**
     * Fetch records that have <code>secretary_id IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.DefenseGroup> fetchBySecretaryId(String... values) {
        return fetch(DefenseGroup.DEFENSE_GROUP.SECRETARY_ID, values);
    }

    /**
     * Fetch records that have <code>defense_arrangement_id IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.DefenseGroup> fetchByDefenseArrangementId(String... values) {
        return fetch(DefenseGroup.DEFENSE_GROUP.DEFENSE_ARRANGEMENT_ID, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.DefenseGroup> fetchByCreateTime(Timestamp... values) {
        return fetch(DefenseGroup.DEFENSE_GROUP.CREATE_TIME, values);
    }
}
