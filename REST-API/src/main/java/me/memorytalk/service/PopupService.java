package me.memorytalk.service;

import com.microsoft.azure.storage.StorageException;
import me.memorytalk.common.constant.GlobalConst;
import me.memorytalk.domain.Popup;
import me.memorytalk.dto.AdminPopupForm;
import me.memorytalk.dto.AdminPopupModel;
import me.memorytalk.dto.PopupModel;
import me.memorytalk.repository.PopupRepository;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

@Service
public class PopupService {

    @Autowired
    private UploadService uploadService;

    @Autowired
    private PopupRepository popupRepository;

    public Boolean addAdminPopup(AdminPopupForm requestForm, MultipartFile file)
            throws IOException, InvalidKeyException, StorageException, URISyntaxException, ParseException {

        uploadService.validateImageFile(file);

        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeZone tz = TimeZone.getDefault();
        Calendar cal = GregorianCalendar.getInstance(tz);
        int offsetInMillis = tz.getOffset(cal.getTimeInMillis());

        SimpleDateFormat startDateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String startDateToString = startDateFormat.format(requestForm.getStartDate());
        Date startDate = transFormat.parse(startDateToString);
        startDate = new Date(startDate.getTime() + offsetInMillis);

        SimpleDateFormat endDateFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        String endDateToString = endDateFormat.format(requestForm.getEndDate());
        Date endDate = transFormat.parse(endDateToString);
        endDate = new Date(endDate.getTime() + offsetInMillis);

        Popup popup = new Popup();
        popup.setEventId(requestForm.getEventId());
        popup.setPopupPage(requestForm.getPopupPage());
        popup.setVisible(requestForm.isVisible());
        popup.setStartDate(startDate);
        popup.setEndDate(endDate);
        popup.setCreatedDate(new Date());
        popupRepository.save(popup);

        uploadService.uploadPopupAttachment(popup, file);

        return Boolean.TRUE;
    }

    public Boolean editAdminPopup(Long popupId, AdminPopupForm requestForm)
            throws ParseException {

        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeZone tz = TimeZone.getDefault();
        Calendar cal = GregorianCalendar.getInstance(tz);
        int offsetInMillis = tz.getOffset(cal.getTimeInMillis());

        SimpleDateFormat startDateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String startDateToString = startDateFormat.format(requestForm.getStartDate());
        Date startDate = transFormat.parse(startDateToString);
        startDate = new Date(startDate.getTime() + offsetInMillis);

        SimpleDateFormat endDateFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        String endDateToString = endDateFormat.format(requestForm.getEndDate());
        Date endDate = transFormat.parse(endDateToString);
        endDate = new Date(endDate.getTime() + offsetInMillis);

        Popup popup = popupRepository.findById(popupId);
        popup.setEventId(requestForm.getEventId());
        popup.setPopupPage(requestForm.getPopupPage());
        popup.setVisible(requestForm.isVisible());
        popup.setStartDate(startDate);
        popup.setEndDate(endDate);
        popup.setCreatedDate(new Date());
        popupRepository.save(popup);

        return Boolean.TRUE;
    }

    public Boolean setAdminPopup(Long popupId, boolean visible) {

        Popup popup = popupRepository.findById(popupId);
        popup.setVisible(visible);
        popupRepository.save(popup);

        return Boolean.TRUE;
    }

    public Boolean removeAdminPopup(Long popupId) {

        popupRepository.delete(popupId);

        return Boolean.TRUE;
    }

    public Page<PopupModel> getPopups(int page, int size) {

        Sort.Order order = new Sort.Order(Sort.Direction.DESC, GlobalConst.CREATED_DATE);
        Sort sort = new Sort(order);
        Pageable pageable = new PageRequest(page - 1, size, sort);

        return popupRepository.findPopupModels(pageable);
    }

    public Page<AdminPopupModel> getAdminPopups(String visible, Pageable pageable) {

        return popupRepository.findAdminPopupModels(visible, pageable);
    }
}
