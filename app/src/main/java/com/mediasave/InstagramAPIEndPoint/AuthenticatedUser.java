package com.mediasave.InstagramAPIEndPoint;

import com.mediasave.sqltools.DatabaseManager;

/**
 * Created by Amir on 8/28/2015.
 */
public class AuthenticatedUser {

    private String username, access_token, profile_pic_URL;

    public AuthenticatedUser (String [] args) {
        username = args[DatabaseManager.USERNAME_INDEX];
        access_token = args[DatabaseManager.ACCESS_TOKEN_INDEX];
        profile_pic_URL = args[DatabaseManager.PROFILE_PIC_URL_INDEX];
    }

}
