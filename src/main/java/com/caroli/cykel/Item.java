package com.caroli.cykel;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Item implements Initializable {
    private String title;
    private String barcode;
    private String itemNumber;
    private Integer itemId;
    private Integer stock;
    private Float sellPrice;
    private Float buyPrice;
    private String supplier;

    public Item(String title,
                String barcode,
                String itemNumber,
                Integer itemId,
                Integer stock,
                Float sellPrice,
                Float buyPrice,
                String supplier) {

        this.title = title;
        this.barcode = barcode;
        this.itemNumber = itemNumber;
        this.itemId = itemId;
        this.stock = stock;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
        this.supplier = supplier;

    }

    public String getTitle() {
        return title;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public Integer getItemId() {
        return itemId;
    }

    public Float getSellPrice() {
        return sellPrice;
    }

    public Float getBuyPrice() {
        return buyPrice;
    }

    public String getSupplier() {
        return supplier;
    }

    public Integer getStock() {
        return stock;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
