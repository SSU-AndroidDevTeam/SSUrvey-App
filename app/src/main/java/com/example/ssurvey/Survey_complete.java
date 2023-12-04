package com.example.ssurvey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.ssurvey.model.Survey;
import com.example.ssurvey.service.CbCode;
import com.example.ssurvey.service.SurveyCallback;

public class Survey_complete extends MainActivity {

    private FirebaseManager fbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_complete);

        // XML에 정의된 ConstraintLayout 가져오기
        ConstraintLayout constraintLayout_survey_complete = findViewById(R.id.constraintLayout_survey_complete);

        // navigationBar 함수 호출
        navigationBar(constraintLayout_survey_complete);

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
            TextView titleSurveyComplete = findViewById(R.id.textView_title_survey_complete);
            TextView dateSurveyComplete = findViewById(R.id.textView_date_survey_complete);
            TextView dDateSurveyComplete = findViewById(R.id.textView_Ddate_survey_complete);
            TextView descSurveyComplete = findViewById(R.id.textView_discription_survey_complete);

            // Survey의 정보를 UI에 설정
            titleSurveyComplete.setText(survey.getName());

            // 날짜 형식에 맞게 변환하여 표시 (예시, 실제 날짜 형식에 따라 수정 필요)
            dateSurveyComplete.setText(survey.getOpenDate().toDate().toString() + " ~ " + survey.getCloseDate().toDate().toString());

            // D-day 계산 및 표시 (예시, 실제 D-day 계산 로직에 따라 수정 필요)
            long dDayMillis = survey.getCloseDate().toDate().getTime() - System.currentTimeMillis();
            int dDay = (int) (dDayMillis / (24 * 60 * 60 * 1000));
            dDateSurveyComplete.setText("D-" + dDay);

            // 설문 설명 설정
            descSurveyComplete.setText(survey.getDesc());

            TextView participantCountSurveyComplete = findViewById(R.id.textView_participantCount_survey_complete);

            TextView q1SurveyComplete = findViewById(R.id.textView_q1_survey_complete);
            TextView q1ans1SurveyComplete = findViewById(R.id.textView_q1ans1_survey_complete);
            TextView q1ans2SurveyComplete = findViewById(R.id.textView_q1ans2_survey_complete);
            TextView q1ans3SurveyComplete = findViewById(R.id.textView_q1ans3_survey_complete);
            TextView q1ans4SurveyComplete = findViewById(R.id.textView_q1ans4_survey_complete);
            TextView q1ans5SurveyComplete = findViewById(R.id.textView_q1ans5_survey_complete);

            TextView q2SurveyComplete = findViewById(R.id.textView_q2_survey_complete);
            TextView q2ans1SurveyComplete = findViewById(R.id.textView_q2ans1_survey_complete);
            TextView q2ans2SurveyComplete = findViewById(R.id.textView_q2ans2_survey_complete);
            TextView q2ans3SurveyComplete = findViewById(R.id.textView_q2ans3_survey_complete);
            TextView q2ans4SurveyComplete = findViewById(R.id.textView_q2ans4_survey_complete);
            TextView q2ans5SurveyComplete = findViewById(R.id.textView_q2ans5_survey_complete);

            TextView q3SurveyComplete = findViewById(R.id.textView_q3_survey_complete);
            TextView q3ans1SurveyComplete = findViewById(R.id.textView_q3ans1_survey_complete);
            TextView q3ans2SurveyComplete = findViewById(R.id.textView_q3ans2_survey_complete);
            TextView q3ans3SurveyComplete = findViewById(R.id.textView_q3ans3_survey_complete);
            TextView q3ans4SurveyComplete = findViewById(R.id.textView_q3ans4_survey_complete);
            TextView q3ans5SurveyComplete = findViewById(R.id.textView_q3ans5_survey_complete);

            q1SurveyComplete.setText(survey.getQ1Desc());
            q1ans1SurveyComplete.setText(survey.getQ1Ans1());
            q1ans2SurveyComplete.setText(survey.getQ1Ans2());
            q1ans3SurveyComplete.setText(survey.getQ1Ans3());
            q1ans4SurveyComplete.setText(survey.getQ1Ans4());
            q1ans5SurveyComplete.setText(survey.getQ1Ans5());

            q2SurveyComplete.setText(survey.getQ2Desc());
            q2ans1SurveyComplete.setText(survey.getQ2Ans1());
            q2ans2SurveyComplete.setText(survey.getQ2Ans2());
            q2ans3SurveyComplete.setText(survey.getQ2Ans3());
            q2ans4SurveyComplete.setText(survey.getQ2Ans4());
            q2ans5SurveyComplete.setText(survey.getQ2Ans5());

            q3SurveyComplete.setText(survey.getQ3Desc());
            q3ans1SurveyComplete.setText(survey.getQ3Ans1());
            q3ans2SurveyComplete.setText(survey.getQ3Ans2());
            q3ans3SurveyComplete.setText(survey.getQ3Ans3());
            q3ans4SurveyComplete.setText(survey.getQ3Ans4());
            q3ans5SurveyComplete.setText(survey.getQ3Ans5());

            TextView q1ans1NumSurveyComplete = findViewById(R.id.textView_q1ans1percent_survey_complete);
            TextView q1ans2NumSurveyComplete = findViewById(R.id.textView_q1ans2percent_survey_complete);
            TextView q1ans3NumSurveyComplete = findViewById(R.id.textView_q1ans3percent_survey_complete);
            TextView q1ans4NumSurveyComplete = findViewById(R.id.textView_q1ans4percent_survey_complete);
            TextView q1ans5NumSurveyComplete = findViewById(R.id.textView_q1ans5percent_survey_complete);

            TextView q2ans1NumSurveyComplete = findViewById(R.id.textView_q2ans1percent_survey_complete);
            TextView q2ans2NumSurveyComplete = findViewById(R.id.textView_q2ans2percent_survey_complete);
            TextView q2ans3NumSurveyComplete = findViewById(R.id.textView_q2ans3percent_survey_complete);
            TextView q2ans4NumSurveyComplete = findViewById(R.id.textView_q2ans4percent_survey_complete);
            TextView q2ans5NumSurveyComplete = findViewById(R.id.textView_q2ans5percent_survey_complete);

            TextView q3ans1NumSurveyComplete = findViewById(R.id.textView_q3ans1percent_survey_complete);
            TextView q3ans2NumSurveyComplete = findViewById(R.id.textView_q3ans2percent_survey_complete);
            TextView q3ans3NumSurveyComplete = findViewById(R.id.textView_q3ans3percent_survey_complete);
            TextView q3ans4NumSurveyComplete = findViewById(R.id.textView_q3ans4percent_survey_complete);
            TextView q3ans5NumSurveyComplete = findViewById(R.id.textView_q3ans5percent_survey_complete);
        }
        else {
            Log.d("Survey_main", "Survey object is null");
        }
    }
}