package com.Ecom.Bee.Baa.Handler;


import com.Ecom.Bee.Baa.Models.TOKEN.JwtResponse;
import com.Ecom.Bee.Baa.Models.TOKEN.RefreshToken;
import com.Ecom.Bee.Baa.Models.User;
import com.Ecom.Bee.Baa.Security.JwtHelper;
import com.Ecom.Bee.Baa.Service.RefreshTokenService;
import com.Ecom.Bee.Baa.Service.inter.UserService;
import com.Ecom.Bee.Baa.config.CustomUser;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;
    private final UserDetailsService userDetailsService;

    @Autowired
    private JwtHelper helper;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException, java.io.IOException {
        CustomUser userDetails = (CustomUser) authentication.getPrincipal();

       User user=userDetails.getUser();
        String email= userDetails.getUsername();
        String password= userDetails.getPassword();
        if (user.getFailedAttempt() > 0) {
            userService.resetFailedAttempts(user.getEmail());
        }

        // Perform any additional tasks upon successful login
        UserDetails userDetails1= userDetailsService.loadUserByUsername(email);
        String token=helper.generateToken(userDetails1);
        System.out.println(token);
        RefreshToken refreshToken=refreshTokenService.createRefreshToken(userDetails.getUsername());
        JwtResponse response1 = JwtResponse.builder()
                .jwtToken(token)
                .username(userDetails1.getUsername())
                .name(user.getName())
                .refreshToken(refreshToken.getRefreshToken())
                .build();

// Inside the onAuthenticationSuccess method
        HttpSession session = request.getSession();
        session.setAttribute("jwtResponse", response1);
//
//        // Inside the onAuthenticationSuccess method
//        Cookie jwtTokenCookie = new Cookie("jwtToken", response1.getJwtToken());
//        jwtTokenCookie.setMaxAge(3600); // Set the cookie expiration time in seconds (e.g., 1 hour)
//        jwtTokenCookie.setPath("/"); // Set the cookie path to '/' to make it accessible across the entire application
//        response.addCookie(jwtTokenCookie);
//
        // Redirect to the desired URL based on the user's role
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ROLE_ADMIN")) {
            System.out.println("ADMIN");
            response.sendRedirect("/sec/Admin");
        } else {
            response.sendRedirect("/bee/");
        }


    }


}