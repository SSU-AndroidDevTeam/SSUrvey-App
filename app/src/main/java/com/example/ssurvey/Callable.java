package com.example.ssurvey;

import com.example.ssurvey.model.Survey;

interface SurveyCallable {
    public void call(Survey survey, boolean hasFailed);
}
