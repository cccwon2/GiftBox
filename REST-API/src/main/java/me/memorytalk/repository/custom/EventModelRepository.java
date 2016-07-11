package me.memorytalk.repository.custom;

import me.memorytalk.dto.AdminEventDetailModel;
import me.memorytalk.dto.AdminEventModel;
import me.memorytalk.dto.EventDetailModel;
import me.memorytalk.dto.EventModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventModelRepository {

    List<EventModel> findPremiumEventModels();

    long countPremiumEventModels();

    Page<EventModel> findPremiumEventModels(Pageable pageable);

    long countEventModels();

    Page<EventModel> findEventModels(Pageable pageable);

    EventDetailModel findEventModel(Long eventId);

    AdminEventDetailModel findAdminEventModel(Long eventId);

    Page<AdminEventModel> findAdminEventModels(String eventId, String eventTitle, String premium, String visible, Pageable pageable);
}
