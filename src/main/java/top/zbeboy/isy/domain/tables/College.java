/*
 * This file is generated by jOOQ.
*/
package top.zbeboy.isy.domain.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;

import top.zbeboy.isy.domain.Isy;
import top.zbeboy.isy.domain.Keys;
import top.zbeboy.isy.domain.tables.records.CollegeRecord;


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
public class College extends TableImpl<CollegeRecord> {

    private static final long serialVersionUID = 1215294001;

    /**
     * The reference instance of <code>isy.college</code>
     */
    public static final College COLLEGE = new College();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CollegeRecord> getRecordType() {
        return CollegeRecord.class;
    }

    /**
     * The column <code>isy.college.college_id</code>.
     */
    public final TableField<CollegeRecord, Integer> COLLEGE_ID = createField("college_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>isy.college.college_name</code>.
     */
    public final TableField<CollegeRecord, String> COLLEGE_NAME = createField("college_name", org.jooq.impl.SQLDataType.VARCHAR.length(200).nullable(false), this, "");

    /**
     * The column <code>isy.college.college_address</code>.
     */
    public final TableField<CollegeRecord, String> COLLEGE_ADDRESS = createField("college_address", org.jooq.impl.SQLDataType.VARCHAR.length(500).nullable(false), this, "");

    /**
     * The column <code>isy.college.college_code</code>.
     */
    public final TableField<CollegeRecord, String> COLLEGE_CODE = createField("college_code", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "");

    /**
     * The column <code>isy.college.college_is_del</code>.
     */
    public final TableField<CollegeRecord, Byte> COLLEGE_IS_DEL = createField("college_is_del", org.jooq.impl.SQLDataType.TINYINT, this, "");

    /**
     * The column <code>isy.college.school_id</code>.
     */
    public final TableField<CollegeRecord, Integer> SCHOOL_ID = createField("school_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>isy.college</code> table reference
     */
    public College() {
        this("college", null);
    }

    /**
     * Create an aliased <code>isy.college</code> table reference
     */
    public College(String alias) {
        this(alias, COLLEGE);
    }

    private College(String alias, Table<CollegeRecord> aliased) {
        this(alias, aliased, null);
    }

    private College(String alias, Table<CollegeRecord> aliased, Field<?>[] parameters) {
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
    public Identity<CollegeRecord, Integer> getIdentity() {
        return Keys.IDENTITY_COLLEGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<CollegeRecord> getPrimaryKey() {
        return Keys.KEY_COLLEGE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<CollegeRecord>> getKeys() {
        return Arrays.<UniqueKey<CollegeRecord>>asList(Keys.KEY_COLLEGE_PRIMARY, Keys.KEY_COLLEGE_COLLEGE_CODE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<CollegeRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<CollegeRecord, ?>>asList(Keys.COLLEGE_IBFK_1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public College as(String alias) {
        return new College(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public College rename(String name) {
        return new College(name, null);
    }
}
