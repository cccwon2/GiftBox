package me.memorytalk.dto;

import lombok.Data;

@Data
public class AdminBannerModel {

    private Long id;

    private Long eventId;

    private String bannerPage;

    private boolean visible;

    public AdminBannerModel(Long id, Long eventId, String bannerPage, boolean visible) {
        this.id = id;
        this.eventId = eventId;
        this.bannerPage = bannerPage;
        this.visible = visible;
    }

}
