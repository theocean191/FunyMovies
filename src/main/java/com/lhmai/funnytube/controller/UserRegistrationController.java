package com.lhmai.funnytube.controller;

import com.lhmai.funnytube.exception.PasswordConfirmationNotMatchException;
import com.lhmai.funnytube.exception.UsernameExistException;
import com.lhmai.funnytube.service.UserService;
import com.lhmai.funnytube.service.dto.UserRegistrationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
@Slf4j
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String getRegistrationForm(Model model) {
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
                                      BindingResult result) {

        try {
            userService.save(userDto);
        } catch (UsernameExistException e) {
            result.rejectValue("username", null, "There is already an account registered with that username");
        } catch (PasswordConfirmationNotMatchException e) {
            result.rejectValue("confirmPassword", null, "There is already an account registered with that username");
        }

        if (result.hasErrors()) {
            return "registration";
        } else {
            return "redirect:/registration?success=true";
        }
    }

}
