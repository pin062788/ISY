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
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class OauthClientToken implements Serializable {

    private static final long serialVersionUID = -1305613851;

    private String tokenId;
    private byte[] token;
    private String authenticationId;
    private String userName;
    private String clientId;

    public OauthClientToken() {}

    public OauthClientToken(OauthClientToken value) {
        this.tokenId = value.tokenId;
        this.token = value.token;
        this.authenticationId = value.authenticationId;
        this.userName = value.userName;
        this.clientId = value.clientId;
    }

    public OauthClientToken(
        String tokenId,
        byte[] token,
        String authenticationId,
        String userName,
        String clientId
    ) {
        this.tokenId = tokenId;
        this.token = token;
        this.authenticationId = authenticationId;
        this.userName = userName;
        this.clientId = clientId;
    }

    @Size(max = 256)
    public String getTokenId() {
        return this.tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public byte[] getToken() {
        return this.token;
    }

    public void setToken(byte... token) {
        this.token = token;
    }

    @NotNull
    @Size(max = 256)
    public String getAuthenticationId() {
        return this.authenticationId;
    }

    public void setAuthenticationId(String authenticationId) {
        this.authenticationId = authenticationId;
    }

    @Size(max = 256)
    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Size(max = 256)
    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("OauthClientToken (");

        sb.append(tokenId);
        sb.append(", ").append("[binary...]");
        sb.append(", ").append(authenticationId);
        sb.append(", ").append(userName);
        sb.append(", ").append(clientId);

        sb.append(")");
        return sb.toString();
    }
}
