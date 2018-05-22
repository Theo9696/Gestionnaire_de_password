package com.programmation.safechest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.SyncConfiguration;
import io.realm.SyncUser;
import com.programmation.safechest.sampledata.Compte;
import com.programmation.safechest.ui.sampledata.RecyclerCompte;


public class ListeComptesActivity extends AppCompatActivity {

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listecompte_activity);



        setSupportActionBar(findViewById(R.id.toolbar));

        findViewById(R.id.fab).setOnClickListener(view -> {
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_task, null);
            EditText taskText = dialogView.findViewById(R.id.task);
            new AlertDialog.Builder(ListeComptesActivity.this)
                    .setTitle("Add a new task")
                    .setMessage("What do you want to do next?")
                    .setView(dialogView)
                    .setPositiveButton("Add", (dialog, which) -> {
                        realm.executeTransactionAsync(realm -> {
                            Compte compte = new Compte();
                            compte.setCompteId(taskText.getText().toString());
                            realm.insert(compte);
                        });
                    })
                    .setNegativeButton("Cancel", null)
                    .create()
                    .show();
        });

        RealmResults<Compte> comptes = setUpRealm();

        final RecyclerCompte itemsRecyclerAdapter = new RecyclerCompte(comptes);
        RecyclerCompte recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemsRecyclerAdapter);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                String id = itemsRecyclerAdapter.getItem(position).getItemId();
                realm.executeTransactionAsync(realm -> {
                    Item item = realm.where(Item.class)
                            .equalTo("itemId", id)
                            .findFirst();
                    if (item != null) {
                        item.deleteFromRealm();
                    }
                });
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private RealmResults<Compte> setUpRealm() {
        Realm.setDefaultConfiguration(SyncConfiguration.automatic());
        realm = Realm.getDefaultInstance();

        return realm
                .where(Compte.class)
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
        if (item.getItemId() == R.id.action_logout) {
            SyncUser syncUser = SyncUser.current();
            if (syncUser != null) {
                syncUser.logOut();
                Intent intent = new Intent(this, EcranAccueil.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


        //

    }
}
