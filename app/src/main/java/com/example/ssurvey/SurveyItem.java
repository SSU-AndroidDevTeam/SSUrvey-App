package com.example.ssurvey;

import com.example.ssurvey.model.Survey;
import java.io.Serializable;

public class SurveyItem {
    private String name;
    private String description;
<<<<<<< Updated upstream
    private String date;
=======
    private String dateText;
    private boolean isClosed = false;
    private int dateLeft = 0;
    private String surveyId;
>>>>>>> Stashed changes

    public SurveyItem(String name, String description, String date, String surveyId) {
        this.name = name;
        this.description = description;
        this.date = date;

    }

    public SurveyItem(Survey survey, String surveyId) {
        this.name = survey.getName();
        this.description = survey.getDesc();
<<<<<<< Updated upstream
        this.date = "D-day"; // TODO: 남은 날짜 계산
=======
        this.surveyId = surveyId;
        long currTimeInMs = System.currentTimeMillis();
        long closeDateInMs = survey.getCloseDate().toDate().getTime();
        SetDateLeft(MsToDays(closeDateInMs - currTimeInMs));
>>>>>>> Stashed changes
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

<<<<<<< Updated upstream
    public String getDate() {
        return date; // TODO
=======
    public String getDateText() {
        return dateText;
    }

    public int getDateLeft() {
        return dateLeft;
    }

    public String getSurveyId() { return surveyId; }

    @Override
    public int compareTo(SurveyItem surveyItem) {
        return this.getDateLeft() - surveyItem.getDateLeft();
>>>>>>> Stashed changes
    }
}

