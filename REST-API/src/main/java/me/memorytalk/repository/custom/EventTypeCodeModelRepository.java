package me.memorytalk.repository.custom;

import me.memorytalk.dto.AdminEventTypeCodeModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventTypeCodeModelRepository {

    List<AdminEventTypeCodeModel> findAdminEventTypeCodeModels(String sort);

    Page<AdminEventTypeCodeModel> findAdminEventTypeCodeModels(String sort, Pageable pageable);
}
