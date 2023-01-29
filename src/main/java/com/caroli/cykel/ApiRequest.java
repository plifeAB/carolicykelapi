package com.caroli.cykel;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ApiRequest {
    public ArrayList<Item> apiReq() throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            ReadSettings settings = new ReadSettings();
            //settings.ReadSettings();
            HttpGet request = new HttpGet(settings.getRequestUrl());

            // add request headers
            // request.addHeader("custom-k
            // ey", "mkyong");
            // request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

            CloseableHttpResponse response = httpClient.execute(request);

            // Get HttpResponse Status
            System.out.println(response.getProtocolVersion());              // HTTP/1.1
            System.out.println(response.getStatusLine().getStatusCode());   // 200
            System.out.println(response.getStatusLine().getReasonPhrase()); // OK
            System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

            try {

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = (JsonArray) parser.parse(result);
                    ArrayList<Item> list = new ArrayList();
                    jsonArray.forEach((n) -> {

                        JsonObject obj = (JsonObject) n;
                        String title = obj.get("Description").getAsString();
                        String barcode = obj.get("Barcode").getAsString();
                        String itemNumber = obj.get("ItemNumber").getAsString();
                        Integer itemId = obj.get("Id").getAsInt();
                        Integer stock = obj.get("StorageAmount").getAsInt();
                        Float sellPrice = obj.get("SellPrice").getAsFloat();
                        Float buyPrice = obj.get("BuyPrice").getAsFloat();

                        JsonObject sup = obj.get("Supplier").getAsJsonObject();
                        String supplier = sup.get("Name").getAsString();

                        //System.out.println(obj.get("Description").getAsString());
                        //System.out.println(supplier);
                        list.add(new Item(title, barcode, itemNumber, itemId, stock, sellPrice, buyPrice, supplier));

                    });
                    return list;

                }

            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }

        return null;
    }

    public void syncReq(ArrayList<Item> items) {
        try {
            ReadSettings settings = new ReadSettings();
            Integer requestLimit = settings.requestLimit;
            ArrayList<List<Item>> it = new ArrayList<>();

            int dividend = items.size() / settings.requestLimit;
            int left = items.size() % settings.requestLimit;

            System.out.println(items.size());

            int count = 0;
            for (int i = 0; i < dividend; i++) {
                count = i * requestLimit;
                List<Item> item = items.subList(count, count + requestLimit);
                it.add(item);
            }
            List<Item> item = items.subList(count + requestLimit, count + requestLimit + left);
            it.add(item);
            //System.out.println(count);

            it.forEach(n -> {
                JSONArray json = buildJson(n);
                makeSyncRequest(json);

                /*
                AtomicReference<Integer> xx = new AtomicReference<>(0);
                json.forEach(json_item -> {
                    try {
                        xx.set(xx.get() +1);
                        System.out.println(xx.get().toString());
                        makeSyncRequest((JSONObject) json_item);
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });

                 */
                //System.out.println(buildJson(n));
            });
            MainController.onProcess = false;
        } catch (FileNotFoundException e) {
            System.out.println("could not read settings");
            MainController.onProcess = false;
        }

    }

    public JSONArray buildJson(List<Item> items) {
        JSONArray allItems = new JSONArray();
        items.forEach(it -> {
            JSONObject item = new JSONObject();
            item.put("title", it.getTitle());
            item.put("barcode", it.getBarcode());
            item.put("itemNumber", it.getItemNumber());
            item.put("itemId", it.getItemId());
            item.put("stock", it.getStock());
            item.put("sellPrice", it.getSellPrice());
            item.put("buyPrice", it.getBuyPrice());
            item.put("supplier", it.getSupplier());
            JSONObject allIt = new JSONObject();
            allIt.put("Item", item);
            allItems.put(allIt);
        });
        return allItems;
    }

    private boolean makeSyncRequest(JSONArray items) {
        System.out.println(items);
        return false;
    }

}
