package com.example.ssurvey.service;

import com.example.ssurvey.model.SurveyResponse;

import java.util.ArrayList;

public interface SurveyReplicantCallback {
    public void onCallback(ArrayList<String> replicants, CbCode cbCode);
}
