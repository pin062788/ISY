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
public class GraduationDesignSubjectType implements Serializable {

    private static final long serialVersionUID = -1760233745;

    private Integer subjectTypeId;
    private String  subjectTypeName;

    public GraduationDesignSubjectType() {}

    public GraduationDesignSubjectType(GraduationDesignSubjectType value) {
        this.subjectTypeId = value.subjectTypeId;
        this.subjectTypeName = value.subjectTypeName;
    }

    public GraduationDesignSubjectType(
        Integer subjectTypeId,
        String  subjectTypeName
    ) {
        this.subjectTypeId = subjectTypeId;
        this.subjectTypeName = subjectTypeName;
    }

    @NotNull
    public Integer getSubjectTypeId() {
        return this.subjectTypeId;
    }

    public void setSubjectTypeId(Integer subjectTypeId) {
        this.subjectTypeId = subjectTypeId;
    }

    @NotNull
    @Size(max = 30)
    public String getSubjectTypeName() {
        return this.subjectTypeName;
    }

    public void setSubjectTypeName(String subjectTypeName) {
        this.subjectTypeName = subjectTypeName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("GraduationDesignSubjectType (");

        sb.append(subjectTypeId);
        sb.append(", ").append(subjectTypeName);

        sb.append(")");
        return sb.toString();
    }
}
