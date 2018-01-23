package com.cdi.formation.salongroupea;

import android.app.Fragment;
import android.content.Context;
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
public class ConfListFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference confReference;
    private List<Conference> confList = new ArrayList<>();
    private int selectedIndex;
    private ListView confListView;
    private ConfArrayAdapter adapter;

    private User currentUser = new User("tanghe", "vianney", "monmail@mail.com", "145789", false);

    public ConfListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        firebaseDatabase = FirebaseDatabase.getInstance();
        confReference = firebaseDatabase.getReference().child("conference");
        View view = inflater.inflate(R.layout.fragment_conf_list, container, false);

        confListView = view.findViewById(R.id.confListView);

        //creation de la vue qui liste les livres
        adapter = new ConfArrayAdapter(this.getActivity(), R.layout.conf_list_item);
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

                    //ajout du livre a la liste
                    confList.add(conf);
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
        ((ViewGroup) confListView.getParent()).removeView(confListView);
        // Inflate the layout for this fragment
        return confListView;
    }

    private class ConfArrayAdapter extends ArrayAdapter<Conference> {

        private Fragment context;
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

            //Récupération de l'utilisateur sur lequel on vient de cliquer
            Conference selectedConference = confList.get(position);

            new User("tanghe", "vianney", "monmail@mail.com", "145789", false);

            View view = getActivity().getLayoutInflater().inflate(R.layout.conf_list_item, parent, false);
            final Conference currentConf = confList.get(position);
            TextView textView = view.findViewById(R.id.confListText);
            textView.setText(currentConf.title.toString());

            Button button = view.findViewById(R.id.buttonRegister);

            if (currentUser != null) {
                button.setEnabled(true);
            }
            Conference conferenceItem = confList.get(position);
            //Boolean foundUser = false;
            if(conferenceItem.attendents!= null){
            for (User user1 : conferenceItem.attendents) {
                if (user1.getUserId().equals(currentUser.getUserId())) {
                    button.setEnabled(false);
                    break;
                }
            }}
            //Log.i("USERID", firebaseDatabase.getReference("conference/" + conferenceItem.getRefKey() + "/attendents/"+currentUser.getUserId()).toString());

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Récupération de l'utilisateur sur lequel on vient de cliquer
                    Conference selectedConference = confList.get(position);

                    new User("tanghe", "vianney", "monmail@mail.com", "145789", false);

                    selectedConference.getAttendents().add(currentUser);

                    Toast.makeText(getContext(), selectedConference.getTitle() + "key =" + selectedConference.getRefKey(), Toast.LENGTH_SHORT).show();
                    //    Log.i("Action :", "Isncritpion");
                    //  Log.i("titre :", selectedConference.getTitle());
                    //  Log.i("prenom :", selectedConference.getAttendents().get(position).getFirstName());
                    //  Log.i("nom :", selectedConference.getAttendents().get(position).getName());

                    //String conf = confReference.child("attendents").push().getKey();

                    // confReference.child("attendents").setValue(selectedConference);
                    firebaseDatabase.getReference().child("conference").child(selectedConference.getRefKey()).setValue(selectedConference);

                }

            });
            return view;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.selectedIndex = position;
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.selectedIndex = position;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


