/**
 * This class is generated by jOOQ
 */
package top.zbeboy.isy.domain.tables.records;


import java.sql.Timestamp;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record11;
import org.jooq.Row11;
import org.jooq.impl.UpdatableRecordImpl;

import top.zbeboy.isy.domain.tables.SchemaVersion;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.4"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SchemaVersionRecord extends UpdatableRecordImpl<SchemaVersionRecord> implements Record11<Integer, Integer, String, String, String, String, Integer, String, Timestamp, Integer, Byte> {

	private static final long serialVersionUID = 1292638475;

	/**
	 * Setter for <code>isy.schema_version.version_rank</code>.
	 */
	public void setVersionRank(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>isy.schema_version.version_rank</code>.
	 */
	@NotNull
	public Integer getVersionRank() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>isy.schema_version.installed_rank</code>.
	 */
	public void setInstalledRank(Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>isy.schema_version.installed_rank</code>.
	 */
	@NotNull
	public Integer getInstalledRank() {
		return (Integer) getValue(1);
	}

	/**
	 * Setter for <code>isy.schema_version.version</code>.
	 */
	public void setVersion(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>isy.schema_version.version</code>.
	 */
	@NotNull
	@Size(max = 50)
	public String getVersion() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>isy.schema_version.description</code>.
	 */
	public void setDescription(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>isy.schema_version.description</code>.
	 */
	@NotNull
	@Size(max = 200)
	public String getDescription() {
		return (String) getValue(3);
	}

	/**
	 * Setter for <code>isy.schema_version.type</code>.
	 */
	public void setType(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>isy.schema_version.type</code>.
	 */
	@NotNull
	@Size(max = 20)
	public String getType() {
		return (String) getValue(4);
	}

	/**
	 * Setter for <code>isy.schema_version.script</code>.
	 */
	public void setScript(String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>isy.schema_version.script</code>.
	 */
	@NotNull
	@Size(max = 1000)
	public String getScript() {
		return (String) getValue(5);
	}

	/**
	 * Setter for <code>isy.schema_version.checksum</code>.
	 */
	public void setChecksum(Integer value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>isy.schema_version.checksum</code>.
	 */
	public Integer getChecksum() {
		return (Integer) getValue(6);
	}

	/**
	 * Setter for <code>isy.schema_version.installed_by</code>.
	 */
	public void setInstalledBy(String value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>isy.schema_version.installed_by</code>.
	 */
	@NotNull
	@Size(max = 100)
	public String getInstalledBy() {
		return (String) getValue(7);
	}

	/**
	 * Setter for <code>isy.schema_version.installed_on</code>.
	 */
	public void setInstalledOn(Timestamp value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>isy.schema_version.installed_on</code>.
	 */
	public Timestamp getInstalledOn() {
		return (Timestamp) getValue(8);
	}

	/**
	 * Setter for <code>isy.schema_version.execution_time</code>.
	 */
	public void setExecutionTime(Integer value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>isy.schema_version.execution_time</code>.
	 */
	@NotNull
	public Integer getExecutionTime() {
		return (Integer) getValue(9);
	}

	/**
	 * Setter for <code>isy.schema_version.success</code>.
	 */
	public void setSuccess(Byte value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>isy.schema_version.success</code>.
	 */
	@NotNull
	public Byte getSuccess() {
		return (Byte) getValue(10);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<String> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record11 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row11<Integer, Integer, String, String, String, String, Integer, String, Timestamp, Integer, Byte> fieldsRow() {
		return (Row11) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row11<Integer, Integer, String, String, String, String, Integer, String, Timestamp, Integer, Byte> valuesRow() {
		return (Row11) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return SchemaVersion.SCHEMA_VERSION.VERSION_RANK;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field2() {
		return SchemaVersion.SCHEMA_VERSION.INSTALLED_RANK;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return SchemaVersion.SCHEMA_VERSION.VERSION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return SchemaVersion.SCHEMA_VERSION.DESCRIPTION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field5() {
		return SchemaVersion.SCHEMA_VERSION.TYPE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field6() {
		return SchemaVersion.SCHEMA_VERSION.SCRIPT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field7() {
		return SchemaVersion.SCHEMA_VERSION.CHECKSUM;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field8() {
		return SchemaVersion.SCHEMA_VERSION.INSTALLED_BY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field9() {
		return SchemaVersion.SCHEMA_VERSION.INSTALLED_ON;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field10() {
		return SchemaVersion.SCHEMA_VERSION.EXECUTION_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Byte> field11() {
		return SchemaVersion.SCHEMA_VERSION.SUCCESS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getVersionRank();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value2() {
		return getInstalledRank();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getVersion();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getDescription();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value5() {
		return getType();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value6() {
		return getScript();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value7() {
		return getChecksum();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value8() {
		return getInstalledBy();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value9() {
		return getInstalledOn();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value10() {
		return getExecutionTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Byte value11() {
		return getSuccess();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SchemaVersionRecord value1(Integer value) {
		setVersionRank(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SchemaVersionRecord value2(Integer value) {
		setInstalledRank(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SchemaVersionRecord value3(String value) {
		setVersion(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SchemaVersionRecord value4(String value) {
		setDescription(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SchemaVersionRecord value5(String value) {
		setType(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SchemaVersionRecord value6(String value) {
		setScript(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SchemaVersionRecord value7(Integer value) {
		setChecksum(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SchemaVersionRecord value8(String value) {
		setInstalledBy(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SchemaVersionRecord value9(Timestamp value) {
		setInstalledOn(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SchemaVersionRecord value10(Integer value) {
		setExecutionTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SchemaVersionRecord value11(Byte value) {
		setSuccess(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SchemaVersionRecord values(Integer value1, Integer value2, String value3, String value4, String value5, String value6, Integer value7, String value8, Timestamp value9, Integer value10, Byte value11) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		value6(value6);
		value7(value7);
		value8(value8);
		value9(value9);
		value10(value10);
		value11(value11);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached SchemaVersionRecord
	 */
	public SchemaVersionRecord() {
		super(SchemaVersion.SCHEMA_VERSION);
	}

	/**
	 * Create a detached, initialised SchemaVersionRecord
	 */
	public SchemaVersionRecord(Integer versionRank, Integer installedRank, String version, String description, String type, String script, Integer checksum, String installedBy, Timestamp installedOn, Integer executionTime, Byte success) {
		super(SchemaVersion.SCHEMA_VERSION);

		setValue(0, versionRank);
		setValue(1, installedRank);
		setValue(2, version);
		setValue(3, description);
		setValue(4, type);
		setValue(5, script);
		setValue(6, checksum);
		setValue(7, installedBy);
		setValue(8, installedOn);
		setValue(9, executionTime);
		setValue(10, success);
	}
}
