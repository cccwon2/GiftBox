package me.memorytalk.dto;

import lombok.Data;

@Data
public class GiftModel {

    private String product;

    private int count;

    public GiftModel(String product, int count) {
        this.product = product;
        this.count = count;
    }
}
