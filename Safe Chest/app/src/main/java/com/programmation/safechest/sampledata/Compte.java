package com.programmation.safechest.sampledata;

import android.widget.Toast;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
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
    private String owner;

    @Required
    private Date timestamp;

    @Ignore
    private Realm realm;

    public Compte(){
        super();
        this.CompteId = "";
        this.login = "";
        this.password = "";
        this.URL = "";
        this.timestamp = new Date();
        this.owner="";
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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
        try {
            CompteId = URL + login;
        }
        catch (Exception e) {

        }
    }

    public void setURL(String URL) {
        this.URL = URL;
        setCompteId();
    }

    public void setLogin(String login) {
        this.login = login;
        setCompteId();
    }

    public void setRealm(Realm realm){ this.realm = realm; }

    public Realm getRealm(Realm realm){ return this.realm; }
}
