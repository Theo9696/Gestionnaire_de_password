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


        Button mPasserelle =  findViewById(R.id.Connect_passerelle);
        Button mPasserelle_insc =  findViewById(R.id.Register_passerelle);
        Button mCredit =  findViewById(R.id.Credits);

        mPasserelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent secondeActivite = new Intent(EcranAccueil.this, EcranConnexion.class);

                // Puis on lance l'intent !
                startActivity(secondeActivite);
                }
        });

        mPasserelle_insc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent secondeActivite = new Intent(EcranAccueil.this, EcranInscription.class);

                // Puis on lance l'intent !
                startActivity(secondeActivite);
            }

        });

        mCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination


                Intent secondeActivite = new Intent(EcranAccueil.this, Credit.class);

                // Puis on lance l'intent !
                startActivity(secondeActivite);

            }

        });

        }
    }

