package me.memorytalk.service;

import com.microsoft.azure.storage.StorageException;
import me.memorytalk.common.constant.GlobalConst;
import me.memorytalk.domain.Banner;
import me.memorytalk.dto.AdminBannerForm;
import me.memorytalk.dto.AdminBannerModel;
import me.memorytalk.dto.BannerModel;
import me.memorytalk.repository.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.text.ParseException;
import java.util.Date;

@Service
public class BannerService {

    @Autowired
    private UploadService uploadService;

    @Autowired
    private BannerRepository bannerRepository;

    public Boolean addAdminBanner(AdminBannerForm requestForm, MultipartFile file)
            throws IOException, InvalidKeyException, StorageException, URISyntaxException, ParseException {

        uploadService.validateImageFile(file);

        Banner banner = new Banner();
        banner.setEventId(requestForm.getEventId());
        banner.setBannerPage(requestForm.getBannerPage());
        banner.setVisible(requestForm.isVisible());
        banner.setCreatedDate(new Date());
        bannerRepository.save(banner);

        uploadService.uploadBannerAttachment(banner, file);

        return Boolean.TRUE;
    }

    public Boolean editAdminBanner(Long bannerId, AdminBannerForm requestForm) {

        Banner banner = bannerRepository.findById(bannerId);
        banner.setEventId(requestForm.getEventId());
        banner.setBannerPage(requestForm.getBannerPage());
        banner.setCreatedDate(new Date());
        bannerRepository.save(banner);

        return Boolean.TRUE;
    }

    public Boolean setAdminBanner(Long bannerId, boolean visible) {

        Banner banner = bannerRepository.findById(bannerId);
        banner.setVisible(visible);
        bannerRepository.save(banner);

        return Boolean.TRUE;
    }

    public Boolean removeAdminBanner(Long bannerId) {

        bannerRepository.delete(bannerId);

        return Boolean.TRUE;
    }

    public Page<BannerModel> getBanners(int page, int size) {

        Sort.Order order = new Sort.Order(Sort.Direction.DESC, GlobalConst.CREATED_DATE);
        Sort sort = new Sort(order);
        Pageable pageable = new PageRequest(page - 1, size, sort);

        return bannerRepository.findBannerModels(pageable);
    }

    public Page<AdminBannerModel> getAdminBanners(String visible, Pageable pageable) {

        return bannerRepository.findAdminBannerModels(visible, pageable);
    }
}
