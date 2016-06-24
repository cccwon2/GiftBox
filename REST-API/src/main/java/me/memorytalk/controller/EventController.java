package me.memorytalk.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Event API", description = "Event API", basePath = "/event")
@RestController
public class EventController {

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public String events() {

        return "event list";
    }
}
