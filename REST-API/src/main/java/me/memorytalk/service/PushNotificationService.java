package me.memorytalk.service;

import me.memorytalk.domain.Event;
import me.memorytalk.domain.PushNotification;
import me.memorytalk.dto.AdminPushNotificationModel;
import me.memorytalk.repository.EventRepository;
import me.memorytalk.repository.PushNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;

@Service
public class PushNotificationService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    PushNotificationRepository pushNotificationRepository;

    public Boolean addAdminPushNotification(String title, String body, Long eventId) {

        Assert.notNull(title, "No Push title.");
        Assert.notNull(body, "No Push body.");

        PushNotification pushNotification = new PushNotification();
        pushNotification.setTitle(title);
        pushNotification.setBody(body);
        if(eventId != null) {
            Event event = eventRepository.findById(eventId);
            pushNotification.setEvent(event);
        }
        pushNotification.setCreatedDate(new Date());

        pushNotificationRepository.save(pushNotification);

        return Boolean.TRUE;
    }

    public Page<AdminPushNotificationModel> getAdminPushNotifications(Pageable pageable) {

        return pushNotificationRepository.findAdminPushNotificationModels(pageable);
    }
}
