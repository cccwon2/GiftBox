package me.memorytalk.repository.custom;

import me.memorytalk.dto.AdminEventDetailModel;
import me.memorytalk.dto.AdminEventModel;
import me.memorytalk.dto.EventDetailModel;
import me.memorytalk.dto.EventModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventModelRepository {

    Page<EventModel> findEventModels(Pageable pageable);

    EventDetailModel findEventModel(Long eventId);

    AdminEventDetailModel findAdminEventModel(Long eventId);

    Page<AdminEventModel> findAdminEventModels(String visible, Pageable pageable);
}
