package com.example.miniprojet.miniprojet.api.klicws.util;

import android.os.AsyncTask;

import org.apache.commons.collections.MultiHashMap;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by steve on 08/01/16.
 */
public class URLActivity extends AsyncTask<String, String, String> {

    private String url;

    private MultiHashMap params;

    public MultiHashMap getParams() {
        return params;
    }

    public void setParams(MultiHashMap params) {
        this.params = params;
    }

    public void setUrl(String url){
        this.url = url;
    }



    @Override
    protected String doInBackground(String... params) {

        String total = "";

        try {

            total = downloadUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return total;

    }



    // Given a URL, establishes an HttpUrlConnection and retrieves
    // the web page content as a InputStream, which it returns as
    // a string.
    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;



        try {

            StringBuilder postData = new StringBuilder();

            Set set = params.entrySet();
            Iterator i = set.iterator();
            while (i.hasNext()) {
                Map.Entry<String, List<String>> me = (Map.Entry) i.next();

                for(int j = 0 ; j< me.getValue().size(); j++ )
                {
                    postData.append(URLEncoder.encode(me.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(me.getValue().get(j), "UTF-8").replace(" ","%20"));
                    postData.append('&');
                }

            }

            String parama = postData.toString();
            if (!parama.equals("")){
                parama = "?"+parama;
            }

            URL url = new URL(myurl+parama);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);





            // Starts the query

            conn.connect();
            int response = conn.getResponseCode();

            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is);

            return contentAsString;


            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    // Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {

        java.util.Scanner s = new java.util.Scanner(stream).useDelimiter("\\A");
        String st = s.hasNext() ? s.next() : "";

        return new String(st);
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}