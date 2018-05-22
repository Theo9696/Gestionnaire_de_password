package com.programmation.safechest.ui.sampledata;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.programmation.safechest.sampledata.Compte;
import com.programmation.safechest.R;
import android.support.v7.widget.RecyclerView;


import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

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

        MyViewHolder(View compteView) {
            super(compteView);
            textView = compteView.findViewById(R.id.body);
        }

        void setCompte(Compte compte){
            this.mCompte = compte;
            this.textView.setText(compte.getCompteId());
        }

        @Override
        public void onClick(View v) {
            String compteId = mCompte.getCompteId();

            }
        }
    }

