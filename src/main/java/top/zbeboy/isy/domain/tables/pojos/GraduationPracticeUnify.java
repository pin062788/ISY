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
        "jOOQ version:3.8.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GraduationPracticeUnify implements Serializable {

    private static final long serialVersionUID = 1466251853;

    private String  graduationPracticeUnifyId;
    private String  studentName;
    private String  collegeClass;
    private String  studentSex;
    private String  studentNumber;
    private String  phoneNumber;
    private String  qqMailbox;
    private String  parentalContact;
    private String  headmaster;
    private String  headmasterContact;
    private String  graduationPracticeUnifyName;
    private String  graduationPracticeUnifyAddress;
    private String  graduationPracticeUnifyContacts;
    private String  graduationPracticeUnifyTel;
    private String  schoolGuidanceTeacher;
    private String  schoolGuidanceTeacherTel;
    private Date    startTime;
    private Date    endTime;
    private Byte    commitmentBook;
    private Byte    safetyResponsibilityBook;
    private Byte    practiceAgreement;
    private Byte    internshipApplication;
    private Byte    practiceReceiving;
    private Byte    securityEducationAgreement;
    private Byte    parentalConsent;
    private Integer studentId;
    private String  internshipReleaseId;

    public GraduationPracticeUnify() {}

    public GraduationPracticeUnify(GraduationPracticeUnify value) {
        this.graduationPracticeUnifyId = value.graduationPracticeUnifyId;
        this.studentName = value.studentName;
        this.collegeClass = value.collegeClass;
        this.studentSex = value.studentSex;
        this.studentNumber = value.studentNumber;
        this.phoneNumber = value.phoneNumber;
        this.qqMailbox = value.qqMailbox;
        this.parentalContact = value.parentalContact;
        this.headmaster = value.headmaster;
        this.headmasterContact = value.headmasterContact;
        this.graduationPracticeUnifyName = value.graduationPracticeUnifyName;
        this.graduationPracticeUnifyAddress = value.graduationPracticeUnifyAddress;
        this.graduationPracticeUnifyContacts = value.graduationPracticeUnifyContacts;
        this.graduationPracticeUnifyTel = value.graduationPracticeUnifyTel;
        this.schoolGuidanceTeacher = value.schoolGuidanceTeacher;
        this.schoolGuidanceTeacherTel = value.schoolGuidanceTeacherTel;
        this.startTime = value.startTime;
        this.endTime = value.endTime;
        this.commitmentBook = value.commitmentBook;
        this.safetyResponsibilityBook = value.safetyResponsibilityBook;
        this.practiceAgreement = value.practiceAgreement;
        this.internshipApplication = value.internshipApplication;
        this.practiceReceiving = value.practiceReceiving;
        this.securityEducationAgreement = value.securityEducationAgreement;
        this.parentalConsent = value.parentalConsent;
        this.studentId = value.studentId;
        this.internshipReleaseId = value.internshipReleaseId;
    }

    public GraduationPracticeUnify(
        String  graduationPracticeUnifyId,
        String  studentName,
        String  collegeClass,
        String  studentSex,
        String  studentNumber,
        String  phoneNumber,
        String  qqMailbox,
        String  parentalContact,
        String  headmaster,
        String  headmasterContact,
        String  graduationPracticeUnifyName,
        String  graduationPracticeUnifyAddress,
        String  graduationPracticeUnifyContacts,
        String  graduationPracticeUnifyTel,
        String  schoolGuidanceTeacher,
        String  schoolGuidanceTeacherTel,
        Date    startTime,
        Date    endTime,
        Byte    commitmentBook,
        Byte    safetyResponsibilityBook,
        Byte    practiceAgreement,
        Byte    internshipApplication,
        Byte    practiceReceiving,
        Byte    securityEducationAgreement,
        Byte    parentalConsent,
        Integer studentId,
        String  internshipReleaseId
    ) {
        this.graduationPracticeUnifyId = graduationPracticeUnifyId;
        this.studentName = studentName;
        this.collegeClass = collegeClass;
        this.studentSex = studentSex;
        this.studentNumber = studentNumber;
        this.phoneNumber = phoneNumber;
        this.qqMailbox = qqMailbox;
        this.parentalContact = parentalContact;
        this.headmaster = headmaster;
        this.headmasterContact = headmasterContact;
        this.graduationPracticeUnifyName = graduationPracticeUnifyName;
        this.graduationPracticeUnifyAddress = graduationPracticeUnifyAddress;
        this.graduationPracticeUnifyContacts = graduationPracticeUnifyContacts;
        this.graduationPracticeUnifyTel = graduationPracticeUnifyTel;
        this.schoolGuidanceTeacher = schoolGuidanceTeacher;
        this.schoolGuidanceTeacherTel = schoolGuidanceTeacherTel;
        this.startTime = startTime;
        this.endTime = endTime;
        this.commitmentBook = commitmentBook;
        this.safetyResponsibilityBook = safetyResponsibilityBook;
        this.practiceAgreement = practiceAgreement;
        this.internshipApplication = internshipApplication;
        this.practiceReceiving = practiceReceiving;
        this.securityEducationAgreement = securityEducationAgreement;
        this.parentalConsent = parentalConsent;
        this.studentId = studentId;
        this.internshipReleaseId = internshipReleaseId;
    }

    @NotNull
    @Size(max = 64)
    public String getGraduationPracticeUnifyId() {
        return this.graduationPracticeUnifyId;
    }

    public void setGraduationPracticeUnifyId(String graduationPracticeUnifyId) {
        this.graduationPracticeUnifyId = graduationPracticeUnifyId;
    }

    @NotNull
    @Size(max = 15)
    public String getStudentName() {
        return this.studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @NotNull
    @Size(max = 50)
    public String getCollegeClass() {
        return this.collegeClass;
    }

    public void setCollegeClass(String collegeClass) {
        this.collegeClass = collegeClass;
    }

    @NotNull
    @Size(max = 2)
    public String getStudentSex() {
        return this.studentSex;
    }

    public void setStudentSex(String studentSex) {
        this.studentSex = studentSex;
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
    @Size(max = 15)
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @NotNull
    @Size(max = 100)
    public String getQqMailbox() {
        return this.qqMailbox;
    }

    public void setQqMailbox(String qqMailbox) {
        this.qqMailbox = qqMailbox;
    }

    @NotNull
    @Size(max = 20)
    public String getParentalContact() {
        return this.parentalContact;
    }

    public void setParentalContact(String parentalContact) {
        this.parentalContact = parentalContact;
    }

    @NotNull
    @Size(max = 10)
    public String getHeadmaster() {
        return this.headmaster;
    }

    public void setHeadmaster(String headmaster) {
        this.headmaster = headmaster;
    }

    @NotNull
    @Size(max = 20)
    public String getHeadmasterContact() {
        return this.headmasterContact;
    }

    public void setHeadmasterContact(String headmasterContact) {
        this.headmasterContact = headmasterContact;
    }

    @NotNull
    @Size(max = 200)
    public String getGraduationPracticeUnifyName() {
        return this.graduationPracticeUnifyName;
    }

    public void setGraduationPracticeUnifyName(String graduationPracticeUnifyName) {
        this.graduationPracticeUnifyName = graduationPracticeUnifyName;
    }

    @NotNull
    @Size(max = 500)
    public String getGraduationPracticeUnifyAddress() {
        return this.graduationPracticeUnifyAddress;
    }

    public void setGraduationPracticeUnifyAddress(String graduationPracticeUnifyAddress) {
        this.graduationPracticeUnifyAddress = graduationPracticeUnifyAddress;
    }

    @NotNull
    @Size(max = 10)
    public String getGraduationPracticeUnifyContacts() {
        return this.graduationPracticeUnifyContacts;
    }

    public void setGraduationPracticeUnifyContacts(String graduationPracticeUnifyContacts) {
        this.graduationPracticeUnifyContacts = graduationPracticeUnifyContacts;
    }

    @NotNull
    @Size(max = 20)
    public String getGraduationPracticeUnifyTel() {
        return this.graduationPracticeUnifyTel;
    }

    public void setGraduationPracticeUnifyTel(String graduationPracticeUnifyTel) {
        this.graduationPracticeUnifyTel = graduationPracticeUnifyTel;
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
    @Size(max = 20)
    public String getSchoolGuidanceTeacherTel() {
        return this.schoolGuidanceTeacherTel;
    }

    public void setSchoolGuidanceTeacherTel(String schoolGuidanceTeacherTel) {
        this.schoolGuidanceTeacherTel = schoolGuidanceTeacherTel;
    }

    @NotNull
    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @NotNull
    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Byte getCommitmentBook() {
        return this.commitmentBook;
    }

    public void setCommitmentBook(Byte commitmentBook) {
        this.commitmentBook = commitmentBook;
    }

    public Byte getSafetyResponsibilityBook() {
        return this.safetyResponsibilityBook;
    }

    public void setSafetyResponsibilityBook(Byte safetyResponsibilityBook) {
        this.safetyResponsibilityBook = safetyResponsibilityBook;
    }

    public Byte getPracticeAgreement() {
        return this.practiceAgreement;
    }

    public void setPracticeAgreement(Byte practiceAgreement) {
        this.practiceAgreement = practiceAgreement;
    }

    public Byte getInternshipApplication() {
        return this.internshipApplication;
    }

    public void setInternshipApplication(Byte internshipApplication) {
        this.internshipApplication = internshipApplication;
    }

    public Byte getPracticeReceiving() {
        return this.practiceReceiving;
    }

    public void setPracticeReceiving(Byte practiceReceiving) {
        this.practiceReceiving = practiceReceiving;
    }

    public Byte getSecurityEducationAgreement() {
        return this.securityEducationAgreement;
    }

    public void setSecurityEducationAgreement(Byte securityEducationAgreement) {
        this.securityEducationAgreement = securityEducationAgreement;
    }

    public Byte getParentalConsent() {
        return this.parentalConsent;
    }

    public void setParentalConsent(Byte parentalConsent) {
        this.parentalConsent = parentalConsent;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("GraduationPracticeUnify (");

        sb.append(graduationPracticeUnifyId);
        sb.append(", ").append(studentName);
        sb.append(", ").append(collegeClass);
        sb.append(", ").append(studentSex);
        sb.append(", ").append(studentNumber);
        sb.append(", ").append(phoneNumber);
        sb.append(", ").append(qqMailbox);
        sb.append(", ").append(parentalContact);
        sb.append(", ").append(headmaster);
        sb.append(", ").append(headmasterContact);
        sb.append(", ").append(graduationPracticeUnifyName);
        sb.append(", ").append(graduationPracticeUnifyAddress);
        sb.append(", ").append(graduationPracticeUnifyContacts);
        sb.append(", ").append(graduationPracticeUnifyTel);
        sb.append(", ").append(schoolGuidanceTeacher);
        sb.append(", ").append(schoolGuidanceTeacherTel);
        sb.append(", ").append(startTime);
        sb.append(", ").append(endTime);
        sb.append(", ").append(commitmentBook);
        sb.append(", ").append(safetyResponsibilityBook);
        sb.append(", ").append(practiceAgreement);
        sb.append(", ").append(internshipApplication);
        sb.append(", ").append(practiceReceiving);
        sb.append(", ").append(securityEducationAgreement);
        sb.append(", ").append(parentalConsent);
        sb.append(", ").append(studentId);
        sb.append(", ").append(internshipReleaseId);

        sb.append(")");
        return sb.toString();
    }
}
