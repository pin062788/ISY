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
public class ApplicationType implements Serializable {

	private static final long serialVersionUID = -1556702589;

	private Integer applicationTypeId;
	private String  applicationTypeName;

	public ApplicationType() {}

	public ApplicationType(ApplicationType value) {
		this.applicationTypeId = value.applicationTypeId;
		this.applicationTypeName = value.applicationTypeName;
	}

	public ApplicationType(
		Integer applicationTypeId,
		String  applicationTypeName
	) {
		this.applicationTypeId = applicationTypeId;
		this.applicationTypeName = applicationTypeName;
	}

	@NotNull
	public Integer getApplicationTypeId() {
		return this.applicationTypeId;
	}

	public void setApplicationTypeId(Integer applicationTypeId) {
		this.applicationTypeId = applicationTypeId;
	}

	@NotNull
	@Size(max = 32)
	public String getApplicationTypeName() {
		return this.applicationTypeName;
	}

	public void setApplicationTypeName(String applicationTypeName) {
		this.applicationTypeName = applicationTypeName;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("ApplicationType (");

		sb.append(applicationTypeId);
		sb.append(", ").append(applicationTypeName);

		sb.append(")");
		return sb.toString();
	}
}
