/*
 * This file is generated by jOOQ.
*/
package top.zbeboy.isy.domain.tables;


import java.sql.Date;
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
import top.zbeboy.isy.domain.tables.records.GraduationPracticeUnifyRecord;


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
public class GraduationPracticeUnify extends TableImpl<GraduationPracticeUnifyRecord> {

    private static final long serialVersionUID = 504765876;

    /**
     * The reference instance of <code>isy.graduation_practice_unify</code>
     */
    public static final GraduationPracticeUnify GRADUATION_PRACTICE_UNIFY = new GraduationPracticeUnify();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GraduationPracticeUnifyRecord> getRecordType() {
        return GraduationPracticeUnifyRecord.class;
    }

    /**
     * The column <code>isy.graduation_practice_unify.graduation_practice_unify_id</code>.
     */
    public final TableField<GraduationPracticeUnifyRecord, String> GRADUATION_PRACTICE_UNIFY_ID = createField("graduation_practice_unify_id", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "");

    /**
     * The column <code>isy.graduation_practice_unify.student_name</code>.
     */
    public final TableField<GraduationPracticeUnifyRecord, String> STUDENT_NAME = createField("student_name", org.jooq.impl.SQLDataType.VARCHAR.length(15).nullable(false), this, "");

    /**
     * The column <code>isy.graduation_practice_unify.college_class</code>.
     */
    public final TableField<GraduationPracticeUnifyRecord, String> COLLEGE_CLASS = createField("college_class", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false), this, "");

    /**
     * The column <code>isy.graduation_practice_unify.student_sex</code>.
     */
    public final TableField<GraduationPracticeUnifyRecord, String> STUDENT_SEX = createField("student_sex", org.jooq.impl.SQLDataType.VARCHAR.length(2).nullable(false), this, "");

    /**
     * The column <code>isy.graduation_practice_unify.student_number</code>.
     */
    public final TableField<GraduationPracticeUnifyRecord, String> STUDENT_NUMBER = createField("student_number", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "");

    /**
     * The column <code>isy.graduation_practice_unify.phone_number</code>.
     */
    public final TableField<GraduationPracticeUnifyRecord, String> PHONE_NUMBER = createField("phone_number", org.jooq.impl.SQLDataType.VARCHAR.length(15).nullable(false), this, "");

    /**
     * The column <code>isy.graduation_practice_unify.qq_mailbox</code>.
     */
    public final TableField<GraduationPracticeUnifyRecord, String> QQ_MAILBOX = createField("qq_mailbox", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "");

    /**
     * The column <code>isy.graduation_practice_unify.parental_contact</code>.
     */
    public final TableField<GraduationPracticeUnifyRecord, String> PARENTAL_CONTACT = createField("parental_contact", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "");

    /**
     * The column <code>isy.graduation_practice_unify.headmaster</code>.
     */
    public final TableField<GraduationPracticeUnifyRecord, String> HEADMASTER = createField("headmaster", org.jooq.impl.SQLDataType.VARCHAR.length(10).nullable(false), this, "");

    /**
     * The column <code>isy.graduation_practice_unify.headmaster_contact</code>.
     */
    public final TableField<GraduationPracticeUnifyRecord, String> HEADMASTER_CONTACT = createField("headmaster_contact", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "");

    /**
     * The column <code>isy.graduation_practice_unify.graduation_practice_unify_name</code>.
     */
    public final TableField<GraduationPracticeUnifyRecord, String> GRADUATION_PRACTICE_UNIFY_NAME = createField("graduation_practice_unify_name", org.jooq.impl.SQLDataType.VARCHAR.length(200).nullable(false), this, "");

    /**
     * The column <code>isy.graduation_practice_unify.graduation_practice_unify_address</code>.
     */
    public final TableField<GraduationPracticeUnifyRecord, String> GRADUATION_PRACTICE_UNIFY_ADDRESS = createField("graduation_practice_unify_address", org.jooq.impl.SQLDataType.VARCHAR.length(500).nullable(false), this, "");

    /**
     * The column <code>isy.graduation_practice_unify.graduation_practice_unify_contacts</code>.
     */
    public final TableField<GraduationPracticeUnifyRecord, String> GRADUATION_PRACTICE_UNIFY_CONTACTS = createField("graduation_practice_unify_contacts", org.jooq.impl.SQLDataType.VARCHAR.length(10).nullable(false), this, "");

    /**
     * The column <code>isy.graduation_practice_unify.graduation_practice_unify_tel</code>.
     */
    public final TableField<GraduationPracticeUnifyRecord, String> GRADUATION_PRACTICE_UNIFY_TEL = createField("graduation_practice_unify_tel", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "");

    /**
     * The column <code>isy.graduation_practice_unify.school_guidance_teacher</code>.
     */
    public final TableField<GraduationPracticeUnifyRecord, String> SCHOOL_GUIDANCE_TEACHER = createField("school_guidance_teacher", org.jooq.impl.SQLDataType.VARCHAR.length(10).nullable(false), this, "");

    /**
     * The column <code>isy.graduation_practice_unify.school_guidance_teacher_tel</code>.
     */
    public final TableField<GraduationPracticeUnifyRecord, String> SCHOOL_GUIDANCE_TEACHER_TEL = createField("school_guidance_teacher_tel", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "");

    /**
     * The column <code>isy.graduation_practice_unify.start_time</code>.
     */
    public final TableField<GraduationPracticeUnifyRecord, Date> START_TIME = createField("start_time", org.jooq.impl.SQLDataType.DATE.nullable(false), this, "");

    /**
     * The column <code>isy.graduation_practice_unify.end_time</code>.
     */
    public final TableField<GraduationPracticeUnifyRecord, Date> END_TIME = createField("end_time", org.jooq.impl.SQLDataType.DATE.nullable(false), this, "");

    /**
     * The column <code>isy.graduation_practice_unify.commitment_book</code>.
     */
    public final TableField<GraduationPracticeUnifyRecord, Byte> COMMITMENT_BOOK = createField("commitment_book", org.jooq.impl.SQLDataType.TINYINT, this, "");

    /**
     * The column <code>isy.graduation_practice_unify.safety_responsibility_book</code>.
     */
    public final TableField<GraduationPracticeUnifyRecord, Byte> SAFETY_RESPONSIBILITY_BOOK = createField("safety_responsibility_book", org.jooq.impl.SQLDataType.TINYINT, this, "");

    /**
     * The column <code>isy.graduation_practice_unify.practice_agreement</code>.
     */
    public final TableField<GraduationPracticeUnifyRecord, Byte> PRACTICE_AGREEMENT = createField("practice_agreement", org.jooq.impl.SQLDataType.TINYINT, this, "");

    /**
     * The column <code>isy.graduation_practice_unify.internship_application</code>.
     */
    public final TableField<GraduationPracticeUnifyRecord, Byte> INTERNSHIP_APPLICATION = createField("internship_application", org.jooq.impl.SQLDataType.TINYINT, this, "");

    /**
     * The column <code>isy.graduation_practice_unify.practice_receiving</code>.
     */
    public final TableField<GraduationPracticeUnifyRecord, Byte> PRACTICE_RECEIVING = createField("practice_receiving", org.jooq.impl.SQLDataType.TINYINT, this, "");

    /**
     * The column <code>isy.graduation_practice_unify.security_education_agreement</code>.
     */
    public final TableField<GraduationPracticeUnifyRecord, Byte> SECURITY_EDUCATION_AGREEMENT = createField("security_education_agreement", org.jooq.impl.SQLDataType.TINYINT, this, "");

    /**
     * The column <code>isy.graduation_practice_unify.parental_consent</code>.
     */
    public final TableField<GraduationPracticeUnifyRecord, Byte> PARENTAL_CONSENT = createField("parental_consent", org.jooq.impl.SQLDataType.TINYINT, this, "");

    /**
     * The column <code>isy.graduation_practice_unify.student_id</code>.
     */
    public final TableField<GraduationPracticeUnifyRecord, Integer> STUDENT_ID = createField("student_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>isy.graduation_practice_unify.internship_release_id</code>.
     */
    public final TableField<GraduationPracticeUnifyRecord, String> INTERNSHIP_RELEASE_ID = createField("internship_release_id", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "");

    /**
     * Create a <code>isy.graduation_practice_unify</code> table reference
     */
    public GraduationPracticeUnify() {
        this("graduation_practice_unify", null);
    }

    /**
     * Create an aliased <code>isy.graduation_practice_unify</code> table reference
     */
    public GraduationPracticeUnify(String alias) {
        this(alias, GRADUATION_PRACTICE_UNIFY);
    }

    private GraduationPracticeUnify(String alias, Table<GraduationPracticeUnifyRecord> aliased) {
        this(alias, aliased, null);
    }

    private GraduationPracticeUnify(String alias, Table<GraduationPracticeUnifyRecord> aliased, Field<?>[] parameters) {
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
    public UniqueKey<GraduationPracticeUnifyRecord> getPrimaryKey() {
        return Keys.KEY_GRADUATION_PRACTICE_UNIFY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<GraduationPracticeUnifyRecord>> getKeys() {
        return Arrays.<UniqueKey<GraduationPracticeUnifyRecord>>asList(Keys.KEY_GRADUATION_PRACTICE_UNIFY_PRIMARY, Keys.KEY_GRADUATION_PRACTICE_UNIFY_STUDENT_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<GraduationPracticeUnifyRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<GraduationPracticeUnifyRecord, ?>>asList(Keys.GRADUATION_PRACTICE_UNIFY_IBFK_1, Keys.GRADUATION_PRACTICE_UNIFY_IBFK_2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GraduationPracticeUnify as(String alias) {
        return new GraduationPracticeUnify(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public GraduationPracticeUnify rename(String name) {
        return new GraduationPracticeUnify(name, null);
    }
}
