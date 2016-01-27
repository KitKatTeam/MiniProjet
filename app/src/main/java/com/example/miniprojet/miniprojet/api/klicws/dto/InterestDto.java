package com.example.miniprojet.miniprojet.api.klicws.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.apache.commons.collections.MultiHashMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Copyright (C) 2016 Chaloupy Julien, Leger Loic, Magras Steeve

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along
 with this program; if not, write to the Free Software Foundation, Inc.,
 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class InterestDto implements Serializable {

    private Long id;

    private Date date;

    private String description;

    private List<CommentDto> comments;

    private Float positionX;

    private Float positionY;

    private List<TagDto> tags;

    private String image;

    private Long userId;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public MultiHashMap toMap(){

        MultiHashMap params = new MultiHashMap();

        if (this.getId() !=  null) {
            params.put("id",this.getId().toString());
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
        if (this.getPositionY() !=null){
            params.put("image",this.getImage());
        }
        if (this.getUserId() !=null){
            params.put("userId",this.getUserId().toString());
        }


        return params;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public boolean containsTagsName(List<TagDto> tagsName) {
        List<String> tagsNameComparable = new ArrayList<String>();
        for (TagDto tag :
                tagsName) {
            tagsNameComparable.add(tag.getNom());
        }
        return !Collections.disjoint(this.tags, tagsName);
    }

}
