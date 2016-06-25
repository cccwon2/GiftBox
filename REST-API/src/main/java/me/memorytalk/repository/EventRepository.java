package me.memorytalk.repository;

import me.memorytalk.domain.Event;
import me.memorytalk.repository.custom.EventModelRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>,
        JpaSpecificationExecutor<Event>,
        EventModelRepository {

        Event findById(Long id);
}
