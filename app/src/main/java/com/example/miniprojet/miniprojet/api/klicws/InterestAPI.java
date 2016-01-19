package com.example.miniprojet.miniprojet.api.klicws;




import com.example.miniprojet.miniprojet.api.klicws.dto.InterestDto;
import com.example.miniprojet.miniprojet.api.klicws.dto.TagDto;
import com.example.miniprojet.miniprojet.api.klicws.util.RestService;
import com.example.miniprojet.miniprojet.db.pojo.Interest;


import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by steve on 17/01/16.
 */
public class InterestAPI extends RestService {


    /**
     * Singleton
     */
    private static InterestAPI instance = new InterestAPI();
    private InterestAPI(){}
    public static InterestAPI getInstance(){
        instance.setSource("http://klic-dev.herokuapp.com/");
        return instance;
    }


    /**
     * Get tags list for an interest
     * @param idInterest
     * @return
     */
    public List<TagDto> getTags(Long idInterest) {

        HashMap<String,String> params = new HashMap<String,String>();

        String json = this.getService("interest/"+idInterest.toString()+"/tags",params);
        List<TagDto> result = null;

        if (!json.equals("")){
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(
                        DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                result = mapper.readValue(json,mapper.getTypeFactory().constructCollectionType(List.class, TagDto.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return result;

    }


    /**
     * Add a tag to an interest
     * @param idInterest
     * @param tag
     * @return
     */
    public Interest addTag( Long idInterest, TagDto tag) {

        HashMap<String,String> params = tag.toMap();
        String json = this.getService("interest/"+idInterest+"/addTag",params);

        Interest result = null;

        if (!json.equals("")){
            try {
                ObjectMapper mapper = new ObjectMapper();

                mapper.configure(
                        DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                result = mapper.readValue(json,Interest.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * Delete interest by id
     * @param id
     * @return
     */
    public Boolean delete(long id) {

        HashMap<String,String> params = new HashMap<>();

        Long idL = id;

        params.put("id",idL.toString());
        String json = this.getService("interest/delete",params);

        Boolean result = null;

        if (!json.equals("")){
            try {
                ObjectMapper mapper = new ObjectMapper();

                mapper.configure(
                        DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                result = mapper.readValue(json,Boolean.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;

    }


    /**
     * Get interest by id
     * @param id
     * @return
     */
    public InterestDto get(long id) {

        HashMap<String,String> params = new HashMap<>();

        String json = this.getService("interest/"+id,params);

        InterestDto interest = null;

        if (!json.equals("")){
            try {
                ObjectMapper mapper = new ObjectMapper();

                mapper.configure(
                        DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                interest = mapper.readValue(json,InterestDto.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return interest;
    }


    /**
     * Update interest (currently non working)
     * @param interest
     * @return
     */
    public InterestDto update(InterestDto interest) {

        HashMap<String,String> params = interest.toMap();

        String json = this.getService("interest/update",params);

        interest = null;

        if (!json.equals("")){
            try {
                ObjectMapper mapper = new ObjectMapper();

                mapper.configure(
                        DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                interest = mapper.readValue(json,InterestDto.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return interest;
    }

    /**
     * Add a new interest
     * @param interest
     * @return
     */
    public InterestDto add(InterestDto interest) {

        HashMap<String,String> params = interest.toMap();

        String json = this.getService("interest/add",params);

        interest = null;

        if (!json.equals("")){
            try {
                ObjectMapper mapper = new ObjectMapper();

                mapper.configure(
                        DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                interest = mapper.readValue(json,InterestDto.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return interest;
    }

    /**
     * Get all interests
     * @return
     */
    public List<InterestDto> getAll() {

        HashMap<String,String> params = new HashMap<String,String>();

        String json = this.getService("interest/all",params);
        List<InterestDto> interests = null;

        if (!json.equals("")){
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(
                        DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                interests = mapper.readValue(json,mapper.getTypeFactory().constructCollectionType(List.class, InterestDto.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return interests;

    }


}
