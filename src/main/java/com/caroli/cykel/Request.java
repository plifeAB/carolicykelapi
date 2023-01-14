package com.caroli.cykel;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class Request {
    public void req() throws IOException {
        URL url = new URL("https://www.example.com/login");
        URLConnection con = url.openConnection();
        HttpURLConnection http = (HttpURLConnection)con;
        http.setRequestMethod("GET"); // PUT is another valid option
        http.setDoOutput(true);
    }
}
