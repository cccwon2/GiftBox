package me.memorytalk.service;

import me.memorytalk.domain.EventType;
import me.memorytalk.dto.AdminEventTypeModel;
import me.memorytalk.repository.EventTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class EventTypeService {

    @Autowired
    private EventTypeRepository eventTypeRepository;

    protected Page<AdminEventTypeModel> getAdminEventTypes(String eventId, String sort, Pageable pageable) {

        return eventTypeRepository.findAdminEventTypeModels(eventId, sort, pageable);
    }

    protected AdminEventTypeModel getAdminEventType(Long id) {

        return eventTypeRepository.findAdminEventTypeModel(id);
    }

    protected Boolean addAdminEventType(String name, String color, String sort) {

        EventType eventType = new EventType();
        eventType.setName(name);
        eventType.setColor(color);
        eventType.setSort(sort);
        eventTypeRepository.save(eventType);

        return Boolean.TRUE;
    }

    protected Boolean editAdminEventType(Long id, String name, String color, String sort) {

        EventType eventType = eventTypeRepository.findById(id);
        Assert.notNull(eventType, "No eventType info.");

        eventType.setName(name);
        eventType.setColor(color);
        eventType.setSort(sort);
        eventTypeRepository.save(eventType);

        return Boolean.TRUE;
    }

    protected Boolean removeAdminEventType(Long id) {

        EventType eventType = eventTypeRepository.findById(id);
        Assert.notNull(eventType, "No eventType info.");

        eventTypeRepository.delete(eventType);

        return Boolean.TRUE;
    }
}
