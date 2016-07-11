package me.memorytalk.dto;

import lombok.Data;

@Data
public class EventTypeCodeModel {

    private Long id;

    private String name;

    private String color;

    private String sort;

    public EventTypeCodeModel(Long id, String name, String color, String sort) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.sort = sort;
    }
}
