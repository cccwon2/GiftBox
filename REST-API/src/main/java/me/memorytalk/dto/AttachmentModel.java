package me.memorytalk.dto;

import lombok.Data;

@Data
public class AttachmentModel {

    private int width;

    private int height;

    private String url;

    private String thumbnailS;

    private String thumbnailM;

    private String thumbnailL;

    public AttachmentModel(int width, int height, String url, String thumbnailS, String thumbnailM, String thumbnailL) {
        this.width = width;
        this.height = height;
        this.url = url;
        this.thumbnailS = thumbnailS;
        this.thumbnailM = thumbnailM;
        this.thumbnailL = thumbnailL;
    }
}
