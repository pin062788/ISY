/*
 * This file is generated by jOOQ.
*/
package top.zbeboy.isy.domain.tables;


import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;

import top.zbeboy.isy.domain.Isy;
import top.zbeboy.isy.domain.Keys;
import top.zbeboy.isy.domain.tables.records.SystemMessageRecord;


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
public class SystemMessage extends TableImpl<SystemMessageRecord> {

    private static final long serialVersionUID = 494681866;

    /**
     * The reference instance of <code>isy.system_message</code>
     */
    public static final SystemMessage SYSTEM_MESSAGE = new SystemMessage();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SystemMessageRecord> getRecordType() {
        return SystemMessageRecord.class;
    }

    /**
     * The column <code>isy.system_message.system_message_id</code>.
     */
    public final TableField<SystemMessageRecord, String> SYSTEM_MESSAGE_ID = createField("system_message_id", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "");

    /**
     * The column <code>isy.system_message.message_title</code>.
     */
    public final TableField<SystemMessageRecord, String> MESSAGE_TITLE = createField("message_title", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false), this, "");

    /**
     * The column <code>isy.system_message.message_content</code>.
     */
    public final TableField<SystemMessageRecord, String> MESSAGE_CONTENT = createField("message_content", org.jooq.impl.SQLDataType.VARCHAR.length(800).nullable(false), this, "");

    /**
     * The column <code>isy.system_message.message_date</code>.
     */
    public final TableField<SystemMessageRecord, Timestamp> MESSAGE_DATE = createField("message_date", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>isy.system_message.send_users</code>.
     */
    public final TableField<SystemMessageRecord, String> SEND_USERS = createField("send_users", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "");

    /**
     * The column <code>isy.system_message.accept_users</code>.
     */
    public final TableField<SystemMessageRecord, String> ACCEPT_USERS = createField("accept_users", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "");

    /**
     * The column <code>isy.system_message.is_see</code>.
     */
    public final TableField<SystemMessageRecord, Byte> IS_SEE = createField("is_see", org.jooq.impl.SQLDataType.TINYINT, this, "");

    /**
     * Create a <code>isy.system_message</code> table reference
     */
    public SystemMessage() {
        this("system_message", null);
    }

    /**
     * Create an aliased <code>isy.system_message</code> table reference
     */
    public SystemMessage(String alias) {
        this(alias, SYSTEM_MESSAGE);
    }

    private SystemMessage(String alias, Table<SystemMessageRecord> aliased) {
        this(alias, aliased, null);
    }

    private SystemMessage(String alias, Table<SystemMessageRecord> aliased, Field<?>[] parameters) {
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
    public UniqueKey<SystemMessageRecord> getPrimaryKey() {
        return Keys.KEY_SYSTEM_MESSAGE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<SystemMessageRecord>> getKeys() {
        return Arrays.<UniqueKey<SystemMessageRecord>>asList(Keys.KEY_SYSTEM_MESSAGE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SystemMessage as(String alias) {
        return new SystemMessage(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public SystemMessage rename(String name) {
        return new SystemMessage(name, null);
    }
}
