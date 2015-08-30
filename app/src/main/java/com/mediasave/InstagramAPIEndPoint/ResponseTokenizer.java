package com.mediasave.InstagramAPIEndPoint;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Amir on 8/29/2015.
 */
public class ResponseTokenizer {

    public static final String pagination_string = "pagination";
    public static final String data_string = "data";
    public static final String meta_string = "meta";
    private final String meta_code_string = "code";

    private String currentResponse;
    private JSONObject rawData;

    public ResponseTokenizer() {
    }



    /*
    The proccessResponse method tokenizes the CurrentResponse in order to get the information
    the whole process is done using JSON
    in the end we should make the current response null just for security reasons!
     */

    public JSONinfo proccessResponse() throws JSONException , IllegalArgumentException {

        System.out.println("starting to process the response...");

        if (currentResponse == null) {
            /*
            if the current response is null then whether no response
            is attached or the previous response is proccessed
            */
            throw new IllegalStateException("Response is null");
        }

        JSONinfo data = new JSONinfo();

        //first we should check whether the response was successful or not
        if (rawData.has(meta_string)) {
            JSONObject meta = rawData.getJSONObject(meta_string);
            int meta_code = meta.getInt(meta_code_string);

            // the main part of checking!
            if (meta_code != JSONinfo.successful_response_meta_code) {
                String detail = meta.getString("error_type")
                        + " : "
                        + meta.getString("error_message");
                throw new IllegalArgumentException(detail);
            }

            data.putInStorage(meta_string, meta);
        }

        if (rawData.has(pagination_string)) {
            JSONObject pagination = rawData.getJSONObject(pagination_string);
            data.putInStorage(pagination_string, pagination);
        }

        if (rawData.has(data_string)) {
            JSONObject dataObject = rawData.getJSONObject(data_string);
            data.putInStorage(data_string, dataObject);
        }

        currentResponse = null;
        rawData = null;
        System.out.println("response processing is done!");
        return data;
    }

    public void setCurrentResponse(String currentResponse) throws JSONException {
        this.currentResponse = currentResponse;
        rawData = new JSONObject(currentResponse);
    }
}
