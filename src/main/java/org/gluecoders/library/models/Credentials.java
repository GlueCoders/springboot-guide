package org.gluecoders.library.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import org.springframework.data.annotation.Transient;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

/**
 * Created by Anand_Rajneesh on 7/1/2017.
 */
@Entity
public class Credentials {

    @Id
    @GeneratedValue
    @JsonIgnore
    private long id;
    @NotNull @NotEmpty
    private String username;
    @Transient @NotNull @NotEmpty
    private String pwd;
    @JsonIgnore
    private String role;
    @JsonIgnore
    private String saltedPwd;
    //@JsonIgnore
    private Instant createdOn;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }
}
