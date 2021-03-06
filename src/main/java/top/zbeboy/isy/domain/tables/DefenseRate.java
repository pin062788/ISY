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
import top.zbeboy.isy.domain.tables.records.DefenseRateRecord;


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
public class DefenseRate extends TableImpl<DefenseRateRecord> {

    private static final long serialVersionUID = 1465153543;

    /**
     * The reference instance of <code>isy.defense_rate</code>
     */
    public static final DefenseRate DEFENSE_RATE = new DefenseRate();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DefenseRateRecord> getRecordType() {
        return DefenseRateRecord.class;
    }

    /**
     * The column <code>isy.defense_rate.defense_order_id</code>.
     */
    public final TableField<DefenseRateRecord, String> DEFENSE_ORDER_ID = createField("defense_order_id", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "");

    /**
     * The column <code>isy.defense_rate.graduation_design_teacher_id</code>.
     */
    public final TableField<DefenseRateRecord, String> GRADUATION_DESIGN_TEACHER_ID = createField("graduation_design_teacher_id", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "");

    /**
     * The column <code>isy.defense_rate.grade</code>.
     */
    public final TableField<DefenseRateRecord, Double> GRADE = createField("grade", org.jooq.impl.SQLDataType.DOUBLE.nullable(false), this, "");

    /**
     * Create a <code>isy.defense_rate</code> table reference
     */
    public DefenseRate() {
        this("defense_rate", null);
    }

    /**
     * Create an aliased <code>isy.defense_rate</code> table reference
     */
    public DefenseRate(String alias) {
        this(alias, DEFENSE_RATE);
    }

    private DefenseRate(String alias, Table<DefenseRateRecord> aliased) {
        this(alias, aliased, null);
    }

    private DefenseRate(String alias, Table<DefenseRateRecord> aliased, Field<?>[] parameters) {
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
    public List<UniqueKey<DefenseRateRecord>> getKeys() {
        return Arrays.<UniqueKey<DefenseRateRecord>>asList(Keys.KEY_DEFENSE_RATE_DEFENSE_ORDER_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<DefenseRateRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<DefenseRateRecord, ?>>asList(Keys.DEFENSE_RATE_IBFK_1, Keys.DEFENSE_RATE_IBFK_2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DefenseRate as(String alias) {
        return new DefenseRate(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public DefenseRate rename(String name) {
        return new DefenseRate(name, null);
    }
}
