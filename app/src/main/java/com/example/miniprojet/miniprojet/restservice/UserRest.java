package com.example.miniprojet.miniprojet.restservice;



import android.util.Log;

import com.example.miniprojet.miniprojet.db.pojo.User;
import com.example.miniprojet.miniprojet.restservice.util.RestService;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by steve on 17/01/16.
 */
public class UserRest  extends RestService {


    /**
     * Singleton
     */
    private static UserRest instance = new UserRest();
    private UserRest(){}
    public static UserRest getInstance(){
        instance.setSource("http://klic-dev.herokuapp.com/");
        return instance;
    }

    /**
     * Check if you can lock an user
     * @param email :  email for the user
     * @param password : password of the user
     * @return user
     */
    public User login(String email, String password){

        HashMap<String,String> params = new HashMap<String,String>();
        params.put("email",email);
        params.put("password",password);
        String json = this.getService("login",params);

        User userDTO = null;
        Log.d("eeeeeeeeeeee ",json);
        if (!json.equals("")){
            try {
                userDTO = new ObjectMapper().readValue(json, User.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return userDTO;

    }


}
