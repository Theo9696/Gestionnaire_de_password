package com.programmation.safechest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

public class EcranAccueil extends AppCompatActivity {

    public final static String AGE = "sdz.chapitreTrois.intent.example.AGE";

    private Button mPasserelle = null;
    private Button mPasserelle_insc = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ecran_accueil);



        //Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //toolbar.setLogo(R.drawable.logo);

        Button mPasserelle = (Button) findViewById(R.id.Connect_passerelle);
        Button mPasserelle_insc = (Button) findViewById(R.id.Register_passerelle);

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
    }

}
