package com.caroli.cykel;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Item implements Initializable {
    private String name;
    private String description;
    private Integer stock;
    public Item(String name, String description, Integer stock) {
        this.name=name;
        this.description=description;
        this.stock=stock;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getStock() {
        return stock;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
