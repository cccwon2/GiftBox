package me.memorytalk.controller;

import io.swagger.annotations.Api;
import me.memorytalk.common.base.RestResponse;
import me.memorytalk.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "Banner API", description = "Banner API", basePath = "/banner")
@RestController
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @RequestMapping(value = "/banners", method = RequestMethod.GET)
    public ResponseEntity<RestResponse> banners(
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Banner List",
                bannerService.getBanners(page, size)),
                HttpStatus.OK);
    }
}
