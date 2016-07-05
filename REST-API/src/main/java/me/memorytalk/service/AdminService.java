package me.memorytalk.service;

import com.microsoft.azure.storage.StorageException;
import me.memorytalk.common.constant.GlobalConst;
import me.memorytalk.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.text.ParseException;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private BannerService bannerService;

    @Autowired
    private EventService eventService;

    @Autowired
    private GiftService giftService;

    @Autowired
    private EventTypeService eventTypeService;

    @Autowired
    private EventTypeCodeService eventTypeCodeService;

    @Autowired
    private PopupService popupService;

    public Boolean login(AdminLoginForm requestForm) {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(requestForm.getPassword()), "Not admin user.");

        return Boolean.TRUE;
    }

    public Page<AdminEventModel> getEvents(String auth, String eventId, String eventTitle, String premium, String visible, int page, int size) {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(auth), "Not admin user.");

        Sort.Order order = new Sort.Order(Sort.Direction.DESC, GlobalConst.CREATED_DATE);
        Sort sort = new Sort(order);
        Pageable pageable = new PageRequest(page - 1, size, sort);

        return eventService.getAdminEvents(eventId, eventTitle, premium, visible, pageable);
    }

    public Boolean addEvent(String auth, AdminEventDetailForm requestForm, MultipartFile file)
            throws IOException, InvalidKeyException, StorageException, URISyntaxException, ParseException {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(auth), "Not admin user.");

        return eventService.addAdminEvent(requestForm, file);
    }

    public AdminEventDetailModel getEvent(String auth, Long eventId) {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(auth), "Not admin user.");

        return eventService.getAdminEvent(eventId);
    }

    public Boolean editEvent(String auth, Long eventId, AdminEventDetailForm requestForm) throws ParseException {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(auth), "Not admin user.");

        return eventService.editAdminEvent(eventId, requestForm);
    }

    public Boolean setEvent(String auth, Long eventId, boolean visible) {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(auth), "Not admin user.");

        return eventService.setAdminEvent(eventId, visible);
    }

    public Boolean removeEvent(String auth, Long eventId) {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(auth), "Not admin user.");

        return eventService.removeAdminEvent(eventId);
    }

    public Page<AdminGiftModel> getGifts(String auth, String eventId, String product, int page, int size) {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(auth), "Not admin user.");

        Sort.Order order = new Sort.Order(Sort.Direction.DESC, GlobalConst.CREATED_DATE);
        Sort sort = new Sort(order);
        Pageable pageable = new PageRequest(page - 1, size, sort);

        return giftService.getAdminGifts(eventId, product, pageable);
    }

    public AdminGiftModel getGift(String auth, Long id) {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(auth), "Not admin user.");

        return giftService.getAdminGift(id);
    }

    public Boolean editGift(String auth, Long id, String product, int count) {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(auth), "Not admin user.");

        return giftService.editAdminGift(id, product, count);
    }

    public Boolean removeGift(String auth, Long id) {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(auth), "Not admin user.");

        return giftService.removeAdminGift(id);
    }

    public Page<AdminEventTypeModel> getEventTypes(String auth, String eventId, String sort, int page, int size) {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(auth), "Not admin user.");

        Sort.Order order = new Sort.Order(Sort.Direction.DESC, GlobalConst.CREATED_DATE);
        Pageable pageable = new PageRequest(page - 1, size, new Sort(order));

        return eventTypeService.getAdminEventTypes(eventId, sort, pageable);
    }

    public AdminEventTypeModel getEventType(String auth, Long id) {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(auth), "Not admin user.");

        return eventTypeService.getAdminEventType(id);
    }

    public Boolean editEventType(String auth, Long id, Long eventTypeCodeId) {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(auth), "Not admin user.");

        return eventTypeService.editAdminEventType(id, eventTypeCodeId);
    }

    public Boolean removeEventType(String auth, Long id) {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(auth), "Not admin user.");

        return eventTypeService.removeAdminEventType(id);
    }

    public List<AdminEventTypeCodeModel> getEventTypeCodes(String auth, String sort) {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(auth), "Not admin user.");

        return eventTypeCodeService.getAdminEventTypeCodes(sort);
    }

    public Page<AdminEventTypeCodeModel> getEventTypeCodes(String auth, String sort, int page, int size) {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(auth), "Not admin user.");

        Sort.Order order = new Sort.Order(Sort.Direction.DESC, GlobalConst.CREATED_DATE);
        Pageable pageable = new PageRequest(page - 1, size, new Sort(order));

        return eventTypeCodeService.getAdminEventTypeCodes(sort, pageable);
    }

    public Boolean addEventTypeCode(String auth, String name, String color, String sort) {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(auth), "Not admin user.");

        return eventTypeCodeService.addAdminEventTypeCode(name, color, sort);
    }

    public Boolean editEventTypeCode(String auth, Long id, String name, String color, String sort) {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(auth), "Not admin user.");

        return eventTypeCodeService.editAdminEventTypeCode(id, name, color, sort);
    }

    public Boolean removeEventTypeCode(String auth, Long id) {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(auth), "Not admin user.");

        return eventTypeCodeService.removeAdminEventTypeCode(id);
    }

    public Page<AdminBannerModel> getBanners(String auth, String visible, int page, int size) {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(auth), "Not admin user.");

        Sort.Order order = new Sort.Order(Sort.Direction.DESC, GlobalConst.CREATED_DATE);
        Sort sort = new Sort(order);
        Pageable pageable = new PageRequest(page - 1, size, sort);

        return bannerService.getAdminBanners(visible, pageable);
    }

    public Boolean addBanner(String auth, AdminBannerForm requestForm, MultipartFile file)
            throws IOException, InvalidKeyException, StorageException, URISyntaxException, ParseException {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(auth), "Not admin user.");

        return bannerService.addAdminBanner(requestForm, file);
    }

    public Boolean editBanner(String auth, Long bannerId, AdminBannerForm requestForm) throws ParseException {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(auth), "Not admin user.");

        return bannerService.editAdminBanner(bannerId, requestForm);
    }

    public Boolean setBanner(String auth, Long bannerId, boolean visible) {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(auth), "Not admin user.");

        return bannerService.setAdminBanner(bannerId, visible);
    }

    public Boolean removeBanner(String auth, Long bannerId) {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(auth), "Not admin user.");

        return bannerService.removeAdminBanner(bannerId);
    }

    public Page<AdminPopupModel> getPopups(String auth, String visible, int page, int size) {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(auth), "Not admin user.");

        Sort.Order order = new Sort.Order(Sort.Direction.DESC, GlobalConst.CREATED_DATE);
        Sort sort = new Sort(order);
        Pageable pageable = new PageRequest(page - 1, size, sort);

        return popupService.getAdminPopups(visible, pageable);
    }

    public Boolean addPopup(String auth, AdminPopupForm requestForm, MultipartFile file)
            throws IOException, InvalidKeyException, StorageException, URISyntaxException, ParseException {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(auth), "Not admin user.");

        return popupService.addAdminPopup(requestForm, file);
    }

    public Boolean editPopup(String auth, Long popupId, AdminPopupForm requestForm) throws ParseException {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(auth), "Not admin user.");

        return popupService.editAdminPopup(popupId, requestForm);
    }

    public Boolean setPopup(String auth, Long popupId, boolean visible) {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(auth), "Not admin user.");

        return popupService.setAdminPopup(popupId, visible);
    }

    public Boolean removePopup(String auth, Long popupId) {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(auth), "Not admin user.");

        return popupService.removeAdminPopup(popupId);
    }
}
