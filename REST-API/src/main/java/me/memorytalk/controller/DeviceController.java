package me.memorytalk.controller;

import me.memorytalk.common.base.RestResponse;
import me.memorytalk.dto.DeviceForm;
import me.memorytalk.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @RequestMapping(value = "/devices", method = RequestMethod.POST)
    public ResponseEntity<RestResponse> addDevice(@RequestBody DeviceForm requestForm) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Add Device",
                deviceService.addDevice(requestForm)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/devices", method = RequestMethod.DELETE)
    public ResponseEntity<RestResponse> removeDevice(@RequestBody DeviceForm requestForm) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Remove Device",
                deviceService.removeDevice(requestForm)),
                HttpStatus.OK);
    }
}
