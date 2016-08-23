package me.memorytalk.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import me.memorytalk.common.json.DateSerializer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class EventModel {

    private Long id;

    private String title;

    private String company;

    private String prizePage;

    @JsonSerialize(using = DateSerializer.class)
    private Date startDate;

    @JsonSerialize(using = DateSerializer.class)
    private Date endDate;

    private boolean premium;

    private List<AttachmentModel> attachments;

    private List<EventTypeModel> eventTypes;

    private List<String> giftTypes;

    public EventModel(Long id, String title, String company, String prizePage,
                      Date startDate, Date endDate, boolean premium,
                      int width, int height, String url,
                      String thumbnailS, String thumbnailM, String thumbnailL) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.prizePage = prizePage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.premium = premium;
        this.attachments = new ArrayList<>();
        this.attachments.add(new AttachmentModel(width, height, url, thumbnailS, thumbnailM, thumbnailL));
    }
}
