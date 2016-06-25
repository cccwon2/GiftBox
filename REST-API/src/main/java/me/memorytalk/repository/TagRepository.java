package me.memorytalk.repository;

import me.memorytalk.domain.Tag;
import me.memorytalk.repository.custom.TagModelRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>,
        JpaSpecificationExecutor<Tag>,
        TagModelRepository {

        Long deleteByEventId(Long eventId);
}
