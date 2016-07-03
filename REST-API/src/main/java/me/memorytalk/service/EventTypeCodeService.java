package me.memorytalk.service;

import me.memorytalk.domain.EventTypeCode;
import me.memorytalk.dto.AdminEventTypeCodeModel;
import me.memorytalk.repository.EventTypeCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@Service
public class EventTypeCodeService {

    @Autowired
    private EventTypeCodeRepository eventTypeCodeRepository;

    protected List<AdminEventTypeCodeModel> getAdminEventTypeCodes(String sort) {

        return eventTypeCodeRepository.findAdminEventTypeCodeModels(sort);
    }

    protected Page<AdminEventTypeCodeModel> getAdminEventTypeCodes(String sort, Pageable pageable) {

        return eventTypeCodeRepository.findAdminEventTypeCodeModels(sort, pageable);
    }

    protected AdminEventTypeCodeModel getAdminEventTypeCode(Long id) {

        return eventTypeCodeRepository.findAdminEventTypeCodeModel(id);
    }

    protected Boolean addAdminEventTypeCode(String name, String color, String sort) {

        EventTypeCode eventTypeCode = eventTypeCodeRepository.findByName(name);
        Assert.isNull(eventTypeCode, "eventTypeCode already exist.");

        eventTypeCode = new EventTypeCode();
        eventTypeCode.setName(name);
        eventTypeCode.setColor(color);
        eventTypeCode.setSort(sort);
        eventTypeCode.setCreatedDate(new Date());
        eventTypeCodeRepository.save(eventTypeCode);

        return Boolean.TRUE;
    }

    protected Boolean editAdminEventTypeCode(Long id, String name, String color, String sort) {

        EventTypeCode eventTypeCode = eventTypeCodeRepository.findById(id);
        Assert.notNull(eventTypeCode, "No eventTypeCode info.");

        eventTypeCode.setName(name);
        eventTypeCode.setColor(color);
        eventTypeCode.setSort(sort);
        eventTypeCodeRepository.save(eventTypeCode);

        return Boolean.TRUE;
    }

    protected Boolean removeAdminEventTypeCode(Long id) {

        EventTypeCode eventTypeCode = eventTypeCodeRepository.findById(id);
        Assert.notNull(eventTypeCode, "No eventTypeCode info.");

        eventTypeCodeRepository.delete(eventTypeCode);

        return Boolean.TRUE;
    }
}
