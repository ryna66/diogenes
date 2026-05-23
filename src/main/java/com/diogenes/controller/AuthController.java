package com.diogenes.controller;

import com.diogenes.auth.LoginErrorCode;
import com.diogenes.model.RegisterForm;
import com.diogenes.service.UserAccountService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserAccountService userAccountService;

    public AuthController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String errorCode, Model model) {
        LoginErrorCode.fromParam(errorCode)
                .ifPresent(code -> model.addAttribute("loginErrorMessage", code.getMessage()));
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        if (!model.containsAttribute("registerForm")) {
            model.addAttribute("registerForm", new RegisterForm());
        }
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerForm") RegisterForm registerForm,
                           BindingResult bindingResult) {
        if (!bindingResult.hasErrors() && !registerForm.getPassword().equals(registerForm.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "match", "Passwords do not match.");
        }

        if (!bindingResult.hasErrors()) {
            try {
                userAccountService.register(registerForm);
                return "redirect:/login?registered";
            } catch (IllegalArgumentException exception) {
                bindingResult.reject("email", exception.getMessage());
            }
        }

        return "register";
    }
}
