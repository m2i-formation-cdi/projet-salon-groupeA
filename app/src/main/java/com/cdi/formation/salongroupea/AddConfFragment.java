package com.cdi.formation.salongroupea;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cdi.formation.salongroupea.model.Conference;
import com.cdi.formation.salongroupea.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddConfFragment extends Fragment {
    // DrawerActivity parentActivity;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ConfReference;
    private Conference conference = new Conference();
    private User user;
    private EditText title;
    private EditText description;
    private Spinner spTheme;
    private TextView name;
    private Button btnValid;
    private Button btnCancel;
    private int titi;
    private User currentUser = new User();

    public AddConfFragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Création conférence");
        //Récupération de l'utilisateur connecté
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        //Affichage des infos utilisateur
        //Toast.makeText(this.getActivity(), "fbUser = "+ fbUser.toString() , Toast.LENGTH_SHORT).show();

        //Activity activity = (MainActivity) getActivity();

        if (fbUser != null) {
            Log.i("FIREBASE_USER", "il existe un utilisateur FBUSER");

            //if() {
            //Hydratation de l'objet user
            String[] name = fbUser.getDisplayName().split(" ");
            Log.i("CURRENT USER","nom : "+ name[0]+" et prenom : "+name[1] );
            currentUser.setName(name[0]);
            currentUser.setFirstName(name[1]);
            currentUser.setEmail(fbUser.getEmail());
            currentUser.setUserId(fbUser.getUid());

            Log.i("CURRENT_USER", "On cree le CURRENT_USER" + currentUser.getEmail());

            //Toast.makeText(this.getActivity(), this.currentUser.getEmail() , Toast.LENGTH_SHORT).show();
            //String userId = userReference.push().getKey();
        }


        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_conference, container, false);

        // Initialisation du spinner contenant les thèmes
        String[] valSpinner = {"", "PHP", "Android", "Java", "Manger des nouilles"};

        Spinner s = (Spinner) view.findViewById(R.id.spTheme);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, valSpinner);
        s.setAdapter(adapter);
        //Liaison des champs du layout avec les variables

        title = (EditText) view.findViewById(R.id.edtTitle);
        spTheme = (Spinner) view.findViewById(R.id.spTheme);
        description = (EditText) view.findViewById((R.id.edtDescription));


        name = (TextView) view.findViewById(R.id.tvName);
        // à remplaceer
        name.setText(currentUser.getName());


        // Création d'un listener sur le bouton de validation
        btnValid = (Button) view.findViewById(R.id.btnValid);
        btnValid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  si les saisies sont Ok, ajoute la conférence
                if (isInputOk()) {
                    addConference();
                    navigateToFragment(new ConfListFragment());
                }
            }
        });

        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    navigateToFragment(new ConfListFragment());
            }
        });


        return view;
    }

    public Boolean isInputOk() {
        Boolean ok = true;
        String message = "";
        if (title.getText().toString().equals("")) {
            message = "Titre";
        }
        if (spTheme.getSelectedItem().toString().equals("")) {
            message = message + " Thème";
        }
        if (description.getText().toString().equals("")) {
            message = message + " Descriptif";
        }

        if (message.length() > 1) {
            Toast.makeText(getActivity(), "Zone(s) manquant(es) à renseigner :" + message + " !", Toast.LENGTH_LONG).show();
            ok = false;
        }
        return ok;
    }

    public void addConference() {

        try {
            firebaseDatabase = FirebaseDatabase.getInstance();
            ConfReference = firebaseDatabase.getReference().child("conference");

            // Hydratation de la conférence
            conference.setTitle(title.getText().toString());
            conference.setTheme(spTheme.getSelectedItem().toString());
            conference.setDescription(description.getText().toString());

            conference.setSpeaker(currentUser);

            // Chargement des données
            String confId = ConfReference.push().getKey();
            conference.setRefKey(confId); //sauvegarde de lid de la conf dans l'objet conference

            ConfReference.child(confId).setValue(conference);

            Toast.makeText(getActivity(), "Enregistrement de la conférence effectuée!", Toast.LENGTH_LONG).show();
            initZones();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Anomalie lors de l'enregistrement !" + e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }


    public void initZones() {
        title.setText("");
        spTheme.setSelection(0);
        description.setText("");

    }
    private void navigateToFragment(Fragment targetFragment) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, targetFragment)
                .commit();
    }

}
