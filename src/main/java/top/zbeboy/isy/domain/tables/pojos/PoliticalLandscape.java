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
public class PoliticalLandscape implements Serializable {

    private static final long serialVersionUID = -1947112407;

    private Integer politicalLandscapeId;
    private String  politicalLandscapeName;

    public PoliticalLandscape() {}

    public PoliticalLandscape(PoliticalLandscape value) {
        this.politicalLandscapeId = value.politicalLandscapeId;
        this.politicalLandscapeName = value.politicalLandscapeName;
    }

    public PoliticalLandscape(
        Integer politicalLandscapeId,
        String  politicalLandscapeName
    ) {
        this.politicalLandscapeId = politicalLandscapeId;
        this.politicalLandscapeName = politicalLandscapeName;
    }

    @NotNull
    public Integer getPoliticalLandscapeId() {
        return this.politicalLandscapeId;
    }

    public void setPoliticalLandscapeId(Integer politicalLandscapeId) {
        this.politicalLandscapeId = politicalLandscapeId;
    }

    @NotNull
    @Size(max = 30)
    public String getPoliticalLandscapeName() {
        return this.politicalLandscapeName;
    }

    public void setPoliticalLandscapeName(String politicalLandscapeName) {
        this.politicalLandscapeName = politicalLandscapeName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("PoliticalLandscape (");

        sb.append(politicalLandscapeId);
        sb.append(", ").append(politicalLandscapeName);

        sb.append(")");
        return sb.toString();
    }
}
