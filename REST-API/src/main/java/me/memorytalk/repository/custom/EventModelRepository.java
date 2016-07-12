package me.memorytalk.repository.custom;

import me.memorytalk.dto.AdminEventDetailModel;
import me.memorytalk.dto.AdminEventModel;
import me.memorytalk.dto.EventDetailModel;
import me.memorytalk.dto.EventModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;

public interface EventModelRepository {

    List<EventModel> findPremiumEventModels(Date now, String sort, List<String> onGoings, List<Long> forms);

    long countPremiumEventModels(Date now, String sort, List<String> onGoings, List<Long> forms);

    Page<EventModel> findPremiumEventModels(Pageable pageable);

    long countEventModels(Date now, String sort, List<String> onGoings, List<Long> forms);

    Page<EventModel> findEventModels(Date now, String sort, List<String> onGoings, List<Long> forms, Pageable pageable);

    EventDetailModel findEventModel(Long eventId);

    AdminEventDetailModel findAdminEventModel(Long eventId);

    Page<AdminEventModel> findAdminEventModels(String eventId, String eventTitle, String premium, String visible, Pageable pageable);
}
