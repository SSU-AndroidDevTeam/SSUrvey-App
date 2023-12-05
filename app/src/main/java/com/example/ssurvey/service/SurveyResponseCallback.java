package com.example.ssurvey.service;

import com.example.ssurvey.model.SurveyResponse;

public interface SurveyResponseCallback {
    public void onCallback(SurveyResponse response, CbCode cbCode);
}
