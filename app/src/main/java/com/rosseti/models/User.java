package com.rosseti.models;

public class User {

    private Integer id;

    private String full_name;

    private String phone;

    private Object topic_id;

    private Object email;

    private Integer comments_count;

    private Integer ratings_count;

    private Integer accepted_proposals_count;

    private Integer denied_proposals_count;

    private Integer proposals_count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(Object topic_id) {
        this.topic_id = topic_id;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Integer getComments_count() {
        return comments_count;
    }

    public void setComments_count(Integer comments_count) {
        this.comments_count = comments_count;
    }

    public Integer getRatings_count() {
        return ratings_count;
    }

    public void setRatings_count(Integer ratings_count) {
        this.ratings_count = ratings_count;
    }

    public Integer getAccepted_proposals_count() {
        return accepted_proposals_count;
    }

    public void setAccepted_proposals_count(Integer accepted_proposals_count) {
        this.accepted_proposals_count = accepted_proposals_count;
    }

    public Integer getDenied_proposals_count() {
        return denied_proposals_count;
    }

    public void setDenied_proposals_count(Integer denied_proposals_count) {
        this.denied_proposals_count = denied_proposals_count;
    }

    public Integer getProposals_count() {
        return proposals_count;
    }

    public void setProposals_count(Integer proposals_count) {
        this.proposals_count = proposals_count;
    }
}
