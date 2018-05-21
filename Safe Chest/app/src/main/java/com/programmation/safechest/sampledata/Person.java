package com.programmation.safechest.sampledata;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Person extends RealmObject {

    @PrimaryKey
    private long id;
    private String name;
    private RealmList<Dog> dogs; // Declare one-to-many relationships

    public void Person() {
        this.id = 0;
        this.name= "Francis";
        this.dogs = new RealmList<Dog>();
    }

    public RealmList<Dog> getDogs() {
        return dogs;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public Realm getRealm() {
        return super.getRealm();
    }
}
