package com.rosseti.models;

import java.util.List;

public class Comments extends BaseModel {

    private List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
