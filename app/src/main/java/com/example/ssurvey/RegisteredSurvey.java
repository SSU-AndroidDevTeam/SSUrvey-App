package com.example.ssurvey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;

public class RegisteredSurvey extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_survey);

        // XML에 정의된 ConstraintLayout 가져오기
        ConstraintLayout constraintLayout_myInformation = findViewById(R.id.constraintLayout_registered_survey);

        navigationBar(constraintLayout_myInformation);
    }
}