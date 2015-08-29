package com.mediasave.InstagramAPIEndPoint;

import com.mediasave.sqltools.DatabaseManager;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ExecutionException;

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
    private GETSender getSender;

    public EndPoint() {
        this.tokenizer = new ResponseTokenizer();
    }

    public static EndPoint getInstance() {
        return instance;
    }

    public String[] getBasicInformationaOfUser(String username, String access_token) throws IOException, JSONException {
        //first of all initializing the result
        String[] res = new String[3];

        //getting the URL ready to send
        String urlTOsend = get_basic_info.replace("ACCESS-TOKEN", access_token);
        urlTOsend = urlTOsend.replace("{user-id}", username);

        //sending URL and reciving the response rom Instagram API
        //throws IOException
        getSender = new GETSender();
        getSender.execute(urlTOsend);
        String response = null;
        try {
            response = getSender.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //now we should proccess the response to get the information out of it
        //throws JSONException
        tokenizer.setCurrentResponse(response);
        JSONinfo response_info = tokenizer.proccessResponse();

        //now let's get the information from the 'data' class inside the JSONinfo
        res[DatabaseManager.USERNAME_INDEX] = (String) response_info.getValue(ResponseTokenizer.data_string, "username");
        res[DatabaseManager.PROFILE_PIC_URL_INDEX] = (String) response_info.getValue(ResponseTokenizer.data_string, "profile_picture");
        res[DatabaseManager.ACCESS_TOKEN_INDEX] = access_token;

        return res;
    }

    public void setUser(AuthenticatedUser user) {
        this.user = user;
    }

    public AuthenticatedUser getUser() {
        return user;
    }
}
