package com.cdi.formation.salongroupea.model;

import java.util.List;

/**
 * Created by Formation on 22/01/2018.
 */

public class Conference {
    private String title;
    private String description;
    private String theme;
    private String day;
    private String startHour;
    private String location;
    private String latitude;
    private String longitude;
    User Speaker;
    List<User> attendents;
    List<Comment> comments;
    private User user;

    public Conference() {
    }

    public Conference(String title, String description, String theme, String day, String startHour, String location, String latitude, String longitude, User speaker, List<User> attendents, List<Comment> comments) {
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public Conference setTitle(String title) {
        this.title = title;
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

    public User getSpeaker() {
        return Speaker;
    }

    public Conference setSpeaker(User speaker) {
        Speaker = speaker;
        return this;
    }

    public List<User> getAttendents() {
        return attendents;
    }

    public Conference setAttendents(List<User> attendents) {
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

    public User getUser() {
        return user;
    }
}
