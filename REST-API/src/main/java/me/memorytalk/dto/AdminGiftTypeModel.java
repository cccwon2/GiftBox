package me.memorytalk.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import me.memorytalk.common.json.DateTimeSerializer;

import java.util.Date;

@Data
@AllArgsConstructor
public class AdminGiftTypeModel {

    private Long id;

    @JsonSerialize(using = DateTimeSerializer.class)
    private Date createdDate;

    private String name;

    private Long eventId;

    private String eventTitle;
}
