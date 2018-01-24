package com.cdi.formation.salongroupea;

import android.app.Fragment;

/**
 * Created by Formation on 23/01/2018.
 */
// Interface de stockage de la reference conference et navigation vers les fragments

public interface InterfaceActivity {
    public String getConfId();
    public void setConfId(String confId);

    public void navigateToFragment(Fragment targetFragment);

}
