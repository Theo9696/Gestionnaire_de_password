package com.programmation.safechest.sampledata;

import android.util.Log;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.SyncCredentials;
import io.realm.SyncUser;
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


    //Cryptage
    @Ignore
    private Cipher cipher;
    @Ignore
    private SecretKey secretKey;
    @Ignore
    private PBEKeySpec keySpec;
    @Ignore
    private SecretKeyFactory keyFactory;

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

    public void setPassword(String password) {
        this.password = password;

        Log.e("ERROR", password);
        //On le crypte directement
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
        try {
            this.password = new String(cipher.doFinal(password.getBytes()));
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
        Log.e("ERROR", this.password);
    }

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

    public void setKey(String key) {
        //On génère le cipher
        keySpec = new PBEKeySpec(key.toCharArray(), "S4f3Ch3st!*&ée78gh".getBytes(), 10);
        keyFactory = null;
        try {
            keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
        try {
            secretKey = keyFactory.generateSecret(keySpec);
            Log.e("ERROR", secretKey.toString());
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
        try {
            cipher = Cipher.getInstance("PBEWithMD5AndDES");
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
    }

    public String getUnencryptedPassord() {
        AlgorithmParameters params = cipher.getParameters();
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey, params);
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
        try {
            return new String(cipher.doFinal(password.getBytes()));
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
        return "";
    }
}
