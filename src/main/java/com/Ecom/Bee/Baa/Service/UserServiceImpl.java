package com.Ecom.Bee.Baa.Service;

import com.Ecom.Bee.Baa.DTO.UserDto;
import com.Ecom.Bee.Baa.Models.User;
import com.Ecom.Bee.Baa.Repository.UserDao;
import com.Ecom.Bee.Baa.Service.inter.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final int MAX_FAILED_ATTEMPTS = 3;  //limit attempt

    public static final long LOCK_TIME_DURATION = 300000;  //5 min

    private final UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserDto userDto) {
        User user = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role("ROLE_USER")
                .enabled(true)
                .accountNonLocked(true)
                .failedAttempt(0)
                .lockTime(null)
                .build();
        userDao.save(user);
    }

    @Override
    public void saveAdmin(UserDto userDto) {
        User user = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .enabled(true)
                .accountNonLocked(true)
                .failedAttempt(0)
                .lockTime(null)
                .role("ROLE_ADMIN")
                .build();
        userDao.save(user);

    }

    @Override
    public User findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }



    @Override
    public void increaseFailedAttempts(User user) {
        int failedAttempts = user.getFailedAttempt() + 1;
        userDao.updateFailedAttempt(failedAttempts, user.getEmail());
    }

    @Override
    public void lockUser(User user) {
        user.setAccountNonLocked(false);
        user.setLockTime(LocalDateTime.now());
        userDao.save(user);
    }

    @Override
    public boolean unlockUser(User user) {
        LocalDateTime lockTime = user.getLockTime();
        if (lockTime != null) {
            long lockTimeInMills = lockTime.toInstant(ZoneOffset.UTC).toEpochMilli();
            long currentTimeMillis = LocalDateTime.now().atZone(ZoneOffset.UTC).toInstant().toEpochMilli();
            long unlockTimeMillis = lockTimeInMills + LOCK_TIME_DURATION;

            System.out.println(currentTimeMillis + " > " + unlockTimeMillis);
            if (currentTimeMillis > unlockTimeMillis) {
                user.setAccountNonLocked(true);
                user.setLockTime(null);
                user.setFailedAttempt(0);
                userDao.save(user);
                return true;
            }
        }
        return false;
    }
    @Override
    public void resetFailedAttempts(String email) {
        System.out.println(email);
        userDao.updateFailedAttempt(0, email);
    }

    @Override
    public List<User> getExpiredLockedUsers() {
        LocalDateTime currentTime = LocalDateTime.now();
        return userDao.findExpiredLockedUsers(currentTime);
    }

    @Override
    public List<User> findByRole() {
        return userDao.findByRole("ROLE_USER");
    }


    @Override
    public User getUserById(Long id) {
        User user=userDao.findById(id).orElse(null);
        return  user;
    }

    @Override
    public User updateUser(Long id, User user) {
        User sr=userDao.findById(id).orElse(null);
        if(sr!=null){
            sr.setName(user.getName());
            sr.setEmail(user.getEmail());
            sr.setRole(user.getRole());
            userDao.save(sr);
            return user;
        }else{
            return  null;
        }
    }

    @Override
    public void deleteUser(Long id) {
        userDao.deleteById(id);
    }

    @Override
    public void saveUser(User user) {
        userDao.save(user);
    }

}
