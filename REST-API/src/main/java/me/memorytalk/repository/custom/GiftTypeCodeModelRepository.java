package me.memorytalk.repository.custom;

import me.memorytalk.dto.AdminGiftTypeCodeModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GiftTypeCodeModelRepository {

    List<AdminGiftTypeCodeModel> findAdminGiftTypeCodeModels();

    Page<AdminGiftTypeCodeModel> findAdminGiftTypeCodeModels(Pageable pageable);
}
