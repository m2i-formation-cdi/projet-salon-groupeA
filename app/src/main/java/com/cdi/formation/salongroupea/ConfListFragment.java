package com.cdi.formation.salongroupea;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cdi.formation.salongroupea.model.Comments;
import com.cdi.formation.salongroupea.model.Conference;
import com.cdi.formation.salongroupea.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
public class ConfListFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference confReference;
    private List<Conference> confList = new ArrayList<>();
    private List<Conference> filteredList = new ArrayList<>();
    private int selectedIndex;
    private ListView confListView;
    private ConfArrayAdapter adapter;
    private int color = 0;

    private Spinner spinner;
    private String [] theme = {"Toutes","Java", "Android", "PHP"};

    //private User currentUser = new User("tanghe", "vianney", "monmail@mail.com", "145789", false);
    public User currentUser = new User();

    public ConfListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Récupération de l'utilisateur connecté
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        //Affichage des infos utilisateur
        //Toast.makeText(this.getActivity(), "fbUser = "+ fbUser.toString() , Toast.LENGTH_SHORT).show();

        if (fbUser != null) {
            Log.i("FIREBASE_USER","il existe un utilisateur FBUSER");

            //if() {
            //Hydratation de l'objet user
            String[] name = fbUser.getDisplayName().split(" ");

            currentUser.setName(name[0]);
            currentUser.setFirstName(name[1]);
            currentUser.setEmail(fbUser.getEmail());
            currentUser.setUserId(fbUser.getUid());

            Log.i("CURRENT_USER","On cree le CURRENT_USER"+currentUser.getEmail());

            //Toast.makeText(this.getActivity(), this.currentUser.getEmail() , Toast.LENGTH_SHORT).show();
            //String userId = userReference.push().getKey();
        }
        firebaseDatabase = FirebaseDatabase.getInstance();
        confReference = firebaseDatabase.getReference().child("conference");
        View view = inflater.inflate(R.layout.fragment_conf_list, container, false);
        confListView = view.findViewById(R.id.confListView);
        //creation de la vue qui liste les livres
        adapter = new ConfArrayAdapter(this.getActivity(), filteredList);
        confListView.setAdapter(adapter);
        //recuperation des données avec abonnement au modif ulterieurs
        confReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //reinitialiser la list
                confList.clear();
                //boucler sur lensemble des noeuds
                for (DataSnapshot confSnap : dataSnapshot.getChildren()) {
                    String key = confSnap.getKey();
                    // Toast.makeText(this.getContext(), selectedConference.getTitle(), Toast.LENGTH_SHORT).show();
                    //creation d'une instance darticle et hydratation avec les données du snapshot
                    Conference conf = confSnap.getValue(Conference.class);
                    conf.setRefKey(key);
                    if (conf.getDay() != null) {
                        //ajout du livre a la liste
                        confList.add(conf);
                        filteredList.add(conf);
                    }
                }
                Log.d(" MAIN", "------------------- Fin de recuperation des données -----------------------");
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(" ERRORRRRRRRR", "------------------- erreur de database -----------------------");
            }
        });
        Log.d("MAIN", " ------------------------ Fin de onCreate() -------------------------------");


        spinner = view.findViewById(R.id.spinnerTheme);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item ,theme);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);
        spinner.setOnItemSelectedListener(this);

        return view;
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.selectedIndex = position;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.selectedIndex = position;

        String selection = theme[position];
        if(! selection.equals("Toutes")){
            filteredList.clear();
            for (int i = 0; i < confList.size(); i++) {
                Conference conf = confList.get(i);
                if (conf.getTheme().equalsIgnoreCase(selection)) {
                    filteredList.add(conf);
                }
            }
            adapter.notifyDataSetChanged();
        }
        else{
            filteredList.clear();
            for (int i = 0; i < confList.size(); i++) {
                Conference conf = confList.get(i);
                    filteredList.add(conf);
                }
            }
            adapter.notifyDataSetChanged();
        }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void navigateToFragment(NotationFragment targetFragment) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, targetFragment)
                .commit();
    }
}


