package com.caroli.cykel;

import javafx.application.Application;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    }

    public static void main(String[] args) {

        Runnable task = () -> {
            //System.out.println("Checking Server");
            //Other multi-line code instructions
            scheduledRequest();
        };
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        executorService.scheduleAtFixedRate(task, 1 ,1, TimeUnit.MINUTES);
        launch();

    }

    public static void scheduledRequest() {
        try {
            ScheduleddReq sch = new ScheduleddReq();
            sch.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t) {
                    System.out.println("done:" + t.getSource().getValue());
                    sch.cancel();
                }
            });
            sch.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}