package com.mediasave.networking;

import android.widget.ProgressBar;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Amir on 8/31/2015.
 */
public class ResponseHandler extends JsonHttpResponseHandler {

    private JSONObject response;
    private ProgressBar item;

    public ResponseHandler(ProgressBar item) {
        this.item = item;
    }

    @Override
    public void onProgress(long bytesWritten, long totalSize) {
        if (item != null){
            item.setMax((int) totalSize);
            item.setProgress((int) bytesWritten);
        }
        System.out.println("progress: " + getRequestURI().toString() + "\t" + bytesWritten + "/" + totalSize + "...");
    }

    @Override
    public void onSuccess(int statusCode, @SuppressWarnings("deprecation") Header[] headers, JSONObject response) {
        System.out.println("got the response!");
        System.out.println("response count is : " + response.length());
        try {
            JSONArray data = response.getJSONArray("data");
            System.out.println(data.length());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.response = response;
    }

    //// TODO: 8/31/2015 :OnFailures must be completely implemented to check the network errors

    @Override
    public void onFailure(int statusCode, @SuppressWarnings("deprecation") Header[] headers, String responseString, Throwable throwable) {
        System.out.println("failed on failure 1 :" + statusCode + " , " + responseString);
    }

    @Override
    public void onFailure(int statusCode, @SuppressWarnings("deprecation") Header[] headers, Throwable throwable, JSONObject errorResponse) {
        System.out.println("failed on failure 2 :" + statusCode + " , " + errorResponse.toString());
    }

    @Override
    public void onFailure(int statusCode, @SuppressWarnings("deprecation") Header[] headers, Throwable throwable, JSONArray errorResponse) {
        System.out.println("failed on failure 3 :" + statusCode + " , " + errorResponse.toString());
    }

    public JSONObject getDataObject() throws JSONException {
        return response.getJSONObject("data");
    }

    public JSONArray getDataArray() throws JSONException {
        return response.getJSONArray("data");
    }

    public JSONObject getResponse() {
        return response;
    }
}
