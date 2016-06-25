package me.memorytalk.service;

import com.microsoft.azure.storage.StorageException;
import me.memorytalk.common.constant.GlobalConst;
import me.memorytalk.dto.AdminEventDetailForm;
import me.memorytalk.dto.AdminEventDetailModel;
import me.memorytalk.dto.AdminEventModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Page<AdminEventModel> getEvents(String password, String visible, int page, int size) {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(password), "Not admin user.");

        Pageable pageable = new PageRequest(page - 1, size);

        return eventService.getAdminEvents(visible, pageable);
    }

    public Boolean addEvent(String password, AdminEventDetailForm requestForm, MultipartFile file)
            throws IOException, InvalidKeyException, StorageException, URISyntaxException, ParseException {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(password), "Not admin user.");

        return eventService.addAdminEvent(requestForm, file);
    }

    public AdminEventDetailModel getEvent(String password, Long eventId) {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(password), "Not admin user.");

        return eventService.getAdminEvent(eventId);
    }

    public Boolean editEvent(String password, Long eventId, AdminEventDetailForm requestForm) throws ParseException {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(password), "Not admin user.");

        return eventService.editAdminEvent(eventId, requestForm);
    }

    public Boolean removeEvent(String password, Long eventId) {

        Assert.isTrue(GlobalConst.ADMIN_PASSWORD.equals(password), "Not admin user.");

        return eventService.removeAdminEvent(eventId);
    }
}
