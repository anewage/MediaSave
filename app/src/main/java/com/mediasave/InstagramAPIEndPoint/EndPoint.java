package com.mediasave.InstagramAPIEndPoint;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Amir on 8/29/2015.
 */
public class EndPoint {

    private final String get_basic_info = "https://api.instagram.com/v1/users/{user-id}/?access_token=ACCESS-TOKEN";

    /*
    This class is a singleton class and it only has one instance!
    Why is that? that's becuase different activites use it and it's kinda like a database.
    This is the connect point where all the data goes back and forth via Instagram's server.
     */

    private static EndPoint instance = new EndPoint();

    private AuthenticatedUser user;
    private ResponseTokenizer tokenizer;

    public EndPoint() {
        this.tokenizer = new ResponseTokenizer();
    }

    public static EndPoint getInstance() {
        return instance;
    }

    // HTTPS GET request
    private String sendGet(String url) throws IOException {
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    public String[] getBasicInformationaOfUser(String username , String access_token) throws IOException, JSONException {
        //first of all initializing the result
        String[] res = new String[3];

        //getting the URL ready to send
        String urlTOsend = get_basic_info.replace("ACCESS-TOKEN",access_token);
        if (username != null){
            urlTOsend = urlTOsend.replace("{user-id}", username);
        } else {
            urlTOsend = urlTOsend.replace("{user-id}", "self");
        }

        //sending URL and reciving the response rom Instagram API
        //throws IOException
        String response = sendGet(urlTOsend);

        //now we should proccess the response to get the information out of it
        //throws JSONException
        tokenizer.setCurrentResponse(response);

        tokenizer.proccessResponse();

        return res;
    }

    public void setUser(AuthenticatedUser user) {
        this.user = user;
    }

    public AuthenticatedUser getUser() {
        return user;
    }
}
