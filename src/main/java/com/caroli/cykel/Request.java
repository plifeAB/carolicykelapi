package com.caroli.cykel;


import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;


public class Request {
    public JsonArray req() throws IOException {
        /*
        URL url = new URL("https://www.example.com/login");
        URLConnection con = url.openConnection();
        HttpURLConnection http = (HttpURLConnection)con;
        http.setRequestMethod("GET"); // PUT is another valid option
        http.setDoOutput(true);
         */
        CloseableHttpClient httpClient = HttpClients.createDefault();


        try {
            ReadSettings settings = new ReadSettings();
            settings.ReadSettings();
            HttpGet request = new HttpGet(settings.getRequestUrl());

            // add request headers
            // request.addHeader("custom-key", "mkyong");
            // request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

            CloseableHttpResponse response = httpClient.execute(request);

            try {

                // Get HttpResponse Status
                System.out.println(response.getProtocolVersion());              // HTTP/1.1
                System.out.println(response.getStatusLine().getStatusCode());   // 200
                System.out.println(response.getStatusLine().getReasonPhrase()); // OK
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // return it as a String
                    String result = EntityUtils.toString(entity);
                    //System.out.println(result);


                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = (JsonArray) parser.parse(result);
                    return jsonArray;
                }

            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }

        return null;
    }
}
