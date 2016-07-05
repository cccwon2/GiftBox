package me.memorytalk.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class AdminPopupForm {

    private Long eventId;

    private String popupPage;

    private Date startDate;

    private Date endDate;

    private boolean visible;

    @JsonCreator
    public AdminPopupForm(@JsonProperty("eventId") Long eventId,
                          @JsonProperty("popupPage") String popupPage,
                          @JsonProperty("startDate") Date startDate,
                          @JsonProperty("endDate") Date endDate,
                          @JsonProperty("visible") boolean visible) {
        this.eventId = eventId;
        this.popupPage = popupPage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.visible = visible;
    }
}
