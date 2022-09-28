package com.soda.apiserver.user.service;

import com.soda.apiserver.user.model.entity.User;

public interface MyInfoService {
    User getMyInfo(String username);
    boolean updateMyPassword(String username, String oldPassword, String newPassword);

}
