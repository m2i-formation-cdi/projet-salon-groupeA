package com.cdi.formation.salongroupea.model;

import java.util.List;

/**
 * Created by Formation on 22/01/2018.
 */

public class Conference {
    private String titre;
    private String description;
    private String theme;
    private String day;
    private String startHour;
    private String location;
    private String latitude;
    private String longitude;
    Utilisateur Speaker;
    List<Utilisateur> attendents;
    List<Comment> comments;

    public Conference() {
    }

    public Conference(String titre, String description, String theme, String day, String startHour, String location, String latitude, String longitude, Utilisateur speaker, List<Utilisateur> attendents, List<Comment> comments) {
        this.titre = titre;
        this.description = description;
        this.theme = theme;
        this.day = day;
        this.startHour = startHour;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        Speaker = speaker;
        this.attendents = attendents;
        this.comments = comments;
    }

    public String getTitre() {
        return titre;
    }

    public Conference setTitre(String titre) {
        this.titre = titre;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Conference setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getTheme() {
        return theme;
    }

    public Conference setTheme(String theme) {
        this.theme = theme;
        return this;
    }

    public String getDay() {
        return day;
    }

    public Conference setDay(String day) {
        this.day = day;
        return this;
    }

    public String getStartHour() {
        return startHour;
    }

    public Conference setStartHour(String startHour) {
        this.startHour = startHour;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public Conference setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getLatitude() {
        return latitude;
    }

    public Conference setLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public String getLongitude() {
        return longitude;
    }

    public Conference setLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public Utilisateur getSpeaker() {
        return Speaker;
    }

    public Conference setSpeaker(Utilisateur speaker) {
        Speaker = speaker;
        return this;
    }

    public List<Utilisateur> getAttendents() {
        return attendents;
    }

    public Conference setAttendents(List<Utilisateur> attendents) {
        this.attendents = attendents;
        return this;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public Conference setComments(List<Comment> comments) {
        this.comments = comments;
        return this;
    }
}
