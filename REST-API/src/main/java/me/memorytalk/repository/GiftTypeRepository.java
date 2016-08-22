package me.memorytalk.repository;

import me.memorytalk.domain.GiftType;
import me.memorytalk.repository.custom.GiftTypeModelRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftTypeRepository extends JpaRepository<GiftType, Long>,
        JpaSpecificationExecutor<GiftType>,
        GiftTypeModelRepository {

        GiftType findById(Long id);
}
