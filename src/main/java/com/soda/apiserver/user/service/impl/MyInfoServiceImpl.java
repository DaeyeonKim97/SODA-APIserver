package com.soda.apiserver.user.service.impl;

import com.soda.apiserver.user.model.entity.User;
import com.soda.apiserver.user.repository.UserRepository;
import com.soda.apiserver.user.service.MyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyInfoServiceImpl implements MyInfoService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MyInfoServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getMyInfo(String userName) {
        User user = repository.findByUserName(userName);
        user.setPassword(null);
        return user;
    }

    @Override
    public boolean updateMyPassword(String userName, String oldPassword, String newPassword) {
        User user = repository.findByUserName(userName);

        if(passwordEncoder.matches(oldPassword,user.getPassword())){
            user.setPassword(passwordEncoder.encode(newPassword));
        }
        else {
            return false;
        }
        repository.save(user);
        return true;
    }
}
