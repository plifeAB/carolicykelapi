package com.caroli.cykel;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

public  class ApiRequest {
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

    public  void syncReq(ArrayList<Item> items) throws IOException  {
        try {
            ReadSettings settings = new ReadSettings();
            Integer requestLimit = settings.requestLimit;
            ArrayList<List<Item>> it = new ArrayList<>();

            int dividend = items.size() / settings.requestLimit;
            int left = items.size() % settings.requestLimit;

            //System.out.println(items.size());

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
                try {
                    makeSyncRequest(json);
                    Thread.sleep(MainController.settings.getSleepPeriod());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

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
            //JSONArray json = new JSONArray("[{}]");
            //makeSyncRequest(json);
            MainController.onProcess = false;
        } catch (FileNotFoundException e) {
            System.out.println("could not read settings");
            MainController.onProcess = false;
        }

    }

    public  JSONArray buildJson(List<Item> items) {
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

    private  boolean makeSyncRequest(JSONArray items) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            ReadSettings settings = new ReadSettings();;
            HttpPost post = new HttpPost(settings.getServerRequestUrl());

            post.addHeader("content-type", "application/x-www-form-urlencoded");
            post.addHeader("store-name", settings.getWareHouseName().toLowerCase(Locale.ROOT));
            post.addHeader("store-key", settings.getStoreKey());
            post.addHeader(HttpHeaders.USER_AGENT, "plife-api-request-engine");

            List<NameValuePair> urlParameters = new ArrayList<>();
            urlParameters.add(new BasicNameValuePair("action", settings.getRequestAction()));
            urlParameters.add(new BasicNameValuePair("items", items.toString()));
            post.setEntity(new UrlEncodedFormEntity(urlParameters,"UTF-8"));

            CloseableHttpResponse response = httpClient.execute(post);

            // Get HttpResponse Status
           // System.out.println(response.getProtocolVersion());              // HTTP/1.1
           // System.out.println(response.getStatusLine().getStatusCode());   // 200
           // System.out.println(response.getStatusLine().getReasonPhrase()); // OK
           // System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

            try {
                HttpEntity res_entity = response.getEntity();
                if (res_entity != null) {
                    String result = EntityUtils.toString(res_entity);
                    System.out.println(result);
                }
            } finally {
                response.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpClient.close();
        }
        httpClient.close();
        return false;
    }
}
