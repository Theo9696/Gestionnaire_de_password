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
import android.widget.Toast;


import io.realm.OrderedRealmCollection;
import io.realm.Realm;
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
        holder.setCompte(compte, position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView urlView;
        TextView logView;
        Compte mCompte;
        Button mButton;

        MyViewHolder(View compteView) {
            super(compteView);
            urlView = compteView.findViewById(R.id.url);
            logView = compteView.findViewById(R.id.log);
            mButton = compteView.findViewById(R.id.button);
            mButton.setOnClickListener(this);
        }

        void setCompte(Compte compte, int position) {
            this.mCompte = compte;
            this.urlView.setText(compte.getURL());
            this.logView.setText(compte.getCompteLogin() + " / " + compte.getPassword());
        }

        @Override
        public void onClick(View view) {
            View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_task, null);

            EditText loginText = dialogView.findViewById(R.id.login);
            EditText PasswordText = dialogView.findViewById(R.id.password);
            EditText UrlText = dialogView.findViewById(R.id.url);

            loginText.setText(mCompte.getCompteLogin());
            PasswordText.setText(mCompte.getPassword());
            UrlText.setText(mCompte.getURL());

            new AlertDialog.Builder(view.getContext())
                    .setTitle("Votre Mémo")
                    .setMessage("Changez vos entrées")
                    .setView(dialogView)
                    .setPositiveButton("Écraser", (dialog, which) -> {
                        Realm realm = mCompte.getRealm();

                        realm.executeTransactionAsync(new Realm.Transaction() {
                            @Override
                            public void execute(Realm bgRealm) {
                                try {
                                    Compte compte = bgRealm.where(Compte.class).equalTo("compteId", mCompte.getCompteId()).findFirst();
                                    compte.setPassword(PasswordText.getText().toString());
                                    compte.setURL(UrlText.getText().toString());
                                    compte.setLogin(loginText.getText().toString());
                                }
                                catch (Exception e){
                                    Toast.makeText(view.getContext(), "error modifying...", Toast.LENGTH_SHORT).show();
                                }
                            }
                            }, new Realm.Transaction.OnSuccess() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(view.getContext(), "Enregistré", Toast.LENGTH_SHORT).show();
                            }
                        });
                    })
                    .setNegativeButton("Annuler", null)
                    .create()
                    .show();
        }
    }
}

