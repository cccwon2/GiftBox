package me.memorytalk.service;

import com.microsoft.azure.storage.StorageException;
import me.memorytalk.common.constant.GlobalConst;
import me.memorytalk.domain.Event;
import me.memorytalk.domain.EventType;
import me.memorytalk.domain.EventTypeCode;
import me.memorytalk.domain.Gift;
import me.memorytalk.dto.*;
import me.memorytalk.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class EventService {

    @Autowired
    private UploadService uploadService;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private GiftRepository giftRepository;

    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Autowired
    private EventTypeCodeRepository eventTypeCodeRepository;

    public Page<EventModel> getEvents(String premium, int page, int size) {

        Sort.Order order = new Sort.Order(Sort.Direction.DESC, GlobalConst.CREATED_DATE);
        Sort sort = new Sort(order);
        Pageable pageable = new PageRequest(page - 1, size, sort);

        Page<EventModel> eventModels = eventRepository.findEventModels(premium, pageable);
        for(EventModel eventModel : eventModels) {
            List<EventTypeModel> eventTypes = eventTypeRepository.findEventTypeModels(eventModel.getId());
            eventModel.setEventTypes(eventTypes);
        }

        return eventModels;
    }

    public EventDetailModel getEvent(Long eventId) {

        EventDetailModel eventDetailModel = eventRepository.findEventModel(eventId);
        eventDetailModel.setAttachments(attachmentRepository.findAttachmentModels(eventId));
        eventDetailModel.setGifts(giftRepository.findGiftModels(eventId));
        eventDetailModel.setEventTypes(eventTypeRepository.findEventTypeModels(eventId));

        return eventDetailModel;
    }

    public Page<AdminEventModel> getAdminEvents(String eventId, String eventTitle, String premium, String visible, Pageable pageable) {
        return eventRepository.findAdminEventModels(eventId, eventTitle, premium, visible, pageable);
    }

    public Boolean addAdminEvent(AdminEventDetailForm requestForm, MultipartFile file)
            throws IOException, InvalidKeyException, StorageException, URISyntaxException, ParseException {

        uploadService.validateImageFile(file);

        Date startDate = null;
        Date endDate = null;
        Date publicationDate = null;

        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeZone tz = TimeZone.getDefault();
        Calendar cal = GregorianCalendar.getInstance(tz);
        int offsetInMillis = tz.getOffset(cal.getTimeInMillis());

        if(requestForm.getStartDate() != null) {
            SimpleDateFormat startDateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
            String startDateToString = startDateFormat.format(requestForm.getStartDate());
            startDate = transFormat.parse(startDateToString);
            startDate = new Date(startDate.getTime() + offsetInMillis);
        }

        if(requestForm.getEndDate() != null) {
            SimpleDateFormat endDateFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
            String endDateToString = endDateFormat.format(requestForm.getEndDate());
            endDate = transFormat.parse(endDateToString);
            endDate = new Date(endDate.getTime() + offsetInMillis);
        }

        if(requestForm.getPublicationDate() != null) {
            SimpleDateFormat publicationDateFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
            String publicationDateToString = publicationDateFormat.format(requestForm.getPublicationDate());
            publicationDate = transFormat.parse(publicationDateToString);
            publicationDate = new Date(publicationDate.getTime() + offsetInMillis);
        }

        Event event = new Event();
        event.setTitle(requestForm.getTitle());
        event.setDescription(requestForm.getDescription());
        event.setCompany(requestForm.getCompany());
        event.setEventTarget(requestForm.getEventTarget());
        event.setEventPage(requestForm.getEventPage());
        event.setPrizePage(requestForm.getPrizePage());
        event.setStartDate(startDate);
        event.setEndDate(endDate);
        event.setPublicationDate(publicationDate);
        event.setPublicationContent1(requestForm.getPublicationContent1());
        event.setPublicationContent2(requestForm.getPublicationContent2());
        event.setPremium(requestForm.isPremium());
        event.setVisible(requestForm.isVisible());
        event.setCreatedDate(new Date());
        eventRepository.save(event);

        uploadService.uploadEventAttachment(event, file);

        addGifts(requestForm.getGifts(), event);
        addEventTypes(requestForm.getEventTypeCodeIds(), event);

        return Boolean.TRUE;
    }

    public AdminEventDetailModel getAdminEvent(Long eventId) {

        AdminEventDetailModel adminEventDetailModel = eventRepository.findAdminEventModel(eventId);
        adminEventDetailModel.setAttachments(attachmentRepository.findAttachmentModels(eventId));
        adminEventDetailModel.setGifts(giftRepository.findGiftModels(eventId));
        adminEventDetailModel.setEventTypeCodeIds(eventTypeRepository.findEventTypeCodeIds(eventId));

        return adminEventDetailModel;
    }

    public Boolean editAdminEvent(Long eventId, AdminEventDetailForm requestForm) throws ParseException {

        Date startDate = null;
        Date endDate = null;
        Date publicationDate = null;

        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeZone tz = TimeZone.getDefault();
        Calendar cal = GregorianCalendar.getInstance(tz);
        int offsetInMillis = tz.getOffset(cal.getTimeInMillis());

        if(requestForm.getStartDate() != null) {
            SimpleDateFormat startDateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
            String startDateToString = startDateFormat.format(requestForm.getStartDate());
            startDate = transFormat.parse(startDateToString);
            startDate = new Date(startDate.getTime() + offsetInMillis);
        }

        if(requestForm.getEndDate() != null) {
            SimpleDateFormat endDateFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
            String endDateToString = endDateFormat.format(requestForm.getEndDate());
            endDate = transFormat.parse(endDateToString);
            endDate = new Date(endDate.getTime() + offsetInMillis);
        }

        if(requestForm.getPublicationDate() != null) {
            SimpleDateFormat publicationDateFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
            String publicationDateToString = publicationDateFormat.format(requestForm.getPublicationDate());
            publicationDate = transFormat.parse(publicationDateToString);
            publicationDate = new Date(publicationDate.getTime() + offsetInMillis);
        }

        Event event = eventRepository.findById(eventId);
        event.setTitle(requestForm.getTitle());
        event.setDescription(requestForm.getDescription());
        event.setCompany(requestForm.getCompany());
        event.setEventTarget(requestForm.getEventTarget());
        event.setEventPage(requestForm.getEventPage());
        event.setPrizePage(requestForm.getPrizePage());
        event.setStartDate(startDate);
        event.setEndDate(endDate);
        event.setPublicationDate(publicationDate);
        event.setPublicationContent1(requestForm.getPublicationContent1());
        event.setPublicationContent2(requestForm.getPublicationContent2());
        event.setPremium(requestForm.isPremium());
        event.setVisible(requestForm.isVisible());
        eventRepository.save(event);

        editGifts(requestForm.getGifts(), event);
        editEventTypes(requestForm.getEventTypeCodeIds(), event);

        return Boolean.TRUE;
    }

    public Boolean setAdminEvent(Long eventId, boolean visible) {

        Event event = eventRepository.findById(eventId);
        event.setVisible(visible);
        eventRepository.save(event);

        return Boolean.TRUE;
    }

    public Boolean removeAdminEvent(Long eventId) {

        eventRepository.delete(eventId);

        return Boolean.TRUE;
    }

    private void addGifts(List<AdminGiftForm> adminGiftForms, Event event) {

        if(adminGiftForms != null && adminGiftForms.size() > 0) {
            for(AdminGiftForm adminGiftForm : adminGiftForms) {
                Gift gift = new Gift();
                gift.setProduct(adminGiftForm.getProduct());
                gift.setCount(adminGiftForm.getCount());
                gift.setEvent(event);
                gift.setCreatedDate(new Date());
                giftRepository.save(gift);
            }
        }
    }

    private void editGifts(List<AdminGiftForm> adminGiftForms, Event event) {

        giftRepository.deleteByEventId(event.getId());
        if(adminGiftForms != null && adminGiftForms.size() > 0) {
            for(AdminGiftForm adminGiftForm : adminGiftForms) {
                Gift gift = new Gift();
                gift.setProduct(adminGiftForm.getProduct());
                gift.setCount(adminGiftForm.getCount());
                gift.setEvent(event);
                gift.setCreatedDate(new Date());
                giftRepository.save(gift);
            }
        }
    }

    private void addEventTypes(List<Long> eventTypeCodeIds, Event event) {

        if(eventTypeCodeIds.size() > 0) {
            for(Long eventTypeCodeId : eventTypeCodeIds) {
                EventTypeCode eventTypeCode = eventTypeCodeRepository.findById(eventTypeCodeId);
                EventType eventType = new EventType();
                eventType.setEvent(event);
                eventType.setEventTypeCode(eventTypeCode);
                eventType.setCreatedDate(new Date());
                eventTypeRepository.save(eventType);
            }
        }
    }

    private void editEventTypes(List<Long> eventTypeCodeIds, Event event) {

        eventTypeRepository.deleteByEventId(event.getId());
        if(eventTypeCodeIds.size() > 0) {
            for (Long eventTypeCodeId : eventTypeCodeIds) {
                EventTypeCode eventTypeCode = eventTypeCodeRepository.findById(eventTypeCodeId);
                EventType eventType = new EventType();
                eventType.setEvent(event);
                eventType.setEventTypeCode(eventTypeCode);
                eventType.setCreatedDate(new Date());
                eventTypeRepository.save(eventType);
            }
        }
    }
}
