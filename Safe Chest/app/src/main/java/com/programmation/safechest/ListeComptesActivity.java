package com.programmation.safechest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.programmation.safechest.sampledata.Compte;
import com.programmation.safechest.ui.sampledata.RecyclerCompte;

import java.util.Random;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.Sort;
import io.realm.SyncConfiguration;
import io.realm.SyncUser;


public class ListeComptesActivity extends AppCompatActivity {

    private Realm realm;
    private String password;

    public final static String PASSWORD = "com.programmation.safechest.Password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listecompte_activity);

        password = getIntent().getStringExtra(PASSWORD);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setLogo(R.drawable.logo_petit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Le bouton pour rajouter un compte
        findViewById(R.id.fab).setOnClickListener(view -> {
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_task, null);

            EditText loginText = dialogView.findViewById(R.id.login);
            EditText PasswordText = dialogView.findViewById(R.id.password);
            EditText UrlText = dialogView.findViewById(R.id.url);

            PasswordText.setText(generate_password()); // On en génère un robuste (possibilité de modifier)

            new AlertDialog.Builder(ListeComptesActivity.this)
                    .setTitle("Rajouter un compte")
                    .setMessage("Renseignez url, id et mot de passe !")
                    .setView(dialogView)
                    .setPositiveButton("Ajouter", (dialog, which) -> {
                        if (UrlText.getText().toString().equals("") || PasswordText.getText().toString().equals("") || loginText.getText().toString().equals("")) {
                            Toast.makeText(ListeComptesActivity.this, "Aucun des trois champs ne peut être vide", Toast.LENGTH_SHORT).show();
                        } else {
                        realm.executeTransactionAsync(realm -> {
                            try {


                                Compte compte = new Compte();
                                compte.setKey(password);
                                compte.setLogin(loginText.getText().toString());
                                compte.setURL(UrlText.getText().toString());
                                compte.setPassword(PasswordText.getText().toString());
                                compte.setOwner(SyncUser.current().getIdentity());
                                realm.insert(compte);

                            } catch (Exception e) {
                                Toast.makeText(ListeComptesActivity.this, "Un compte avec ce login existe déjà !", Toast.LENGTH_SHORT).show();
                            }
                        });
                        }
                    })
                    .setNegativeButton("Annuler", null)
                    .create()
                    .show();
        });

        //On affiche tous les comptes
        afficher();
    }

    public void afficher(){

        OrderedRealmCollection<Compte> comptes = setUpRealm();

        final RecyclerCompte compteRecycler = new RecyclerCompte(comptes, password);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(compteRecycler);

        //On permet de supprimer un compte en le swipant
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                try {
                    //On vérifie que l'utilisateur sait ce qu'il fait
                    View dialogView = LayoutInflater.from(ListeComptesActivity.this).inflate(R.layout.suppression, null);

                    new AlertDialog.Builder(ListeComptesActivity.this)
                            .setTitle("Suppression du mémo")
                            .setView(dialogView)
                            .setPositiveButton("Supprimer",  (dialog, which) -> {
                                //On supprime de la bdd
                                int position = viewHolder.getAdapterPosition();
                                String id = compteRecycler.getItem(position).getCompteId();
                                realm.executeTransactionAsync(realm -> {
                                    Compte compte = realm.where(Compte.class)
                                            .equalTo("CompteId", id)
                                            .findFirst();
                                    if (compte != null) {
                                        compte.deleteFromRealm();
                                    }
                                });
                            })
                            .setNegativeButton("Annuler",   (dialog, which) -> {
                                //On réaffiche tout
                                afficher();
                            })
                            .create()
                            .show();
                }
                catch (Exception e){
                    Log.e("ERROR", e.getMessage() + "\n" + e.getStackTrace());
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public String choose_random(String caracters){
        //On choisi un caractère aléatoirement
        Random randomizer = new Random();
        return String.valueOf(caracters.toCharArray()[randomizer.nextInt(caracters.length())]);
    }

    public String generate_password(){
        //On génère un mot de passe robuste à 20 caractères
        String res = "";
        for(; res.length() < 20; res += choose_random("azertyuiopqsdfghjklmwxcvbnAZERTYUIOPQSDFGHJKLMWXCVBN,;:!?./§$*ù^£µ%¨&é\"'(-è_çà)=1234567890°+'"));
        return res;
    }

    private OrderedRealmCollection<Compte> setUpRealm() {
        try{
            Realm.setDefaultConfiguration(SyncConfiguration.automatic());
        }
        catch(Exception e){
            Toast.makeText(this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
            Log.e("ERROR", e.getMessage());
        }
        realm = Realm.getDefaultInstance();

        return realm
                .where(Compte.class)
                .equalTo("owner", SyncUser.current().getIdentity())
                .sort("timestamp", Sort.DESCENDING)
                .findAllAsync();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SyncUser syncUser = SyncUser.current();
        switch(item.getItemId()) {
            case R.id.action_logout:
                if (syncUser != null) {
                    syncUser.logOut();
                    Intent intent = new Intent(this, EcranAccueil.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                return true;
            case R.id.home:
                // La ligne de code ci-dessous permet d'activer le bouton retour
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
