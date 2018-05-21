package com.programmation.safechest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class EcranAccueil extends AppCompatActivity {

    public final static String AGE = "sdz.chapitreTrois.intent.example.AGE";

    private Button mPasserelle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ecran_accueil);

        Button mPasserelle = (Button) findViewById(R.id.Connect_passerelle);

        mPasserelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent secondeActivite = new Intent(EcranAccueil.this, EcranConnexion.class);

                // On rajoute un extra
                secondeActivite.putExtra(AGE, 31);

                // Puis on lance l'intent !
                startActivity(secondeActivite);
            }
        });
    }

}
