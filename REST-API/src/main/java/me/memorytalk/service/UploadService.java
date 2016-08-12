package me.memorytalk.service;

import com.microsoft.azure.storage.StorageException;
import me.memorytalk.common.base.BaseObject;
import me.memorytalk.common.util.AmazonS3Util;
import me.memorytalk.domain.Attachment;
import me.memorytalk.domain.Banner;
import me.memorytalk.domain.Event;
import me.memorytalk.domain.Popup;
import me.memorytalk.repository.AttachmentRepository;
import net.coobird.thumbnailator.Thumbnails;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

@Service
public class UploadService extends BaseObject {

    @Autowired
    private AttachmentRepository attachmentRepository;

    protected Attachment uploadEventAttachment(Event event, MultipartFile file)
            throws IOException, InvalidKeyException, StorageException, URISyntaxException {

        // image(jpg/jpeg/png) validation
        String contentType = validateImageFile(file);
        String originalFilename = file.getOriginalFilename();
        long size = file.getSize();

        InputStream inputStream = file.getInputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        bufferedInputStream.mark(Integer.MAX_VALUE);
        BufferedImage bufferedImage = ImageIO.read(bufferedInputStream);
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        Attachment attachment = new Attachment();
        attachment.setContentType(contentType);
        attachment.setOriginalFilename(originalFilename);
        attachment.setSize(size);
        attachment.setSort(1);
        attachment.setEvent(event);
        attachment.setCreatedDate(new Date());
        attachment.setUrl("");
        attachment.setThumbnailS("");
        attachment.setThumbnailM("");
        attachment.setThumbnailL("");
        // width, height
        attachment.setWidth(width);
        attachment.setHeight(height);
        attachment.setCreatedDate(new Date());

        attachmentRepository.save(attachment);

        String attachmentId = attachment.getId().toString();
        AmazonS3Util amazonS3Util = new AmazonS3Util();
        String filename = String.format("%s_%s", attachmentId, new DateTime(DateTimeZone.UTC).toString("yyyyMMddHHmmss"));

        // reset bufferedInputStream
        bufferedInputStream.reset();
        String url = amazonS3Util.upload(bufferedInputStream, size, contentType, filename, "attachments");
        addThumbnails(bufferedImage, filename, contentType);

        String attachmentName = url.substring(url.lastIndexOf("/") + 1);
        String thumbnailS  = AmazonS3Util.CDN_DOMAIN + "/attachment-thumbs/" + attachmentName + "_thumb_S";
        String thumbnailM = AmazonS3Util.CDN_DOMAIN + "/attachment-thumbs/" + attachmentName + "_thumb_M";
        String thumbnailL = AmazonS3Util.CDN_DOMAIN + "/attachment-thumbs/" + attachmentName + "_thumb_L";

        attachment.setUrl(url);
        attachment.setThumbnailS(thumbnailS);
        attachment.setThumbnailM(thumbnailM);
        attachment.setThumbnailL(thumbnailL);

        return attachmentRepository.save(attachment);
    }

    protected Attachment uploadBannerAttachment(Banner banner, MultipartFile file)
            throws IOException, InvalidKeyException, StorageException, URISyntaxException {

        // image(jpg/jpeg/png) validation
        String contentType = validateImageFile(file);
        String originalFilename = file.getOriginalFilename();
        long size = file.getSize();

        InputStream inputStream = file.getInputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        bufferedInputStream.mark(Integer.MAX_VALUE);
        BufferedImage bufferedImage = ImageIO.read(bufferedInputStream);
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        Attachment attachment = new Attachment();
        attachment.setContentType(contentType);
        attachment.setOriginalFilename(originalFilename);
        attachment.setSize(size);
        attachment.setSort(1);
        attachment.setBanner(banner);
        attachment.setCreatedDate(new Date());
        attachment.setUrl("");
        attachment.setThumbnailS("");
        attachment.setThumbnailM("");
        attachment.setThumbnailL("");
        // width, height
        attachment.setWidth(width);
        attachment.setHeight(height);
        attachment.setCreatedDate(new Date());

        attachmentRepository.save(attachment);

        String attachmentId = attachment.getId().toString();
        AmazonS3Util amazonS3Util = new AmazonS3Util();
        String filename = String.format("%s_%s", attachmentId, new DateTime(DateTimeZone.UTC).toString("yyyyMMddHHmmss"));

        // reset bufferedInputStream
        bufferedInputStream.reset();
        String url = amazonS3Util.upload(bufferedInputStream, size, contentType, filename, "attachments");
        addThumbnails(bufferedImage, filename, contentType);

        String attachmentName = url.substring(url.lastIndexOf("/") + 1);
        String thumbnailS  = AmazonS3Util.CDN_DOMAIN + "/attachment-thumbs/" + attachmentName + "_thumb_S";
        String thumbnailM = AmazonS3Util.CDN_DOMAIN + "/attachment-thumbs/" + attachmentName + "_thumb_M";
        String thumbnailL = AmazonS3Util.CDN_DOMAIN + "/attachment-thumbs/" + attachmentName + "_thumb_L";

        attachment.setUrl(url);
        attachment.setThumbnailS(thumbnailS);
        attachment.setThumbnailM(thumbnailM);
        attachment.setThumbnailL(thumbnailL);

        return attachmentRepository.save(attachment);
    }

