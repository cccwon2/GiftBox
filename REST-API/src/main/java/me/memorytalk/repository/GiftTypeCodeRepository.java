package me.memorytalk.repository;

import me.memorytalk.domain.GiftTypeCode;
import me.memorytalk.repository.custom.GiftTypeCodeModelRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftTypeCodeRepository extends JpaRepository<GiftTypeCode, Long>,
        JpaSpecificationExecutor<GiftTypeCode>,
        GiftTypeCodeModelRepository {

        GiftTypeCode findById(Long id);

        GiftTypeCode findByName(String name);
}
