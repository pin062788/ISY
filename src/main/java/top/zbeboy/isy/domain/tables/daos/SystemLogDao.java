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

import top.zbeboy.isy.domain.tables.SystemLog;
import top.zbeboy.isy.domain.tables.records.SystemLogRecord;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Repository
public class SystemLogDao extends DAOImpl<SystemLogRecord, top.zbeboy.isy.domain.tables.pojos.SystemLog, String> {

    /**
     * Create a new SystemLogDao without any configuration
     */
    public SystemLogDao() {
        super(SystemLog.SYSTEM_LOG, top.zbeboy.isy.domain.tables.pojos.SystemLog.class);
    }

    /**
     * Create a new SystemLogDao with an attached configuration
     */
    @Autowired
    public SystemLogDao(Configuration configuration) {
        super(SystemLog.SYSTEM_LOG, top.zbeboy.isy.domain.tables.pojos.SystemLog.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getId(top.zbeboy.isy.domain.tables.pojos.SystemLog object) {
        return object.getSystemLogId();
    }

    /**
     * Fetch records that have <code>system_log_id IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.SystemLog> fetchBySystemLogId(String... values) {
        return fetch(SystemLog.SYSTEM_LOG.SYSTEM_LOG_ID, values);
    }

    /**
     * Fetch a unique record that has <code>system_log_id = value</code>
     */
    public top.zbeboy.isy.domain.tables.pojos.SystemLog fetchOneBySystemLogId(String value) {
        return fetchOne(SystemLog.SYSTEM_LOG.SYSTEM_LOG_ID, value);
    }

    /**
     * Fetch records that have <code>behavior IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.SystemLog> fetchByBehavior(String... values) {
        return fetch(SystemLog.SYSTEM_LOG.BEHAVIOR, values);
    }

    /**
     * Fetch records that have <code>operating_time IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.SystemLog> fetchByOperatingTime(Timestamp... values) {
        return fetch(SystemLog.SYSTEM_LOG.OPERATING_TIME, values);
    }

    /**
     * Fetch records that have <code>username IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.SystemLog> fetchByUsername(String... values) {
        return fetch(SystemLog.SYSTEM_LOG.USERNAME, values);
    }

    /**
     * Fetch records that have <code>ip_address IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.SystemLog> fetchByIpAddress(String... values) {
        return fetch(SystemLog.SYSTEM_LOG.IP_ADDRESS, values);
    }
}
