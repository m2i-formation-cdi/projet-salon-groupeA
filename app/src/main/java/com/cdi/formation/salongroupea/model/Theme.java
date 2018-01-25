package com.cdi.formation.salongroupea.model;

/**
 * Created by Formation on 23/01/2018.
 */

public class Theme {
    private String themeName;

    public Theme(String theme) {
        this.themeName = theme;
    }

    public Theme() {
    }

    public String getThemeName() {
        return themeName;
    }

    public Theme setTheme(String theme) {
        this.themeName = theme;
        return this;
    }
}
