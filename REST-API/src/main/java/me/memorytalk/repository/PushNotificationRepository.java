package me.memorytalk.repository;

import me.memorytalk.domain.PushNotification;
import me.memorytalk.repository.custom.PushNotificationModelRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PushNotificationRepository extends JpaRepository<PushNotification, Long>,
        JpaSpecificationExecutor<PushNotification>,
        PushNotificationModelRepository {
}
