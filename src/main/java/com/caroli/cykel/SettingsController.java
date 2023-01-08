package com.caroli.cykel;

import com.google.gson.*;
import javafx.event.ActionEvent;

import java.io.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import org.json.JSONArray;
import org.json.JSONObject;

import org.json.simple.parser.ParseException;


public class SettingsController {
    @FXML
    ToggleGroup mode; //I called it mode in SceneBuilder.
    @FXML
    TextField warehausename;
    @FXML
    TextField storekey;

    public void writeJsonAction(ActionEvent actionEvent) throws IOException {

        //First Employee
        JSONObject employeeDetails = new JSONObject();
        employeeDetails.put("firstName", "Lokesh");
        employeeDetails.put("lastName", "Gupta");
        employeeDetails.put("website", "howtodoinjava.com");

        JSONObject employeeObject = new JSONObject();
        employeeObject.put("employee", employeeDetails);

        //Second Employee
        JSONObject employeeDetails2 = new JSONObject();
        employeeDetails2.put("firstName", "Brian");
        employeeDetails2.put("lastName", "Schultz");
        employeeDetails2.put("website", "example.com");

        JSONObject employeeObject2 = new JSONObject();
        employeeObject2.put("employee", employeeDetails2);

        //Add employees to list
        JSONArray employeeList = new JSONArray();
        employeeList.put(employeeObject);
        employeeList.put(employeeObject2);

        //Write JSON file
        try (FileWriter file = new FileWriter("employees.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(employeeList.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void readJsonAction(ActionEvent actionEvent) throws IOException, ParseException {

        JsonParser parser = new JsonParser();
        JsonArray jsonArray = (JsonArray) parser.parse(new FileReader("employees.json"));

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject item = (JsonObject) jsonArray.get(i);
            JsonObject emp = (JsonObject) item.get("employee");
            System.out.println(emp.get("firstName"));
        }
    }

    public void saveSettingsAction(ActionEvent actionEvent) {

        RadioButton selectedRadioButton = (RadioButton) mode.getSelectedToggle();
        String toogleGroupValue = selectedRadioButton.getText();

        String wareHauseName = warehausename.getText();
        String storeKey = storekey.getText();

        //First Employee
        JSONObject settingDetails = new JSONObject();
        settingDetails.put("warehousename", wareHauseName);
        settingDetails.put("storekey", storeKey);
        settingDetails.put("mode", toogleGroupValue);
        JSONObject settingObject = new JSONObject();
        settingObject.put("settings", settingDetails);
        //Add employees to list
        JSONArray settingList = new JSONArray();
        settingList.put(settingObject);
        //Write JSON file
        try (FileWriter file = new FileWriter("settings.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(settingList.toString());
            file.flush();
            handleCloseButtonAction(actionEvent);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void settingsDiscardAction(ActionEvent actionEvent) {
        handleCloseButtonAction(actionEvent);
    }
    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

}
