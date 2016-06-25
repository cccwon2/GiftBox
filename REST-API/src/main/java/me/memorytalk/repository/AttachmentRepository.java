package me.memorytalk.repository;

import me.memorytalk.domain.Attachment;
import me.memorytalk.repository.custom.AttachmentModelRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long>,
        JpaSpecificationExecutor<Attachment>,
        AttachmentModelRepository {
}
