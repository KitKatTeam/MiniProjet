package com.example.miniprojet.miniprojet.api.klicws;




import com.example.miniprojet.miniprojet.api.klicws.dto.InterestDto;
import com.example.miniprojet.miniprojet.api.klicws.dto.TagDto;
import com.example.miniprojet.miniprojet.api.util.RestService;
import com.example.miniprojet.miniprojet.db.pojo.Interest;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.apache.commons.collections.MultiHashMap;


import java.io.IOException;
import java.util.List;


/**
 * Created by steve on 17/01/16.
 */
public class InterestAPI extends RestService {


    /**
     * Singleton
     */
    private static InterestAPI instance = null;
    private InterestAPI(){}
    public static InterestAPI getInstance(){
        if(instance == null) {
            instance = new InterestAPI();
            instance.setSource("http://klic-dev.herokuapp.com/");
        }
        return instance;
    }


    /**
     * Get tags list for an interest
     * @param idInterest
     * @return
     */
    public List<TagDto> getTags(Long idInterest) {

        MultiHashMap params = new MultiHashMap();

        String json = this.getService("interest/"+idInterest.toString()+"/tags",params);
        List<TagDto> result = null;

        if (!json.equals("")){
            try {
                ObjectMapper mapper = new ObjectMapper();
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
    public InterestDto addTag( Long idInterest, TagDto tag) {

        MultiHashMap params = tag.toMap();
        String json = this.getService("interest/"+idInterest+"/addTag",params);

        InterestDto result = null;

        if (!json.equals("")){
            try {
                ObjectMapper mapper = new ObjectMapper();
                result = mapper.readValue(json,InterestDto.class);
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

        MultiHashMap params = new MultiHashMap();

        Long idL = id;

        params.put("id",idL.toString());
        String json = this.getService("interest/delete",params);

        Boolean result = null;

        if (!json.equals("")){
            try {
                ObjectMapper mapper = new ObjectMapper();
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

        MultiHashMap params = new MultiHashMap();

        String json = this.getService("interest/"+id,params);

        InterestDto interest = null;

        if (!json.equals("")){
            try {
                ObjectMapper mapper = new ObjectMapper();
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

        MultiHashMap params = interest.toMap();

        String json = this.getService("interest/update",params);

        interest = null;

        if (!json.equals("")){
            try {
                ObjectMapper mapper = new ObjectMapper();
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

        MultiHashMap params = interest.toMap();

        String json = this.getService("interest/add",params);

        interest = null;

        if (!json.equals("")){
            try {
                ObjectMapper mapper = new ObjectMapper();
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

        MultiHashMap params = new MultiHashMap();

        String json = this.getService("interest/all",params);
        List<InterestDto> interests = null;

        if (!json.equals("")){
            try {
                ObjectMapper mapper = new ObjectMapper();
                interests = mapper.readValue(json,mapper.getTypeFactory().constructCollectionType(List.class, InterestDto.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return interests;

    }




    /**
     * Get all interests coresponding to tags
     * @return
     */
    public List<InterestDto> findByTags(List<String> keys) {

        MultiHashMap params = new MultiHashMap();

        String listParams = "";

        for (String s : keys){
            params.put("keys",s);
        }


        String json = this.getService("interest/getByTags",params);
        List<InterestDto> interests = null;

        if (!json.equals("")){
            try {
                ObjectMapper mapper = new ObjectMapper();
                interests = mapper.readValue(json,mapper.getTypeFactory().constructCollectionType(List.class, InterestDto.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return interests;

    }



}
