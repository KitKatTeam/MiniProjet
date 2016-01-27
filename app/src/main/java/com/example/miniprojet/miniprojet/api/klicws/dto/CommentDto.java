package com.example.miniprojet.miniprojet.api.klicws.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

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
