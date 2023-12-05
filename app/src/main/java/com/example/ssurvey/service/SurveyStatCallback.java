package com.example.ssurvey.service;

import com.example.ssurvey.model.SurveyStatistics;

public interface SurveyStatCallback {
    public void onCallback(SurveyStatistics statistics, CbCode cbCode);
}
