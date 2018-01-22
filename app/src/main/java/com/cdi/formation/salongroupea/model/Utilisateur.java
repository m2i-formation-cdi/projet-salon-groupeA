package com.cdi.formation.salongroupea.model;

/**
 * Created by Formation on 22/01/2018.
 */

public class Utilisateur {
    private String name;
    private String firstName;
    private String email;
    private String id;
    private String isAdmin = "user";

    public Utilisateur() {
    }

    public Utilisateur(String nom, String prenom, String email, String id, String isAdmin) {
        this.name = nom;
        this.firstName = prenom;
        this.email = email;
        this.id = id;
        this.isAdmin = isAdmin;
    }

    public String getNom() {
        return name;
    }

    public Utilisateur setNom(String name) {
        this.name = name;
        return this;
    }

    public String getPrenom() {
        return firstName;
    }

    public Utilisateur setPrenom(String first_name) {
        this.firstName = first_name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Utilisateur setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getId() {
        return id;
    }

    public Utilisateur setId(String id) {
        this.id = id;
        return this;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public Utilisateur setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
        return this;
    }
}
