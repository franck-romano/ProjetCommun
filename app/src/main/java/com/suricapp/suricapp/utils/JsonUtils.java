package com.suricapp.suricapp.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Franck on 18/03/15.
 */
    public class JsonUtils {
        public static JSONObject StringToJSON(String var) throws JSONException {
            JSONArray array = new JSONArray(var);
            JSONObject e = array.getJSONObject(0);
            return e;
        }
    }

