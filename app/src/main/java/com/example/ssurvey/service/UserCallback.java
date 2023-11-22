package com.example.ssurvey.service;

import com.example.ssurvey.model.User;

public interface UserCallback {
    public void onCallback(User user, CbCode code);
}