/**
 * This class is generated by jOOQ
 */
package top.zbeboy.isy.domain.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;


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
public class CollegeApplication implements Serializable {

	private static final long serialVersionUID = -2050570289;

	private Integer applicationId;
	private Integer collegeId;

	public CollegeApplication() {}

	public CollegeApplication(CollegeApplication value) {
		this.applicationId = value.applicationId;
		this.collegeId = value.collegeId;
	}

	public CollegeApplication(
		Integer applicationId,
		Integer collegeId
	) {
		this.applicationId = applicationId;
		this.collegeId = collegeId;
	}

	@NotNull
	public Integer getApplicationId() {
		return this.applicationId;
	}

	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}

	@NotNull
	public Integer getCollegeId() {
		return this.collegeId;
	}

	public void setCollegeId(Integer collegeId) {
		this.collegeId = collegeId;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("CollegeApplication (");

		sb.append(applicationId);
		sb.append(", ").append(collegeId);

		sb.append(")");
		return sb.toString();
	}
}