package me.memorytalk.controller;

import io.swagger.annotations.Api;
import me.memorytalk.common.base.RestResponse;
import me.memorytalk.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "Event API", description = "Event API", basePath = "/event")
@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public ResponseEntity<RestResponse> events(
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Event List",
                eventService.getEvents(page, size)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/events/{id}", method = RequestMethod.GET)
    public ResponseEntity<RestResponse> eventDetail(
            @PathVariable("id") Long id) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Event Detail",
                eventService.getEvent(id)),
                HttpStatus.OK);
    }
}
