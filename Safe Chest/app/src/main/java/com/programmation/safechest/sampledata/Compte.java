package com.programmation.safechest.sampledata;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Compte extends RealmObject{

    @PrimaryKey
    @Required
    private String CompteId;

    @Required
    private String password;

    @Required
    private String URL;

    @Required
    private String login;

    @Required
    private Date timestamp;

    public Compte(){
        super();
        this.CompteId = "";
        this.login = "";
        this.password = "";
        this.URL = "";
        this.timestamp = new Date();
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getCompteId() {
        return CompteId;
    }

    public String getCompteLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getURL() {
        return URL;
    }

    public void setPassword(String password) { this.password = password; }

    protected void setCompteId() {
        CompteId = URL + login;
    }

    public void setURL(String URL) {
        this.URL = URL;
        setCompteId();
    }

    public void setLogin(String login) {
        this.login = login;
        setCompteId();
    }
}
