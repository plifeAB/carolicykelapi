package com.caroli.cykel;

import com.google.gson.*;
import javafx.event.ActionEvent;

import java.io.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
    TextField requestAction;
    @FXML
    TextField sleepPeriod;

    @FXML
    public void initialize() throws FileNotFoundException {
        //MainController.settings.ReadSettings();
        //ReadSettings settings = new ReadSettings();
        //settings.ReadSettings();
        try {
            warehousename.setText(MainController.settings.getWareHouseName());
            storekey.setText(MainController.settings.getStoreKey());
            reqUrl.setText(MainController.settings.getRequestUrl());
            requestLimit.setText(MainController.settings.getRequestLimit().toString());
            serverReqUrl.setText(MainController.settings.getServerRequestUrl());
            String mode = MainController.settings.getMode();
            if (mode.equals("Manuel")) {
                radioManuel.setSelected(true);
            } else {
                radioAuto.setSelected(true);
            }
            timePeriod.getSelectionModel().select(MainController.settings.getSyncTimePeriod().toString());
            requestAction.setText(MainController.settings.getRequestAction());
            sleepPeriod.setText(MainController.settings.getSleepPeriod().toString());
        } catch (NullPointerException e ) {
            return;
        } catch (Exception e ) {
            e.printStackTrace();
        }

    }

    public void saveSettingsAction(ActionEvent actionEvent) {

        try {
            RadioButton selectedRadioButton = (RadioButton) mode.getSelectedToggle();
            String toogleGroupValue = selectedRadioButton.getText();
            String wareHouseName = warehousename.getText();
            String storeKey = storekey.getText();
            String requestUrl = reqUrl.getText();
            Integer requestLim = Integer.parseInt(requestLimit.getText());
            String serverRequestUrl = serverReqUrl.getText();
            Integer syncTimePeriod = Integer.parseInt((String) timePeriod.getValue());
            String reqAction = requestAction.getText();
            Long sleepTimePeriod = Long.parseLong(sleepPeriod.getText());
            //Settings
            JSONObject settingDetails = new JSONObject();
            settingDetails.put("wareHouseName", wareHouseName);
            settingDetails.put("storeKey", storeKey);
            settingDetails.put("mode", toogleGroupValue);
            settingDetails.put("requestUrl", requestUrl);
            settingDetails.put("requestLimit",requestLim);
            settingDetails.put("serverRequestUrl",serverRequestUrl);
            settingDetails.put("syncTimePeriod",syncTimePeriod);
            settingDetails.put("requestAction",reqAction);
            settingDetails.put("sleepPeriod",sleepTimePeriod);

            JSONObject settingObject = new JSONObject();
            settingObject.put("settings", settingDetails);

            JSONArray settingList = new JSONArray();
            settingList.put(settingObject);
            //Write JSON file
            try (FileWriter file = new FileWriter("settings.json")) {
                //We can write any JSONArray or JSONObject instance to the file
                file.write(settingList.toString());
                file.flush();
                handleCloseButtonAction(actionEvent);
                MainController.wareHouseStatusLabel.setText(wareHouseName);
                MainController.modeStatusLabel.setText(toogleGroupValue);
                MainController.limitLabel.setText(requestLim.toString());


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
