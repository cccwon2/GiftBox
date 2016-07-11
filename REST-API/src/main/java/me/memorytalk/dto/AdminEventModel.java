package me.memorytalk.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import me.memorytalk.common.json.DateSerializer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class AdminEventModel {

    private Long id;

    private String title;

    private String description;

    private String company;

    private String eventTarget;

    private String eventPage;

    private String prizePage;

    @JsonSerialize(using = DateSerializer.class)
    private Date startDate;

    @JsonSerialize(using = DateSerializer.class)
    private Date endDate;

    @JsonSerialize(using = DateSerializer.class)
    private Date publicationDate;

    @JsonSerialize(using = DateSerializer.class)
    private Date createdDate;

    private String publicationContent1;

    private String publicationContent2;

    private String publicationType;

    private boolean premium;

    private boolean visible;

    private List<AttachmentModel> attachments;

    public AdminEventModel(Long id, String title, String description,
                           String company, String eventTarget, String eventPage, String prizePage,
                           Date startDate, Date endDate, Date publicationDate, Date createdDate,
                           String publicationContent1, String publicationContent2, String publicationType,
                           boolean premium, boolean visible,
                           int width, int height, String url,
                           String thumbnailS, String thumbnailM, String thumbnailL) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.company = company;
        this.eventTarget = eventTarget;
        this.eventPage = eventPage;
        this.prizePage = prizePage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.publicationDate = publicationDate;
        this.createdDate = createdDate;
        this.publicationContent1 = publicationContent1;
        this.publicationContent2 = publicationContent2;
        this.publicationType = publicationType;
        this.premium = premium;
        this.visible = visible;
        this.attachments = new ArrayList<>();
        this.attachments.add(new AttachmentModel(width, height, url, thumbnailS, thumbnailM, thumbnailL));
    }

}
