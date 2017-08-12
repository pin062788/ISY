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
public class GraduationDesignSubjectOriginType implements Serializable {

    private static final long serialVersionUID = -616750865;

    private Integer originTypeId;
    private String  originTypeName;

    public GraduationDesignSubjectOriginType() {}

    public GraduationDesignSubjectOriginType(GraduationDesignSubjectOriginType value) {
        this.originTypeId = value.originTypeId;
        this.originTypeName = value.originTypeName;
    }

    public GraduationDesignSubjectOriginType(
        Integer originTypeId,
        String  originTypeName
    ) {
        this.originTypeId = originTypeId;
        this.originTypeName = originTypeName;
    }

    @NotNull
    public Integer getOriginTypeId() {
        return this.originTypeId;
    }

    public void setOriginTypeId(Integer originTypeId) {
        this.originTypeId = originTypeId;
    }

    @NotNull
    @Size(max = 30)
    public String getOriginTypeName() {
        return this.originTypeName;
    }

    public void setOriginTypeName(String originTypeName) {
        this.originTypeName = originTypeName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("GraduationDesignSubjectOriginType (");

        sb.append(originTypeId);
        sb.append(", ").append(originTypeName);

        sb.append(")");
        return sb.toString();
    }
}
