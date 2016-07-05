package me.memorytalk.dto;

import lombok.Data;

@Data
public class BannerModel {

    private Long id;

    private Long eventId;

    private String bannerPage;

    public BannerModel(Long id, Long eventId, String bannerPage) {
        this.id = id;
        this.eventId = eventId;
        this.bannerPage = bannerPage;
    }

}
