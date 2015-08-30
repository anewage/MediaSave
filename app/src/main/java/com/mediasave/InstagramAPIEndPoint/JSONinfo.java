package com.mediasave.InstagramAPIEndPoint;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amir on 8/29/2015.
 */
public class JSONinfo {

    public static final int successful_response_meta_code = 200;

    /*
    This is where the data from response is stored
     */

    private Map<String, JSONObject> storage;

    public JSONinfo () {
        storage = new HashMap<String, JSONObject>();
    }

    public void putInStorage(String key, JSONObject object) {
        storage.put(key, object);
    }

    public Object getValue(String JSONObjectName,String key) throws JSONException {
        JSONObject object = getJSONObject(JSONObjectName);
        Object result = object.get(key);
        return result;
    }

    private JSONObject getJSONObject(String key) {
        return storage.get(key);
    }

}
