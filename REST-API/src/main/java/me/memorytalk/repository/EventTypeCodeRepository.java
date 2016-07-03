package me.memorytalk.repository;

import me.memorytalk.domain.EventTypeCode;
import me.memorytalk.repository.custom.EventTypeCodeModelRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTypeCodeRepository extends JpaRepository<EventTypeCode, Long>,
        JpaSpecificationExecutor<EventTypeCode>,
        EventTypeCodeModelRepository {

        EventTypeCode findById(Long id);

        EventTypeCode findByName(String name);
}
