/**
 * This class is generated by jOOQ
 */
package top.zbeboy.isy.domain.tables.pojos;


import java.io.Serializable;

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
public class GraduationPracticeCollege implements Serializable {

	private static final long serialVersionUID = -2039631787;

	private String  graduationPracticeCollegeId;
	private Integer studentId;
	private String  internshipReleaseId;

	public GraduationPracticeCollege() {}

	public GraduationPracticeCollege(GraduationPracticeCollege value) {
		this.graduationPracticeCollegeId = value.graduationPracticeCollegeId;
		this.studentId = value.studentId;
		this.internshipReleaseId = value.internshipReleaseId;
	}

	public GraduationPracticeCollege(
		String  graduationPracticeCollegeId,
		Integer studentId,
		String  internshipReleaseId
	) {
		this.graduationPracticeCollegeId = graduationPracticeCollegeId;
		this.studentId = studentId;
		this.internshipReleaseId = internshipReleaseId;
	}

	@NotNull
	@Size(max = 100)
	public String getGraduationPracticeCollegeId() {
		return this.graduationPracticeCollegeId;
	}

	public void setGraduationPracticeCollegeId(String graduationPracticeCollegeId) {
		this.graduationPracticeCollegeId = graduationPracticeCollegeId;
	}

	@NotNull
	public Integer getStudentId() {
		return this.studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	@NotNull
	@Size(max = 100)
	public String getInternshipReleaseId() {
		return this.internshipReleaseId;
	}

	public void setInternshipReleaseId(String internshipReleaseId) {
		this.internshipReleaseId = internshipReleaseId;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("GraduationPracticeCollege (");

		sb.append(graduationPracticeCollegeId);
		sb.append(", ").append(studentId);
		sb.append(", ").append(internshipReleaseId);

		sb.append(")");
		return sb.toString();
	}
}