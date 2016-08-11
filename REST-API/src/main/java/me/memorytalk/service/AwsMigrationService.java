package me.memorytalk.service;

import me.memorytalk.common.util.AmazonS3Util;
import me.memorytalk.domain.Attachment;
import me.memorytalk.repository.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

@Service
public class AwsMigrationService {

    @Autowired
    private AttachmentRepository attachmentRepository;

    protected Boolean setAwsAttachmentMigration() throws IOException {

        long startTime = System.currentTimeMillis();

        List<Attachment> attachments = attachmentRepository.findAll();
        String attachmentFolder = "attachments";
        String attachmentThumbnailFolder = "attachment-thumbs";

        for(Attachment attachment : attachments) {
            String attachmentUrl = attachment.getUrl();
            String attachmentName = attachmentUrl.substring(attachmentUrl.lastIndexOf("/") + 1);

            String thumbnailLUrl = String.format("http://az685860.vo.msecnd.net/attachment-thumbs/%s_thumb_L", attachmentName);
            String thumbnailLName = thumbnailLUrl.substring(thumbnailLUrl.lastIndexOf("/") + 1);

            String thumbnailMUrl = String.format("http://az685860.vo.msecnd.net/attachment-thumbs/%s_thumb_M", attachmentName);
            String thumbnailMName = thumbnailMUrl.substring(thumbnailMUrl.lastIndexOf("/") + 1);

            String thumbnailSUrl = String.format("http://az685860.vo.msecnd.net/attachment-thumbs/%s_thumb_S", attachmentName);
            String thumbnailSName = thumbnailSUrl.substring(thumbnailSUrl.lastIndexOf("/") + 1);

            String awsAttachmentThumbnailLUrl = AmazonS3Util.CDN_DOMAIN + "/" + attachmentThumbnailFolder + "/" + thumbnailLName;
            String awsAttachmentThumbnailMUrl = AmazonS3Util.CDN_DOMAIN + "/" + attachmentThumbnailFolder + "/" + thumbnailMName;
            String awsAttachmentThumbnailSUrl = AmazonS3Util.CDN_DOMAIN + "/" + attachmentThumbnailFolder + "/" + thumbnailSName;

            if(!attachmentUrl.startsWith(AmazonS3Util.CDN_DOMAIN)) {
                String awsAttachmentS3Url = AmazonS3Util.S3_DOMAIN + "/" + attachmentFolder + "/" + attachmentName;
                String awsAttachmentUrl = AmazonS3Util.CDN_DOMAIN + "/" + attachmentFolder + "/" + attachmentName;

                int attachmentSize = getFileSize(new URL(attachmentUrl));
                int awsAttachmentSize = getFileSize(new URL(awsAttachmentS3Url));
                //System.err.println("Attachment ID: " + attachment.getId());
                //System.err.println("Azure Attachment Size: " + attachmentSize);
                //System.err.println("AWS Attachment Size: " + awsAttachmentSize);
                if(awsAttachmentSize == 0 || attachmentSize != awsAttachmentSize) {
                    AmazonS3Util amazonS3Util = new AmazonS3Util();
                    String attachmentContentType = getContentType(new URL(attachmentUrl));
                    awsAttachmentUrl = amazonS3Util.upload(attachmentUrl, attachmentSize, attachmentContentType, attachmentName, attachmentFolder);
                    int thumbnailLSize = getFileSize(new URL(thumbnailLUrl));
                    awsAttachmentThumbnailLUrl = amazonS3Util.upload(thumbnailLUrl, thumbnailLSize, attachmentContentType, thumbnailLName, attachmentThumbnailFolder);
                    int thumbnailMSize = getFileSize(new URL(thumbnailMUrl));
                    awsAttachmentThumbnailMUrl = amazonS3Util.upload(thumbnailMUrl, thumbnailMSize, attachmentContentType, thumbnailMName, attachmentThumbnailFolder);
                    int thumbnailSSize = getFileSize(new URL(thumbnailSUrl));
                    awsAttachmentThumbnailSUrl = amazonS3Util.upload(thumbnailSUrl, thumbnailSSize, attachmentContentType, thumbnailSName, attachmentThumbnailFolder);
                }

                attachment.setUrl(awsAttachmentUrl);
                attachment.setThumbnailL(awsAttachmentThumbnailLUrl);
                attachment.setThumbnailM(awsAttachmentThumbnailMUrl);
                attachment.setThumbnailS(awsAttachmentThumbnailSUrl);
                attachmentRepository.save(attachment);
            }
        }

        double endTime = (System.currentTimeMillis() - startTime)/1000.0;
        System.err.println("attachment migration time: " + endTime);

        return Boolean.TRUE;
    }

    private String getContentType(URL url) throws IOException {

        InputStream inputStream = url.openStream();
        ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);
        Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(imageInputStream);

        try {
            ImageReader reader = imageReaders.next();
            String formatName = reader.getFormatName();

            if ("jpeg".equalsIgnoreCase(reader.getFormatName())) {
                formatName = "image/jpeg";
            } else if ("jpg".equalsIgnoreCase(reader.getFormatName())) {
                formatName = "image/jpeg";
            } else if ("png".equalsIgnoreCase(reader.getFormatName())) {
                formatName = "image/png";
            }

            return formatName;
        } finally {
            imageInputStream.close();
            inputStream.close();
        }
    }

    private int getFileSize(URL url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            conn.getInputStream();
            return conn.getContentLength();
        } catch (IOException e) {
            return 0;
        } finally {
            if(conn != null) {
                conn.disconnect();
            }
        }
    }
}
