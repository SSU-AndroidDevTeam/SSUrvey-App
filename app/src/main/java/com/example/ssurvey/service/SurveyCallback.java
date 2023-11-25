package com.example.ssurvey.service;

import com.example.ssurvey.databinding.ActivityFirebaseExampleBinding;
import com.example.ssurvey.model.Survey;

public interface SurveyCallback {
    public void onCallback(Survey survey, CbCode cbCode);
}