package com.hometest.service;

import com.hometest.mybatis.domain.*;

public interface UserService {

    User getByUsername(LoginUser loginUser);

    User getByUserId(Long userid);

    boolean isUserExists(String username);

    UserPassword generateOtp(Long userid);

    boolean verifyUser(Long userid, String otp);

    boolean updateUserSuccessLoginDetails(User user);

    boolean updateUserFailureLoginDetails(User user);

    boolean lockUser(String username);

    boolean changeUserPassword(ChangePassword changepassword);

    boolean updateUserProfile(Profile profile);

    ChangeMobileRequest changeUserMobile(ChangeMobileRequest mobile);

    User signup(User request);

    void changeUserEmail(String email);

    void logout(String token);
}
