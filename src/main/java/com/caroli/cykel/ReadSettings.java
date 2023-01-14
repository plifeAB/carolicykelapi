package com.caroli.cykel;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReadSettings  {
    @FXML
    public  TextFlow logBox;
    public  String wareHouseName;
    public  String storeKey;
    public  String requestUrl;
    public  String mode;
    public  String requestLimit;
    public  String serverRequestUrl;
    public  Integer syncTimePeriod;


    public  void ReadSettings()   throws FileNotFoundException {
        try {
            /*
            ArrayList<Item> list = new ArrayList<Item>();
            list.add(new Item("test 3 ","test desc 3 ",3));
            list.add(new Item("test 4 ","test desc 4",4));
            list.add(new Item("test 5 ","test desc 5",5));
            list.forEach((n) -> {
                System.out.println(n.getDescription());
            });

             */
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = (JsonArray) parser.parse(new FileReader("settings.json"));
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject item = (JsonObject) jsonArray.get(i);
                JsonObject emp = (JsonObject) item.get("settings");
                wareHouseName=emp.get("wareHouseName").toString().replaceAll("\"", "");
                storeKey=emp.get("storeKey").toString().replaceAll("\"", "");
                requestUrl = emp.get("requestUrl").toString().replaceAll("\"", "");
                mode = emp.get("mode").toString().replaceAll("\"", "");
                requestLimit = emp.get("requestLimit").toString().replaceAll("\"", "");
                serverRequestUrl = emp.get("serverRequestUrl").toString().replaceAll("\"", "");
                syncTimePeriod = emp.get("syncTimePeriod").getAsInt();

            }
        } catch(Exception e) {
            // create text
            Text text_1 = new Text(e.toString());
            //logBox.getChildren().add(text_1);
        }

    }

    public String getWareHouseName()
    {
        return wareHouseName;
    }
    public String getStoreKey() {
        return storeKey;
    }
    public String getRequestUrl()
    {
        return requestUrl;
    }
    public String getMode() {
        return mode;
    }
    public String getRequestLimit() {
        return requestLimit;
    }
    public String getServerRequestUrl() {
        return serverRequestUrl;
    }
    public Integer getSyncTimePeriod() {
        return  syncTimePeriod;
    }


}
