package com.cdi.formation.salongroupea;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
 * A simple {@link Fragment} subclass.
 */
public class FragmentValidConference extends Fragment {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference confReference;
    private List<Conference> confListSource = new ArrayList<>();
    private List<Conference> confList = new ArrayList<>();
    private ListView lvConference;


    public FragmentValidConference() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_valid_conference, container, false);
        lvConference = (ListView) view.findViewById(R.id.lvConference);


        // charger les données dans une liste
        // et liaison avec l'écouteur

        firebaseDatabase = FirebaseDatabase.getInstance();
        confReference = firebaseDatabase.getReference().child("conference");

        confReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //reinitialiser la list
                confList.clear();
                //boucler sur lensemble des noeuds
                for (DataSnapshot confSnap : dataSnapshot.getChildren()) {
                    //creation d'une instance darticle et hydratation avec les données du snapshot
                    Conference conf = confSnap.getValue(Conference.class);
                    //ajout du livre a la liste
                    confListSource.add(conf);
                }
                // adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(" ERRORRRRRRRR", "------------------- erreur de database -----------------------");
            }
        });
        Toast.makeText(getActivity(),confListSource.get(0).getTitle(),Toast.LENGTH_LONG).show();

        // affecter les informations à la listView

        // créer un listener pour pouvoir envoyer la conférence à valiuder


        // aiguillage vers la validation avec les informations nécessaires : id conference
        return view;
    }

    private class ConfArrayAdapter extends ArrayAdapter<Conference> {

        private android.app.Fragment context;
        int resource;
        List<Conference> data;

        public ConfArrayAdapter(@NonNull Context context, int resource) {
            super(getActivity(), R.layout.conf_list_item, confList);
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            //    LayoutInflater inflater = this.context.getLayoutInflater();
            //    final View view = inflater.inflate(R.layout.details, parent, false);

            View view = getActivity().getLayoutInflater().inflate(R.layout.conf_list_item, parent, false);
            Conference currentConf = confList.get(position);
            TextView textView = view.findViewById(R.id.confListText);
            textView.setText(currentConf.title.toString());


            return view;
        }
    }
}
