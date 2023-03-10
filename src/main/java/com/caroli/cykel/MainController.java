package com.caroli.cykel;

import javafx.application.Platform;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.apache.http.conn.HttpHostConnectException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainController {
    @FXML
    public  TextFlow logBox;
    @FXML
    private ScrollPane scrollLogBox;
    @FXML
    private MenuItem closeButtonMenu;
    @FXML
    MenuBar myMenuBar;
    @FXML
    Label modeStatus;
    @FXML
    Label wareHouseStatus;
    @FXML
    Label lastRequestStatus;

    @FXML
    Label itemSizeLab;
    @FXML
    Label nextReqLab;
    @FXML
    Label limitLab;

    private Stage stageSettings;
    public static ReadSettings settings;
    public static boolean onProcess = false;
    public static boolean scheduledError =false;

    public static TextFlow log_box;
    public static Label lastRequestStatusLabel;
    public static ScrollPane scrollLogBoxPane;
    public static Label itemSizeLabel;
    public static Label nextReqLabel;
    public static Label limitLabel;
    public static Label modeStatusLabel;
    public static Label wareHouseStatusLabel;

    @FXML
    public void initialize() throws FileNotFoundException {
        settings = new ReadSettings();
        //settings.ReadSettings();
        wareHouseStatus.setText(settings.getWareHouseName());
        wareHouseStatusLabel = wareHouseStatus;
        String mode = settings.getMode();
        modeStatus.setText(mode);
        modeStatusLabel = modeStatus;
        itemSizeLabel = itemSizeLab;
        nextReqLabel = nextReqLab;
        limitLabel = limitLab;
        log_box = logBox;
        lastRequestStatusLabel = lastRequestStatus;
        scrollLogBoxPane = scrollLogBox;

        lastRequestStatusLabel.setText("Waiting");
        itemSizeLab.setText("Waiting");
        nextReqLabel.setText("Waiting");

        limitLabel.setText(settings.getRequestLimit().toString());


    }

    @FXML
    protected void onPushButtonClick()  {
        try {
            if (!onProcess) {
                onProcess = true;
                ApiRequest request = new ApiRequest();
                ArrayList<Item> items = request.apiReq();
                ExecutorService executor = Executors.newFixedThreadPool(3);
                executor.submit(new SyncRequest(items, executor));
                itemSizeLabel.setText(String.valueOf(items.size()));
                update_time();
            } else {
                Text text_1 = new Text("Last sync process still in queue\n");
                text_1.setFill(Color.RED);
                text_1.setFont(Font.font("Verdana", 15));
                logBox.getChildren().add(text_1);
            }
        } catch(HttpHostConnectException e) {
            Text text_1 = new Text("Connection Refused for api source\n");
            text_1.setFill(Color.RED);
            text_1.setFont(Font.font("Verdana", 13));
            logBox.getChildren().add(text_1);
            onProcess = false;
        } catch (UnknownHostException e ) {
            Text text_1 = new Text("Unknown host for api source\n");
            text_1.setFill(Color.RED);
            text_1.setFont(Font.font("Verdana", 13));
            logBox.getChildren().add(text_1);
            onProcess = false;
        } catch (Exception e) {
            e.printStackTrace();
            onProcess = false;
        }
    }

    @FXML
    private void closeButtonAction() {
        // get a handle to the stage
        Stage stage = (Stage) myMenuBar.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    private void aboutButtonAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("about.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("About");
            Image image = new Image(CaroliKassaApp.class.getResourceAsStream("icons/ic_launcher-web.png"));
            stage.getIcons().add(image);
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void settingsButtonAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("settings-view.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            if (stageSettings == null) {
                stageSettings = new Stage();
                stageSettings.setTitle("Settings");
                Image image = new Image(CaroliKassaApp.class.getResourceAsStream("icons/ic_launcher-web.png"));
                stageSettings.getIcons().add(image);
                stageSettings.setScene(new Scene(root1));
                stageSettings.setResizable(false);
                stageSettings.setMaximized(false);
            /*
            init function calling dynamically but this method able to use for calling another function

            SettingsController controller = fxmlLoader.getController();
            stage.setOnShowing( event -> {controller.initialize();} );
            */

                stageSettings.show();
            } else if (stageSettings.isShowing()) {
                stageSettings.toFront();
            } else {
                stageSettings.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveLogButtonAction(ActionEvent actionEvent) {
        // Creating an instance of file
        Path path = Paths.get("log.txt");
        String str = getStringFromTextFlow(logBox);

        try {
            Files.writeString(path, str, StandardOpenOption.APPEND);
            logBox.getChildren().clear();
        } catch (IOException ex) {
            System.out.print("Invalid Path");
            Text text_1 = new Text("Invalid Path\n");
            text_1.setFill(Color.RED);
            text_1.setFont(Font.font("Verdana", 15));
            logBox.getChildren().add(text_1);
        }
    }

    public static String getStringFromTextFlow(TextFlow tf) {
        StringBuilder sb = new StringBuilder();
        tf.getChildren().stream()
                .filter(t -> Text.class.equals(t.getClass()))
                .forEach(t -> sb.append(((Text) t).getText()));
        return sb.toString();
    }
    public static void update_time()
    {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy HH:mm:ss", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String timeString = timeFormat.format(date);
        String dateString = dateFormat.format(date);
        lastRequestStatusLabel.setText(timeString);
        Text text_1 = new Text("Request Process begin at\n");
        text_1.setFill(Color.BLACK);
        text_1.setFont(Font.font("Verdana", 14));
        //log_box.getChildren().add(text_1);
        Text time_text= new Text(dateString + "\n");
        time_text.setFill(Color.BLUE);
        time_text.setFont(Font.font("Verdana", 12));
        log_box.getChildren().addAll(text_1,time_text);


        scrollLogBoxPane.setVvalue(Double.MAX_VALUE);

    }
    public static void update_log_box(String txt, Color cl) {
        Text text_1 = new Text(txt);
        text_1.setFill(cl);
        text_1.setFont(Font.font("Verdana", 14));
        log_box.getChildren().add(text_1);
        System.out.println("here");
    }
}