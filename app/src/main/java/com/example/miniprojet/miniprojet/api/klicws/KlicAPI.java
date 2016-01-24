package com.example.miniprojet.miniprojet.api.klicws;

import com.example.miniprojet.miniprojet.api.klicws.dto.TagDto;
import com.example.miniprojet.miniprojet.api.util.RestService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.collections.MultiHashMap;

import java.io.IOException;

/**
 * Created by steve on 23/01/16.
 */
public class KlicAPI extends RestService {


    /**
     * Singleton
     */
    private static KlicAPI instance = new KlicAPI();
    private KlicAPI(){}
    public static KlicAPI getInstance(){
        instance.setSource("http://klic-dev.herokuapp.com/");
        return instance;
    }

    /**
     * Load the api
     * @return
     */
    public Boolean init() {

        MultiHashMap params = new MultiHashMap();

        String json = this.getService("",params);

        return !json.equals("");

    }
}
