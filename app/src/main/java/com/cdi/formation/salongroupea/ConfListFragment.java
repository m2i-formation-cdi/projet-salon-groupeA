package com.cdi.formation.salongroupea;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cdi.formation.salongroupea.model.Conference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfListFragment extends Fragment {


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference convReference;
    private List<Conference> confList = new ArrayList<>();
    private ListView convListView;
    private ConfArrayAdapter adapter;






    public ConfListFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        firebaseDatabase = FirebaseDatabase.getInstance();
        convReference = firebaseDatabase.getReference().child("conference");

        View view = inflater.inflate(R.layout.fragment_conf_list, container, false);

        convListView = view.findViewById(R.id.confListView);
        //creation de la vue qui liste les livres
        adapter = new ConfArrayAdapter(this.getContext(), R.layout.conf_list_item );
        convListView.setAdapter(adapter);
        //recuperation des données avec abonnement au modif ulterieurs
        convReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //reinitialiser la list
                confList.clear();
                //boucler sur lensemble des noeuds
                for (DataSnapshot convSnap : dataSnapshot.getChildren()) {
                    //creation d'une instance darticle et hydratation avec les données du snapshot
                    Conference conf = convSnap.getValue(Conference.class);
                    //ajout du livre a la liste
                    confList.add(conf);
                }
                Log.d(" MAIN", "------------------- Fin de recuperation des données -----------------------");
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        Log.d("MAIN", "Fin de onCreate()");




        // Inflate the layout for this fragment
        return convListView;
    }

    private class ConfArrayAdapter extends ArrayAdapter<Conference> {

        private Fragment context;
        int resource;
        List<Conference> data;

        public ConfArrayAdapter(@NonNull Context context, int resource) {
            super(context, resource);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = context.getLayoutInflater().inflate(this.resource, parent, false);
            Conference currentConf = confList.get(position);
            TextView textView = view.findViewById(R.id.confListText);
            textView.setText(currentConf.getTitle());
            return view;
        }
    }

}
