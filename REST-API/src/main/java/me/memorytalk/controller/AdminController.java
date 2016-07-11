package me.memorytalk.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.storage.StorageException;
import io.swagger.annotations.Api;
import me.memorytalk.common.base.RestResponse;
import me.memorytalk.dto.AdminBannerForm;
import me.memorytalk.dto.AdminEventDetailForm;
import me.memorytalk.dto.AdminPopupForm;
import me.memorytalk.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.text.ParseException;

@Api(value = "Admin API", description = "Admin API", basePath = "/admin")
@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/admin/events", method = RequestMethod.GET)
    public ResponseEntity<RestResponse> events(
            @RequestHeader("Authorization") String auth,
            @RequestParam("eventId") String eventId,
            @RequestParam("eventTitle") String eventTitle,
            @RequestParam("premium") String premium,
            @RequestParam("visible") String visible,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Event List",
                adminService.getEvents(auth, eventId, eventTitle, premium, visible, page, size)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/events", method = RequestMethod.POST)
    public ResponseEntity<RestResponse> addEvent(
            @RequestHeader("Authorization") String auth,
            @RequestPart("event") String event,
            @RequestPart(required = false, name = "file") MultipartFile file)
            throws IOException, InvalidKeyException, ParseException, StorageException, URISyntaxException {

        AdminEventDetailForm requestForm = new ObjectMapper().readValue(event, AdminEventDetailForm.class);

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Add Event",
                adminService.addEvent(auth, requestForm, file)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/events/{id}", method = RequestMethod.GET)
    public ResponseEntity<RestResponse> eventDetail(
            @RequestHeader("Authorization") String auth,
            @PathVariable("id") Long id) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Event Detail",
                adminService.getEvent(auth, id)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/events/{id}", method = RequestMethod.POST)
    public ResponseEntity<RestResponse> editEvent(
            @RequestHeader("Authorization") String auth,
            @PathVariable("id") Long id,
            @RequestBody AdminEventDetailForm requestForm) throws ParseException {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Edit Event",
                adminService.editEvent(auth, id, requestForm)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/events/{id}/visible", method = RequestMethod.POST)
    public ResponseEntity<RestResponse> setEventVisible(
            @RequestHeader("Authorization") String auth,
            @PathVariable("id") Long id,
            @RequestParam("visible") boolean visible) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Set Event Visible",
                adminService.setEventVisible(auth, id, visible)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/events/{id}/premium", method = RequestMethod.POST)
    public ResponseEntity<RestResponse> setEventPremium(
            @RequestHeader("Authorization") String auth,
            @PathVariable("id") Long id,
            @RequestParam("premium") boolean premium) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Set Event Premium",
                adminService.setEventPremium(auth, id, premium)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/events/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<RestResponse> removeEvent(
            @RequestHeader("Authorization") String auth,
            @PathVariable("id") Long id) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Remove Event",
                adminService.removeEvent(auth, id)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/gifts", method = RequestMethod.GET)
    public ResponseEntity<RestResponse> gifts(
            @RequestHeader("Authorization") String auth,
            @RequestParam("eventId") String eventId,
            @RequestParam("product") String product,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Gift List",
                adminService.getGifts(auth, eventId, product, page, size)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/gifts/{id}", method = RequestMethod.GET)
    public ResponseEntity<RestResponse> gift(
            @RequestHeader("Authorization") String auth,
            @PathVariable("id") Long id) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Gift Information",
                adminService.getGift(auth, id)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/gifts/{id}", method = RequestMethod.POST)
    public ResponseEntity<RestResponse> editGift(
            @RequestHeader("Authorization") String auth,
            @PathVariable("id") Long id,
            @RequestParam("product") String product,
            @RequestParam("count") int count) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Edit Gift",
                adminService.editGift(auth, id, product, count)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/gifts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<RestResponse> removeGift(
            @RequestHeader("Authorization") String auth,
            @PathVariable("id") Long id) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Remove Gift",
                adminService.removeGift(auth, id)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/eventTypes", method = RequestMethod.GET)
    public ResponseEntity<RestResponse> eventTypes(
            @RequestHeader("Authorization") String auth,
            @RequestParam("eventId") String eventId,
            @RequestParam("sort") String sort,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "EventType List",
                adminService.getEventTypes(auth, eventId, sort, page, size)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/eventTypes/{id}", method = RequestMethod.GET)
    public ResponseEntity<RestResponse> eventType(
            @RequestHeader("Authorization") String auth,
            @PathVariable("id") Long id) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "EventType Information",
                adminService.getEventType(auth, id)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/eventTypes/{id}", method = RequestMethod.POST)
    public ResponseEntity<RestResponse> editEventType(
            @RequestHeader("Authorization") String auth,
            @PathVariable("id") Long id,
            @RequestParam("eventTypeCodeId") Long eventTypeCodeId) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Edit EventType",
                adminService.editEventType(auth, id, eventTypeCodeId)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/eventTypes/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<RestResponse> removeEventType(
            @RequestHeader("Authorization") String auth,
            @PathVariable("id") Long id) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Remove EventType",
                adminService.removeEventType(auth, id)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/eventTypeCodes/{sort}", method = RequestMethod.GET)
    public ResponseEntity<RestResponse> eventTypeCodesBySort(
            @RequestHeader("Authorization") String auth,
            @PathVariable("sort") String sort) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "EventTypeCode List By Sort",
                adminService.getEventTypeCodes(auth, sort)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/eventTypeCodes", method = RequestMethod.GET)
    public ResponseEntity<RestResponse> eventTypeCodes(
            @RequestHeader("Authorization") String auth,
            @RequestParam("sort") String sort,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "EventTypeCode List",
                adminService.getEventTypeCodes(auth, sort, page, size)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/eventTypeCodes", method = RequestMethod.POST)
    public ResponseEntity<RestResponse> addEventTypeCode(
            @RequestHeader("Authorization") String auth,
            @RequestParam("name") String name,
            @RequestParam("color") String color,
            @RequestParam("sort") String sort) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Add EventType",
                adminService.addEventTypeCode(auth, name, color, sort)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/eventTypeCodes/{id}", method = RequestMethod.POST)
    public ResponseEntity<RestResponse> editEventTypeCode(
            @RequestHeader("Authorization") String auth,
            @PathVariable("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("color") String color,
            @RequestParam("sort") String sort) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Edit EventTypeCode",
                adminService.editEventTypeCode(auth, id, name, color, sort)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/eventTypeCodes/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<RestResponse> removeEventTypeCode(
            @RequestHeader("Authorization") String auth,
            @PathVariable("id") Long id) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Remove EventType",
                adminService.removeEventTypeCode(auth, id)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/banners", method = RequestMethod.GET)
    public ResponseEntity<RestResponse> banners(
            @RequestHeader("Authorization") String auth,
            @RequestParam("visible") String visible,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Banner List",
                adminService.getBanners(auth, visible, page, size)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/banners", method = RequestMethod.POST)
    public ResponseEntity<RestResponse> addBanner(
            @RequestHeader("Authorization") String auth,
            @RequestPart("banner") String banner,
            @RequestPart(required = false, name = "file") MultipartFile file)
            throws IOException, InvalidKeyException, ParseException, StorageException, URISyntaxException {

        AdminBannerForm requestForm = new ObjectMapper().readValue(banner, AdminBannerForm.class);

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Add Banner",
                adminService.addBanner(auth, requestForm, file)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/banners/{id}", method = RequestMethod.POST)
    public ResponseEntity<RestResponse> editBanner(
            @RequestHeader("Authorization") String auth,
            @PathVariable("id") Long id,
            @RequestBody AdminBannerForm requestForm) throws ParseException {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Edit Banner",
                adminService.editBanner(auth, id, requestForm)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/banners/{id}/visible", method = RequestMethod.POST)
    public ResponseEntity<RestResponse> setBanner(
            @RequestHeader("Authorization") String auth,
            @PathVariable("id") Long id,
            @RequestParam("visible") boolean visible) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Set Banner Visible",
                adminService.setBanner(auth, id, visible)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/banners/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<RestResponse> removeBanner(
            @RequestHeader("Authorization") String auth,
            @PathVariable("id") Long id) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Remove Banner",
                adminService.removeBanner(auth, id)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/popups", method = RequestMethod.GET)
    public ResponseEntity<RestResponse> popups(
            @RequestHeader("Authorization") String auth,
            @RequestParam("visible") String visible,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Popup List",
                adminService.getPopups(auth, visible, page, size)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/popups", method = RequestMethod.POST)
    public ResponseEntity<RestResponse> addPopup(
            @RequestHeader("Authorization") String auth,
            @RequestPart("popup") String popup,
            @RequestPart(required = false, name = "file") MultipartFile file)
            throws IOException, InvalidKeyException, ParseException, StorageException, URISyntaxException {

        AdminPopupForm requestForm = new ObjectMapper().readValue(popup, AdminPopupForm.class);

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Add Popup",
                adminService.addPopup(auth, requestForm, file)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/popups/{id}", method = RequestMethod.POST)
    public ResponseEntity<RestResponse> editPopup(
            @RequestHeader("Authorization") String auth,
            @PathVariable("id") Long id,
            @RequestBody AdminPopupForm requestForm) throws ParseException {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Edit Popup",
                adminService.editPopup(auth, id, requestForm)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/popups/{id}/visible", method = RequestMethod.POST)
    public ResponseEntity<RestResponse> setPopup(
            @RequestHeader("Authorization") String auth,
            @PathVariable("id") Long id,
            @RequestParam("visible") boolean visible) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Set Popup Visible",
                adminService.setPopup(auth, id, visible)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/popups/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<RestResponse> removePopup(
            @RequestHeader("Authorization") String auth,
            @PathVariable("id") Long id) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Remove Popup",
                adminService.removePopup(auth, id)),
                HttpStatus.OK);
    }
}
