package com.example.miniprojet.miniprojet.api.klicws.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by loicleger on 16/01/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentDto implements Serializable {


	private long id;

	private String text;

	private InterestDto interest;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public InterestDto getInterest() {
		return interest;
	}

	public void setInterest(InterestDto interest) {
		this.interest = interest;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}




}
