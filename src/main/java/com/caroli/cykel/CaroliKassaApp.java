package com.caroli.cykel;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CaroliKassaApp extends Application {
    //For Api Request
    private static ScheduledExecutorService executorService;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CaroliKassaApp.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Revolutio AB Api Bridge | Plife");
        Image image = new Image(CaroliKassaApp.class.getResourceAsStream("icons/ic_launcher-web.png"));
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> {
            executorService.shutdown();
        });
    }

    public static void main(String[] args) {

        Runnable task = () -> {
            scheduledRequest();
        };

        //ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        executorService = Executors.newScheduledThreadPool(2);

        executorService.scheduleAtFixedRate(task, 30, 30, TimeUnit.SECONDS);

        launch();

    }

    public static void scheduledRequest() {
        try {
            ReadSettings settings = new ReadSettings();
            //settings.ReadSettings();
            String mode = settings.getMode();
            if (mode.equals("Auto") && ! MainController.onProcess) {
                MainController.onProcess = true;
                /*
                Date date = new Date();
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                long HOUR = 3600*1000; // in milli-seconds.
                Date newDate = new Date(date.getTime() + settings.getSyncTimePeriod() * HOUR);
                String timeString = timeFormat.format(newDate);
                MainController.nextReqLabel.setText(timeString);

                 */
                try {
                    ScheduledReq sch = new ScheduledReq();
                    sch.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent t) {
                            ArrayList<Item> list = (ArrayList) t.getSource().getValue();
                            ExecutorService executor = Executors.newFixedThreadPool(3);
                            executor.submit(new SyncRequest(list, executor));
                            MainController.update_time();
                            sch.cancel();
                            MainController.itemSizeLabel.setText(String.valueOf(list.size()));
                        }
                    });
                    sch.start();
                } catch (Exception e) {
                    MainController.onProcess = false;
                }

            } else {
                System.out.println("onProcess or Manuel mode");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}