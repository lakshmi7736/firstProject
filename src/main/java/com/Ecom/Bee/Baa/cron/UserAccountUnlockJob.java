package com.Ecom.Bee.Baa.cron;

import com.Ecom.Bee.Baa.Models.User;
import com.Ecom.Bee.Baa.Service.inter.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class UserAccountUnlockJob {

    private final UserService userService;
    @Scheduled(cron = "0 */5 * * * *")   //Every five minutes after it's going to execute
    public void unlockUserAccount() {
        List<User> lockedUsers = userService.getExpiredLockedUsers();
        for (User user : lockedUsers) {
            System.out.println("inside cron operation"+ LocalDateTime.now());
            userService.unlockUser(user);
        }
    }
}