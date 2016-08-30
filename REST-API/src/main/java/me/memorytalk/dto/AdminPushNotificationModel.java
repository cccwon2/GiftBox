package me.memorytalk.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import me.memorytalk.common.json.DateSerializer;

import java.util.Date;

@Data
public class AdminPushNotificationModel {

    private Long id;

    private String title;

    private String body;

    private Long eventId;

    @JsonSerialize(using = DateSerializer.class)
    private Date createdDate;

    public AdminPushNotificationModel(Long id, String title, String body, Long eventId, Date createdDate) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.eventId = eventId;
        this.createdDate = createdDate;
    }
}
