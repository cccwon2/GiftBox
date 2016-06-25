package me.memorytalk.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import me.memorytalk.common.json.DateTimeSerializer;

import java.util.Date;

@Data
@AllArgsConstructor
public class AdminGiftModel {

    private Long id;

    @JsonSerialize(using = DateTimeSerializer.class)
    private Date createdDate;

    private String product;

    private int count;

    private Long eventId;

    private String eventTitle;
}
