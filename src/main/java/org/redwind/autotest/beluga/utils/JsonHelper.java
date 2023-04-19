package org.redwind.autotest.beluga.utils;

import io.restassured.response.Response;
import org.json.JSONObject;

public class JsonHelper {

    private JSONObject jsonObject;
    public String getJsonString(Response response, String jsonKey) {
        String result;
        jsonObject = new JSONObject(response.asString());
        result = jsonObject.getString(jsonKey);
        return result;
    }

    public Integer getIntegerFromJsonObj(Response response, String jsonObj, String jsonKey) {
        int result;
        jsonObject = new JSONObject(response.asString());
        result = jsonObject.getJSONObject(jsonObj).getInt(jsonKey);
        return result;
    }
    public String getStringFromJsonObj(Response response, String jsonObj, String jsonKey) {
        String result;
        jsonObject = new JSONObject(response.asString());
        result = jsonObject.getJSONObject(jsonObj).getString(jsonKey);
        return result;
    }

}
