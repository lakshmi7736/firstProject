package com.Ecom.Bee.Baa.Handler;

import com.Ecom.Bee.Baa.Models.User;
import com.Ecom.Bee.Baa.Service.inter.UserService;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginFailureHandler implements AuthenticationFailureHandler {

    private final UserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException, java.io.IOException {
        System.out.println("inside login failure");

        String email = request.getParameter("username");
        User user = userService.findUserByEmail(email);

        if (user != null) {
            if (user.isEnabled() && user.isAccountNonLocked()) {
                if (user.getFailedAttempt() < 2) {
                    userService.increaseFailedAttempts(user);
                } else {
                    userService.lockUser(user);
                    exception = new LockedException("Account is locked since you have exceeded the number of trials.");
                    request.getSession().setAttribute("lockedExceptionMessage", exception.getMessage());
                }
            } else if (!user.isAccountNonLocked()) {
                boolean unlocked = userService.unlockUser(user);
                if (unlocked) {
                    exception = new LockedException("Your account has been unlocked. Please try to login again.");
                    request.getSession().setAttribute("lockedExceptionMessage", exception.getMessage());
                }
            } else if (!user.isEnabled()) {

                exception = new LockedException("Account is been blocked. Contact Admin for more queries");
                request.getSession().setAttribute("lockedExceptionMessage", exception.getMessage());
            }
        }

        response.sendRedirect("/bee/authentication-login?error"); // Redirect to the desired URL after authentication failure
    }
}