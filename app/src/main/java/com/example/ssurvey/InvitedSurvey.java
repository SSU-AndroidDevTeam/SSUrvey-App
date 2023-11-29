package com.example.ssurvey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;

public class InvitedSurvey extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invited_survey);

        // XML에 정의된 ConstraintLayout 가져오기
        ConstraintLayout constraintLayout_myInformation = findViewById(R.id.constraintLayout_invited_survey);

        navigationBar(constraintLayout_myInformation);
    }
}