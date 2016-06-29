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

    private String homePage;

    @JsonSerialize(using = DateSerializer.class)
    private Date startDate;

    @JsonSerialize(using = DateSerializer.class)
    private Date endDate;

    private List<AttachmentModel> attachments;

    private List<TagModel> tags;

    public EventModel(Long id, String title, String company, String homePage,
                      Date startDate, Date endDate,
                      int width, int height, String url,
                      String thumbnailS, String thumbnailM, String thumbnailL) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.homePage = homePage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.attachments = new ArrayList<>();
        this.attachments.add(new AttachmentModel(width, height, url, thumbnailS, thumbnailM, thumbnailL));
    }
}
