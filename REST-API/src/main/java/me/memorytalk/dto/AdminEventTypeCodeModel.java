package me.memorytalk.dto;

import lombok.Data;

@Data
public class AdminEventTypeCodeModel {

    private Long id;

    private String name;

    private String color;

    private String sort;

    private boolean checked;

    public AdminEventTypeCodeModel(Long id, String name, String color, String sort) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.sort = sort;
    }
}
