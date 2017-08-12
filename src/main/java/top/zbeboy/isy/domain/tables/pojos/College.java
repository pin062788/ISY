/*
 * This file is generated by jOOQ.
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
        "jOOQ version:3.9.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class College implements Serializable {

    private static final long serialVersionUID = 1009996741;

    private Integer collegeId;
    private String  collegeName;
    private String  collegeAddress;
    private String  collegeCode;
    private Byte    collegeIsDel;
    private Integer schoolId;

    public College() {}

    public College(College value) {
        this.collegeId = value.collegeId;
        this.collegeName = value.collegeName;
        this.collegeAddress = value.collegeAddress;
        this.collegeCode = value.collegeCode;
        this.collegeIsDel = value.collegeIsDel;
        this.schoolId = value.schoolId;
    }

    public College(
        Integer collegeId,
        String  collegeName,
        String  collegeAddress,
        String  collegeCode,
        Byte    collegeIsDel,
        Integer schoolId
    ) {
        this.collegeId = collegeId;
        this.collegeName = collegeName;
        this.collegeAddress = collegeAddress;
        this.collegeCode = collegeCode;
        this.collegeIsDel = collegeIsDel;
        this.schoolId = schoolId;
    }

    @NotNull
    public Integer getCollegeId() {
        return this.collegeId;
    }

    public void setCollegeId(Integer collegeId) {
        this.collegeId = collegeId;
    }

    @NotNull
    @Size(max = 200)
    public String getCollegeName() {
        return this.collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    @NotNull
    @Size(max = 500)
    public String getCollegeAddress() {
        return this.collegeAddress;
    }

    public void setCollegeAddress(String collegeAddress) {
        this.collegeAddress = collegeAddress;
    }

    @NotNull
    @Size(max = 20)
    public String getCollegeCode() {
        return this.collegeCode;
    }

    public void setCollegeCode(String collegeCode) {
        this.collegeCode = collegeCode;
    }

    public Byte getCollegeIsDel() {
        return this.collegeIsDel;
    }

    public void setCollegeIsDel(Byte collegeIsDel) {
        this.collegeIsDel = collegeIsDel;
    }

    @NotNull
    public Integer getSchoolId() {
        return this.schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("College (");

        sb.append(collegeId);
        sb.append(", ").append(collegeName);
        sb.append(", ").append(collegeAddress);
        sb.append(", ").append(collegeCode);
        sb.append(", ").append(collegeIsDel);
        sb.append(", ").append(schoolId);

        sb.append(")");
        return sb.toString();
    }
}
