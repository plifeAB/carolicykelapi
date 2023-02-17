package com.caroli.cykel;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.paint.Color;
import org.apache.http.conn.HttpHostConnectException;

import java.net.SocketException;
import java.util.ArrayList;

public class ScheduledReq extends Service<ArrayList> {

    protected Task<ArrayList> createTask() {

        return new Task<ArrayList>() {
            @Override
            protected ArrayList<Item> call()  {

                try {
                    ApiRequest request = new ApiRequest();
                    ArrayList<Item> response = request.apiReq();
                    return response;

                } catch (SocketException e) {
                    System.out.println("connection error");
                    MainController.onProcess=false;
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                    MainController.onProcess=false;
                    return null;
                }
            }
        };
    }
}
