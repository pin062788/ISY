/**
 * This class is generated by jOOQ
 */
package top.zbeboy.isy.domain.tables.records;


import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;

import top.zbeboy.isy.domain.tables.Files;


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
public class FilesRecord extends UpdatableRecordImpl<FilesRecord> implements Record6<String, String, String, String, String, String> {

	private static final long serialVersionUID = -1540969812;

	/**
	 * Setter for <code>isy.files.file_id</code>.
	 */
	public void setFileId(String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>isy.files.file_id</code>.
	 */
	@NotNull
	@Size(max = 64)
	public String getFileId() {
		return (String) getValue(0);
	}

	/**
	 * Setter for <code>isy.files.size</code>.
	 */
	public void setSize(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>isy.files.size</code>.
	 */
	@Size(max = 16777215)
	public String getSize() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>isy.files.original_file_name</code>.
	 */
	public void setOriginalFileName(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>isy.files.original_file_name</code>.
	 */
	@Size(max = 300)
	public String getOriginalFileName() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>isy.files.new_name</code>.
	 */
	public void setNewName(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>isy.files.new_name</code>.
	 */
	@Size(max = 300)
	public String getNewName() {
		return (String) getValue(3);
	}

	/**
	 * Setter for <code>isy.files.relative_path</code>.
	 */
	public void setRelativePath(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>isy.files.relative_path</code>.
	 */
	@Size(max = 800)
	public String getRelativePath() {
		return (String) getValue(4);
	}

	/**
	 * Setter for <code>isy.files.ext</code>.
	 */
	public void setExt(String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>isy.files.ext</code>.
	 */
	@Size(max = 20)
	public String getExt() {
		return (String) getValue(5);
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
	// Record6 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row6<String, String, String, String, String, String> fieldsRow() {
		return (Row6) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row6<String, String, String, String, String, String> valuesRow() {
		return (Row6) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field1() {
		return Files.FILES.FILE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return Files.FILES.SIZE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return Files.FILES.ORIGINAL_FILE_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return Files.FILES.NEW_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field5() {
		return Files.FILES.RELATIVE_PATH;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field6() {
		return Files.FILES.EXT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value1() {
		return getFileId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getSize();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getOriginalFileName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getNewName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value5() {
		return getRelativePath();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value6() {
		return getExt();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FilesRecord value1(String value) {
		setFileId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FilesRecord value2(String value) {
		setSize(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FilesRecord value3(String value) {
		setOriginalFileName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FilesRecord value4(String value) {
		setNewName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FilesRecord value5(String value) {
		setRelativePath(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FilesRecord value6(String value) {
		setExt(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FilesRecord values(String value1, String value2, String value3, String value4, String value5, String value6) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		value6(value6);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached FilesRecord
	 */
	public FilesRecord() {
		super(Files.FILES);
	}

	/**
	 * Create a detached, initialised FilesRecord
	 */
	public FilesRecord(String fileId, String size, String originalFileName, String newName, String relativePath, String ext) {
		super(Files.FILES);

		setValue(0, fileId);
		setValue(1, size);
		setValue(2, originalFileName);
		setValue(3, newName);
		setValue(4, relativePath);
		setValue(5, ext);
	}
}
