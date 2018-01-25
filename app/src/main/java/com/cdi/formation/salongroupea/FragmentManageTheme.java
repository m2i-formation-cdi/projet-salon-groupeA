package com.cdi.formation.salongroupea;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentManageTheme extends Fragment {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference confReference;

    public FragmentManageTheme() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manage_theme, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        confReference = firebaseDatabase.getReference().child("conference");
        return view;
    }

}
