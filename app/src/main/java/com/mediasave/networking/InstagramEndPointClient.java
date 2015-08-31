package com.mediasave.networking;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mediasave.user.AuthenticatedUser;

/**
 * Created by Amir on 8/30/2015.
 */
public class InstagramEndPointClient {

    private final String access_token = "access_token";

    private AuthenticatedUser user;

    private static InstagramEndPointClient instance;

    private InstagramEndPointClient() {
    }

    public static InstagramEndPointClient getInstance() {
        if (instance == null) {
            instance = new InstagramEndPointClient();
        }
        return instance;
    }

    public void getFeed(RequestParams params, JsonHttpResponseHandler handler) {
        if (params == null){
            params = new RequestParams();
        }
        params.put(access_token, getAccess_token());
        InstagramEndPoint.get(InstagramEndPoint.SELF_FEED_URL, params, handler);
    }

    public void getUserBasicInfo(String access_token, JsonHttpResponseHandler handler) {
        InstagramEndPoint.get(
                InstagramEndPoint.SELF_BASIC_INFO_URL,
                new RequestParams("access_token", access_token),
                handler);
    }

    public String getAccess_token() {
        return user.getAccess_token();
    }

    public String getUsername() {
        return user.getUsername();
    }

    public String getUserProfilePictureURL() {
        return user.getProfile_pic_url();
    }

    public void setUser(AuthenticatedUser user) {
        this.user = user;
    }
}
