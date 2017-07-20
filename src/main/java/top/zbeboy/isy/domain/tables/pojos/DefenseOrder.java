/*
 * This file is generated by jOOQ.
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
        "jOOQ version:3.9.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DefenseOrder implements Serializable {

    private static final long serialVersionUID = 703207412;

    private String  defenseOrderId;
    private String  studentNumber;
    private String  studentName;
    private String  subject;
    private Date    defenseDate;
    private String  defenseTime;
    private String  staffName;
    private Integer scoreTypeId;
    private Integer sortNum;
    private Integer defenseStatus;
    private String  defenseQuestion;
    private Integer studentId;
    private String  defenseGroupId;

    public DefenseOrder() {}

    public DefenseOrder(DefenseOrder value) {
        this.defenseOrderId = value.defenseOrderId;
        this.studentNumber = value.studentNumber;
        this.studentName = value.studentName;
        this.subject = value.subject;
        this.defenseDate = value.defenseDate;
        this.defenseTime = value.defenseTime;
        this.staffName = value.staffName;
        this.scoreTypeId = value.scoreTypeId;
        this.sortNum = value.sortNum;
        this.defenseStatus = value.defenseStatus;
        this.defenseQuestion = value.defenseQuestion;
        this.studentId = value.studentId;
        this.defenseGroupId = value.defenseGroupId;
    }

    public DefenseOrder(
        String  defenseOrderId,
        String  studentNumber,
        String  studentName,
        String  subject,
        Date    defenseDate,
        String  defenseTime,
        String  staffName,
        Integer scoreTypeId,
        Integer sortNum,
        Integer defenseStatus,
        String  defenseQuestion,
        Integer studentId,
        String  defenseGroupId
    ) {
        this.defenseOrderId = defenseOrderId;
        this.studentNumber = studentNumber;
        this.studentName = studentName;
        this.subject = subject;
        this.defenseDate = defenseDate;
        this.defenseTime = defenseTime;
        this.staffName = staffName;
        this.scoreTypeId = scoreTypeId;
        this.sortNum = sortNum;
        this.defenseStatus = defenseStatus;
        this.defenseQuestion = defenseQuestion;
        this.studentId = studentId;
        this.defenseGroupId = defenseGroupId;
    }

    @NotNull
    @Size(max = 64)
    public String getDefenseOrderId() {
        return this.defenseOrderId;
    }

    public void setDefenseOrderId(String defenseOrderId) {
        this.defenseOrderId = defenseOrderId;
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
    @Size(max = 30)
    public String getStudentName() {
        return this.studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Size(max = 100)
    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @NotNull
    public Date getDefenseDate() {
        return this.defenseDate;
    }

    public void setDefenseDate(Date defenseDate) {
        this.defenseDate = defenseDate;
    }

    @NotNull
    @Size(max = 20)
    public String getDefenseTime() {
        return this.defenseTime;
    }

    public void setDefenseTime(String defenseTime) {
        this.defenseTime = defenseTime;
    }

    @NotNull
    @Size(max = 30)
    public String getStaffName() {
        return this.staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Integer getScoreTypeId() {
        return this.scoreTypeId;
    }

    public void setScoreTypeId(Integer scoreTypeId) {
        this.scoreTypeId = scoreTypeId;
    }

    @NotNull
    public Integer getSortNum() {
        return this.sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Integer getDefenseStatus() {
        return this.defenseStatus;
    }

    public void setDefenseStatus(Integer defenseStatus) {
        this.defenseStatus = defenseStatus;
    }

    @Size(max = 65535)
    public String getDefenseQuestion() {
        return this.defenseQuestion;
    }

    public void setDefenseQuestion(String defenseQuestion) {
        this.defenseQuestion = defenseQuestion;
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
    public String getDefenseGroupId() {
        return this.defenseGroupId;
    }

    public void setDefenseGroupId(String defenseGroupId) {
        this.defenseGroupId = defenseGroupId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("DefenseOrder (");

        sb.append(defenseOrderId);
        sb.append(", ").append(studentNumber);
        sb.append(", ").append(studentName);
        sb.append(", ").append(subject);
        sb.append(", ").append(defenseDate);
        sb.append(", ").append(defenseTime);
        sb.append(", ").append(staffName);
        sb.append(", ").append(scoreTypeId);
        sb.append(", ").append(sortNum);
        sb.append(", ").append(defenseStatus);
        sb.append(", ").append(defenseQuestion);
        sb.append(", ").append(studentId);
        sb.append(", ").append(defenseGroupId);

        sb.append(")");
        return sb.toString();
    }
}
