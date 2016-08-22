package me.memorytalk.dto;

import lombok.Data;

@Data
public class AdminGiftTypeCodeModel {

    private Long id;

    private String name;

    private boolean checked;

    public AdminGiftTypeCodeModel(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
