package com.programmation.safechest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.ObjectServerError;
import io.realm.SyncCredentials;
import io.realm.SyncUser;
import static com.programmation.safechest.Constants.AUTH_URL;

public class EcranInscription extends AppCompatActivity {

    public final static String PASSWORD = "com.programmation.safechest.Password";
    public final static String ID = "com.programmation.safechest.ID";

    EditText et_password1;
    EditText et_password2;
    EditText et_ide;

    private Button mPasserelleMenu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ecran_inscription);

        Button mPasserelleMenu = (Button) findViewById(R.id.Connect_info_inscription);


        et_password1 = (EditText) findViewById (R.id.et_mdp_inscription);
        et_password2 = (EditText) findViewById (R.id.et_mdp_inscription_2);

        et_ide = (EditText) findViewById (R.id.et_id_inscription);
        Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT);


        mPasserelleMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination

                Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT);

                Intent menu_principal = new Intent(EcranInscription.this, EcranAccueil.class);


                String password1 = et_password1.getText().toString();
                String password2 = et_password2.getText().toString();
                String ide = et_ide.getText().toString();

                /*
                if (!password1.equals(password2)) {
                    Toast.makeText(getApplicationContext(),"Les mots de passe doivent être identiques", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (ide.equals("")){
                        Toast.makeText(getApplicationContext(),"L'identifiant ne doit pas être vide", Toast.LENGTH_SHORT).show();
                    } else if (password1.length() < 12){
                        Toast.makeText(getApplicationContext(),"Votre mot de passe maître doit contenir au minimum 12 caractères", Toast.LENGTH_SHORT).show();
                    } else if (password1.toLowerCase().equals(password1) || password1.toUpperCase().equals(password1)){
                        Toast.makeText(getApplicationContext(),"Votre mot de passe maître doit contenir des majuscules et des minuscules", Toast.LENGTH_SHORT).show();
                    } else if (!isNumeric(password1)){
                        Toast.makeText(getApplicationContext(),"Votre mot de passe maître doit contenir des nombres et un caractère spécial : @, ?, . ... ", Toast.LENGTH_SHORT).show();
                    }
                    else { */
                        // On rajoute un extra
                        menu_principal.putExtra(PASSWORD, password1);
                        menu_principal.putExtra(ID, ide);

                        // Puis on lance l'intent !
                        attemptLogin();
                /*
                    }
                }
                */


            }
        });

    }

        private void attemptLogin() {
            // Reset errors.
            et_ide.setError(null);
            et_password1.setError(null);
            // Store values at the time of the login attempt.
            String nickname = et_ide.getText().toString();
            String password = et_password1.getText().toString();
            Toast.makeText(getApplicationContext(), nickname+password, Toast.LENGTH_SHORT);


            SyncCredentials credentials = SyncCredentials.usernamePassword(nickname, password, true);
            SyncUser.logInAsync(credentials, AUTH_URL, new SyncUser.Callback<SyncUser>() {
                @Override
                public void onSuccess(SyncUser user) {

                    Intent menu_principal = new Intent(EcranInscription.this, EcranAccueil.class);
                    Toast.makeText(getApplicationContext(), "don't succeed to open listescompte", Toast.LENGTH_SHORT);
                    startActivity(menu_principal);
                }

                @Override
                public void onError(ObjectServerError error) {

                    et_ide.setError("Un utilisateur avec ce mot de passe existe déjà ou la connexion ne peut s'établir" + error.toString());
                    et_ide.requestFocus();
                    Log.e("Login error", error.toString());
                }
            });
        }

    public static boolean isNumeric(String str)
    {
        return (str.matches("(.*)\\d(.*)") && str.matches("(.*)\\W(.*)"));
    }

}