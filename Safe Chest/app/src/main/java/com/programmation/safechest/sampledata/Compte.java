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
    private Date timestamp;

    public Compte(){
        super();
        this.CompteId = "";
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

    public String getPassword() {
        return password;
    }

    public String getURL() {
        return URL;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCompteId(String compteId) {
        CompteId = compteId;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
