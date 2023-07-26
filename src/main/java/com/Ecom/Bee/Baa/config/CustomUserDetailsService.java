package com.Ecom.Bee.Baa.config;

import com.Ecom.Bee.Baa.Models.User;
import com.Ecom.Bee.Baa.Repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao userRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userRepo.findUserByEmail(email);
		System.out.println(user);
		if (user == null) {
			throw new UsernameNotFoundException("user not found");
		} else {
			return new CustomUser(user);
		}
	}
}
