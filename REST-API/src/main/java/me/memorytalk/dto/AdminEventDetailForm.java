package me.memorytalk.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AdminEventDetailForm {

    private String title;

    private String description;

    private String company;

    private String eventType;

    private String eventPage;

    private String homePage;

    private Date startDate;

    private Date endDate;

    private Date publicationDate;

    private Date registrationDate;

    private boolean premium;

    private boolean visible;

    private List<AdminGiftForm> gifts;

    private List<String> tags;

    @JsonCreator
    public AdminEventDetailForm(@JsonProperty("title") String title,
                                @JsonProperty("description") String description,
                                @JsonProperty("company") String company,
                                @JsonProperty("eventType") String eventType,
                                @JsonProperty("eventPage") String eventPage,
                                @JsonProperty("homePage") String homePage,
                                @JsonProperty("startDate") Date startDate,
                                @JsonProperty("endDate") Date endDate,
                                @JsonProperty("publicationDate") Date publicationDate,
                                @JsonProperty("premium") boolean premium,
                                @JsonProperty("visible") boolean visible,
                                @JsonProperty("gifts") List<AdminGiftForm> gifts,
                                @JsonProperty("tags") List<String> tags) {
        this.title = title;
        this.description = description;
        this.company = company;
        this.eventType = eventType;
        this.eventPage = eventPage;
        this.homePage = homePage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.publicationDate = publicationDate;
        this.premium = premium;
        this.visible = visible;
        this.gifts = gifts;
        this.tags = tags;
    }
}
