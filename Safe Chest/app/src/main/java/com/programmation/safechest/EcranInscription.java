package com.programmation.safechest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EcranInscription extends AppCompatActivity {

    public final static String PASSWORD = "com.programmation.safechest.Password";
    public final static String ID = "com.programmation.safechest.ID";

    private Button mPasserelleMenu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ecran_inscription);

        Button mPasserelleMenu = (Button) findViewById(R.id.Connect_info_inscription);

        mPasserelleMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination

                Intent menu_principal = new Intent(EcranInscription.this, MenuPrincipal.class);

                EditText password1 = (EditText) findViewById (R.id.et_mdp_inscription);
                EditText password2 = (EditText) findViewById (R.id.et_mdp_inscription_2);

                EditText ide = (EditText) findViewById (R.id.et_id_inscription);

                if (!password1.getText().toString().equals(password2.getText().toString())) {
                    Toast.makeText(getApplicationContext(),"Les mots de passe doivent être identiques", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (ide.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"L'identifiant ne doit pas être vide", Toast.LENGTH_SHORT).show();
                    } else if (password1.getText().toString().length() < 12){
                        Toast.makeText(getApplicationContext(),"Votre mot de passe maître doit contenir au minimum 12 caractères", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        // On rajoute un extra
                        menu_principal.putExtra(PASSWORD, password1.getText().toString());
                        menu_principal.putExtra(ID, ide.getText().toString());

                        // Puis on lance l'intent !
                        startActivity(menu_principal);
                    }
                }


            }
        });
    }
}