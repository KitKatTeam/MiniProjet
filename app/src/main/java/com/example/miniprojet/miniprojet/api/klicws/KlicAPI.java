package com.example.miniprojet.miniprojet.api.klicws;

import com.example.miniprojet.miniprojet.api.klicws.dto.TagDto;
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
