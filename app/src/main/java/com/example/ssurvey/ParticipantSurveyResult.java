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
    TextView participantParticipantSurveyResult;
    TextView q1ParticipantSurveyResult, q2ParticipantSurveyResult, q3ParticipantSurveyResult;
    TextView q1ansParticipantSurveyResult, q2ansParticipantSurveyResult, q3ansParticipantSurveyResult;
    int q1Num, q2Num, q3Num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_survey_result);

        // XML에 정의된 ConstraintLayout 가져오기
        ConstraintLayout constraintLayout_survey_main = findViewById(R.id.constraintLayout_participant_survey_result);

        navigationBar(constraintLayout_survey_main);

        goParticipantCheckBtn = findViewById(R.id.button_before_participant_survey_result);

        Intent intent = getIntent();
        String surveyId = intent.getStringExtra("surveyId");
        String replicantId = intent.getStringExtra("replicantId");
        String replicantName = intent.getStringExtra("replicantName");

        goParticipantCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ParticipantCheck.class);
                intent.putExtra("surveyId", surveyId);
                startActivity(intent);
            }
        });

        fbManager = new FirebaseManager();

        fbManager.loadSurveyResponse(respCallable, surveyId, replicantId);
        fbManager.loadSurveyOfId(surveyCallback, surveyId);


        participantParticipantSurveyResult = findViewById(R.id.textView_participant_participant_survey_result);

        participantParticipantSurveyResult.setText(replicantName + "님의 설문 응답 결과");
    }

    // SurveyCallback 구현
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

    private void displaySurveyResult(SurveyResponse response) {
        // Survey 정보를 화면에 표시하는 로직을 작성

        if (response != null) {

            q1Num = response.getQ1Response();
            q2Num = response.getQ2Response();
            q3Num = response.getQ3Response();
        }
        else {
            Log.d("Survey_main", "Survey object is null");
        }
    }

    private void displaySurveyDetails(Survey survey) {
        // Survey 정보를 화면에 표시하는 로직을 작성

        if (survey != null) {

            q1ansParticipantSurveyResult = findViewById(R.id.textView_q1ans_participant_survey_result);
            q2ansParticipantSurveyResult = findViewById(R.id.textView_q2ans_participant_survey_result);
            q3ansParticipantSurveyResult = findViewById(R.id.textView_q3ans_participant_survey_result);

            q1ParticipantSurveyResult = findViewById(R.id.textView_q1_participant_survey_result);
            q2ParticipantSurveyResult = findViewById(R.id.textView_q2_participant_survey_result);
            q3ParticipantSurveyResult = findViewById(R.id.textView_q3_participant_survey_result);

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
}
