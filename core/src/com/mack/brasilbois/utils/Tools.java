package com.mack.brasilbois.utils;

import com.badlogic.gdx.Gdx;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class Tools {

    public static void printJson(JSONObject json) {
        Iterator<String> keys = json.keys();

        while(keys.hasNext()) {
            String key = keys.next();
            Gdx.app.log("key: ", key);
            try {
                Gdx.app.log("value: ",  json.get(key).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
