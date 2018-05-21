package com.programmation.safechest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EcranConnexion extends AppCompatActivity {

    public final static String PASSWORD = "com.programmation.safechest.Password";
    public final static String ID = "com.programmation.safechest.ID";

    private Button mPasserelleMenu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion);

        Button mPasserelleMenu = (Button) findViewById(R.id.Connect_info);

        mPasserelleMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent menu_principal = new Intent(EcranConnexion.this, MenuPrincipal.class);

                EditText password = (EditText) findViewById (R.id.et_mdp);
                EditText ide = (EditText) findViewById (R.id.et_id);

                // On rajoute un extra
                menu_principal.putExtra(PASSWORD, password.getText().toString());
                menu_principal.putExtra(ID, ide.getText().toString());

                // Puis on lance l'intent !
                startActivity(menu_principal);
            }
        });
}
}
