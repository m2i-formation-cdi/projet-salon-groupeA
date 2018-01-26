package com.cdi.formation.salongroupea;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
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
    private int selectedIndex;
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
        textViewThemeView = (TextView) view.findViewById(R.id.textViewThemeView);
        textViewLocationView = (TextView) view.findViewById(R.id.textViewLocationView);
        textViewTitleSpeakerNameView = (TextView) view.findViewById(R.id.textViewTitleSpeakerNameView);
        textViewFirstNameView = (TextView) view.findViewById(R.id.textViewFirstNameView);
        textViewDescriptionView = (TextView) view.findViewById(R.id.textViewDescriptionView);
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

        //On recupere le Bundle de Fragment appellant
        Bundle b = getArguments();
        String refKey = b.get("RefKey").toString();


        //Récupération de la conférence via ID
        //confId = activity.getConfId();

        confId = refKey;
        //test récupération des données de Conférence
        // confId = "-L3Y5U4ztX31S7dmB-64";

        Button butonValidate = view.findViewById(R.id.buttonValidate);
        Button buttonCancel = view.findViewById(R.id.buttonCancel);

        if (confId != null) {
            referenceBD.child(confId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    conference = dataSnapshot.getValue(Conference.class);
                    Log.i("DATA SNAPSHOT", conference.getTitle() + " " + conference.getRefKey());

                    /*
                    for (DataSnapshot confSnap : dataSnapshot.getChildren()) {
                        conference = confSnap.getValue(Conference.class);
                    }
                    */

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });



            String title = b.get("Title").toString();
            String theme = b.get("Theme").toString();
            String description = b.get("Description").toString();
             refKey = b.get("RefKey").toString();
            String selectedUser = b.get("SelectedUser").toString();

            Log.i("AFFICHAGE DU TITRE ", title + " et le Key : " + refKey);
            textViewTitleView.setText(title);

            Log.i("AFFICHAGE DU THEME ", theme + " et le Key : " + refKey);
            textViewThemeView.setText(theme);

            Log.i("AFFIC DE LA DESCR", description + " et le Key : " + refKey);
            textViewDescriptionView.setText(description);

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
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();


                    ConfListFragment ConfFragment = new ConfListFragment();
                    navigateToFragment(ConfFragment);

                }
            });


        } else {
            butonValidate.setVisibility(View.INVISIBLE);
        }
//Gestion du clic sur le bouton Annuler
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = "Vous avez annulé la validation !";
                Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
                toast.show();

                FragmentValidConference ConfFragment = new FragmentValidConference();
                navigateToFragment(ConfFragment);
            }
        });

        return view;
    }


    private void navigateToFragment(Fragment targetFragment) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, targetFragment)
                .commit();
    }

}