package com.programmation.safechest.sampledata;


import android.util.Log;
import java.nio.charset.Charset;
import java.security.AlgorithmParameters;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

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


    private int color;

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
    @Ignore
    private AlgorithmParameters params;

    public Compte(){
        super();
        this.CompteId = "";
        this.login = "";
        this.password = "";
        this.URL = "";
        this.timestamp = new Date();
        this.owner="";
        this.color = (int) Math.floor(Math.random()*4);
    }

    public int getColor() {
        return color;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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
        this.password = "";

        try {
            //On le crypte directement
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedTextBytes = cipher.doFinal(password.getBytes());

            //Et on met les byte dans un String
            for(byte b : encryptedTextBytes)
                this.password += b + " "; // On brouille encore plus les pistes
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
    }

    protected void setCompteId() { CompteId = URL + login; }

    public void setURL(String URL) {
        this.URL = URL;
        setCompteId();
    }

    public void setLogin(String login) {
        this.login = login;
        setCompteId();
    }

    public void setKey(String key) {
        //On génère le cipher, à partir du mot de passe utilisateur
        try {
            keySpec = new PBEKeySpec(key.toCharArray(), "S4f3Ch3st!*&ée78gh".getBytes(), 10);
            keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
            secretKey = keyFactory.generateSecret(keySpec);
            Log.e("ERROR", secretKey.toString());
            cipher = Cipher.getInstance("PBEWithMD5AndDES");
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
        params = cipher.getParameters();
    }

    public String getUnencryptedPassword() {
        String res = "";
        try {
            //Il faut repasser à la chaine de bytes
            String[] stringOfBytes = this.password.split("\\s");
            final int n = stringOfBytes.length;
            byte[] to_decode = new byte[n];
            for(int i = 0; i<n; i++)
                to_decode[i] = Byte.valueOf(stringOfBytes[i]);

            //Puis on décrypte
            cipher.init(Cipher.DECRYPT_MODE, secretKey, params);
            byte[] decryptedTextBytes = cipher.doFinal(to_decode);//encrypted.getBytes());
            res = new String(decryptedTextBytes, Charset.forName("iso-8859-1"));
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            res = "[ERROR DATA CORRUPTED]";
        }
        return res;
    }
}
