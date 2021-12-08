package com.amr.project.webapp.rest_controller;

import com.amr.project.service.email.EmailVerificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@Api(tags = {"API для проверки activation token и активации пользователя"})
@RestController
public class EmailVerificationController {

    private EmailVerificationService verificationService;

    @ApiOperation(value = "Передаёт параметры, пришедшие в запросе, в метод для проверки и активации нового пользователя")
    @GetMapping("/registrationConfirm")
    public String verifyEmail(@RequestParam(name="email") String email, @RequestParam(name = "token") String token) {
        return verificationService.activateUser(email, token);
    }
}