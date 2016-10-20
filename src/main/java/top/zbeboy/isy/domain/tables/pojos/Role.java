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
public class Role implements Serializable {

	private static final long serialVersionUID = 1676662713;

	private Integer roleId;
	private String  roleName;
	private String  roleEnName;

	public Role() {}

	public Role(Role value) {
		this.roleId = value.roleId;
		this.roleName = value.roleName;
		this.roleEnName = value.roleEnName;
	}

	public Role(
		Integer roleId,
		String  roleName,
		String  roleEnName
	) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleEnName = roleEnName;
	}

	@NotNull
	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@NotNull
	@Size(max = 50)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@NotNull
	@Size(max = 64)
	public String getRoleEnName() {
		return this.roleEnName;
	}

	public void setRoleEnName(String roleEnName) {
		this.roleEnName = roleEnName;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Role (");

		sb.append(roleId);
		sb.append(", ").append(roleName);
		sb.append(", ").append(roleEnName);

		sb.append(")");
		return sb.toString();
	}
}
