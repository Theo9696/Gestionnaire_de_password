package com.programmation.safechest;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;


public class EcranAccueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ecran_accueil);

        Button bConnexion =  findViewById(R.id.Connect_passerelle);
        Button bInscription =  findViewById(R.id.Register_passerelle);
        Button bCredit =  findViewById(R.id.Credits);

        bConnexion.setOnClickListener(v -> {
            Intent secondeActivite = new Intent(EcranAccueil.this, EcranConnexion.class);
            startActivity(secondeActivite);
        });

        bInscription.setOnClickListener(v -> {
            Intent secondeActivite = new Intent(EcranAccueil.this, EcranInscription.class);
            startActivity(secondeActivite);
        });

        bCredit.setOnClickListener((View.OnClickListener) v -> {
            Intent secondeActivite = new Intent(EcranAccueil.this, Credit.class);
            startActivity(secondeActivite);
        });
    }
}

