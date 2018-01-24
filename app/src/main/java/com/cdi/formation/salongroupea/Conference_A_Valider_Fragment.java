package com.cdi.formation.salongroupea;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cdi.formation.salongroupea.model.Conference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Conference_A_Valider_Fragment extends Fragment {

    //Création des attributs
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference referenceBD;
    private TextView textViewTitleView;
    private TextView textViewThemeView;
    private TextView textViewLocationView;
    private TextView textViewTitleSpeakerNameView;
    private TextView textViewFirstNameView;
    private TextView textViewDescriptionView;
    private EditText editTextDay;
    private EditText editTextStartHour;
    private EditText editTextLatitude;
    private EditText editTextLongitude;
    private Conference conference;
    private String confId;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conference__a__valider_, container, false);

        //Instanciation des Attributs
        textViewTitleView = (TextView) view.findViewById(R.id.textViewTitleView);
        textViewThemeView  = (TextView) view.findViewById(R.id.textViewThemeView);
        textViewLocationView  = (TextView) view.findViewById(R.id.textViewLocationView);
        textViewTitleSpeakerNameView  = (TextView) view.findViewById(R.id.textViewTitleSpeakerNameView);
        textViewFirstNameView  = (TextView) view.findViewById(R.id.textViewFirstNameView);
        textViewDescriptionView  = (TextView) view.findViewById(R.id.textViewDescriptionView);
        editTextDay = (EditText) view.findViewById(R.id.editTextDay);
        editTextStartHour = (EditText) view.findViewById(R.id.editTextStartHour);
        editTextLatitude = (EditText) view.findViewById(R.id.editTextLatitude);
        editTextLongitude = (EditText) view.findViewById(R.id.editTextLongitude);
        conference = new Conference();

        //Connexion database
        firebaseDatabase = FirebaseDatabase.getInstance();
        //Update de la bd
        referenceBD = firebaseDatabase.getReference().child("conference");
        final MainActivity activity = (MainActivity) getActivity();
        //Récupération de la conférence via ID
        //confId = activity.getConfId();

        //test récupération des données de Conférence
        confId = "-L3Y5U4ztX31S7dmB-64";

        Button butonValidate = view.findViewById(R.id.buttonValidate);
        Button buttonCancel = view.findViewById(R.id.buttonCancel);

        if(confId != null) {
            referenceBD.child(confId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot confSnap : dataSnapshot.getChildren()) {
                        conference = confSnap.getValue(Conference.class);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });

            //Gestion du clic sur le bouton valider

            butonValidate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //Hydratation
                    conference.setDay(editTextDay.getText().toString());
                    conference.setStartHour(editTextStartHour.getText().toString());
                    conference.setLatitude(editTextLatitude.getText().toString());
                    conference.setLongitude(editTextLongitude.getText().toString());

                    referenceBD.child(confId).setValue(conference);

                    String message = "La conférence a été validée !";
                    Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
                    toast.show();

                    //Naviguer vers ListeConferencesEnAttente
                    // activity.navigateToFragment(new ListeConferencesEnAttente());
                }
            });
            //Gestion du clic sur le bouton Annuler
            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String message = "Vous avez annulé la validation !";
                    Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
                    toast.show();

                    //Naviguer vers ListeConferencesEnAttente
                    //activity.navigateToFragment(new ListeConferencesEnAttente());
                }
            });

        }else {
                butonValidate.setVisibility(View.INVISIBLE);
        }

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = "Vous avez annulé la validation !";
                Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
                toast.show();

                //Naviguer vers ListeConferencesEnAttente
                //activity.navigateToFragment(new ListeConferencesEnAttente());
            }
        });

        return view;
    }
}