package com.example.miniprojet.miniprojet.db.pojo;

import java.sql.Date;
import java.util.Collection;

/**
 * Created by loicleger on 16/01/16.
 */
public class Interest {

    private TypeLocation.Type type;
    private Date date;
    private String description;
    private Collection<Tag> tags;
    private Collection<Comment> comments;

    public TypeLocation.Type getType() {
        return type;
    }

    public void setType(TypeLocation.Type type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
