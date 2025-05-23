package com.blog.learning.repository.model;

import com.blog.learning.repository.model.common.Entity;
import com.faunadb.client.types.FaunaConstructor;
import com.faunadb.client.types.FaunaField;

import java.util.List;

/**
 * <p>A Post entity.</p>
 *
 * <p>It represents a simple Post, like the one in a Blog,
 * along with some basic attributes.</p>
 */
public class Post extends Entity {

    private String title;
    private List<String> tags;

    @FaunaConstructor
    public Post(
            @FaunaField("id") String id,
            @FaunaField("title") String title,
            @FaunaField("tags") List<String> tags) {
        this.id = id;
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
