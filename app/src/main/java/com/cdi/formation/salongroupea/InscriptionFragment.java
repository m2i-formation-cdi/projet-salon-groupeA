package com.cdi.formation.salongroupea;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class InscriptionFragment extends Fragment {

    MainActivity parentActivity;
    EditText userNameEditText;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inscription, container, false);

        //Recuperation d'une référence
        userNameEditText = view.findViewById(R.id.editTextInscription);

        //recuperation d'une référence à l'activité
        parentActivity=(MainActivity) getActivity();


        return view;
    }


}
