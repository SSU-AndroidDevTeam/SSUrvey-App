package com.example.ssurvey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.ssurvey.model.Survey;
import com.example.ssurvey.model.SurveyStatistics;
import com.example.ssurvey.service.CbCode;
import com.example.ssurvey.service.SurveyCallback;
import com.example.ssurvey.service.SurveyStatCallback;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
        Log.d("FB", "surveyId");

        // FirebaseManager를 통해 Survey 데이터 로드
        if (surveyId != null) {
            fbManager.loadSurveyOfId(surveyCallback, surveyId);
            fbManager.getSurveyStatistics(surveyId, statCallable);
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

    SurveyStatCallback statCallable = new SurveyStatCallback() {
        @Override
        public void onCallback(SurveyStatistics statistics, CbCode cbCode) {
            // 예외 처리
            switch (cbCode) {
                case OK:
                    displaySurveyStatistics(statistics);
                    break;
                case ERROR:
                    Log.d("FB", "알 수 없는 이유로 파이어스토어에 접근할 수 없습니다.");
                    return;
                case NOT_FOUND:
                    Log.d("FB", "파이어스토어에서 요청한 데이터를 찾을 수 없습니다.");
                    return;
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

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

// Firebase Timestamp 객체를 Date 객체로 변환
            Date openDate = survey.getOpenDate().toDate();
            Date closeDate = survey.getCloseDate().toDate();

// Date 객체를 문자열로 포맷
            String formattedOpenDate = dateFormat.format(openDate);
            String formattedCloseDate = dateFormat.format(closeDate);

            String displayText = formattedOpenDate + " ~ " + formattedCloseDate;
            dateSurveyComplete.setText(displayText);
            // D-day 계산 및 표시 (예시, 실제 D-day 계산 로직에 따라 수정 필요)
            long dDayMillis = survey.getCloseDate().toDate().getTime() - System.currentTimeMillis();
            int dDay = (int) (dDayMillis / (24 * 60 * 60 * 1000));
            dDateSurveyComplete.setText("D-" + dDay);

            // 설문 설명 설정
            descSurveyComplete.setText(survey.getDesc());

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


        }
        else {
            Log.d("Survey_main", "Survey object is null");
        }
    }
    private void displaySurveyStatistics(SurveyStatistics statistics) {

        TextView participantCountSurveyComplete = findViewById(R.id.textView_participantCount_survey_complete);

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

        participantCountSurveyComplete.setText(String.valueOf(statistics.getTotalRespondents()));

        int q1Count1, q1Count2, q1Count3, q1Count4, q1Count5;
        int q2Count1, q2Count2, q2Count3, q2Count4, q2Count5;
        int q3Count1, q3Count2, q3Count3, q3Count4, q3Count5;

        q1Count1 = (int)((double)statistics.getQ1ResponseCount(1)/statistics.getTotalRespondents())*100;
        q1Count2 = (int)((double)statistics.getQ1ResponseCount(2)/statistics.getTotalRespondents())*100;
        q1Count3 = (int)((double)statistics.getQ1ResponseCount(3)/statistics.getTotalRespondents())*100;
        q1Count4 = (int)((double)statistics.getQ1ResponseCount(4)/statistics.getTotalRespondents())*100;
        q1Count5 = (int)((double)statistics.getQ1ResponseCount(5)/statistics.getTotalRespondents())*100;

        q2Count1 = (int)((double)statistics.getQ2ResponseCount(1)/statistics.getTotalRespondents())*100;
        q2Count2 = (int)((double)statistics.getQ2ResponseCount(2)/statistics.getTotalRespondents())*100;
        q2Count3 = (int)((double)statistics.getQ2ResponseCount(3)/statistics.getTotalRespondents())*100;
        q2Count4 = (int)((double)statistics.getQ2ResponseCount(4)/statistics.getTotalRespondents())*100;
        q2Count5 = (int)((double)statistics.getQ2ResponseCount(5)/statistics.getTotalRespondents())*100;

        q3Count1 = (int)((double)statistics.getQ3ResponseCount(1)/statistics.getTotalRespondents())*100;
        q3Count2 = (int)((double)statistics.getQ3ResponseCount(2)/statistics.getTotalRespondents())*100;
        q3Count3 = (int)((double)statistics.getQ3ResponseCount(3)/statistics.getTotalRespondents())*100;
        q3Count4 = (int)((double)statistics.getQ3ResponseCount(4)/statistics.getTotalRespondents())*100;
        q3Count5 = (int)((double)statistics.getQ3ResponseCount(5)/statistics.getTotalRespondents())*100;

        q1ans1NumSurveyComplete.setText(String.valueOf(q1Count1));
        q1ans2NumSurveyComplete.setText(String.valueOf(q1Count2));
        q1ans3NumSurveyComplete.setText(String.valueOf(q1Count3));
        q1ans4NumSurveyComplete.setText(String.valueOf(q1Count4));
        q1ans5NumSurveyComplete.setText(String.valueOf(q1Count5));

        q2ans1NumSurveyComplete.setText(String.valueOf(q2Count1));
        q2ans2NumSurveyComplete.setText(String.valueOf(q2Count2));
        q2ans3NumSurveyComplete.setText(String.valueOf(q2Count3));
        q2ans4NumSurveyComplete.setText(String.valueOf(q2Count4));
        q2ans5NumSurveyComplete.setText(String.valueOf(q2Count5));

        q3ans1NumSurveyComplete.setText(String.valueOf(q3Count1));
        q3ans2NumSurveyComplete.setText(String.valueOf(q3Count2));
        q3ans3NumSurveyComplete.setText(String.valueOf(q3Count3));
        q3ans4NumSurveyComplete.setText(String.valueOf(q3Count4));
        q3ans5NumSurveyComplete.setText(String.valueOf(q3Count5));
    }
}