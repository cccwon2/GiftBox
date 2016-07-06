package me.memorytalk.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AdminBannerModel {

    private Long id;

    private Long eventId;

    private String bannerPage;

    private boolean visible;

    private List<AttachmentModel> attachments;

    public AdminBannerModel(Long id, Long eventId, String bannerPage, boolean visible,
                            int width, int height, String url, String thumbnailS, String thumbnailM, String thumbnailL) {
        this.id = id;
        this.eventId = eventId;
        this.bannerPage = bannerPage;
        this.visible = visible;
        this.attachments = new ArrayList<>();
        this.attachments.add(new AttachmentModel(width, height, url, thumbnailS, thumbnailM, thumbnailL));
    }

}
