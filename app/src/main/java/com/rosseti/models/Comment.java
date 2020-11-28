package com.rosseti.models;

public class Comment extends Object {

    private Integer id;

    private String text;

    private Integer user_id;

    private Integer suggestion_id;

    private String datetime;

    private Integer you;

    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getSuggestion_id() {
        return suggestion_id;
    }

    public void setSuggestion_id(Integer suggestion_id) {
        this.suggestion_id = suggestion_id;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Integer getYou() {
        return you;
    }

    public void setYou(Integer you) {
        this.you = you;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
