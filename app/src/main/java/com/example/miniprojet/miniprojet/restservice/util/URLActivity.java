package com.example.miniprojet.miniprojet.restservice.util;

import android.app.Activity;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by steve on 08/01/16.
 */
public class URLActivity extends AsyncTask<String, String, String> {

    private String url;


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
            URL url = new URL(myurl);
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