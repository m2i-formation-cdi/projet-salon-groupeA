package com.cdi.formation.salongroupea;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.TextView;

import com.cdi.formation.salongroupea.model.Conference;
import com.cdi.formation.salongroupea.model.User;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public final int LOGIN_REQUESTCODE = 1;
    private FirebaseUser fbUser;
    private TextView userNameTextView;
    private TextView userEmailTextView;


    private static User currentUser;
    private static Conference selectedConference;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private DatabaseReference userReference;
    private FirebaseDatabase firebaseDatabase;
    private Spinner spinner;
    private int toto = 5;

    //Gestion de la référence Conférence pour le formulaire validation conf en attente


    public static Conference getSelectedConference() {
        return selectedConference;
    }

    public static void setSelectedConference(Conference selectedConference) {
        MainActivity.selectedConference = selectedConference;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        MainActivity.currentUser = currentUser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("MAIN ACTIVITY", " ------------- Methode OnCreate --------------");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firebaseDatabase = FirebaseDatabase.getInstance();
        userReference = firebaseDatabase.getReference().child("user");
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Bundle bundle = new Bundle();
        // bundle.putString("key", "value");
        //
        //
        //
        // ConfFragment.setArguments(bundle);
        //Reference aux textview dans l'entête de la navigation
        View headerView = ((NavigationView) navigationView.findViewById(R.id.nav_view)).getHeaderView(0);
        userEmailTextView = headerView.findViewById(R.id.headerUserEmail);
        userNameTextView = headerView.findViewById(R.id.headerUserName);


        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();


        if (fbUser != null) {
            userNameTextView.setText((fbUser.getDisplayName()));
            userEmailTextView.setText((fbUser.getEmail()));
            userReference.child(fbUser.getUid()).addListenerForSingleValueEvent( new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.i("MAIN ACTIVITY", " ------------- Methode onDataChange FIREBASE --------------");

                    if (dataSnapshot.exists()) {
                        setCurrentUser(dataSnapshot.getValue(User.class));

                        Log.i("USER NAME", "--------------------------------------" + getCurrentUser().getName());
                        Log.i("USER FIRSTNAME", "--------------------------------------" + getCurrentUser().getFirstName());
                        Log.i("USER EMAIL", "--------------------------------------" + getCurrentUser().getEmail());
                        Log.i("USER ID", "--------------------------------------" + getCurrentUser().getUserId());

                        if (currentUser.getIsAdmin() == true) {
                            navigationView.getMenu().findItem(R.id.validateConf).setVisible(true);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



            userReference.child(fbUser.getUid()).child("userId").setValue(fbUser.getUid());

            //Masquage du lien login
            Fragment ConfFragment = new ConfListFragment();
            navigateToFragment(ConfFragment);

            affichageLogInOut();

        }

    }

    @Override
    public void onBackPressed() {
        Log.i("MAIN ACTIVITY", " ------------- Methode onBackPressed --------------");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("MAIN ACTIVITY", " ------------- Methode onCreateOptionsMenu --------------");

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i("MAIN ACTIVITY", " ------------- Methode onOptionsItemSelected --------------");

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.i("MAIN ACTIVITY", " ------------- Methode onNavigationItemSelected --------------");

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.actionLogin) {
            navigateToFragment(new InscriptionFragment());
        } else if (id == R.id.actionLogout) {
        } else if (id == R.id.myConf) {
            navigateToFragment(new ConfListFragment());
        } else if (id == R.id.createConf) {
            navigateToFragment(new AddConfFragment());

        } else if (id == R.id.validateConf) {
            navigateToFragment(new FragmentValidConference());
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //lancement de la procédure d'authentification
    public void onLogin(MenuItem item) {
        Log.i("MAIN ACTIVITY", " ------------- Methode onLogin --------------");

        //Définition des fournisseurs d'authentification
        List<AuthUI.IdpConfig> providers = new ArrayList<>();
        providers.add(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build());
        //lancement de l'activité authentification
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers)
                .build(), LOGIN_REQUESTCODE);
    }

    //Résultat de l'intention
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("MAIN ACTIVITY", " ------------- Methode onActivityResult --------------");

        if (requestCode == LOGIN_REQUESTCODE) {
            //Récupération de la réponse
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                //Récupération de l'utilisateur connecté
                fbUser = FirebaseAuth.getInstance().getCurrentUser();
                //Affichage des infos utilisateur
                if (fbUser != null) {
                    userNameTextView.setText((fbUser.getDisplayName()));
                    userEmailTextView.setText((fbUser.getEmail()));

                    userReference.child(fbUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {

                                currentUser = dataSnapshot.getValue(User.class);
                                if (currentUser.getIsAdmin() == true) {
                                    navigationView.getMenu().findItem(R.id.validateConf).setVisible(true);
                                }
                            } else {
                                //Hydratation de notre objet User à partir de fbUser
                                hydrateUser();
                                //creer un nouvel utilisiteur dans la base
                                userReference.child(fbUser.getUid()).setValue(currentUser);

                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                affichageLogInOut();

                //Fermeture du drawer navView
                drawer.closeDrawer(GravityCompat.START);

            } else {
                if (response != null) {
                    Log.d("Main", " Erreur Fireauth code: " + response.getErrorCode());
                }
                Toast.makeText(this, "Impossible de vous identifier", Toast.LENGTH_SHORT).show();
            }
            Fragment ConfFragment = new ConfListFragment();
            navigateToFragment(ConfFragment);
        }
    }

    private void affichageLogInOut() {
        Log.i("MAIN ACTIVITY", " ------------- Methode affichageLogInOut --------------");

        //Masquage du lien login
        navigationView.getMenu().findItem(R.id.actionLogin).setVisible(false);
        //Affichage du lien logout
        navigationView.getMenu().findItem(R.id.actionLogout).setVisible(true);
    }

    private void hydrateUser() {
        //Hydratation de l'objet user
        String[] name = fbUser.getDisplayName().split(" ");
        currentUser.setName(name[0]);
        currentUser.setFirstName(name[1]);
        currentUser.setEmail(fbUser.getEmail());
        currentUser.setUserId(fbUser.getUid());

    }


    public void launchValidation(String confId) {
        navigateToFragment(new ConfValidationFragment());
    }

    public void onLogout(MenuItem item) {
        Log.i("MAIN ACTIVITY", " ------------- Methode onLogout --------------");

        AuthUI.getInstance().signOut(this).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //affichage du lien login
                        navigationView.getMenu().findItem(R.id.actionLogin).setVisible(true);
                        //masquage du lien logout
                        navigationView.getMenu().findItem(R.id.actionLogout).setVisible(false);
                        //Suppression des infos utilisateurs dans l'en tête
                        userNameTextView.setText("");
                        userEmailTextView.setText("");
                        navigationView.getMenu().findItem(R.id.validateConf).setVisible(false);
                        //fermeture du menu

                        drawer.closeDrawer((GravityCompat.START));
                        Fragment ConfFragment = new ConfListFragment();
                        navigateToFragment(ConfFragment);
                    }
                });
    }

    private void navigateToFragment(Fragment targetFragment) {
        Log.i("MAIN ACTIVITY", " ------------- Methode navigateToFragment --------------");

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, targetFragment)
                .commit();
    }
}
