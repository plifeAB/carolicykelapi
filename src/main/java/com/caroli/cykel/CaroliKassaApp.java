package com.caroli.cykel;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.application.Application;
import javafx.concurrent.WorkerStateEvent;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CaroliKassaApp extends Application {
    //For Api Request
    private static ScheduledExecutorService executorService;
    //For store sync
    private static ScheduledExecutorService executorSyncService;

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
            //System.out.println("Checking Server");
            //Other multi-line code instructions
            scheduledRequest();
        };
        //ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        executorService = Executors.newScheduledThreadPool(2);
        executorService.scheduleAtFixedRate(task, 30 ,30, TimeUnit.SECONDS);

        launch();

    }

    public static void scheduledRequest() {
        try {
            ScheduleddReq sch = new ScheduleddReq();
            sch.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t) {
                    //System.out.println("done:" + t.getSource().getValue());
                    ArrayList<Item> list = new ArrayList<Item>();
                    JsonArray json = (JsonArray)t.getSource().getValue();

                    json.forEach((n) -> {
                        JsonObject obj = (JsonObject) n;
                        String title = obj.get("Description").getAsString();
                        String barcode = obj.get("Barcode").getAsString();
                        String itemNumber = obj.get("ItemNumber").getAsString();
                        Integer itemId = obj.get("Id").getAsInt();
                        Integer stock = obj.get("StorageAmount").getAsInt();
                        Float sellPrice = obj.get("SellPrice").getAsFloat();
                        Float buyPrice = obj.get("BuyPrice").getAsFloat();

                        JsonObject sup = (JsonObject) obj.get("Supplier").getAsJsonObject();
                        String supplier = sup.get("Name").getAsString();

                        //System.out.println(obj.get("Description").getAsString());
                        //System.out.println(supplier);

                        list.add(new Item(title,barcode,itemNumber,itemId,stock,sellPrice,buyPrice,supplier));

                    });

                    sch.cancel();

                    /*
                    //Sync Request
                    Thread syncReq = new SyncRequest(list);
                    //syncReq.setDaemon(true);
                    syncReq.start();
                    */

                    ExecutorService executor = Executors.newFixedThreadPool(3);
                    executor.submit(new SyncRequest(list,executor));
                }
            });
            sch.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}