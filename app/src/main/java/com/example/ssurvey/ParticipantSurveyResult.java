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
import com.example.ssurvey.model.SurveyResponse;
import com.example.ssurvey.service.CbCode;
import com.example.ssurvey.service.SurveyCallback;
import com.example.ssurvey.service.SurveyResponseCallback;

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

        // SurveyId를 Intent에서 가져옴 어디서 받아올건지 알아볼 것
        Intent intent = getIntent();
        String surveyId = intent.getStringExtra("surveyId");

        // FirebaseManager를 통해 Survey 데이터 로드
        if (surveyId != null) {
            fbManager.loadSurveyOfId(surveyCallback, surveyId);
            fbManager.loadSurveyResponse(respCallable, "DummySurvey", "홍길동");
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

    SurveyResponseCallback respCallable = new SurveyResponseCallback() {
        @Override
        public void onCallback(SurveyResponse response, CbCode cbCode) {
            switch (cbCode) {
                case OK:
                    displaySurveyResult(response);
                    break;
                case ERROR:
                    Log.d("FB", "알 수 없는 이유로 파이어스토어에 접근할 수 없습니다.");
                    return;
                case NOT_FOUND:
                    Log.d("FB", "해당 유저의 응답을 찾을 수 없습니다.");
                    return;
            }
        }
    };

    TextView q1ansParticipantSurveyResult = findViewById(R.id.textView_q1ans_participant_survey_result);
    TextView q2ansParticipantSurveyResult = findViewById(R.id.textView_q2ans_participant_survey_result);
    TextView q3ansParticipantSurveyResult = findViewById(R.id.textView_q3ans_participant_survey_result);

    int q1Num, q2Num, q3Num;

    private void displaySurveyDetails(Survey survey) {
        // Survey 정보를 화면에 표시하는 로직을 작성

        if (survey != null) {

            TextView q1ParticipantSurveyResult = findViewById(R.id.textView_q1_participant_survey_result);
            TextView q2ParticipantSurveyResult = findViewById(R.id.textView_q2_participant_survey_result);
            TextView q3ParticipantSurveyResult = findViewById(R.id.textView_q3_participant_survey_result);

            q1ParticipantSurveyResult.setText(survey.getQ1Desc());
            q2ParticipantSurveyResult.setText(survey.getQ2Desc());
            q3ParticipantSurveyResult.setText(survey.getQ3Desc());

            if (q1Num == 1) {
                q1ansParticipantSurveyResult.setText(survey.getQ1Ans1());
            }
            else if (q1Num == 2) {
                q1ansParticipantSurveyResult.setText(survey.getQ1Ans2());
            }
            else if (q1Num == 3) {
                q1ansParticipantSurveyResult.setText(survey.getQ1Ans3());
            }
            else if (q1Num == 4) {
                q1ansParticipantSurveyResult.setText(survey.getQ1Ans4());
            }
            else if (q1Num == 5) {
                q1ansParticipantSurveyResult.setText(survey.getQ1Ans5());
            }

            if (q2Num == 1) {
                q2ansParticipantSurveyResult.setText(survey.getQ2Ans1());
            }
            else if (q2Num == 2) {
                q2ansParticipantSurveyResult.setText(survey.getQ2Ans2());
            }
            else if (q2Num == 3) {
                q2ansParticipantSurveyResult.setText(survey.getQ2Ans3());
            }
            else if (q2Num == 4) {
                q2ansParticipantSurveyResult.setText(survey.getQ2Ans4());
            }
            else if (q2Num == 5) {
                q2ansParticipantSurveyResult.setText(survey.getQ2Ans5());
            }

            if (q3Num == 1) {
                q3ansParticipantSurveyResult.setText(survey.getQ3Ans1());
            }
            else if (q3Num == 2) {
                q3ansParticipantSurveyResult.setText(survey.getQ3Ans2());
            }
            else if (q3Num == 3) {
                q3ansParticipantSurveyResult.setText(survey.getQ3Ans3());
            }
            else if (q3Num == 4) {
                q3ansParticipantSurveyResult.setText(survey.getQ3Ans4());
            }
            else if (q3Num == 5) {
                q3ansParticipantSurveyResult.setText(survey.getQ3Ans5());
            }
        }
        else {
            Log.d("Survey_main", "Survey object is null");
        }
    }

    private void displaySurveyResult(SurveyResponse response) {
        // Survey 정보를 화면에 표시하는 로직을 작성

        if (response != null) {
            TextView participantParticipantSurveyResult = findViewById(R.id.textView_participant_participant_survey_result);

            participantParticipantSurveyResult.setText(response.getUserId() + "님의 설문 응답 결과");

            q1Num = response.getQ1Response();
            q2Num = response.getQ2Response();
            q3Num = response.getQ3Response();
        }
        else {
            Log.d("Survey_main", "Survey object is null");
        }
    }
}
