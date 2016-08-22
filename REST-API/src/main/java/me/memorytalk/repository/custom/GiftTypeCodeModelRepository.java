package me.memorytalk.repository.custom;

import me.memorytalk.dto.AdminGiftTypeCodeModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GiftTypeCodeModelRepository {

    Page<AdminGiftTypeCodeModel> findAdminGiftTypeCodeModels(Pageable pageable);
}
