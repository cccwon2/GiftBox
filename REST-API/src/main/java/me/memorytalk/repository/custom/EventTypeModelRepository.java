package me.memorytalk.repository.custom;

import me.memorytalk.dto.AdminEventTypeModel;
import me.memorytalk.dto.EventTypeModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventTypeModelRepository {

    List<Long> findEventTypeCodeIds(Long eventId);

    List<EventTypeModel> findEventTypeModels(Long eventId);

    Long deleteByEventId(Long eventId);

    AdminEventTypeModel findAdminEventTypeModel(Long id);

    Page<AdminEventTypeModel> findAdminEventTypeModels(String eventId, String sort, Pageable pageable);
}
