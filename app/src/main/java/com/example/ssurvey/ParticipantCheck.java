package com.example.ssurvey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ParticipantCheck extends MainActivity {

    Button goSurveyResultRegistrantBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_check);

        // XML에 정의된 ConstraintLayout 가져오기
        ConstraintLayout constraintLayout_survey_main = findViewById(R.id.constraintLayout_participant_check);

        navigationBar(constraintLayout_survey_main);

        goSurveyResultRegistrantBtn = findViewById(R.id.button_before_participant_check);

        goSurveyResultRegistrantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SurveyResultRegistrant.class);
                startActivity(intent);
            }
        });
    }
}