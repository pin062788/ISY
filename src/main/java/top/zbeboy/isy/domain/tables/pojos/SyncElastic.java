/*
 * This file is generated by jOOQ.
*/
package top.zbeboy.isy.domain.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

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
public class SyncElastic implements Serializable {

    private static final long serialVersionUID = -1388012634;

    private Integer   syncElastic;
    private String    applicationName;
    private Timestamp syncTime;
    private String    syncUrl;
    private String    username;

    public SyncElastic() {}

    public SyncElastic(SyncElastic value) {
        this.syncElastic = value.syncElastic;
        this.applicationName = value.applicationName;
        this.syncTime = value.syncTime;
        this.syncUrl = value.syncUrl;
        this.username = value.username;
    }

    public SyncElastic(
        Integer   syncElastic,
        String    applicationName,
        Timestamp syncTime,
        String    syncUrl,
        String    username
    ) {
        this.syncElastic = syncElastic;
        this.applicationName = applicationName;
        this.syncTime = syncTime;
        this.syncUrl = syncUrl;
        this.username = username;
    }

    @NotNull
    public Integer getSyncElastic() {
        return this.syncElastic;
    }

    public void setSyncElastic(Integer syncElastic) {
        this.syncElastic = syncElastic;
    }

    @NotNull
    @Size(max = 30)
    public String getApplicationName() {
        return this.applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    @NotNull
    public Timestamp getSyncTime() {
        return this.syncTime;
    }

    public void setSyncTime(Timestamp syncTime) {
        this.syncTime = syncTime;
    }

    @NotNull
    @Size(max = 200)
    public String getSyncUrl() {
        return this.syncUrl;
    }

    public void setSyncUrl(String syncUrl) {
        this.syncUrl = syncUrl;
    }

    @NotNull
    @Size(max = 64)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SyncElastic (");

        sb.append(syncElastic);
        sb.append(", ").append(applicationName);
        sb.append(", ").append(syncTime);
        sb.append(", ").append(syncUrl);
        sb.append(", ").append(username);

        sb.append(")");
        return sb.toString();
    }
}
