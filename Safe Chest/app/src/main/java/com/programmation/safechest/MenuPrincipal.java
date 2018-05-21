package com.programmation.safechest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
public class MenuPrincipal extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        LinearLayout layout = (LinearLayout) LinearLayout.inflate(this, R.layout.menu_principal, null);

        // On récupère l'intent qui a lancé cette activité
        Intent i = getIntent();

        // Puis on récupère l'âge donné dans l'autre activité, ou 0 si cet extra n'est pas dans l'intent
        String password = i.getStringExtra(EcranConnexion.PASSWORD);
        String ide = i.getStringExtra(EcranConnexion.ID);

        TextView textView = layout.findViewById(R.id.info);
        textView.setText("id : " + ide + " mdp : " + password);

        setContentView(layout);
        //


    }
}
