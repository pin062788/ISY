/*
 * This file is generated by jOOQ.
*/
package top.zbeboy.isy.domain.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;

import top.zbeboy.isy.domain.Isy;
import top.zbeboy.isy.domain.Keys;
import top.zbeboy.isy.domain.tables.records.CollegeRoleRecord;


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
public class CollegeRole extends TableImpl<CollegeRoleRecord> {

    private static final long serialVersionUID = -1284454467;

    /**
     * The reference instance of <code>isy.college_role</code>
     */
    public static final CollegeRole COLLEGE_ROLE = new CollegeRole();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CollegeRoleRecord> getRecordType() {
        return CollegeRoleRecord.class;
    }

    /**
     * The column <code>isy.college_role.role_id</code>.
     */
    public final TableField<CollegeRoleRecord, String> ROLE_ID = createField("role_id", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "");

    /**
     * The column <code>isy.college_role.college_id</code>.
     */
    public final TableField<CollegeRoleRecord, Integer> COLLEGE_ID = createField("college_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>isy.college_role</code> table reference
     */
    public CollegeRole() {
        this("college_role", null);
    }

    /**
     * Create an aliased <code>isy.college_role</code> table reference
     */
    public CollegeRole(String alias) {
        this(alias, COLLEGE_ROLE);
    }

    private CollegeRole(String alias, Table<CollegeRoleRecord> aliased) {
        this(alias, aliased, null);
    }

    private CollegeRole(String alias, Table<CollegeRoleRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Isy.ISY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<CollegeRoleRecord> getPrimaryKey() {
        return Keys.KEY_COLLEGE_ROLE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<CollegeRoleRecord>> getKeys() {
        return Arrays.<UniqueKey<CollegeRoleRecord>>asList(Keys.KEY_COLLEGE_ROLE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<CollegeRoleRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<CollegeRoleRecord, ?>>asList(Keys.COLLEGE_ROLE_IBFK_1, Keys.COLLEGE_ROLE_IBFK_2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CollegeRole as(String alias) {
        return new CollegeRole(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public CollegeRole rename(String name) {
        return new CollegeRole(name, null);
    }
}
