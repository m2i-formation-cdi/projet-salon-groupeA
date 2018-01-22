package com.cdi.formation.salongroupea.model;

/**
 * Created by Formation on 22/01/2018.
 */

public class User {
    private String name;
    private String firstName;
    private String email;
    private String id;
    private Boolean isAdmin = false;

    public User() {
    }

    public User(String name, String firstName, String email, String id, Boolean isAdmin) {
        this.name = name;
        this.firstName = firstName;
        this.email = email;
        this.id = id;
        this.isAdmin = isAdmin;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setPrenom(String first_name) {
        this.firstName = first_name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public User setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
        return this;
    }
}
