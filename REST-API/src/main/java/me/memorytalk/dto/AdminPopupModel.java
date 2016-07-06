package me.memorytalk.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import me.memorytalk.common.json.DateSerializer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class AdminPopupModel {

    private Long id;

    private Long eventId;

    private String popupPage;

    @JsonSerialize(using = DateSerializer.class)
    private Date startDate;

    @JsonSerialize(using = DateSerializer.class)
    private Date endDate;

    private boolean visible;

    private List<AttachmentModel> attachments;

    public AdminPopupModel(Long id, Long eventId, String popupPage, Date startDate, Date endDate, boolean visible,
                           int width, int height, String url, String thumbnailS, String thumbnailM, String thumbnailL) {
        this.id = id;
        this.eventId = eventId;
        this.popupPage = popupPage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.visible = visible;
        this.attachments = new ArrayList<>();
        this.attachments.add(new AttachmentModel(width, height, url, thumbnailS, thumbnailM, thumbnailL));
    }

}
