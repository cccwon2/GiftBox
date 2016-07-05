package me.memorytalk.repository.custom;

import me.memorytalk.dto.AdminBannerModel;
import me.memorytalk.dto.BannerModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BannerModelRepository {

    Page<BannerModel> findBannerModels(Pageable pageable);

    Page<AdminBannerModel> findAdminBannerModels(String visible, Pageable pageable);
}
