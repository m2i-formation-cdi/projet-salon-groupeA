package com.cdi.formation.salongroupea.model;



/**
 * Created by Formation on 22/01/2018.
 */

public class Comments {


    public String authorId;
    public String message;
    public String rate;


    public Comments() {
    }

    public Comments(String authorId, String message, String rate) {
        this.authorId = authorId;
        this.message = message;
        this.rate = rate;
    }

    public String getAuthorId() {
        return authorId;
    }

    public Comments setAuthorId(String authorId) {
        this.authorId = authorId;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Comments setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getRate() {
        return rate;
    }

    public Comments setRate(String rate) {
        this.rate = rate;
        return this;
    }
}
