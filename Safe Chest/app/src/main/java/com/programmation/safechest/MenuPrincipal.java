package com.programmation.safechest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.programmation.safechest.sampledata.Dog;
import com.programmation.safechest.sampledata.Person;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmResults;


public class MenuPrincipal extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        LinearLayout layout = (LinearLayout) LinearLayout.inflate(this, R.layout.menu_principal, null);


        Dog dog = new Dog();
        dog.setName("Rex");
        dog.setAge(1);

        Toast.makeText(this, "ça va", Toast.LENGTH_SHORT).show();

// Initialize Realm (just once per application)
        Realm.init(this);


// Get a Realm instance for this thread
        Realm realm = Realm.getDefaultInstance();


// Query Realm for all dogs younger than 2 years old
        final RealmResults<Dog> puppies = realm.where(Dog.class).lessThan("age", 2).findAll();
        puppies.size(); // => 0 because no dogs have been added to the Realm yet


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
