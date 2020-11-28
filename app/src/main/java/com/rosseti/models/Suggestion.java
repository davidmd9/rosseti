package com.rosseti.models;

import java.util.List;

public class Suggestion {

    private Integer id;

    private Integer author_id;

    private String title;

    private Integer rating;

    private Integer topic_id;

    private String existing_solution_text;

    private String existing_solution_image;

    private String existing_solution_video;

    private String proposed_solution_text;

    private String proposed_solution_image;

    private String proposed_solution_video;

    private String positive_effect;

    private Integer status_id;

    private Author author;

    private Integer experted;

    private List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Integer author_id) {
        this.author_id = author_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(Integer topic_id) {
        this.topic_id = topic_id;
    }

    public String getExisting_solution_text() {
        return existing_solution_text;
    }

    public void setExisting_solution_text(String existing_solution_text) {
        this.existing_solution_text = existing_solution_text;
    }

    public String getExisting_solution_image() {
        return existing_solution_image;
    }

    public void setExisting_solution_image(String existing_solution_image) {
        this.existing_solution_image = existing_solution_image;
    }

    public String getExisting_solution_video() {
        return existing_solution_video;
    }

    public void setExisting_solution_video(String existing_solution_video) {
        this.existing_solution_video = existing_solution_video;
    }

    public String getProposed_solution_text() {
        return proposed_solution_text;
    }

    public void setProposed_solution_text(String proposed_solution_text) {
        this.proposed_solution_text = proposed_solution_text;
    }

    public String getProposed_solution_image() {
        return proposed_solution_image;
    }

    public void setProposed_solution_image(String proposed_solution_image) {
        this.proposed_solution_image = proposed_solution_image;
    }

    public String getProposed_solution_video() {
        return proposed_solution_video;
    }

    public void setProposed_solution_video(String proposed_solution_video) {
        this.proposed_solution_video = proposed_solution_video;
    }

    public String getPositive_effect() {
        return positive_effect;
    }

    public void setPositive_effect(String positive_effect) {
        this.positive_effect = positive_effect;
    }

    public Integer getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Integer status_id) {
        this.status_id = status_id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getExperted() {
        return experted;
    }

    public void setExperted(Integer experted) {
        this.experted = experted;
    }
}
