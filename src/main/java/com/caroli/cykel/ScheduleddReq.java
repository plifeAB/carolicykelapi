package com.caroli.cykel;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.apache.http.conn.HttpHostConnectException;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ScheduleddReq extends Service<ArrayList> {

    protected Task<ArrayList> createTask() {

        return new Task<ArrayList>() {
            @Override
            protected ArrayList<Item> call()  {

                try {
                    ApiRequest request = new ApiRequest();
                    ArrayList<Item> response = request.apiReq();
                    return response;

                } catch (Exception e) {
                    e.printStackTrace();
                    MainController.onProcess=false;
                    return null;
                }
            }
        };
    }
}
