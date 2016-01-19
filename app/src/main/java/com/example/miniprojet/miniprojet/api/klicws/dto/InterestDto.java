package com.example.miniprojet.miniprojet.api.klicws.dto;


import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by steve on 19/01/16.
 */
public class InterestDto implements Serializable {

    private long id;

    private Date date;

    private String description;

    private List<CommentDto> comments;

    private Float positionX;

    private Float positionY;

    private List<TagDto> tags;


    public HashMap<String,String> toMap(){

        HashMap<String,String> params = new HashMap<>();

        if (this.getId() !=  0) {
            Long id = this.getId();
            params.put("id",id.toString());
        }
        if (this.getDescription() !=null) {
            params.put("description", this.getDescription());
        }
        if (this.getPositionX() !=null){
            params.put("positionX",this.getPositionX().toString());
        }
        if (this.getPositionY() !=null){
            params.put("positionY",this.getPositionY().toString());
        }

        return params;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }

    public Float getPositionX() {
        return positionX;
    }

    public void setPositionX(Float positionX) {
        this.positionX = positionX;
    }

    public Float getPositionY() {
        return positionY;
    }

    public void setPositionY(Float positionY) {
        this.positionY = positionY;
    }

    public List<TagDto> getTags() {
        return tags;
    }

    public void setTags(List<TagDto> tags) {
        this.tags = tags;
    }
}
