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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cdi.formation.salongroupea.model.Comments;
import com.cdi.formation.salongroupea.model.Conference;
import com.cdi.formation.salongroupea.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CommentFragment extends Fragment {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference confReference;
    private RatingBar rate;
    private RatingBar confRatingView;
    private EditText comment;
    private TextView title;
    private TextView speaker;
    private Conference conf = new Conference();
    private ListView commentListView;
    private ArrayAdapter adapter;
    private User currentUser;
    private List<Comments> commentsList;
    private TextView confSpeakerNameView;
    private TextView confSpeakerFirstNameView;
    private TextView confTitleView;

    public CommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("la liste des commentaire");

        MainActivity mainActivity = new MainActivity();
        Conference selectedConf = mainActivity.getSelectedConference();
        commentsList = selectedConf.getComments();

        final View view = inflater.inflate(R.layout.fragment_comment, container, false);

        commentListView = view.findViewById(R.id.commentListView);
        //creation de la vue qui liste les conferences
        adapter = new CommentArrayAdapter(this.getActivity(), R.layout.comm_list_item);
        commentListView.setAdapter(adapter);

        confTitleView = view.findViewById(R.id.confTitle);
        confSpeakerNameView = view.findViewById(R.id.confSpeakerName);
        confSpeakerFirstNameView = view.findViewById(R.id.confSpeakerFirstName);
        confRatingView = view.findViewById(R.id.ratingValue);
        String title = selectedConf.getTitle();
        String name = selectedConf.getSpeaker().getName();
        String firstName = selectedConf.getSpeaker().getFirstName();

        float confRate = getConfRating(view, selectedConf);

        confTitleView.setText(title);
        confSpeakerNameView.setText(name);
        confSpeakerFirstNameView.setText(firstName);
        confRatingView.setRating(confRate);

        // Inflate the layout for this fragment
        return view;
    }


    private class CommentArrayAdapter extends ArrayAdapter<Comments> {

        private Fragment context;
        int resource;
        List<Comments> data;

        public CommentArrayAdapter(@NonNull Context context, int resource) {

            super(getActivity(), R.layout.comm_list_item, commentsList);
            Log.i("COMMENT FRAGMENT", " ------------- Methode CommentArrayAdapter --------------");

        }

        @Nullable
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Log.i("COMMENT FRAGMENT", " ------------- Methode GetView --------------");

            View view = getActivity().getLayoutInflater().inflate(R.layout.comm_list_item, parent, false);

            TextView commentView = view.findViewById(R.id.comment);
            RatingBar ratingView = view.findViewById(R.id.rating);
            TextView userFirstNameView = view.findViewById(R.id.userFirstName);
            TextView userNameView = view.findViewById(R.id.userName);

            Comments com = commentsList.get(position);

            //Récupération de l'utilisateur connecté
            MainActivity mainActivity = new MainActivity();
            currentUser = mainActivity.getCurrentUser();

            commentView.setText(com.getMessage());
            ratingView.setRating((float) Float.parseFloat(com.getRate()));
            userFirstNameView.setText(currentUser.getFirstName());
            userNameView.setText(currentUser.getName());


            return view;
        }
    }

    private void navigateToFragment(ConfListFragment targetFragment) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, targetFragment)
                .commit();
    }

    private float getConfRating(View view, Conference currentConf) {
        float noteMoy = 0;
        // on calcul la note moyenne de la conference
        RatingBar rateConf = view.findViewById(R.id.ratingConf);
        float note = (float) 0.0;

        //LinearLayout confItem = view.findViewById(R.id.confItem);

        //color++;
        //if (color % 2 == 1) {
        //    confItem.setBackgroundColor(0x0f0F0f);
        //} else {
        //    confItem.setBackgroundColor(0x05F505);
        //}
//
        if (currentConf.getDay() != null && currentConf.getComments() != null) {
            for (Comments com : currentConf.getComments()) {

                Log.i("COM", "on a trouvé une note" + com.getRate() + "de la conf" + currentConf.title);
                note = note + (float) Float.parseFloat(com.getRate());

            }
            noteMoy = note / currentConf.getComments().size();
            Log.i("NOTE MOYENNE", String.valueOf(noteMoy));

        }
        return noteMoy;

    }

}
