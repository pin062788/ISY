package top.zbeboy.isy.service;

import com.alibaba.fastjson.JSONObject;
import org.jooq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import top.zbeboy.isy.domain.tables.daos.SystemLogDao;
import top.zbeboy.isy.domain.tables.pojos.SystemLog;
import top.zbeboy.isy.service.plugin.DataTablesPlugin;
import top.zbeboy.isy.service.util.SQLQueryUtils;
import top.zbeboy.isy.web.bean.system.log.SystemLogBean;
import top.zbeboy.isy.web.util.DataTablesUtils;

import java.sql.Timestamp;

import static top.zbeboy.isy.domain.Tables.SYSTEM_LOG;

/**
 * Created by lenovo on 2016-09-11.
 */
@Service("systemLogService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SystemLogServiceImpl  extends DataTablesPlugin<SystemLogBean> implements SystemLogService {

    private final Logger log = LoggerFactory.getLogger(SystemLogServiceImpl.class);

    private final DSLContext create;

    private SystemLogDao systemLogDao;

    @Autowired
    public SystemLogServiceImpl(DSLContext dslContext, Configuration configuration) {
        this.create = dslContext;
        this.systemLogDao = new SystemLogDao(configuration);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(SystemLog systemLog) {
        systemLogDao.insert(systemLog);
    }

    @Override
    public Result<Record> findAllByPage(DataTablesUtils<SystemLogBean> dataTablesUtils) {
        return dataPagingQueryAll(dataTablesUtils,create,SYSTEM_LOG);
    }

    @Override
    public int countAll() {
        return statisticsAll(create,SYSTEM_LOG);
    }

    @Override
    public int countByCondition(DataTablesUtils<SystemLogBean> dataTablesUtils) {
        return statisticsWithCondition(dataTablesUtils,create,SYSTEM_LOG);
    }

    /**
     * 系统日志全局搜索条件
     *
     * @param dataTablesUtils
     * @return 搜索条件
     */
    @Override
    public Condition searchCondition(DataTablesUtils<SystemLogBean> dataTablesUtils) {
        Condition a = null;

        JSONObject search = dataTablesUtils.getSearch();
        if (!ObjectUtils.isEmpty(search)) {
            String username = StringUtils.trimWhitespace(search.getString("username"));
            String behavior = StringUtils.trimWhitespace(search.getString("behavior"));
            String ipAddress = StringUtils.trimWhitespace(search.getString("ipAddress"));
            if (StringUtils.hasLength(username)) {
                a = SYSTEM_LOG.USERNAME.like(SQLQueryUtils.likeAllParam(username));
            }

            if (StringUtils.hasLength(behavior)) {
                if (ObjectUtils.isEmpty(a)) {
                    a = SYSTEM_LOG.BEHAVIOR.like(SQLQueryUtils.likeAllParam(behavior));
                } else {
                    a = a.and(SYSTEM_LOG.BEHAVIOR.like(SQLQueryUtils.likeAllParam(behavior)));
                }
            }

            if (StringUtils.hasLength(ipAddress)) {
                if (ObjectUtils.isEmpty(a)) {
                    a = SYSTEM_LOG.IP_ADDRESS.like(SQLQueryUtils.likeAllParam(ipAddress));
                } else {
                    a = a.and(SYSTEM_LOG.IP_ADDRESS.like(SQLQueryUtils.likeAllParam(ipAddress)));
                }
            }
        }
        return a;
    }

    /**
     * 系统日志排序
     *
     * @param dataTablesUtils
     * @param selectConditionStep
     */
    @Override
    public void sortCondition(DataTablesUtils<SystemLogBean> dataTablesUtils, SelectConditionStep<Record> selectConditionStep, SelectJoinStep<Record> selectJoinStep, int type) {
        String orderColumnName = dataTablesUtils.getOrderColumnName();
        String orderDir = dataTablesUtils.getOrderDir();
        boolean isAsc = orderDir.equalsIgnoreCase("asc");
        SortField<String> a = null;
        SortField<Timestamp> b = null;
        if (StringUtils.hasLength(orderColumnName)) {
            if (orderColumnName.equalsIgnoreCase("system_log_id")) {
                if (isAsc) {
                    a = SYSTEM_LOG.SYSTEM_LOG_ID.asc();
                } else {
                    a = SYSTEM_LOG.SYSTEM_LOG_ID.desc();
                }
            }

            if (orderColumnName.equalsIgnoreCase("username")) {
                if (isAsc) {
                    a = SYSTEM_LOG.USERNAME.asc();
                } else {
                    a = SYSTEM_LOG.USERNAME.desc();
                }
            }

            if (orderColumnName.equalsIgnoreCase("behavior")) {
                if (isAsc) {
                    a = SYSTEM_LOG.BEHAVIOR.asc();
                } else {
                    a = SYSTEM_LOG.BEHAVIOR.desc();
                }
            }

            if (orderColumnName.equalsIgnoreCase("operating_time")) {
                if (isAsc) {
                    b = SYSTEM_LOG.OPERATING_TIME.asc();
                } else {
                    b = SYSTEM_LOG.OPERATING_TIME.desc();
                }
            }

            if (orderColumnName.equalsIgnoreCase("ip_address")) {
                if (isAsc) {
                    a = SYSTEM_LOG.IP_ADDRESS.asc();
                } else {
                    a = SYSTEM_LOG.IP_ADDRESS.desc();
                }
            }

        }

        if (!ObjectUtils.isEmpty(a)) {
            if (type == CONDITION_TYPE) {
                selectConditionStep.orderBy(a);
            }

            if (type == JOIN_TYPE) {
                selectJoinStep.orderBy(a);
            }

        } else if(!ObjectUtils.isEmpty(b)){
            if (type == CONDITION_TYPE) {
                selectConditionStep.orderBy(b);
            }

            if (type == JOIN_TYPE) {
                selectJoinStep.orderBy(b);
            }
        } else {
            if (type == CONDITION_TYPE) {
                selectConditionStep.orderBy(SYSTEM_LOG.OPERATING_TIME.desc());
            }

            if (type == JOIN_TYPE) {
                selectJoinStep.orderBy(SYSTEM_LOG.OPERATING_TIME.desc());
            }
        }
    }
}