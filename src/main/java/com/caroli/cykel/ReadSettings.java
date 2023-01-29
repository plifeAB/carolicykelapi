package com.caroli.cykel;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.fxml.Initializable;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;

public class ReadSettings  {
    public String wareHouseName;
    public String storeKey;
    public String requestUrl;
    public String mode;
    public Integer requestLimit;
    public String serverRequestUrl;
    public Integer syncTimePeriod;

    public  ReadSettings() throws FileNotFoundException {
        try {

            JsonParser parser = new JsonParser();
            JsonArray jsonArray = (JsonArray) parser.parse(new FileReader("settings.json"));
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject item = (JsonObject) jsonArray.get(i);
                JsonObject set = (JsonObject) item.get("settings");
                wareHouseName = set.get("wareHouseName").getAsString();
                storeKey = set.get("storeKey").getAsString();
                requestUrl = set.get("requestUrl").getAsString();
                mode = set.get("mode").getAsString();
                requestLimit = set.get("requestLimit").getAsInt();
                serverRequestUrl = set.get("serverRequestUrl").getAsString();
                syncTimePeriod = set.get("syncTimePeriod").getAsInt();

            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public String getWareHouseName() {
        return wareHouseName;
    }

    public String getStoreKey() {
        return storeKey;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public String getMode() {
        return mode;
    }

    public Integer getRequestLimit() {
        return requestLimit;
    }

    public String getServerRequestUrl() {
        return serverRequestUrl;
    }

    public Integer getSyncTimePeriod() {
        return syncTimePeriod;
    }
}
