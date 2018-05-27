package com.programmation.safechest;


import android.app.Application;
import android.widget.Toolbar;

import io.realm.Realm;

public class SafeChest extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}

