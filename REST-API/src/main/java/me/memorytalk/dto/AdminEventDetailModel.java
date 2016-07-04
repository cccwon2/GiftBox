package me.memorytalk.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import me.memorytalk.common.json.DateSerializer;

import java.util.Date;
import java.util.List;

@Data
public class AdminEventDetailModel {

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

    private boolean premium;

    private boolean visible;

    private List<AttachmentModel> attachments;

    private List<GiftModel> gifts;

    private List<Long> eventTypeCodeIds;

    public AdminEventDetailModel(Long id, String title, String description,
                                 String company, String eventTarget, String eventPage, String prizePage,
                                 Date startDate, Date endDate, Date publicationDate, Date createdDate,
                                 String publicationContent1, String publicationContent2,
                                 boolean premium, boolean visible) {
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
        this.premium = premium;
        this.visible = visible;
    }

}
