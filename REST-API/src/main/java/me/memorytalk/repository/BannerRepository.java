package me.memorytalk.repository;

import me.memorytalk.domain.Banner;
import me.memorytalk.repository.custom.BannerModelRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long>,
        JpaSpecificationExecutor<Banner>,
        BannerModelRepository {

        Banner findById(Long id);
}