    protected Attachment uploadPopupAttachment(Popup popup, MultipartFile file)
            throws IOException, InvalidKeyException, StorageException, URISyntaxException {

        // image(jpg/jpeg/png) validation
        String contentType = validateImageFile(file);
        String originalFilename = file.getOriginalFilename();
        long size = file.getSize();

        InputStream inputStream = file.getInputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        bufferedInputStream.mark(Integer.MAX_VALUE);
        BufferedImage bufferedImage = ImageIO.read(bufferedInputStream);
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        Attachment attachment = new Attachment();
        attachment.setContentType(contentType);
        attachment.setOriginalFilename(originalFilename);
        attachment.setSize(size);
        attachment.setSort(1);
        attachment.setPopup(popup);
        attachment.setCreatedDate(new Date());
        attachment.setUrl("");
        attachment.setThumbnailS("");
        attachment.setThumbnailM("");
        attachment.setThumbnailL("");
        // width, height
        attachment.setWidth(width);
        attachment.setHeight(height);
        attachment.setCreatedDate(new Date());

        attachmentRepository.save(attachment);

        String attachmentId = attachment.getId().toString();
        AmazonS3Util amazonS3Util = new AmazonS3Util();
        String filename = String.format("%s_%s", attachmentId, new DateTime(DateTimeZone.UTC).toString("yyyyMMddHHmmss"));

        // reset bufferedInputStream
        bufferedInputStream.reset();
        String url = amazonS3Util.upload(bufferedInputStream, size, contentType, filename, "attachments");
        addThumbnails(bufferedImage, filename, contentType);

        String attachmentName = url.substring(url.lastIndexOf("/") + 1);
        String thumbnailS  = AmazonS3Util.CDN_DOMAIN + "/attachment-thumbs/" + attachmentName + "_thumb_S";
        String thumbnailM = AmazonS3Util.CDN_DOMAIN + "/attachment-thumbs/" + attachmentName + "_thumb_M";
        String thumbnailL = AmazonS3Util.CDN_DOMAIN + "/attachment-thumbs/" + attachmentName + "_thumb_L";

        attachment.setUrl(url);
        attachment.setThumbnailS(thumbnailS);
        attachment.setThumbnailM(thumbnailM);
        attachment.setThumbnailL(thumbnailL);

        return attachmentRepository.save(attachment);
    }

    private void addThumbnails(BufferedImage originalImage, String fName, String contentType)
            throws IOException, InvalidKeyException, StorageException, URISyntaxException {

        //inputStream.reset();
        //BufferedImage originalImage = ImageIO.read(inputStream);
        String[] sub = {"_thumb_S", "_thumb_M", "_thumb_L"};
        // large size 는 원본으로 대체
        int width;
        int originalImageWidth = originalImage.getWidth();
        int originalImageHeight = originalImage.getHeight();
        int[] widths = {300, 600, 900};
        long size;
        String imageFormat = "jpg";
        if(contentType.toLowerCase().contains("png"))
            imageFormat = "png";
        BufferedInputStream bufferedInputStream;
        AmazonS3Util amazonS3Util = new AmazonS3Util();

        for (int i = 0; i < widths.length; i++) {
            String fileName = fName + sub[i];
            if (originalImageWidth < widths[i]) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(originalImage, imageFormat, baos);
                size = baos.size();
                bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(baos.toByteArray()));
            } else {
                width = widths[i];
                int height = (int) ((float) originalImageHeight * (float) width / originalImageWidth);
                BufferedImage thumbnail = Thumbnails.of(originalImage)
                        .size(width, height)
                        .asBufferedImage();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(thumbnail, imageFormat, baos);
                size = baos.size();
                bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(baos.toByteArray()));
            }

            amazonS3Util.upload(bufferedInputStream, size, contentType, fileName, "attachment-thumbs");
        }
    }

    protected String  validateImageFile(MultipartFile file)
            throws IOException, URISyntaxException {

        Assert.notNull(file, "Null File.");
        Assert.isTrue(!file.isEmpty(), "Empty File.");

        String formatName = null;
        InputStream inputStream = file.getInputStream();
        ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);
        Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(imageInputStream);

        try {
            if (!imageReaders.hasNext())
                throw new IIOException(messageSource.getMessage("file.type.unsupported", null, Locale.getDefault()));

            ImageReader reader = imageReaders.next();
            formatName = reader.getFormatName();

            if ("jpeg".equalsIgnoreCase(reader.getFormatName())) {
                formatName = "image/jpeg";
            } else if ("jpg".equalsIgnoreCase(reader.getFormatName())) {
                formatName = "image/jpeg";
            } else if ("png".equalsIgnoreCase(reader.getFormatName())) {
                formatName = "image/png";
            } else if ("gif".equalsIgnoreCase(reader.getFormatName())) {
                formatName = "image/gif";
            } else {
                throw new IIOException(messageSource.getMessage("image.type.unsupported", null, Locale.getDefault()));
            }
        } finally {
            imageInputStream.close();
            inputStream.close();
        }

        return formatName;
    }

}
