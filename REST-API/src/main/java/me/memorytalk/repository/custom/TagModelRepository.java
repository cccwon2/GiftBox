package me.memorytalk.repository.custom;

import me.memorytalk.dto.AdminTagModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagModelRepository {

    List<String> findTags(Long eventId);

    Long deleteByEventId(Long eventId);

    AdminTagModel findAdminTagModel(Long id);

    Page<AdminTagModel> findAdminTagModels(String eventId, String tagName, Pageable pageable);
}
