package com.programmation.safechest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;

import io.realm.ObjectServerError;
import io.realm.SyncCredentials;
import io.realm.SyncUser;

import static com.programmation.safechest.Constants.AUTH_URL;

public class EcranConnexion extends AppCompatActivity {

    public final static String PASSWORD = "com.programmation.safechest.Password";

    EditText et_id;
    EditText et_password;

    private Button mPasserelleMenu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo_petit);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        logUsersOut(); //On a besoin du mot de passe pour crypter, et c'est plus sécurisé de redemander

        et_password = findViewById (R.id.et_mdp);
        et_id = findViewById (R.id.et_id);

        Button bConnexion = findViewById(R.id.Connect_info);

        bConnexion.setOnClickListener(v -> {
            Intent menu_principal = new Intent(EcranConnexion.this, ListeComptesActivity.class);
            menu_principal.putExtra(PASSWORD, et_password.getText().toString()); //Pour crypter
            attemptLogin();
        });
    }

    private void logUsersOut(){
        Map<String, SyncUser> users = SyncUser.all();
        for (Map.Entry<String, SyncUser> user : users.entrySet())
            user.getValue().logOut();
    }

    private void attemptLogin() {
        et_id.setError(null);
        et_password.setError(null);

        final String nickname = et_id.getText().toString();
        String password = et_password.getText().toString();

        if (nickname.equals("")) {
            et_id.setError("Votre identifiant ne peut pas être vide");
            et_id.requestFocus();
        } else if (password.equals("")) {
            et_password.setError("Votre mot de passe ne peut pas être vide");
            et_password.requestFocus();
        } else {
            final SyncCredentials credentials = SyncCredentials.usernamePassword(nickname, password, false);
            SyncUser.logInAsync(credentials, AUTH_URL, new SyncUser.Callback<SyncUser>() {
                @Override
                public void onSuccess(SyncUser user) {
                    Intent liste_comptes_intent = new Intent(EcranConnexion.this, ListeComptesActivity.class);
                    liste_comptes_intent.putExtra(PASSWORD, password); // cryptage
                    startActivity(liste_comptes_intent);
                }

                @Override
                public void onError(ObjectServerError error) {
                    et_id.setError("La connexion ne peut s'établir ou la combinaison mot de passe utilisateur est incorrecte");
                    et_id.requestFocus();
                    Log.e("Login error", error.toString());
                }
            });
        }
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
