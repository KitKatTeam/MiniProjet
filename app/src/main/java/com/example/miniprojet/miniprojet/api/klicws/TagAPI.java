package com.example.miniprojet.miniprojet.api.klicws;

import com.example.miniprojet.miniprojet.api.klicws.dto.TagDto;
import com.example.miniprojet.miniprojet.api.util.RestService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.collections.MultiHashMap;

import java.io.IOException;
import java.util.List;

/**
 * Created by steve on 23/01/16.
 */
public class TagAPI  extends RestService {


    /**
     * Singleton
     */
    private static TagAPI instance = new TagAPI();
    private TagAPI(){}
    public static TagAPI getInstance(){
        instance.setSource("http://klic-dev.herokuapp.com/");
        return instance;
    }

    /**
     * Get tag by id
     * @param idInterest
     * @return
     */
    public TagDto get(Long idInterest) {

        MultiHashMap params = new MultiHashMap();

        String json = this.getService("/tag/"+idInterest.toString(),params);
        TagDto result = null;

        if (!json.equals("")){
            try {
                ObjectMapper mapper = new ObjectMapper();
                result = mapper.readValue(json,TagDto.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return result;

    }

    /**
     * Get all unique tag (no doublon)
     * @return
     */
    public List<String> getAllUnique() {

        MultiHashMap params = new MultiHashMap();

        String json = this.getService("tag/allUnique",params);

        List<String> result = null;

        if (!json.equals("")){
            try {
                ObjectMapper mapper = new ObjectMapper();
                result = mapper.readValue(json,mapper.getTypeFactory().constructCollectionType(List.class, String.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return result;

    }


}
