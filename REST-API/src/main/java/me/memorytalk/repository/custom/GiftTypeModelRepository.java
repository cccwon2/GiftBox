package me.memorytalk.repository.custom;

import me.memorytalk.dto.AdminGiftTypeModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GiftTypeModelRepository {

    List<Long> findGiftTypeCodeIds(Long eventId);

    List<String> findGiftTypeModels(Long eventId);

    Long deleteByEventId(Long eventId);

    AdminGiftTypeModel findAdminGiftTypeModel(Long id);

    Page<AdminGiftTypeModel> findAdminGiftTypeModels(String eventId, Pageable pageable);
}
