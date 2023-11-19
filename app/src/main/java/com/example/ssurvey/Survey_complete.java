package com.example.ssurvey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;

public class Survey_complete extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_complete);

        // XML에 정의된 ConstraintLayout 가져오기
        ConstraintLayout constraintLayout_survey_complete = findViewById(R.id.constraintLayout_survey_complete);

        // navigationBar 함수 호출
        navigationBar(constraintLayout_survey_complete);
    }
}