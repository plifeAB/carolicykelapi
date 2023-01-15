package com.caroli.cykel;
import com.google.gson.JsonArray;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class ScheduleddReq extends Service<JsonArray> {

    protected Task<JsonArray> createTask() {

        return new Task<JsonArray>() {
            @Override
            protected JsonArray call() throws Exception {
                Request request = new Request();
                JsonArray response = request.req();
                return response;
            }
        };
    }
}
