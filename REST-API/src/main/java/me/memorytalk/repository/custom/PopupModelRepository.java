package me.memorytalk.repository.custom;

import me.memorytalk.dto.AdminPopupModel;
import me.memorytalk.dto.PopupModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PopupModelRepository {

    Page<PopupModel> findPopupModels(Pageable pageable);

    Page<AdminPopupModel> findAdminPopupModels(String visible, Pageable pageable);
}
