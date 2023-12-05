package com.example.ssurvey;

import com.example.ssurvey.model.Survey;
import java.io.Serializable;

public class SurveyItem {
    private String name;
    private String description;
    private String date;

    public SurveyItem(String name, String description, String date) {
        this.name = name;
        this.description = description;
        this.date = date;

    }

    public SurveyItem(Survey survey) {
        this.name = survey.getName();
        this.description = survey.getDesc();
        this.date = "D-day"; // TODO: 남은 날짜 계산
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date; // TODO
    }
}

