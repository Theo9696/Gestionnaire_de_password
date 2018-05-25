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
    public final static String ID = "com.programmation.safechest.ID";



    EditText ide;
    EditText e_password;

    private Button mPasserelleMenu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        logUsersOut();

        e_password = (EditText) findViewById (R.id.et_mdp);
        ide = (EditText) findViewById (R.id.et_id);

        Button mPasserelleMenu = (Button) findViewById(R.id.Connect_info);

        mPasserelleMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent menu_principal = new Intent(EcranConnexion.this, ListeComptesActivity.class);

                // On rajoute un extra
                menu_principal.putExtra(PASSWORD, e_password.getText().toString());
                menu_principal.putExtra(ID, ide.getText().toString());

                // Puis on lance l'intent !
                attemptLogin();
            }
        });
    }

    private void logUsersOut(){
        Map<String, SyncUser> users = SyncUser.all();
        for (Map.Entry<String, SyncUser> user : users.entrySet())
            user.getValue().logOut();
    }

    private void attemptLogin() {
        // Reset errors.
        ide.setError(null);
        e_password.setError(null);
        // Store values at the time of the login attempt.
        final String nickname = ide.getText().toString();
        String password = e_password.getText().toString();

        if (nickname.equals("")) {
            Toast.makeText(this,"Votre identifiant ne peut pas être vide", Toast.LENGTH_SHORT).show();
        } else if (password.equals("")) {
            Toast.makeText(this,"Votre mot de passe ne peut pas être vide", Toast.LENGTH_SHORT).show();
        } else {


            final SyncCredentials credentials = SyncCredentials.usernamePassword(nickname, password, false);
            SyncUser.logInAsync(credentials, AUTH_URL, new SyncUser.Callback<SyncUser>() {
                @Override
                public void onSuccess(SyncUser user) {
                    Intent menu_principal = new Intent(EcranConnexion.this, ListeComptesActivity.class);
                    startActivity(menu_principal);
                }

                @Override
                public void onError(ObjectServerError error) {
                    ide.setError("La connexion ne peut s'établir ou la combinaison mot de passe utilisateur est incorrecte");
                    ide.requestFocus();
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
