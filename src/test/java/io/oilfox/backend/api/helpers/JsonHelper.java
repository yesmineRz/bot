package io.oilfox.backend.api.helpers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

import java.util.ArrayList;

/**
 * Created by ipusic on 1/21/16.
 */
public class JsonHelper {

    public static ArrayList<String> bodyAsArray(HttpResponse<JsonNode> response) {

        ArrayList<String> list = new ArrayList<>();

        JsonNode result = response.getBody();
        for (int i = 0; i < result.getArray().length(); i++) {
            list.add(result.getArray().getString(i));
        }

        return list;
    }
}
