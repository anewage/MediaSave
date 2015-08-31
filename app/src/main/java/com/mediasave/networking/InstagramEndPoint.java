package com.mediasave.networking;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by Amir on 8/30/2015.
 */
public class InstagramEndPoint {
    public static final String BASE_URL = "https://api.instagram.com/v1/";
    public static final String SELF_BASIC_INFO_URL = "users/self/";
    public static final String SELF_FEED_URL = "users/self/feed";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        System.out.println("sending GET request to instagram API...");
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
