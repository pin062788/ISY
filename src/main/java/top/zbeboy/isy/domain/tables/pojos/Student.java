/**
 * This class is generated by jOOQ
 */
package top.zbeboy.isy.domain.tables.pojos;


import java.io.Serializable;
import java.sql.Date;

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
public class Student implements Serializable {

	private static final long serialVersionUID = -2041276067;

	private Integer studentId;
	private String  studentNumber;
	private Date    birthday;
	private String  sex;
	private String  idCard;
	private String  familyResidence;
	private Integer politicalLandscapeId;
	private Integer nationId;
	private String  dormitoryNumber;
	private String  parentName;
	private String  parentContactPhone;
	private String  placeOrigin;
	private Integer organizeId;
	private String  username;

	public Student() {}

	public Student(Student value) {
		this.studentId = value.studentId;
		this.studentNumber = value.studentNumber;
		this.birthday = value.birthday;
		this.sex = value.sex;
		this.idCard = value.idCard;
		this.familyResidence = value.familyResidence;
		this.politicalLandscapeId = value.politicalLandscapeId;
		this.nationId = value.nationId;
		this.dormitoryNumber = value.dormitoryNumber;
		this.parentName = value.parentName;
		this.parentContactPhone = value.parentContactPhone;
		this.placeOrigin = value.placeOrigin;
		this.organizeId = value.organizeId;
		this.username = value.username;
	}

	public Student(
		Integer studentId,
		String  studentNumber,
		Date    birthday,
		String  sex,
		String  idCard,
		String  familyResidence,
		Integer politicalLandscapeId,
		Integer nationId,
		String  dormitoryNumber,
		String  parentName,
		String  parentContactPhone,
		String  placeOrigin,
		Integer organizeId,
		String  username
	) {
		this.studentId = studentId;
		this.studentNumber = studentNumber;
		this.birthday = birthday;
		this.sex = sex;
		this.idCard = idCard;
		this.familyResidence = familyResidence;
		this.politicalLandscapeId = politicalLandscapeId;
		this.nationId = nationId;
		this.dormitoryNumber = dormitoryNumber;
		this.parentName = parentName;
		this.parentContactPhone = parentContactPhone;
		this.placeOrigin = placeOrigin;
		this.organizeId = organizeId;
		this.username = username;
	}

	@NotNull
	public Integer getStudentId() {
		return this.studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	@NotNull
	@Size(max = 20)
	public String getStudentNumber() {
		return this.studentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Size(max = 2)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Size(max = 20)
	public String getIdCard() {
		return this.idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	@Size(max = 600)
	public String getFamilyResidence() {
		return this.familyResidence;
	}

	public void setFamilyResidence(String familyResidence) {
		this.familyResidence = familyResidence;
	}

	public Integer getPoliticalLandscapeId() {
		return this.politicalLandscapeId;
	}

	public void setPoliticalLandscapeId(Integer politicalLandscapeId) {
		this.politicalLandscapeId = politicalLandscapeId;
	}

	public Integer getNationId() {
		return this.nationId;
	}

	public void setNationId(Integer nationId) {
		this.nationId = nationId;
	}

	@Size(max = 15)
	public String getDormitoryNumber() {
		return this.dormitoryNumber;
	}

	public void setDormitoryNumber(String dormitoryNumber) {
		this.dormitoryNumber = dormitoryNumber;
	}

	@Size(max = 10)
	public String getParentName() {
		return this.parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	@Size(max = 15)
	public String getParentContactPhone() {
		return this.parentContactPhone;
	}

	public void setParentContactPhone(String parentContactPhone) {
		this.parentContactPhone = parentContactPhone;
	}

	@Size(max = 500)
	public String getPlaceOrigin() {
		return this.placeOrigin;
	}

	public void setPlaceOrigin(String placeOrigin) {
		this.placeOrigin = placeOrigin;
	}

	@NotNull
	public Integer getOrganizeId() {
		return this.organizeId;
	}

	public void setOrganizeId(Integer organizeId) {
		this.organizeId = organizeId;
	}

	@NotNull
	@Size(max = 200)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Student (");

		sb.append(studentId);
		sb.append(", ").append(studentNumber);
		sb.append(", ").append(birthday);
		sb.append(", ").append(sex);
		sb.append(", ").append(idCard);
		sb.append(", ").append(familyResidence);
		sb.append(", ").append(politicalLandscapeId);
		sb.append(", ").append(nationId);
		sb.append(", ").append(dormitoryNumber);
		sb.append(", ").append(parentName);
		sb.append(", ").append(parentContactPhone);
		sb.append(", ").append(placeOrigin);
		sb.append(", ").append(organizeId);
		sb.append(", ").append(username);

		sb.append(")");
		return sb.toString();
	}
}
