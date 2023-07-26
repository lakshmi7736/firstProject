package com.Ecom.Bee.Baa.Service;

import com.Ecom.Bee.Baa.Models.TOKEN.RefreshToken;
import com.Ecom.Bee.Baa.Models.User;
import com.Ecom.Bee.Baa.Repository.RefreshTokenRepository;
import com.Ecom.Bee.Baa.Repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;


@Service
public class RefreshTokenService {

    public long refreshTokenValidity=5*60*60*10000;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserDao userRepository;

    public RefreshToken createRefreshToken(String userName){

        User user = userRepository.findUserByEmail(userName);
        System.out.println(user);

        RefreshToken refreshToken1=user.getRefreshToken();

        if(refreshToken1==null){

            refreshToken1=RefreshToken.builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .expiry(Instant.now().plusMillis(refreshTokenValidity))
                    .user(user)
                    .build();
        }else {
            refreshToken1.setExpiry(Instant.now().plusMillis(refreshTokenValidity));
        }

        user.setRefreshToken(refreshToken1);


        //save to database
        refreshTokenRepository.save(refreshToken1);
        return refreshToken1;
    }

    public RefreshToken verifyRefreshToken(String refreshToken){

        RefreshToken refreshTokenOb=refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(()->new RuntimeException("TOKEN DOES NOT EXIST IN YOUR DATABASE !!"));
        if(refreshTokenOb.getExpiry().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(refreshTokenOb);
            throw  new RuntimeException("Refresh Token Expired");
        }
        return refreshTokenOb;
    }
}