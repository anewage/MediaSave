package com.mediasave.user;

import com.mediasave.databasetools.DatabaseManager;

/**
 * Created by Amir on 8/31/2015.
 */
public class AuthenticatedUser {

    private String username, profile_pic_url, access_token;

    public AuthenticatedUser(String [] args) {
        username = args[DatabaseManager.USERNAME_INDEX];
        profile_pic_url = args[DatabaseManager.PROFILE_PIC_URL_INDEX];
        access_token = args[DatabaseManager.ACCESS_TOKEN_INDEX];
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getUsername() {
        return username;
    }

    public String getProfile_pic_url() {
        return profile_pic_url;
    }
}
