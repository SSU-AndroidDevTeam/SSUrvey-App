package com.example.ssurvey.service;

import com.example.ssurvey.model.User;

/** UserService 메서드 콜백용 인터페이스 */
public interface UserCallback {
    public void onCallback(User user, CbCode code);
}