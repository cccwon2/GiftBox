package me.memorytalk.dto;

import lombok.Data;

@Data
public class EventTypeCodeModel {

    private String name;

    private String color;

    private String sort;

    public EventTypeCodeModel(String name, String color, String sort) {
        this.name = name;
        this.color = color;
        this.sort = sort;
    }
}
