package com.example.miniprojet.miniprojet.api.klicws;



import android.util.Log;

import com.example.miniprojet.miniprojet.api.klicws.dto.UserDto;
import com.example.miniprojet.miniprojet.api.util.RestService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.collections.MultiHashMap;


import java.io.IOException;

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

public class UserAPI extends RestService {


    /**
     * Singleton
     */
    private static UserAPI instance = new UserAPI();
    private UserAPI(){}
    public static UserAPI getInstance(){
        instance.setSource("http://klic-dev.herokuapp.com/");
        return instance;
    }

    /**
     * Check if you can lock an user
     * @param email :  email for the user
     * @param password : password of the user
     * @return user
     */
    public UserDto login(String email, String password){

        MultiHashMap params = new MultiHashMap();
        params.put("email",email);
        params.put("password",password);
        String json = this.getService("login",params);

        UserDto userDTO = null;

        Log.d("eee",json);


        if (!json.equals("")){
            try {
                ObjectMapper mapper = new ObjectMapper();
                userDTO = mapper.readValue(json, UserDto.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return userDTO;

    }


    /**
     * For new user
     * @param email :  email for the user
     * @param password : password of the user
     * @return user
     */
    public UserDto subscribe(String username,String email, String password){

        MultiHashMap params = new MultiHashMap();
        params.put("username",username);
        params.put("email",email);
        params.put("password",password);
        String json = this.getService("subscription",params);

        UserDto userDTO = null;


        if (!json.equals("")){
            try {
                ObjectMapper mapper = new ObjectMapper();
                userDTO = mapper.readValue(json, UserDto.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return userDTO;

    }


}
