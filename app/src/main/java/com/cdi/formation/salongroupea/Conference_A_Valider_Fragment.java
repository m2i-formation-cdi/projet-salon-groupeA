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


public class Conference_A_Valider_Fragment extends Fragment {

    //Création des attributs
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
    private Button buttonValidate;
    private Button buttonCancel;



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


                //Gestion du clic sur le bouton valider
        Button butonValidate = view.findViewById(R.id.buttonValidate);
        butonValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Récupération de la saisi de l'utilisateur
                conference.setDay(editTextDay.getText().toString());
                conference.setStartHour(editTextStartHour.getText().toString());
                conference.setLatitude(editTextLatitude.getText().toString());
                conference.setLongitude(editTextLongitude.getText().toString());

                String message = "La conférence a été validée !";
                Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
                toast.show();

                //Naviguer vers ListeConferencesEnAttente
                //InterfaceActivity activity = (InterfaceActivity) getActivity();
                //activity.navigateToFragment(new ListeConferencesEnAttente);

        //Gestion du clic sur le bouton Annuler
        Button buttonCancel = view.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = "Vous avez annulé la validation !";
                Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
                toast.show();
            }
            });

        //Naviguer vers ListeConferencesEnAttente
        //InterfaceActivity activity = (InterfaceActivity) getActivity();
        //activity.navigateToFragment(new ListeConferencesEnAttente);

        return view;

    }

}
