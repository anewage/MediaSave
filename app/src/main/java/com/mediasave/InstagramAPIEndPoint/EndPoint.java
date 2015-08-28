package com.mediasave.InstagramAPIEndPoint;

/**
 * Created by Amir on 8/29/2015.
 */
public class EndPoint {

    /*
    This class is a singleton class and it only has one instance!
    Why is that? that's becuase different activites use it and it's kinda like a database.
    This is the connect point where all the data goes back and forth via Instagram's server.
     */

    private static EndPoint instance = new EndPoint();

    private AuthenticatedUser user;

    public EndPoint () {
    }

    public static EndPoint getInstance() {
        return instance;
    }

    public void setUser(AuthenticatedUser user) {
        this.user = user;
    }

    public AuthenticatedUser getUser() {
        return user;
    }
}
