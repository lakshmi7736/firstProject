package com.Ecom.Bee.Baa.Service.inter;

import com.Ecom.Bee.Baa.DTO.UserDto;
import com.Ecom.Bee.Baa.Models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void saveUser(UserDto userDto);

    void saveAdmin(UserDto userDto);

    User findUserByEmail(String email);

    void increaseFailedAttempts(User user);
    void lockUser(User user);

    boolean unlockUser(User user);

    void resetFailedAttempts(String email);

    List<User> getExpiredLockedUsers();

    List<User> findByRole();

    User getUserById(Long id);

    User updateUser(Long id, User user);

    void deleteUser(Long id);
    void saveUser(User user);

}
