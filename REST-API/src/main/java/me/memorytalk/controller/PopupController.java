package me.memorytalk.controller;

import io.swagger.annotations.Api;
import me.memorytalk.common.base.RestResponse;
import me.memorytalk.service.PopupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Popup API", description = "Popup API", basePath = "/popup")
@RestController
public class PopupController {

    @Autowired
    private PopupService popupService;

    @RequestMapping(value = "/popups", method = RequestMethod.GET)
    public ResponseEntity<RestResponse> banners(
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Popup List",
                popupService.getPopups(page, size)),
                HttpStatus.OK);
    }
}
