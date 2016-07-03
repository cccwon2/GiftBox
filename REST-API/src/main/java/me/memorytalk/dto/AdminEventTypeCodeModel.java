package me.memorytalk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminEventTypeCodeModel {

    private Long id;

    private String name;

    private String color;

    private String sort;
}
