/**
 * This class is generated by jOOQ
 */
package top.zbeboy.isy.domain.tables.pojos;


import java.io.Serializable;
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
public class InternshipRelease implements Serializable {

	private static final long serialVersionUID = -541196252;

	private String    internshipReleaseId;
	private String    internshipTitle;
	private Timestamp releaseTime;
	private String    username;
	private String    allowGrade;
	private Timestamp teacherDistributionStartTime;
	private Timestamp teacherDistributionEndTime;
	private Timestamp startTime;
	private Timestamp endTime;
	private Byte      internshipReleaseIsDel;
	private Integer   departmentId;
	private Integer   internshipTypeId;

	public InternshipRelease() {}

	public InternshipRelease(InternshipRelease value) {
		this.internshipReleaseId = value.internshipReleaseId;
		this.internshipTitle = value.internshipTitle;
		this.releaseTime = value.releaseTime;
		this.username = value.username;
		this.allowGrade = value.allowGrade;
		this.teacherDistributionStartTime = value.teacherDistributionStartTime;
		this.teacherDistributionEndTime = value.teacherDistributionEndTime;
		this.startTime = value.startTime;
		this.endTime = value.endTime;
		this.internshipReleaseIsDel = value.internshipReleaseIsDel;
		this.departmentId = value.departmentId;
		this.internshipTypeId = value.internshipTypeId;
	}

	public InternshipRelease(
		String    internshipReleaseId,
		String    internshipTitle,
		Timestamp releaseTime,
		String    username,
		String    allowGrade,
		Timestamp teacherDistributionStartTime,
		Timestamp teacherDistributionEndTime,
		Timestamp startTime,
		Timestamp endTime,
		Byte      internshipReleaseIsDel,
		Integer   departmentId,
		Integer   internshipTypeId
	) {
		this.internshipReleaseId = internshipReleaseId;
		this.internshipTitle = internshipTitle;
		this.releaseTime = releaseTime;
		this.username = username;
		this.allowGrade = allowGrade;
		this.teacherDistributionStartTime = teacherDistributionStartTime;
		this.teacherDistributionEndTime = teacherDistributionEndTime;
		this.startTime = startTime;
		this.endTime = endTime;
		this.internshipReleaseIsDel = internshipReleaseIsDel;
		this.departmentId = departmentId;
		this.internshipTypeId = internshipTypeId;
	}

	@NotNull
	@Size(max = 100)
	public String getInternshipReleaseId() {
		return this.internshipReleaseId;
	}

	public void setInternshipReleaseId(String internshipReleaseId) {
		this.internshipReleaseId = internshipReleaseId;
	}

	@NotNull
	@Size(max = 100)
	public String getInternshipTitle() {
		return this.internshipTitle;
	}

	public void setInternshipTitle(String internshipTitle) {
		this.internshipTitle = internshipTitle;
	}

	public Timestamp getReleaseTime() {
		return this.releaseTime;
	}

	public void setReleaseTime(Timestamp releaseTime) {
		this.releaseTime = releaseTime;
	}

	@NotNull
	@Size(max = 200)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@NotNull
	@Size(max = 5)
	public String getAllowGrade() {
		return this.allowGrade;
	}

	public void setAllowGrade(String allowGrade) {
		this.allowGrade = allowGrade;
	}

	@NotNull
	public Timestamp getTeacherDistributionStartTime() {
		return this.teacherDistributionStartTime;
	}

	public void setTeacherDistributionStartTime(Timestamp teacherDistributionStartTime) {
		this.teacherDistributionStartTime = teacherDistributionStartTime;
	}

	@NotNull
	public Timestamp getTeacherDistributionEndTime() {
		return this.teacherDistributionEndTime;
	}

	public void setTeacherDistributionEndTime(Timestamp teacherDistributionEndTime) {
		this.teacherDistributionEndTime = teacherDistributionEndTime;
	}

	@NotNull
	public Timestamp getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	@NotNull
	public Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	@NotNull
	public Byte getInternshipReleaseIsDel() {
		return this.internshipReleaseIsDel;
	}

	public void setInternshipReleaseIsDel(Byte internshipReleaseIsDel) {
		this.internshipReleaseIsDel = internshipReleaseIsDel;
	}

	@NotNull
	public Integer getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	@NotNull
	public Integer getInternshipTypeId() {
		return this.internshipTypeId;
	}

	public void setInternshipTypeId(Integer internshipTypeId) {
		this.internshipTypeId = internshipTypeId;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("InternshipRelease (");

		sb.append(internshipReleaseId);
		sb.append(", ").append(internshipTitle);
		sb.append(", ").append(releaseTime);
		sb.append(", ").append(username);
		sb.append(", ").append(allowGrade);
		sb.append(", ").append(teacherDistributionStartTime);
		sb.append(", ").append(teacherDistributionEndTime);
		sb.append(", ").append(startTime);
		sb.append(", ").append(endTime);
		sb.append(", ").append(internshipReleaseIsDel);
		sb.append(", ").append(departmentId);
		sb.append(", ").append(internshipTypeId);

		sb.append(")");
		return sb.toString();
	}
}
