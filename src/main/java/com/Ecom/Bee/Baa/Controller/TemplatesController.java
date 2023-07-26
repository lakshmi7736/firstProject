package com.Ecom.Bee.Baa.Controller;

import com.Ecom.Bee.Baa.DTO.UserDto;
import com.Ecom.Bee.Baa.Service.inter.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/bee")
public class TemplatesController {

    @Autowired
    private UserService userService;

    @GetMapping({"/"})
    public String home(Model model){;
        return "basicTemplates/index";
    }

    @GetMapping({"/Womens"})
    public String women(){
        return "basicTemplates/Womens";
    }

    @GetMapping({"/Mens"})
    public String men(){
        return "basicTemplates/Mens";
    }

    @GetMapping({"/shop"})
    public String shop(){
        return "basicTemplates/shop";
    }

    @GetMapping({"/product-details"})
    public String productDetails(){
        return "basicTemplates/product-details";
    }

    @GetMapping({"/shop-cart"})
    public String shopCart(){
        return "basicTemplates/shop-cart";
    }


    @GetMapping({"/checkout"})
    public String checkOut(){
        return "basicTemplates/checkout";
    }


    @GetMapping({"/blog-details"})
    public String blogDetails(){
        return "basicTemplates/blog-details";
    }


    @GetMapping({"/blog"})
    public String blog(){
        return "basicTemplates/blog";
    }

    @GetMapping({"/contact"})
    public String contact(){
        return "basicTemplates/contact";
    }

    @RequestMapping("/authentication-login")
    public String loginForm() {
        return "authentication-login";
    }

    @GetMapping("/authentication-register")
    public String registrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "authentication-register";
    }
}
