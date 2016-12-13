/**
 * This class is generated by jOOQ
 */
package top.zbeboy.isy.domain.tables.pojos;


import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


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
public class InternshipJournal implements Serializable {

	private static final long serialVersionUID = 1636183176;

	private String    internshipJournalId;
	private String    studentName;
	private String    studentNumber;
	private String    organize;
	private String    schoolGuidanceTeacher;
	private String    graduationPracticeCompanyName;
	private String    internshipJournalContent;
	private Date      internshipJournalDate;
	private Timestamp createDate;
	private Integer   studentId;
	private String    internshipReleaseId;
	private String    internshipJournalWord;

	public InternshipJournal() {}

	public InternshipJournal(InternshipJournal value) {
		this.internshipJournalId = value.internshipJournalId;
		this.studentName = value.studentName;
		this.studentNumber = value.studentNumber;
		this.organize = value.organize;
		this.schoolGuidanceTeacher = value.schoolGuidanceTeacher;
		this.graduationPracticeCompanyName = value.graduationPracticeCompanyName;
		this.internshipJournalContent = value.internshipJournalContent;
		this.internshipJournalDate = value.internshipJournalDate;
		this.createDate = value.createDate;
		this.studentId = value.studentId;
		this.internshipReleaseId = value.internshipReleaseId;
		this.internshipJournalWord = value.internshipJournalWord;
	}

	public InternshipJournal(
		String    internshipJournalId,
		String    studentName,
		String    studentNumber,
		String    organize,
		String    schoolGuidanceTeacher,
		String    graduationPracticeCompanyName,
		String    internshipJournalContent,
		Date      internshipJournalDate,
		Timestamp createDate,
		Integer   studentId,
		String    internshipReleaseId,
		String    internshipJournalWord
	) {
		this.internshipJournalId = internshipJournalId;
		this.studentName = studentName;
		this.studentNumber = studentNumber;
		this.organize = organize;
		this.schoolGuidanceTeacher = schoolGuidanceTeacher;
		this.graduationPracticeCompanyName = graduationPracticeCompanyName;
		this.internshipJournalContent = internshipJournalContent;
		this.internshipJournalDate = internshipJournalDate;
		this.createDate = createDate;
		this.studentId = studentId;
		this.internshipReleaseId = internshipReleaseId;
		this.internshipJournalWord = internshipJournalWord;
	}

	@NotNull
	@Size(max = 64)
	public String getInternshipJournalId() {
		return this.internshipJournalId;
	}

	public void setInternshipJournalId(String internshipJournalId) {
		this.internshipJournalId = internshipJournalId;
	}

	@NotNull
	@Size(max = 10)
	public String getStudentName() {
		return this.studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	@NotNull
	@Size(max = 20)
	public String getStudentNumber() {
		return this.studentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	@NotNull
	@Size(max = 10)
	public String getOrganize() {
		return this.organize;
	}

	public void setOrganize(String organize) {
		this.organize = organize;
	}

	@NotNull
	@Size(max = 10)
	public String getSchoolGuidanceTeacher() {
		return this.schoolGuidanceTeacher;
	}

	public void setSchoolGuidanceTeacher(String schoolGuidanceTeacher) {
		this.schoolGuidanceTeacher = schoolGuidanceTeacher;
	}

	@NotNull
	@Size(max = 200)
	public String getGraduationPracticeCompanyName() {
		return this.graduationPracticeCompanyName;
	}

	public void setGraduationPracticeCompanyName(String graduationPracticeCompanyName) {
		this.graduationPracticeCompanyName = graduationPracticeCompanyName;
	}

	@NotNull
	@Size(max = 65535)
	public String getInternshipJournalContent() {
		return this.internshipJournalContent;
	}

	public void setInternshipJournalContent(String internshipJournalContent) {
		this.internshipJournalContent = internshipJournalContent;
	}

	@NotNull
	public Date getInternshipJournalDate() {
		return this.internshipJournalDate;
	}

	public void setInternshipJournalDate(Date internshipJournalDate) {
		this.internshipJournalDate = internshipJournalDate;
	}

	@NotNull
	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	@NotNull
	public Integer getStudentId() {
		return this.studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	@NotNull
	@Size(max = 64)
	public String getInternshipReleaseId() {
		return this.internshipReleaseId;
	}

	public void setInternshipReleaseId(String internshipReleaseId) {
		this.internshipReleaseId = internshipReleaseId;
	}

	@NotNull
	@Size(max = 500)
	public String getInternshipJournalWord() {
		return this.internshipJournalWord;
	}

	public void setInternshipJournalWord(String internshipJournalWord) {
		this.internshipJournalWord = internshipJournalWord;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("InternshipJournal (");

		sb.append(internshipJournalId);
		sb.append(", ").append(studentName);
		sb.append(", ").append(studentNumber);
		sb.append(", ").append(organize);
		sb.append(", ").append(schoolGuidanceTeacher);
		sb.append(", ").append(graduationPracticeCompanyName);
		sb.append(", ").append(internshipJournalContent);
		sb.append(", ").append(internshipJournalDate);
		sb.append(", ").append(createDate);
		sb.append(", ").append(studentId);
		sb.append(", ").append(internshipReleaseId);
		sb.append(", ").append(internshipJournalWord);

		sb.append(")");
		return sb.toString();
	}
}
