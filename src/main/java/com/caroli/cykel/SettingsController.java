package com.caroli.cykel;

import com.google.gson.*;
import javafx.event.ActionEvent;

import java.io.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import org.json.JSONArray;
import org.json.JSONObject;

import org.json.simple.parser.ParseException;


public class SettingsController {
    @FXML
    ToggleGroup mode; //I called it mode in SceneBuilder.
    @FXML
    TextField warehousename;
    @FXML
    TextField storekey;
    @FXML
    TextField reqUrl;
    @FXML
    RadioButton radioAuto;
    @FXML
    RadioButton radioManuel;
    @FXML
    TextField requestLimit;
    @FXML
    ComboBox timePeriod;
    @FXML
    TextField serverReqUrl;

    @FXML
    public void initialize() throws FileNotFoundException {
        MainController.settings.ReadSettings();
        //ReadSettings settings = new ReadSettings();
        //settings.ReadSettings();
        try {
            warehousename.setText(MainController.settings.getWareHouseName());
            storekey.setText(MainController.settings.getStoreKey());
            reqUrl.setText(MainController.settings.getRequestUrl());
            requestLimit.setText(MainController.settings.getRequestLimit());
            serverReqUrl.setText(MainController.settings.getServerRequestUrl());
            String mode = MainController.settings.getMode();
            if (mode.equals("Manuel")) {
                radioManuel.setSelected(true);
            } else {
                radioAuto.setSelected(true);
            }
            timePeriod.getSelectionModel().select(MainController.settings.getSyncTimePeriod().toString());
        } catch (NullPointerException e ) {
            return;
        } catch (Exception e ) {
            e.printStackTrace();
        }

    }

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

        try {
            RadioButton selectedRadioButton = (RadioButton) mode.getSelectedToggle();
            String toogleGroupValue = selectedRadioButton.getText();
            String wareHouseName = warehousename.getText();
            String storeKey = storekey.getText();
            String requestUrl = reqUrl.getText();
            String requestLim = requestLimit.getText();
            String serverRequestUrl = serverReqUrl.getText();
            Integer syncTimePeriod = Integer.parseInt((String) timePeriod.getValue());
            //Settings
            JSONObject settingDetails = new JSONObject();
            settingDetails.put("wareHouseName", wareHouseName);
            settingDetails.put("storeKey", storeKey);
            settingDetails.put("mode", toogleGroupValue);
            settingDetails.put("requestUrl", requestUrl);
            settingDetails.put("requestLimit",requestLim);
            settingDetails.put("serverRequestUrl",serverRequestUrl);
            settingDetails.put("syncTimePeriod",syncTimePeriod);
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
        } catch (NumberFormatException e) {
            Alert a = new Alert(Alert.AlertType.NONE,"default Dialog",ButtonType.OK);
            // set content text
            a.setContentText("Please choose Sync Time Period ");
            // show the dialog
            a.show();
        }
        catch (Exception e) {
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
