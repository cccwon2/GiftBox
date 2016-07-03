package me.memorytalk.repository;

import me.memorytalk.domain.EventType;
import me.memorytalk.repository.custom.EventTypeModelRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType, Long>,
        JpaSpecificationExecutor<EventType>,
        EventTypeModelRepository {

        EventType findById(Long id);
}
