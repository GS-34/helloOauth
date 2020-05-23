package com.example.oauth.service;

import com.example.oauth.entity.User;
import com.example.oauth.enums.UserType;
import com.example.oauth.param.UserInfo;
import com.example.oauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HomeService {

    @Autowired
    UserRepository userRepository;

    @Transactional
    public UserInfo join(String id, String password, UserType userType){
        User user = User.builder()
                .id(id)
                .password(password)
                .userType(userType)
                .build();

        userRepository.save(user);

        return UserInfo.builder().id(id).build();
    }

}
