package me.memorytalk.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AdminBannerForm {

    private Long eventId;

    private String bannerPage;

    private boolean visible;

    @JsonCreator
    public AdminBannerForm(@JsonProperty("eventId") Long eventId,
                           @JsonProperty("bannerPage") String bannerPage,
                           @JsonProperty("visible") boolean visible) {
        this.eventId = eventId;
        this.bannerPage = bannerPage;
        this.visible = visible;
    }
}
