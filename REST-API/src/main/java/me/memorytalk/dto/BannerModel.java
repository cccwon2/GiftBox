package me.memorytalk.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BannerModel {

    private Long id;

    private Long eventId;

    private String bannerPage;

    private List<AttachmentModel> attachments;

    public BannerModel(Long id, Long eventId, String bannerPage,
                       int width, int height, String url, String thumbnailS, String thumbnailM, String thumbnailL) {
        this.id = id;
        this.eventId = eventId;
        this.bannerPage = bannerPage;
        this.attachments = new ArrayList<>();
        this.attachments.add(new AttachmentModel(width, height, url, thumbnailS, thumbnailM, thumbnailL));
    }

}
