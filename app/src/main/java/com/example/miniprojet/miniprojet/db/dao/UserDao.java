package com.example.miniprojet.miniprojet.db.dao;

import android.content.ContentValues;
import android.content.Context;

import com.example.miniprojet.miniprojet.db.pojo.User;

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

public class UserDao extends DaoBase {

    public UserDao(Context pContext) {
        super(pContext);
    }

    public static final String TABLE_NAME = "user";
    public static final String KEY = "id";
    public static final String PRENOM = "prenom";
    public static final String NOM = "nom";
    public static final String EMAIL = "email";
    public static final String DATE_INSCRIPTION = "dateInscription";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PRENOM + " TEXT, " + NOM + " TEXT, "+
            USERNAME + " TEXT, "+ EMAIL + " TEXT, " + PASSWORD + " TEXT, " +
            DATE_INSCRIPTION + " TEXT);";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    /**
     * @param u le métier à ajouter à la base
     */
    public void ajouter(User u) {
        ContentValues value = new ContentValues();
        value.put(PRENOM, u.getPrenom());
        value.put(NOM, u.getNom());
        value.put(EMAIL, u.getEmail());
        value.put(DATE_INSCRIPTION, u.getDateInscription().toString());
        value.put(USERNAME, u.getUsername());
        value.put(PASSWORD, u.getPassword());
        mDb.insert(TABLE_NAME, null, value);
    }

    /**
     * @param id l'identifiant du métier à supprimer
     */
    public void supprimer(long id) {

    }

    /**
     * @param u le métier modifié
     */
    public void modifier(User u) {

    }

    /**
     * @param id l'identifiant du métier à récupérer
     */
    public User selectionner(long id) {
        return new User();
    }
}
