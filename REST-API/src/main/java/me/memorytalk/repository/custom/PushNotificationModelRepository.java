package me.memorytalk.repository.custom;

import me.memorytalk.dto.AdminPushNotificationModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PushNotificationModelRepository {

    Page<AdminPushNotificationModel> findAdminPushNotificationModels(Pageable pageable);
}
