package com.example.ssurvey;

import com.example.ssurvey.model.Survey;

public class MakeSurvey {
    public static Survey makeSurvey;

    static {
        makeSurvey = new Survey();
    }
    public static void resetSurvey() {
        makeSurvey = new Survey();
    }
}
