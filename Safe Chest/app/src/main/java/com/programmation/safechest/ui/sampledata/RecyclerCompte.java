package com.programmation.safechest.ui.sampledata;

import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.programmation.safechest.R;
import com.programmation.safechest.sampledata.Compte;

import android.support.v7.widget.RecyclerView;


import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;


public class RecyclerCompte extends RealmRecyclerViewAdapter<Compte, RecyclerCompte.MyViewHolder> {

    public RecyclerCompte(OrderedRealmCollection<Compte> data) {
        super(data, true);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.compte_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Compte compte = getItem(position);
        holder.setCompte(compte);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        Compte mCompte;
        Button mButton;

        MyViewHolder(View compteView) {
            super(compteView);
            textView = compteView.findViewById(R.id.body);
            mButton = compteView.findViewById(R.id.button);
            mButton.setOnClickListener(this);
        }

        void setCompte(Compte compte) {
            this.mCompte = compte;
            this.textView.setText(compte.getCompteId());
        }

        @Override
        public void onClick(View dialogView) {
            /*String compteId = mCompte.getCompteId();
            EditText loginText = dialogView.findViewById(R.id.login);
            EditText PasswordText = dialogView.findViewById(R.id.password);
            EditText UrlText = dialogView.findViewById(R.id.url);
            new AlertDialog.Builder(MyViewHolder.this)
                    .setTitle("Add a new task")
                    .setMessage("What do you want to do next?")
                    .setView(dialogView)
                    .setPositiveButton("Add", (dialog, which) -> {
                        realm.executeTransactionAsync(realm -> {
                            Item item = new Item();
                            item.setBody(taskText.getText().toString());
                            realm.insert(item);
                        });
                    })
                    .setNegativeButton("Cancel", null)
                    .create()
                    .show();*/


        }



        }
    }

