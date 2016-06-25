package me.memorytalk.repository;

import me.memorytalk.domain.Gift;
import me.memorytalk.repository.custom.GiftModelRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftRepository extends JpaRepository<Gift, Long>,
        JpaSpecificationExecutor<Gift>,
        GiftModelRepository{

        Gift findById(Long id);
}
