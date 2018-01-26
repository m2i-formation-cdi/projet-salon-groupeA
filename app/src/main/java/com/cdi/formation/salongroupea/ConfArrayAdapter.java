package com.cdi.formation.salongroupea;

import android.app.Activity;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cdi.formation.salongroupea.model.Comments;
import com.cdi.formation.salongroupea.model.Conference;
import com.cdi.formation.salongroupea.model.User;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Formation on 26/01/2018.
 */

public class ConfArrayAdapter extends ArrayAdapter<Conference> {

    private FirebaseDatabase firebaseDatabase;
    private Activity context;
    int resource;
    List<Conference> list;
    private List<Conference> filteredList = new ArrayList<>();
    public User currentUser = new User();
    private List<Conference> confList = new ArrayList<>();
    private LayoutInflater inflater;

    public ConfArrayAdapter(@NonNull Context context, List<Conference> list) {
        super(context,0, list);
        //Creation du constructeur
        this.context= (Activity) context;
        this.resource = resource;
        this.list = list;
        this.inflater = this.context.getLayoutInflater();


    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = inflater.inflate(R.layout.conf_list_item, parent, false);
        final Conference currentConf = list.get(position);
        TextView textView = view.findViewById(R.id.confListText);
        TextView nbCom = view.findViewById(R.id.numberComments);

        // on calcul la note moyenne de la conference
        RatingBar rateConf = view.findViewById(R.id.ratingConf);
        float note = (float) 0.0;

        LinearLayout confItem = view.findViewById(R.id.confItem);


        //color++;
        //if (color % 2 == 1) {
        //    confItem.setBackgroundColor(0x0f0F0f);
        //} else {
        //    confItem.setBackgroundColor(0x05F505);
        //}
//
        if (currentConf.getDay() != null && currentConf.getComments() != null) {
            for (Comments com : currentConf.getComments()) {

                Log.i("COM", "on a trouvé une note" + com.getRate() + "de la conf" + currentConf.getRefKey());
                note = note + (float) Float.parseFloat(com.getRate());

            }
            float noteMoy = note / currentConf.getComments().size();
            Log.i("NOTE MOYENNE", String.valueOf(noteMoy));
            rateConf.setRating(noteMoy);

            nbCom.setText(String.valueOf(currentConf.getComments().size()));

        }

        textView.setText(currentConf.getTitle());

        //recuperation des boutons
        Button button = view.findViewById(R.id.buttonRegister);
        Button notation = view.findViewById(R.id.buttonRating);
        final ImageView image = view.findViewById(R.id.getMap);

        //Toast.makeText(getActivity(), currentUser.getEmail() , Toast.LENGTH_SHORT).show();

        if (currentUser.getUserId() != null) {
            Log.i("CURRENT_USER", "il existe un utilisateur AUTH, activation du bouton..." + currentUser.getName());
            button.setEnabled(true);
            notation.setEnabled(true);
            Conference conferenceItem = list.get(position);
            //Boolean foundUser = false;
            if (conferenceItem.attendents != null && conferenceItem.getComments() != null ) {
                //Verification que l'utilisateur authentifié est inscrit a la conf
                for (User user1 : conferenceItem.attendents) {
                    if (user1.getUserId().equals(currentUser.getUserId())) {
                        button.setEnabled(false);
                        break;
                    }
                }

                //Verification que l'utilisateur authentifié a deja mis une note a la conf
                for (Comments com1 : conferenceItem.comments) {
                    if (com1.getAuthorId().equals(currentUser.getUserId())) {
                        notation.setEnabled(false);
                        break;
                    }
                }
            }
        }else       {  Log.i("CURRENT_USER","On a pas trouvé de CURRENT_USER");}

        //Log.i("USERID", firebaseDatabase.getReference("conference/" + conferenceItem.getRefKey() + "/attendents/"+currentUser.getUserId()).toString());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Récupération de l'utilisateur sur lequel on vient de cliquer
                Conference selectedConference = list.get(position);
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
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //map
                Conference selectedConference = list.get(position);
                //Création d'un intention pour l'affichage de la carte
                Intent mapIntention = new Intent(image.getContext(), MapsActivity.class);
                //Passage des paramètres
                mapIntention.putExtra("latitude", Double.valueOf(selectedConference.getLatitude()));
                mapIntention.putExtra("longitude", Double.valueOf(selectedConference.getLongitude()));
                mapIntention.putExtra("title", selectedConference.getTitle());
                mapIntention.putExtra("theme", selectedConference.getTheme());
                mapIntention.putExtra("day", selectedConference.getDay());
                mapIntention.putExtra("startHour", selectedConference.getStartHour());
                //Affichage de l'activité
                image.getContext().startActivity(mapIntention);
            }
        });

        //Ajouter une note et un commentaire appel du fragment NotationFragment
        notation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //map
                Conference selectedConference = list.get(position);
                //Création d'un intention pour l'affichage de la carte

                Bundle bundle = new Bundle();
                bundle.putString("ConfKey", selectedConference.getRefKey());
                bundle.putString("SelectedUser", currentUser.getUserId());

                NotationFragment notation = new NotationFragment();
                notation.setArguments(bundle);



            }
        });

        return view;
    }


}
