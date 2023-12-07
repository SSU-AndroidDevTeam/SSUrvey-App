package com.example.ssurvey;

import android.util.Log;

import com.example.ssurvey.model.Survey;
import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.Date;

public class SurveyItem implements Comparable<SurveyItem> {
    private String name;
    private String description;
    private String dateText;
    private boolean isClosed = false;
    private int dateLeft = 0;

    public SurveyItem(String name, String description, String dateText) {
        this.name = name;
        this.description = description;
        this.dateText = dateText;
    }

    public SurveyItem(Survey survey) {
        this.name = survey.getName();
        this.description = survey.getDesc();
        long currTimeInMs = System.currentTimeMillis();
        long closeDateInMs = survey.getCloseDate().toDate().getTime();
        SetDateLeft(MsToDays(closeDateInMs - currTimeInMs));
    }

    private int MsToDays(long milliseconds) {
        return Math.round(milliseconds / 1000f / 60f / 60f / 24f);
    }

    private void SetDateLeft(int days) {
        if (days < 0) {
            dateText = "종료";
            setIsClosed(true);
            dateLeft = Integer.MAX_VALUE;
        } else {
            dateText = "D-" + Integer.toString(days);
            dateLeft = days;
        }
    }

    public boolean getIsClosed() { return isClosed; }

    public void setIsClosed(boolean value) { isClosed = value; }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDateText() {
        return dateText;
    }

    public int getDateLeft() {
        return dateLeft;
    }

    @Override
    public int compareTo(SurveyItem surveyItem) {
        return this.getDateLeft() - surveyItem.getDateLeft();
    }
}

