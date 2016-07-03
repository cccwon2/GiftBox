package me.memorytalk.dto;

import lombok.Data;

@Data
public class EventTypeModel {

    private String name;

    private String color;

    private String sort;

    public EventTypeModel(String name, String color, String sort) {
        this.name = name;
        this.color = color;
        this.sort = sort;
    }
}
