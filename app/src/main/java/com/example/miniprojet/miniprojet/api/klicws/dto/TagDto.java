package com.example.miniprojet.miniprojet.api.klicws.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.apache.commons.collections.MultiHashMap;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by loicleger on 16/01/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TagDto implements Serializable  {

	private long id;

	private InterestDto interest;

	private String nom;

	private Float positionTopX;

	private Float positionTopY;

	private Float positionBottomX;


    private Float positionBottomY;

	private TypeLocation type;

	public MultiHashMap toMap(){

		MultiHashMap params = new MultiHashMap();

		if (this.getId() !=  0) {
			Long id = this.getId();
			params.put("id",id.toString());
		}
        if (this.getType() !=  null) {
            params.put("type",this.getType().toString());
        }
		if (this.getNom() !=null) {
			params.put("nom", this.getNom());
		}
		if (this.getPositionTopX() !=null){
			params.put("positionTopX",this.getPositionTopX().toString());
		}
		if (this.getPositionTopY() !=null){
			params.put("positionTopY",this.getPositionTopY().toString());
		}

		if (this.getPositionBottomX() !=null){
			params.put("positionBottomX",this.getPositionBottomX().toString());
		}

		if (this.getPositionBottomY() !=null){
			params.put("positionBottomY",this.getPositionBottomY().toString());
		}

		return params;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public InterestDto getInterest() {
		return interest;
	}

	public void setInterest(InterestDto interest) {
		this.interest = interest;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Float getPositionTopX() {
		return positionTopX;
	}

	public void setPositionTopX(Float positionTopX) {
		this.positionTopX = positionTopX;
	}

	public Float getPositionTopY() {
		return positionTopY;
	}

	public void setPositionTopY(Float positionTopY) {
		this.positionTopY = positionTopY;
	}

	public Float getPositionBottomX() {
		return positionBottomX;
	}

	public void setPositionBottomX(Float positionBottomX) {
		this.positionBottomX = positionBottomX;
	}

	public TypeLocation getType() {
		return type;
	}

	public void setType(TypeLocation type) {
		this.type = type;
	}

    public Float getPositionBottomY() {
        return positionBottomY;
    }

    public void setPositionBottomY(Float positionBottomY) {
        this.positionBottomY = positionBottomY;
    }

}
