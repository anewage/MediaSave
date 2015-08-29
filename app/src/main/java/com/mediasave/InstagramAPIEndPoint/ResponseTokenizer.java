package com.mediasave.InstagramAPIEndPoint;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Amir on 8/29/2015.
 */
public class ResponseTokenizer {

    private String currentResponse;
    private JSONObject jsonObject;

    public ResponseTokenizer () {
    }



    /*
    The proccessResponse method tokenizes the CurrentResponse in order to get the information
    the whole process is done using JSON
    in the end we should make the current response null just for security reasons!
     */

    public void proccessResponse() {
        if (currentResponse == null)
            return;
        if (jsonObject.has("pagination")){

        }

        currentResponse = null;
    }

    public void setCurrentResponse(String currentResponse) throws JSONException {
        this.currentResponse = currentResponse;
        jsonObject = new JSONObject(currentResponse);
    }
}
