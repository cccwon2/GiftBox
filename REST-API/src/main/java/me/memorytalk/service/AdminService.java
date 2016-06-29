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

@Service
public class AdminService {

    @Autowired
    private EventService eventService;

    @Autowired
    private GiftService giftService;

    @Autowired
    private TagService tagService;

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

    public Page<AdminTagModel> getTags(String auth, String eventId, String tagName, int page, int size) {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(auth), "Not admin user.");

        Sort.Order order = new Sort.Order(Sort.Direction.DESC, GlobalConst.CREATED_DATE);
        Sort sort = new Sort(order);
        Pageable pageable = new PageRequest(page - 1, size, sort);

        return tagService.getAdminTags(eventId, tagName, pageable);
    }

    public AdminTagModel getTag(String auth, Long id) {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(auth), "Not admin user.");

        return tagService.getAdminTag(id);
    }

    public Boolean editTag(String auth, Long id, String name, String color) {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(auth), "Not admin user.");

        return tagService.editAdminTag(id, name, color);
    }

    public Boolean removeTag(String auth, Long id) {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(auth), "Not admin user.");

        return tagService.removeAdminTag(id);
    }
}
