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
            @RequestParam("password") String password,
            @RequestParam("visible") String visible,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Event List",
                adminService.getEvents(password, visible, page, size)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/events", method = RequestMethod.POST)
    public ResponseEntity<RestResponse> addEvent(
            @RequestParam("password") String password,
            @RequestPart("event") String event,
            @RequestPart(required = false, name = "file") MultipartFile file)
            throws IOException, InvalidKeyException, ParseException, StorageException, URISyntaxException {

        AdminEventDetailForm requestForm = new ObjectMapper().readValue(event, AdminEventDetailForm.class);

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Add Event",
                adminService.addEvent(password, requestForm, file)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/events/{id}", method = RequestMethod.GET)
    public ResponseEntity<RestResponse> eventDetail(
            @RequestParam("password") String password,
            @PathVariable("id") Long id) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Event Detail",
                adminService.getEvent(password, id)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/events/{id}", method = RequestMethod.POST)
    public ResponseEntity<RestResponse> editEvent(
            @RequestParam("password") String password,
            @PathVariable("id") Long id,
            @RequestBody AdminEventDetailForm requestForm) throws ParseException {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Edit Event",
                adminService.editEvent(password, id, requestForm)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/banners/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<RestResponse> removeBanner(
            @RequestParam("password") String password,
            @PathVariable("id") Long id) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Remove Event",
                adminService.removeEvent(password, id)),
                HttpStatus.OK);
    }
}
