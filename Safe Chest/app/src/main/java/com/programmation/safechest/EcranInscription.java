package com.programmation.safechest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.Map;

import io.realm.ObjectServerError;
import io.realm.SyncCredentials;
import io.realm.SyncUser;
import static com.programmation.safechest.Constants.AUTH_URL;

public class EcranInscription extends AppCompatActivity {

    public final static String PASSWORD = "com.programmation.safechest.Password";

    EditText et_password1;
    EditText et_password2;
    EditText et_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ecran_inscription);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo_petit);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        Button mPasserelleMenu = (Button) findViewById(R.id.Connect_info_inscription);

        et_password1 = findViewById (R.id.et_mdp_inscription);
        et_password2 = findViewById (R.id.et_mdp_inscription_2);
        et_id = findViewById (R.id.et_id_inscription);

        //Si on a fini de remplir
        mPasserelleMenu.setOnClickListener(v -> {

            String password1 = et_password1.getText().toString();
            String password2 = et_password2.getText().toString();
            String id = et_id.getText().toString();

            //On vérifie les mots de passe
            if (!password1.equals(password2)) {
                et_password2.setError("Les mots de passe doivent être identiques");
                et_password2.requestFocus();
            }
            else {
                // Le mot de passe doit être fort
                if (id.equals("")){
                    et_id.setError("L'identifiant ne doit pas être vide");
                    et_id.requestFocus();
                } else if (password1.length() < 12){
                    et_password1.setError("Votre mot de passe maître doit contenir au minimum 12 caractères");
                    et_password1.requestFocus();
                } else if (password1.toLowerCase().equals(password1) || password1.toUpperCase().equals(password1)){
                    et_password1.setError("Votre mot de passe maître doit contenir des majuscules et des minuscules");
                    et_password1.requestFocus();
                } else if (!isAnyNumeric(password1)){
                    et_password1.setError("Votre mot de passe maître doit contenir des nombres et un caractère spécial : @, ?, . ... ");
                    et_password1.requestFocus();
                }
                else {
                    // sinon on inscrit
                    attemptSignup();
                }
            }
        });
    }

    private void logUsersOut(){
        //On déconnecte un précédent utilisateur
        Map<String, SyncUser> users = SyncUser.all();
        for (Map.Entry<String, SyncUser> user : users.entrySet())
            user.getValue().logOut();
    }

    private void attemptSignup() {
        logUsersOut();

        et_id.setError(null);
        et_password1.setError(null);

        String nickname = et_id.getText().toString();
        String password = et_password1.getText().toString();

        SyncCredentials credentials = SyncCredentials.usernamePassword(nickname, password, true);
        SyncUser.logInAsync(credentials, AUTH_URL, new SyncUser.Callback<SyncUser>() {
            @Override
            public void onSuccess(SyncUser user) {
                Intent liste_comptes_intent = new Intent(EcranInscription.this, ListeComptesActivity.class);
                liste_comptes_intent.putExtra(PASSWORD, password);
                startActivity(liste_comptes_intent);
            }

            @Override
            public void onError(ObjectServerError error) {
                et_id.setError("Un utilisateur avec ce mot de passe existe déjà ou la connexion ne peut s'établir");
                et_id.requestFocus();
                Log.e("Login error", error.toString());
            }
        });
    }

    public static boolean isAnyNumeric(String str) {
        return (str.matches("(.*)\\d(.*)") && str.matches("(.*)\\W(.*)"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Intent intent = NavUtils.getParentActivityIntent(this);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                NavUtils.navigateUpTo(this, intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}