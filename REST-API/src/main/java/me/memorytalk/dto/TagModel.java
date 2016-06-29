package me.memorytalk.dto;

import lombok.Data;

@Data
public class TagModel {

    private String name;

    private String color;

    public TagModel(String name, String color) {
        this.name = name;
        this.color = color;
    }
}
