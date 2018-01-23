package com.cdi.formation.salongroupea;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.cdi.formation.salongroupea.dummy.DummyContent;
import com.cdi.formation.salongroupea.dummy.DummyContent.DummyItem;
import com.cdi.formation.salongroupea.model.Conference;
import com.cdi.formation.salongroupea.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * interface.
 */
public class ListeConferencesEnAttente extends Fragment {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference conferencesReference;
    private List<Conference> conferencesList = new ArrayList<>();
    private ListView conferencesListView;
    private ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseDatabase = FirebaseDatabase.getInstance();
        conferencesReference = firebaseDatabase.getReference().child("conférences");

        conferencesList = findViewById(R.id.text_list_view);
        adapter = new BookArrayAdapter(this, R.layout.fragment_conference__a__valider_, conferencesList);
        conferencesList.setAdapter(adapter);

        //Récupération des données avec abonnement aux modifications ultérieures
        conferencesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Réinitialisation de la liste des livres
                conferencesList.clear();

                //Boucle sur l'ensemble des noeuds
                for(DataSnapshot bookSnapshot : dataSnapshot.getChildren()){
                    //Création d'une instance de Book et hydratation avec les données du snapshot
                    Conference conference = bookSnapshot.getValue(Conference.class);
                    //ajout du livre à la liste
                    conferencesList.add(conference);
                }

                Log.d("MAIN", "Fin de récupération des données");
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Log.d("MAIN", "Fin de onCreate");

        //addConferences();
    }

    private void addConferences() {
        //Création d'une conference

        User antoine = new User("Antoine", "BALU", "balu@yahoo.fr", "1",false );
        User andre = new User("André", "MAIGA", "maiga@gmail.com", "2", false);

        String conferenceId = conferencesReference.getKey();
        Conference conference = new Conference("Apprendre Java","Cours de framework", antoine,);
        conferencesReference.child(conferenceId).setValue(conference);
    }

    private class BookArrayAdapter extends ArrayAdapter<Conference>{

        private Activity context;
        int resource;
        List<Conference> data;

        public BookArrayAdapter(Activity context, int resource, List<Conference> data) {
            super(context, resource, data);
            this.context = context;
            this.resource = resource;
            this.data = data;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = context.getLayoutInflater().inflate(
                    this.resource, parent, false);

            Conference currentConference = conferencesList.get(position);
            TextView textView = view.findViewById(R.id.confListView);
            textView.setText(
                    currentConference.getTitle()
                            +  " par "
                            + currentConference.getUser().getName()
            );

            return view;
        }
    }
}
