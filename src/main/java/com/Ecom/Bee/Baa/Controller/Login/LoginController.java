package com.Ecom.Bee.Baa.Controller.Login;

import com.Ecom.Bee.Baa.DTO.UserDto;
import com.Ecom.Bee.Baa.Models.User;
import com.Ecom.Bee.Baa.Service.inter.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private UserService userService;
    @PostMapping("/registration")
    public String registration(
            @Valid @ModelAttribute("user") UserDto userDto,
            BindingResult result,
            Model model) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if (existingUser != null)
            result.rejectValue("email", null,
                    "User already registered !!!");

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);

            return "/authentication-register";
        }

        userService.saveUser(userDto);
        return "redirect:/auth/authentication-register?success";
    }

    @PostMapping("/registrationAdmin")
    public String registrationAdmin(
            @Valid @ModelAttribute("user") UserDto userDto,
            BindingResult result,
            Model model) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if (existingUser != null)
            result.rejectValue("email", null,
                    "User already registered !!!");

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);

            return "/authentication-register";
        }

        userService.saveAdmin(userDto);
        return "redirect:/auth/authentication-register?success";
    }



}
