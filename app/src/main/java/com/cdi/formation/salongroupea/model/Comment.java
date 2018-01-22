package com.cdi.formation.salongroupea.model;

/**
 * Created by Formation on 22/01/2018.
 */

public class Comment {


    private String authorId;
    private String message;
    private String rate;

    public Comment() {
    }

    public Comment(String authorId, String message, String rate) {
        this.authorId = authorId;
        this.message = message;
        this.rate = rate;
    }

    public String getAuthorId() {
        return authorId;
    }

    public Comment setAuthorId(String authorId) {
        this.authorId = authorId;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Comment setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getRate() {
        return rate;
    }

    public Comment setRate(String rate) {
        this.rate = rate;
        return this;
    }
}
