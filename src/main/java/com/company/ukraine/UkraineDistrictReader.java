package com.company.ukraine;

import com.company.ukraine.utils.GsonUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

public class UkraineDistrictReader {

    public static void execute() throws IOException {
        GsonUtils gsonUtils = new GsonUtils();
        String dirPath = "C:\\Users\\user\\IdeaProjects\\Learning\\src\\main\\resources\\ukraine_geojson\\Cherkasy.json";
        String json = gsonUtils.readJsonFromFile(dirPath);
        System.out.println(gsonUtils.deserializeClass(GeoJson.class, json));
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

        jsonObject.getAsJsonObject("geometry").getAsJsonArray("coordinates").get(0);

    }

    public static void main(String[] args) throws IOException {
        execute();
    }
}
