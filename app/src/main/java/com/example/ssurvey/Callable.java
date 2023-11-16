package com.example.ssurvey;

import java.util.Map;

interface SurveyCallable {
    public void call(Survey survey, boolean hasFailed);
}
