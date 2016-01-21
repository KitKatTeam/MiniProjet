package com.example.miniprojet.miniprojet.api.klicws;



import com.example.miniprojet.miniprojet.api.klicws.dto.UserDto;
import com.example.miniprojet.miniprojet.api.klicws.util.RestService;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by steve on 17/01/16.
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

        HashMap<String,String> params = new HashMap<String,String>();
        params.put("email",email);
        params.put("password",password);
        String json = this.getService("login",params);

        UserDto userDTO = null;

        if (!json.equals("")){
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(
                        DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                userDTO = mapper.readValue(json, UserDto.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return userDTO;

    }


}
