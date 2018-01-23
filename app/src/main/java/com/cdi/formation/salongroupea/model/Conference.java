package com.cdi.formation.salongroupea.model;


import java.util.List;

/**
 * Created by Formation on 22/01/2018.
 */
public class Conference {
    public String title;
    public String description;
    public String theme;
    public String day;
    public String startHour;
    public String location;
    public String latitude;
    public String longitude;
    public User speaker;
    public List<User> attendents;
    public List<Comments> comments;
    public String refKey;

    public Conference() {
    }

    public Conference(String title, String description, String theme, String day, String startHour, String location,
                      String latitude, String longitude, User speaker, List<User> attendents, List<Comments> comments) {
        this.title = title;
        this.description = description;
        this.theme = theme;
        this.day = day;
        this.startHour = startHour;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speaker = speaker;
        this.attendents = attendents;
        this.comments = comments;
    }

    public String getTitle() {
        return title;
    }

    //
    public Conference setTitle(String title) {
        this.title = title;
        return this;
    }

    //
    public String getDescription() {
        return description;
    }

    //
    public Conference setDescription(String description) {
        this.description = description;
        return this;
    }

    //
    public String getTheme() {
        return theme;
    }

    //
    public Conference setTheme(String theme) {
        this.theme = theme;
        return this;
    }

    //
    public String getDay() {
        return day;
    }

    //
    public Conference setDay(String day) {
        this.day = day;
        return this;
    }

    //
    public String getStartHour() {
        return startHour;
    }

    //
    public Conference setStartHour(String startHour) {
        this.startHour = startHour;
        return this;
    }

    //
    public String getLocation() {
        return location;
    }

    //
    public Conference setLocation(String location) {
        this.location = location;
        return this;
    }

    //
    public String getLatitude() {
        return latitude;
    }

    //
    public Conference setLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    //
    public String getLongitude() {
        return longitude;
    }

    //
    public Conference setLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    //
    public User getSpeaker() {
        return speaker;
    }

    //
    public Conference setSpeaker(User speaker) {
        this.speaker = speaker;
        return this;
    }

    //
    public List<User> getAttendents() {
        return attendents;
    }

    //
    public Conference setAttendents(List<User> attendents) {
        this.attendents = attendents;
        return this;
    }

    //
    public List<Comments> getComments() {
        return comments;
    }

    //
    public Conference setComments(List<Comments> comments) {
        this.comments = comments;
        return this;
    }

    public String getRefKey() {
        return refKey;
    }

    public Conference setRefKey(String refKey) {
        this.refKey = refKey;
        return this;
    }
}
