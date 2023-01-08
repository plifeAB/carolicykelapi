package com.caroli.cykel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class CaroliKassaApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CaroliKassaApp.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Revolutio AB Api Bridge | Plife");
        Image image = new Image(CaroliKassaApp.class.getResourceAsStream("icons/ic_launcher-web.png"));
        stage.getIcons().add(image);
        stage.setScene(scene);

        stage.show();

        //repeating timer: prints stuff every 10s
        Timer myRepeatingTimer = new Timer();
        myRepeatingTimer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                System.out.println("hello from repeating timer");
            }
        }, 0, 1000);

    }


    public static void main(String[] args) {
        launch();
    }
}