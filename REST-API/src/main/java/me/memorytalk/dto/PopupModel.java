package me.memorytalk.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import me.memorytalk.common.json.DateTimeSerializer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class PopupModel {

    private Long id;

    private Long eventId;

    private String popupPage;

    @JsonSerialize(using = DateTimeSerializer.class)
    private Date startDate;

    @JsonSerialize(using = DateTimeSerializer.class)
    private Date endDate;

    private List<AttachmentModel> attachments;

    public PopupModel(Long id, Long eventId, String popupPage, Date startDate, Date endDate,
                      int width, int height, String url, String thumbnailS, String thumbnailM, String thumbnailL) {
        this.id = id;
        this.eventId = eventId;
        this.popupPage = popupPage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.attachments = new ArrayList<>();
        this.attachments.add(new AttachmentModel(width, height, url, thumbnailS, thumbnailM, thumbnailL));
    }

}
