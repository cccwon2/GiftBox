package me.memorytalk.controller;

import io.swagger.annotations.Api;
import me.memorytalk.common.base.RestResponse;
import me.memorytalk.service.EventTypeCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "EventTypeCode API", description = "EventTypeCode API", basePath = "/eventTypeCode")
@RestController
public class EventTypeCodeController {

    @Autowired
    private EventTypeCodeService eventTypeCodeService;

    @RequestMapping(value = "/eventTypeCodes", method = RequestMethod.GET)
    public ResponseEntity<RestResponse> eventTypeCodes(
            @RequestParam("sort") String sort) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "EventTypeCode List",
                eventTypeCodeService.getEventTypeCodes(sort)),
                HttpStatus.OK);
    }
}
