package org.gluecoders.library.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Anand_Rajneesh on 7/1/2017.
 */
@Document(collection = "creds")
public class Credentials {

    @Id
    @JsonIgnore
    private String id;
    @NotNull @NotEmpty
    private String username;
    @Transient @NotNull @NotEmpty
    private String pwd;
    @JsonIgnore
    private String role;
    @JsonIgnore
    private String saltedPwd;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSaltedPwd() {
        return saltedPwd;
    }

    public void setSaltedPwd(String saltedPwd) {
        this.saltedPwd = saltedPwd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "{\"username\":\""+username+"\"}";
    }
}
