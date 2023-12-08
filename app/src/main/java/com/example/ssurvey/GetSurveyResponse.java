package com.example.ssurvey;

import com.example.ssurvey.model.SurveyResponse;

public class GetSurveyResponse {
    public static SurveyResponse getSurveyResponse;

    static {
        getSurveyResponse = new SurveyResponse();
    }

    public static SurveyResponse generateNewResponse() {
        return new SurveyResponse();
    }
    public static void resetSurvey() {
        getSurveyResponse = new SurveyResponse();
    }
}
