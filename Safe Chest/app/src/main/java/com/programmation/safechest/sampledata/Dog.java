package com.programmation.safechest.sampledata;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

// Define your model class by extending RealmObject
public class Dog extends RealmObject {

    private String name;
    private int age;

    public void Dog(){
        this.name = "Waf";
        this.age = 0;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    @Override
    public Realm getRealm() {
        return super.getRealm();
    }
}

