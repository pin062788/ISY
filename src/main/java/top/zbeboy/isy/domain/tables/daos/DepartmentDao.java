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

import top.zbeboy.isy.domain.tables.Department;
import top.zbeboy.isy.domain.tables.records.DepartmentRecord;


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
public class DepartmentDao extends DAOImpl<DepartmentRecord, top.zbeboy.isy.domain.tables.pojos.Department, Integer> {

    /**
     * Create a new DepartmentDao without any configuration
     */
    public DepartmentDao() {
        super(Department.DEPARTMENT, top.zbeboy.isy.domain.tables.pojos.Department.class);
    }

    /**
     * Create a new DepartmentDao with an attached configuration
     */
    @Autowired
    public DepartmentDao(Configuration configuration) {
        super(Department.DEPARTMENT, top.zbeboy.isy.domain.tables.pojos.Department.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(top.zbeboy.isy.domain.tables.pojos.Department object) {
        return object.getDepartmentId();
    }

    /**
     * Fetch records that have <code>department_id IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.Department> fetchByDepartmentId(Integer... values) {
        return fetch(Department.DEPARTMENT.DEPARTMENT_ID, values);
    }

    /**
     * Fetch a unique record that has <code>department_id = value</code>
     */
    public top.zbeboy.isy.domain.tables.pojos.Department fetchOneByDepartmentId(Integer value) {
        return fetchOne(Department.DEPARTMENT.DEPARTMENT_ID, value);
    }

    /**
     * Fetch records that have <code>department_name IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.Department> fetchByDepartmentName(String... values) {
        return fetch(Department.DEPARTMENT.DEPARTMENT_NAME, values);
    }

    /**
     * Fetch records that have <code>department_is_del IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.Department> fetchByDepartmentIsDel(Byte... values) {
        return fetch(Department.DEPARTMENT.DEPARTMENT_IS_DEL, values);
    }

    /**
     * Fetch records that have <code>college_id IN (values)</code>
     */
    public List<top.zbeboy.isy.domain.tables.pojos.Department> fetchByCollegeId(Integer... values) {
        return fetch(Department.DEPARTMENT.COLLEGE_ID, values);
    }
}
