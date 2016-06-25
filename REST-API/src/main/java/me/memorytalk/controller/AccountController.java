package me.memorytalk.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import me.memorytalk.common.base.RestResponse;
import me.memorytalk.dto.AdminLoginForm;
import me.memorytalk.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@Api(value = "Account API", description = "계정 API", basePath = "/account")
@RestController
public class AccountController {

    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "[Fixed] Admin Login", notes = "관리자 로그인", response = String.class)
    @RequestMapping(value = "/account/login/admin", method = RequestMethod.POST)
    public ResponseEntity<RestResponse> adminLogin(
            @ApiParam(value = "관리자 로그인<br>Request Form", required = true)
            @RequestBody AdminLoginForm requestForm) {

        return new ResponseEntity<>(new RestResponse(Boolean.TRUE,
                "Admin Login",
                adminService.login(requestForm)),
                HttpStatus.OK);
    }
}
