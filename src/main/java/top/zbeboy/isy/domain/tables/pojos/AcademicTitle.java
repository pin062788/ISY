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
public class AcademicTitle implements Serializable {

    private static final long serialVersionUID = -1379580759;

    private Integer academicTitleId;
    private String  academicTitleName;

    public AcademicTitle() {}

    public AcademicTitle(AcademicTitle value) {
        this.academicTitleId = value.academicTitleId;
        this.academicTitleName = value.academicTitleName;
    }

    public AcademicTitle(
        Integer academicTitleId,
        String  academicTitleName
    ) {
        this.academicTitleId = academicTitleId;
        this.academicTitleName = academicTitleName;
    }

    @NotNull
    public Integer getAcademicTitleId() {
        return this.academicTitleId;
    }

    public void setAcademicTitleId(Integer academicTitleId) {
        this.academicTitleId = academicTitleId;
    }

    @NotNull
    @Size(max = 30)
    public String getAcademicTitleName() {
        return this.academicTitleName;
    }

    public void setAcademicTitleName(String academicTitleName) {
        this.academicTitleName = academicTitleName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("AcademicTitle (");

        sb.append(academicTitleId);
        sb.append(", ").append(academicTitleName);

        sb.append(")");
        return sb.toString();
    }
}
