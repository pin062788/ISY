/**
 * This class is generated by jOOQ
 */
package top.zbeboy.isy.domain.tables.records;


import java.sql.Date;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;

import top.zbeboy.isy.domain.tables.InternshipCompany;


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
public class InternshipCompanyRecord extends UpdatableRecordImpl<InternshipCompanyRecord> {

	private static final long serialVersionUID = -1172925936;

	/**
	 * Setter for <code>isy.internship_company.internship_company_id</code>.
	 */
	public void setInternshipCompanyId(String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>isy.internship_company.internship_company_id</code>.
	 */
	@NotNull
	@Size(max = 64)
	public String getInternshipCompanyId() {
		return (String) getValue(0);
	}

	/**
	 * Setter for <code>isy.internship_company.student_name</code>.
	 */
	public void setStudentName(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>isy.internship_company.student_name</code>.
	 */
	@NotNull
	@Size(max = 15)
	public String getStudentName() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>isy.internship_company.college_class</code>.
	 */
	public void setCollegeClass(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>isy.internship_company.college_class</code>.
	 */
	@NotNull
	@Size(max = 50)
	public String getCollegeClass() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>isy.internship_company.student_sex</code>.
	 */
	public void setStudentSex(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>isy.internship_company.student_sex</code>.
	 */
	@NotNull
	@Size(max = 2)
	public String getStudentSex() {
		return (String) getValue(3);
	}

	/**
	 * Setter for <code>isy.internship_company.student_number</code>.
	 */
	public void setStudentNumber(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>isy.internship_company.student_number</code>.
	 */
	@NotNull
	@Size(max = 20)
	public String getStudentNumber() {
		return (String) getValue(4);
	}

	/**
	 * Setter for <code>isy.internship_company.phone_number</code>.
	 */
	public void setPhoneNumber(String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>isy.internship_company.phone_number</code>.
	 */
	@NotNull
	@Size(max = 15)
	public String getPhoneNumber() {
		return (String) getValue(5);
	}

	/**
	 * Setter for <code>isy.internship_company.qq_mailbox</code>.
	 */
	public void setQqMailbox(String value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>isy.internship_company.qq_mailbox</code>.
	 */
	@NotNull
	@Size(max = 100)
	public String getQqMailbox() {
		return (String) getValue(6);
	}

	/**
	 * Setter for <code>isy.internship_company.parental_contact</code>.
	 */
	public void setParentalContact(String value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>isy.internship_company.parental_contact</code>.
	 */
	@NotNull
	@Size(max = 20)
	public String getParentalContact() {
		return (String) getValue(7);
	}

	/**
	 * Setter for <code>isy.internship_company.headmaster</code>.
	 */
	public void setHeadmaster(String value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>isy.internship_company.headmaster</code>.
	 */
	@NotNull
	@Size(max = 10)
	public String getHeadmaster() {
		return (String) getValue(8);
	}

	/**
	 * Setter for <code>isy.internship_company.headmaster_contact</code>.
	 */
	public void setHeadmasterContact(String value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>isy.internship_company.headmaster_contact</code>.
	 */
	@NotNull
	@Size(max = 20)
	public String getHeadmasterContact() {
		return (String) getValue(9);
	}

	/**
	 * Setter for <code>isy.internship_company.internship_company_name</code>.
	 */
	public void setInternshipCompanyName(String value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>isy.internship_company.internship_company_name</code>.
	 */
	@NotNull
	@Size(max = 200)
	public String getInternshipCompanyName() {
		return (String) getValue(10);
	}

	/**
	 * Setter for <code>isy.internship_company.internship_company_address</code>.
	 */
	public void setInternshipCompanyAddress(String value) {
		setValue(11, value);
	}

	/**
	 * Getter for <code>isy.internship_company.internship_company_address</code>.
	 */
	@NotNull
	@Size(max = 500)
	public String getInternshipCompanyAddress() {
		return (String) getValue(11);
	}

	/**
	 * Setter for <code>isy.internship_company.internship_company_contacts</code>.
	 */
	public void setInternshipCompanyContacts(String value) {
		setValue(12, value);
	}

	/**
	 * Getter for <code>isy.internship_company.internship_company_contacts</code>.
	 */
	@NotNull
	@Size(max = 10)
	public String getInternshipCompanyContacts() {
		return (String) getValue(12);
	}

	/**
	 * Setter for <code>isy.internship_company.internship_company_tel</code>.
	 */
	public void setInternshipCompanyTel(String value) {
		setValue(13, value);
	}

	/**
	 * Getter for <code>isy.internship_company.internship_company_tel</code>.
	 */
	@NotNull
	@Size(max = 20)
	public String getInternshipCompanyTel() {
		return (String) getValue(13);
	}

	/**
	 * Setter for <code>isy.internship_company.school_guidance_teacher</code>.
	 */
	public void setSchoolGuidanceTeacher(String value) {
		setValue(14, value);
	}

	/**
	 * Getter for <code>isy.internship_company.school_guidance_teacher</code>.
	 */
	@NotNull
	@Size(max = 10)
	public String getSchoolGuidanceTeacher() {
		return (String) getValue(14);
	}

	/**
	 * Setter for <code>isy.internship_company.school_guidance_teacher_tel</code>.
	 */
	public void setSchoolGuidanceTeacherTel(String value) {
		setValue(15, value);
	}

	/**
	 * Getter for <code>isy.internship_company.school_guidance_teacher_tel</code>.
	 */
	@NotNull
	@Size(max = 20)
	public String getSchoolGuidanceTeacherTel() {
		return (String) getValue(15);
	}

	/**
	 * Setter for <code>isy.internship_company.start_time</code>.
	 */
	public void setStartTime(Date value) {
		setValue(16, value);
	}

	/**
	 * Getter for <code>isy.internship_company.start_time</code>.
	 */
	@NotNull
	public Date getStartTime() {
		return (Date) getValue(16);
	}

	/**
	 * Setter for <code>isy.internship_company.end_time</code>.
	 */
	public void setEndTime(Date value) {
		setValue(17, value);
	}

	/**
	 * Getter for <code>isy.internship_company.end_time</code>.
	 */
	@NotNull
	public Date getEndTime() {
		return (Date) getValue(17);
	}

	/**
	 * Setter for <code>isy.internship_company.commitment_book</code>.
	 */
	public void setCommitmentBook(Byte value) {
		setValue(18, value);
	}

	/**
	 * Getter for <code>isy.internship_company.commitment_book</code>.
	 */
	public Byte getCommitmentBook() {
		return (Byte) getValue(18);
	}

	/**
	 * Setter for <code>isy.internship_company.safety_responsibility_book</code>.
	 */
	public void setSafetyResponsibilityBook(Byte value) {
		setValue(19, value);
	}

	/**
	 * Getter for <code>isy.internship_company.safety_responsibility_book</code>.
	 */
	public Byte getSafetyResponsibilityBook() {
		return (Byte) getValue(19);
	}

	/**
	 * Setter for <code>isy.internship_company.practice_agreement</code>.
	 */
	public void setPracticeAgreement(Byte value) {
		setValue(20, value);
	}

	/**
	 * Getter for <code>isy.internship_company.practice_agreement</code>.
	 */
	public Byte getPracticeAgreement() {
		return (Byte) getValue(20);
	}

	/**
	 * Setter for <code>isy.internship_company.internship_application</code>.
	 */
	public void setInternshipApplication(Byte value) {
		setValue(21, value);
	}

	/**
	 * Getter for <code>isy.internship_company.internship_application</code>.
	 */
	public Byte getInternshipApplication() {
		return (Byte) getValue(21);
	}

	/**
	 * Setter for <code>isy.internship_company.practice_receiving</code>.
	 */
	public void setPracticeReceiving(Byte value) {
		setValue(22, value);
	}

	/**
	 * Getter for <code>isy.internship_company.practice_receiving</code>.
	 */
	public Byte getPracticeReceiving() {
		return (Byte) getValue(22);
	}

	/**
	 * Setter for <code>isy.internship_company.security_education_agreement</code>.
	 */
	public void setSecurityEducationAgreement(Byte value) {
		setValue(23, value);
	}

	/**
	 * Getter for <code>isy.internship_company.security_education_agreement</code>.
	 */
	public Byte getSecurityEducationAgreement() {
		return (Byte) getValue(23);
	}

	/**
	 * Setter for <code>isy.internship_company.parental_consent</code>.
	 */
	public void setParentalConsent(Byte value) {
		setValue(24, value);
	}

	/**
	 * Getter for <code>isy.internship_company.parental_consent</code>.
	 */
	public Byte getParentalConsent() {
		return (Byte) getValue(24);
	}

	/**
	 * Setter for <code>isy.internship_company.student_id</code>.
	 */
	public void setStudentId(Integer value) {
		setValue(25, value);
	}

	/**
	 * Getter for <code>isy.internship_company.student_id</code>.
	 */
	@NotNull
	public Integer getStudentId() {
		return (Integer) getValue(25);
	}

	/**
	 * Setter for <code>isy.internship_company.internship_release_id</code>.
	 */
	public void setInternshipReleaseId(String value) {
		setValue(26, value);
	}

	/**
	 * Getter for <code>isy.internship_company.internship_release_id</code>.
	 */
	@NotNull
	@Size(max = 64)
	public String getInternshipReleaseId() {
		return (String) getValue(26);
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
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached InternshipCompanyRecord
	 */
	public InternshipCompanyRecord() {
		super(InternshipCompany.INTERNSHIP_COMPANY);
	}

	/**
	 * Create a detached, initialised InternshipCompanyRecord
	 */
	public InternshipCompanyRecord(String internshipCompanyId, String studentName, String collegeClass, String studentSex, String studentNumber, String phoneNumber, String qqMailbox, String parentalContact, String headmaster, String headmasterContact, String internshipCompanyName, String internshipCompanyAddress, String internshipCompanyContacts, String internshipCompanyTel, String schoolGuidanceTeacher, String schoolGuidanceTeacherTel, Date startTime, Date endTime, Byte commitmentBook, Byte safetyResponsibilityBook, Byte practiceAgreement, Byte internshipApplication, Byte practiceReceiving, Byte securityEducationAgreement, Byte parentalConsent, Integer studentId, String internshipReleaseId) {
		super(InternshipCompany.INTERNSHIP_COMPANY);

		setValue(0, internshipCompanyId);
		setValue(1, studentName);
		setValue(2, collegeClass);
		setValue(3, studentSex);
		setValue(4, studentNumber);
		setValue(5, phoneNumber);
		setValue(6, qqMailbox);
		setValue(7, parentalContact);
		setValue(8, headmaster);
		setValue(9, headmasterContact);
		setValue(10, internshipCompanyName);
		setValue(11, internshipCompanyAddress);
		setValue(12, internshipCompanyContacts);
		setValue(13, internshipCompanyTel);
		setValue(14, schoolGuidanceTeacher);
		setValue(15, schoolGuidanceTeacherTel);
		setValue(16, startTime);
		setValue(17, endTime);
		setValue(18, commitmentBook);
		setValue(19, safetyResponsibilityBook);
		setValue(20, practiceAgreement);
		setValue(21, internshipApplication);
		setValue(22, practiceReceiving);
		setValue(23, securityEducationAgreement);
		setValue(24, parentalConsent);
		setValue(25, studentId);
		setValue(26, internshipReleaseId);
	}
}
