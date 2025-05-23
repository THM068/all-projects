package com.blog.learning.repository.model;

import java.util.List;

/**
 * It contains all the necessary data, with the exception of
 * the Id, for creating or replacing a {@link Post} entity.
 */
public class CreateReplacePostData {

    private String title;
    private List<String> tags;

    public CreateReplacePostData(String title, List<String> tags) {
        this.title = title;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
