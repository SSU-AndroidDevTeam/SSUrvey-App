package com.example.ssurvey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ssurvey.model.Survey;
import com.example.ssurvey.service.CbCode;
import com.example.ssurvey.service.SurveyCallback;

public class ParticipantSurveyResult extends MainActivity {

    private FirebaseManager fbManager;
    Button goParticipantCheckBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_survey_result);

        // XML에 정의된 ConstraintLayout 가져오기
        ConstraintLayout constraintLayout_survey_main = findViewById(R.id.constraintLayout_participant_survey_result);

        navigationBar(constraintLayout_survey_main);

        goParticipantCheckBtn = findViewById(R.id.button_before_participant_survey_result);

        goParticipantCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ParticipantCheck.class);
                startActivity(intent);
            }
        });

        fbManager = new FirebaseManager();

        // SurveyId를 Intent에서 가져옴
        Intent intent = getIntent();
        String surveyId = intent.getStringExtra("surveyId");

        // FirebaseManager를 통해 Survey 데이터 로드
        if (surveyId != null) {
            fbManager.loadSurveyOfId(surveyCallback, surveyId);
        } else {
            Log.d("Survey_main", "SurveyId is null");
        }
    }

    // SurveyCallback 구현
    private SurveyCallback surveyCallback = new SurveyCallback() {
        @Override
        public void onCallback(Survey survey, CbCode cbCode) {
            // 예외 처리
            switch (cbCode) {
                case OK:
                    displaySurveyDetails(survey);
                    break;
                case ERROR:
                    Log.d("FB", "알 수 없는 이유로 파이어스토어에 접근할 수 없습니다.");
                    break;
                case NOT_FOUND:
                    Log.d("FB", "파이어스토어에서 요청한 데이터를 찾을 수 없습니다.");
                    break;
            }
        }
    };

    private void displaySurveyDetails(Survey survey) {
        // Survey 정보를 화면에 표시하는 로직을 작성

        if (survey != null) {
            TextView participantParticipantSurveyResult = findViewById(R.id.textView_participant_participant_survey_result);

            TextView q1ParticipantSurveyResult = findViewById(R.id.textView_q1_participant_survey_result);
            TextView q2ParticipantSurveyResult = findViewById(R.id.textView_q2_participant_survey_result);
            TextView q3ParticipantSurveyResult = findViewById(R.id.textView_q3_participant_survey_result);
            TextView q1ansParticipantSurveyResult = findViewById(R.id.textView_q1ans_participant_survey_result);
            TextView q2ansParticipantSurveyResult = findViewById(R.id.textView_q2ans_participant_survey_result);
            TextView q3ansParticipantSurveyResult = findViewById(R.id.textView_q3ans_participant_survey_result);

            q1ParticipantSurveyResult.setText(survey.getQ1Desc());
            q2ParticipantSurveyResult.setText(survey.getQ2Desc());
            q3ParticipantSurveyResult.setText(survey.getQ3Desc());
        }
        else {
            Log.d("Survey_main", "Survey object is null");
        }
    }
}