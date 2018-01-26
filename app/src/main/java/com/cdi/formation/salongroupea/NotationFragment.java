package com.cdi.formation.salongroupea;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.cdi.formation.salongroupea.model.Comments;
import com.cdi.formation.salongroupea.model.Conference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotationFragment extends Fragment {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference confReference;
    private RatingBar rate;
    private EditText comment;
    private Conference conf = new Conference();

    public NotationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Noter la conférence");

        View view = inflater.inflate(R.layout.fragment_notation, container, false);
        rate = view.findViewById(R.id.ratingValue);
        comment = view.findViewById(R.id.editComment);

        Button button = view.findViewById(R.id.addComment);

        Bundle b = getArguments();
        String confKey = b.get("ConfKey").toString();
        String selectedUser = b.get("SelectedUser").toString();

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child("conference").child(confKey).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        conf = dataSnapshot.getValue(Conference.class);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );

        //Ajouter une note et un commentaire appel du fragment NotationFragment
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Float rateVal = rate.getRating();
                String commentVal = comment.getText().toString();

                Comments com = new Comments();

                Bundle b = getArguments();
                String confKey = b.get("ConfKey").toString();
                String selectedUser = b.get("SelectedUser").toString();

                com.setAuthorId(selectedUser);
                com.setMessage(commentVal);
                com.setRate(String.valueOf(rateVal));

                if (conf.getComments() == null) {
                    List<Comments> listCom = new ArrayList<Comments>();
                    listCom.add(com);
                    conf.setComments(listCom);
                } else {
                    conf.addComment(com);
                }

                firebaseDatabase = FirebaseDatabase.getInstance();
                firebaseDatabase.getReference().child("conference").child(confKey).setValue(conf);

                Log.i("ECRITURE DANS LA BASE", " ------------------------------- un commentaire a ete ajouté ---------- " );

                Bundle bundle = new Bundle();
                bundle.putString("rateVal", String.valueOf(rateVal));
                bundle.putString("commentVal", commentVal);

                NotationFragment notation = new NotationFragment();
                notation.setArguments(bundle);

                ConfListFragment confListFragment = new ConfListFragment();
                navigateToFragment(confListFragment);

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void navigateToFragment(ConfListFragment targetFragment) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, targetFragment)
                .commit();
    }

}
