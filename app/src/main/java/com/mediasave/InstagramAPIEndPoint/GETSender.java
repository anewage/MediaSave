package com.mediasave.InstagramAPIEndPoint;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Amir on 8/29/2015.
 */
public class GETSender extends AsyncTask<String, Void, String> {


    // HTTPS GET request
    private String sendGet(String url) throws IOException {

        System.out.println("sending GET Request to : " + url);

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

        System.out.println("Got a response: " + response.toString());
        return response.toString();
    }

    @Override
    protected String doInBackground(String... params) {
        String response = null;
        try {
            response = sendGet(params[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
