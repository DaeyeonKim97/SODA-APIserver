package com.soda.apiserver.auth.service;

import com.soda.apiserver.auth.model.entity.User;

public interface MyInfoService {
    User getMyInfo(String username);
    boolean updateMyPassword(String username, String oldPassword, String newPassword);

}
