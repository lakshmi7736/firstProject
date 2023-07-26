package com.Ecom.Bee.Baa.Controller;

import com.Ecom.Bee.Baa.Models.User;
import com.Ecom.Bee.Baa.Repository.UserDao;
import com.Ecom.Bee.Baa.Service.inter.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/sec")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;


    @GetMapping({"/user"})
    public String users(Model model) {
        List<User> users = userService.findByRole();
        model.addAttribute("users", users);
        return "Admin/user"; // Thymeleaf will look for "users.html" in the templates folder
    }


     @GetMapping("/users/delete/{id}")
     public String deleteUser(@PathVariable("id") Long id, RedirectAttributes ra) {
     try {
         userService.deleteUser(id);
         ra.addFlashAttribute("message", "The user ID " + id + " has been deleted.");
        }catch (UsernameNotFoundException e) {
         ra.addFlashAttribute("message", e.getMessage());
        }
      return "redirect:/sec/user";
     }

    @GetMapping("/users/block/{email}")
    public String blockUser(@PathVariable("email") String  email) {
     User user=userService.findUserByEmail(email);
     if (user!=null){
         boolean lock=user.isEnabled();
         user.setEnabled(!lock);
     }
        userService.saveUser(user);
        return "redirect:/sec/user";
    }

    @PutMapping("/update-user/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user){
        return userService.updateUser(id, user);
    }


}
