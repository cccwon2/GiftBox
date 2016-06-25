package me.memorytalk.repository.custom;

import me.memorytalk.dto.AdminGiftModel;
import me.memorytalk.dto.GiftModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GiftModelRepository {

    List<GiftModel> findGiftModels(Long eventId);

    Long deleteByEventId(Long eventId);

    AdminGiftModel findAdminGiftModel(Long id);

    Page<AdminGiftModel> findAdminGiftModels(String eventId, String product, Pageable pageable);
}
