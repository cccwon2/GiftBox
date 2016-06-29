package me.memorytalk.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.storage.StorageException;
import io.swagger.annotations.Api;
import me.memorytalk.common.base.RestResponse;
import me.memorytalk.dto.AdminEventDetailForm;
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
    public ResponseEntity<RestResponse> setEvent(
            @RequestHeader("Authorization") String auth,
            @PathVariable("id") Long id,
            @RequestParam("visible") boolean visible) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Set Event Visible",
                adminService.setEvent(auth, id, visible)),
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

    @RequestMapping(value = "/admin/tags", method = RequestMethod.GET)
    public ResponseEntity<RestResponse> tags(
            @RequestHeader("Authorization") String auth,
            @RequestParam("eventId") String eventId,
            @RequestParam("tagName") String tagName,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Tag List",
                adminService.getTags(auth, eventId, tagName, page, size)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/tags/{id}", method = RequestMethod.GET)
    public ResponseEntity<RestResponse> tag(
            @RequestHeader("Authorization") String auth,
            @PathVariable("id") Long id) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Tag Information",
                adminService.getTag(auth, id)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/tags/{id}", method = RequestMethod.POST)
    public ResponseEntity<RestResponse> editTag(
            @RequestHeader("Authorization") String auth,
            @PathVariable("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("color") String color) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Edit Tag",
                adminService.editTag(auth, id, name, color)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/tags/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<RestResponse> removeTag(
            @RequestHeader("Authorization") String auth,
            @PathVariable("id") Long id) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Remove Tag",
                adminService.removeTag(auth, id)),
                HttpStatus.OK);
    }
}
