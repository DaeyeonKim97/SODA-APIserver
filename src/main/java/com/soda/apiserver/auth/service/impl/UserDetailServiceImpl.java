package com.soda.apiserver.auth.service.impl;

import com.soda.apiserver.auth.model.entity.User;
import com.soda.apiserver.auth.model.entity.UserRole;
import com.soda.apiserver.auth.repository.UserRepository;
import com.soda.apiserver.auth.repository.UserRoleRepository;
import com.soda.apiserver.auth.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailService {
    private final UserRepository repository;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserDetailServiceImpl(UserRepository repository, UserRoleRepository userRoleRepository) {
        this.repository = repository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = repository.findByUserName(username);

        if(user == null){
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        List<UserRole> userRoleList = userRoleRepository.findByUser(user);

        List<GrantedAuthority> authorities = new ArrayList<>();
        userRoleList.forEach(userRole -> authorities.add(new SimpleGrantedAuthority(userRole.getRole().getRoleName())));

        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
    }
}
