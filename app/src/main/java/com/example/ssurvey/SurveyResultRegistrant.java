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
import com.example.ssurvey.model.SurveyStatistics;
import com.example.ssurvey.service.CbCode;
import com.example.ssurvey.service.SurveyCallback;
import com.example.ssurvey.service.SurveyStatCallback;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class SurveyResultRegistrant extends MainActivity {

    private FirebaseManager fbManager;

    Button goParticipantCheckBtn; //응답자확인으로 이동하는 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_result_registrant);

        // XML에 정의된 ConstraintLayout 가져오기
        ConstraintLayout constraintLayout_survey_main = findViewById(R.id.constraintLayout_survey_result_registrant);

        navigationBar(constraintLayout_survey_main);

        fbManager = new FirebaseManager();

        // SurveyId를 Intent에서 가져옴
        Intent intent = getIntent();
        String surveyId = intent.getStringExtra("surveyId");

        goParticipantCheckBtn = findViewById(R.id.button_goParticipantCheck_survey_result_registrant);
        goParticipantCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ParticipantCheck.class);
                intent.putExtra("surveyId", surveyId);
                startActivity(intent);
            }
        });

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
            TextView titleSurveyResultRegistrant = findViewById(R.id.textView_title_survey_result_registrant);
            TextView dateSurveyResultRegistrant = findViewById(R.id.textView_date_survey_result_registrant);
            TextView dDateSurveyResultRegistrant = findViewById(R.id.textView_Ddate_survey_result_registrant);

            // Survey의 정보를 UI에 설정
            titleSurveyResultRegistrant.setText(survey.getName());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

// Firebase Timestamp 객체를 Date 객체로 변환
            Date openDate = survey.getOpenDate().toDate();
            Date closeDate = survey.getCloseDate().toDate();

// Date 객체를 문자열로 포맷
            String formattedOpenDate = dateFormat.format(openDate);
            String formattedCloseDate = dateFormat.format(closeDate);

            String displayText = formattedOpenDate + " ~ " + formattedCloseDate;
            dateSurveyResultRegistrant.setText(displayText);

            // 현재 날짜를 구합니다.
            Date currentDate = new Date();

// closeDate와의 날짜 차이를 계산합니다.
            long timeDiff = closeDate.getTime() - currentDate.getTime();
            long daysDiff = TimeUnit.MILLISECONDS.toDays(timeDiff);

// 계산된 D-day를 문자열로 표시합니다.
            String dDayText;
            if (daysDiff > 0) {
                dDayText = "D-" + (daysDiff + 1); // 남은 날짜를 D-1부터 시작하도록 표시
            } else if (daysDiff == 0) {
                dDayText = "D-day";
            } else {
                dDayText = "종료";
            }

            dDateSurveyResultRegistrant.setText(dDayText);


            TextView q1SurveyResultRegistrant = findViewById(R.id.textView_q1_survey_result_registrant);
            TextView q1ans1SurveyResultRegistrant = findViewById(R.id.textView_q1ans1_survey_result_registrant);
            TextView q1ans2SurveyResultRegistrant = findViewById(R.id.textView_q1ans2_survey_result_registrant);
            TextView q1ans3SurveyResultRegistrant = findViewById(R.id.textView_q1ans3_survey_result_registrant);
            TextView q1ans4SurveyResultRegistrant = findViewById(R.id.textView_q1ans4_survey_result_registrant);
            TextView q1ans5SurveyResultRegistrant = findViewById(R.id.textView_q1ans5_survey_result_registrant);

            TextView q2SurveyResultRegistrant = findViewById(R.id.textView_q2_survey_result_registrant);
            TextView q2ans1SurveyResultRegistrant = findViewById(R.id.textView_q2ans1_survey_result_registrant);
            TextView q2ans2SurveyResultRegistrant = findViewById(R.id.textView_q2ans2_survey_result_registrant);
            TextView q2ans3SurveyResultRegistrant = findViewById(R.id.textView_q2ans3_survey_result_registrant);
            TextView q2ans4SurveyResultRegistrant = findViewById(R.id.textView_q2ans4_survey_result_registrant);
            TextView q2ans5SurveyResultRegistrant = findViewById(R.id.textView_q2ans5_survey_result_registrant);

            TextView q3SurveyResultRegistrant = findViewById(R.id.textView_q3_survey_result_registrant);
            TextView q3ans1SurveyResultRegistrant = findViewById(R.id.textView_q3ans1_survey_result_registrant);
            TextView q3ans2SurveyResultRegistrant = findViewById(R.id.textView_q3ans2_survey_result_registrant);
            TextView q3ans3SurveyResultRegistrant = findViewById(R.id.textView_q3ans3_survey_result_registrant);
            TextView q3ans4SurveyResultRegistrant = findViewById(R.id.textView_q3ans4_survey_result_registrant);
            TextView q3ans5SurveyResultRegistrant = findViewById(R.id.textView_q3ans5_survey_result_registrant);

            q1SurveyResultRegistrant.setText(survey.getQ1Desc());
            q1ans1SurveyResultRegistrant.setText(survey.getQ1Ans1());
            q1ans2SurveyResultRegistrant.setText(survey.getQ1Ans2());
            q1ans3SurveyResultRegistrant.setText(survey.getQ1Ans3());
            q1ans4SurveyResultRegistrant.setText(survey.getQ1Ans4());
            q1ans5SurveyResultRegistrant.setText(survey.getQ1Ans5());

            q2SurveyResultRegistrant.setText(survey.getQ2Desc());
            q2ans1SurveyResultRegistrant.setText(survey.getQ2Ans1());
            q2ans2SurveyResultRegistrant.setText(survey.getQ2Ans2());
            q2ans3SurveyResultRegistrant.setText(survey.getQ2Ans3());
            q2ans4SurveyResultRegistrant.setText(survey.getQ2Ans4());
            q2ans5SurveyResultRegistrant.setText(survey.getQ2Ans5());

            q3SurveyResultRegistrant.setText(survey.getQ3Desc());
            q3ans1SurveyResultRegistrant.setText(survey.getQ3Ans1());
            q3ans2SurveyResultRegistrant.setText(survey.getQ3Ans2());
            q3ans3SurveyResultRegistrant.setText(survey.getQ3Ans3());
            q3ans4SurveyResultRegistrant.setText(survey.getQ3Ans4());
            q3ans5SurveyResultRegistrant.setText(survey.getQ3Ans5());
        }
        else {
            Log.d("Survey_main", "Survey object is null");
        }
    }

    private void displaySurveyStatistics(SurveyStatistics statistics) {

        TextView participantSurveyResultRegistrant = findViewById(R.id.textView_participantCount_survey_result_registrant);

        TextView q1ans1NumSurveyResultRegistrant = findViewById(R.id.textView_q1ans1percent_survey_result_registrant);
        TextView q1ans2NumSurveyResultRegistrant = findViewById(R.id.textView_q1ans2percent_survey_result_registrant);
        TextView q1ans3NumSurveyResultRegistrant = findViewById(R.id.textView_q1ans3percent_survey_result_registrant);
        TextView q1ans4NumSurveyResultRegistrant = findViewById(R.id.textView_q1ans4percent_survey_result_registrant);
        TextView q1ans5NumSurveyResultRegistrant = findViewById(R.id.textView_q1ans5percent_survey_result_registrant);

        TextView q2ans1NumSurveyResultRegistrant = findViewById(R.id.textView_q2ans1percent_survey_result_registrant);
        TextView q2ans2NumSurveyResultRegistrant = findViewById(R.id.textView_q2ans2percent_survey_result_registrant);
        TextView q2ans3NumSurveyResultRegistrant = findViewById(R.id.textView_q2ans3percent_survey_result_registrant);
        TextView q2ans4NumSurveyResultRegistrant = findViewById(R.id.textView_q2ans4percent_survey_result_registrant);
        TextView q2ans5NumSurveyResultRegistrant = findViewById(R.id.textView_q2ans5percent_survey_result_registrant);

        TextView q3ans1NumSurveyResultRegistrant = findViewById(R.id.textView_q3ans1percent_survey_result_registrant);
        TextView q3ans2NumSurveyResultRegistrant = findViewById(R.id.textView_q3ans2percent_survey_result_registrant);
        TextView q3ans3NumSurveyResultRegistrant = findViewById(R.id.textView_q3ans3percent_survey_result_registrant);
        TextView q3ans4NumSurveyResultRegistrant = findViewById(R.id.textView_q3ans4percent_survey_result_registrant);
        TextView q3ans5NumSurveyResultRegistrant = findViewById(R.id.textView_q3ans5percent_survey_result_registrant);

        double q1Count1, q1Count2, q1Count3, q1Count4, q1Count5;
        double q2Count1, q2Count2, q2Count3, q2Count4, q2Count5;
        double q3Count1, q3Count2, q3Count3, q3Count4, q3Count5;

        q1Count1 = Math.round((double)statistics.getQ1ResponseCount(1)/statistics.getTotalRespondents()*100);
        q1Count2 = Math.round((double)statistics.getQ1ResponseCount(2)/statistics.getTotalRespondents()*100);
        q1Count3 = Math.round((double)statistics.getQ1ResponseCount(3)/statistics.getTotalRespondents()*100);
        q1Count4 = Math.round((double)statistics.getQ1ResponseCount(4)/statistics.getTotalRespondents()*100);
        q1Count5 = Math.round((double)statistics.getQ1ResponseCount(5)/statistics.getTotalRespondents()*100);

        q2Count1 = Math.round((double)statistics.getQ2ResponseCount(1)/statistics.getTotalRespondents()*100);
        q2Count2 = Math.round((double)statistics.getQ2ResponseCount(2)/statistics.getTotalRespondents()*100);
        q2Count3 = Math.round((double)statistics.getQ2ResponseCount(3)/statistics.getTotalRespondents()*100);
        q2Count4 = Math.round((double)statistics.getQ2ResponseCount(4)/statistics.getTotalRespondents()*100);
        q2Count5 = Math.round((double)statistics.getQ2ResponseCount(5)/statistics.getTotalRespondents()*100);

        q3Count1 = Math.round((double)statistics.getQ3ResponseCount(1)/statistics.getTotalRespondents()*100);
        q3Count2 = Math.round((double)statistics.getQ3ResponseCount(2)/statistics.getTotalRespondents()*100);
        q3Count3 = Math.round((double)statistics.getQ3ResponseCount(3)/statistics.getTotalRespondents()*100);
        q3Count4 = Math.round((double)statistics.getQ3ResponseCount(4)/statistics.getTotalRespondents()*100);
        q3Count5 = Math.round((double)statistics.getQ3ResponseCount(5)/statistics.getTotalRespondents()*100);

        participantSurveyResultRegistrant.setText(String.valueOf(statistics.getTotalRespondents()));

        q1ans1NumSurveyResultRegistrant.setText(String.valueOf(q1Count1));
        q1ans2NumSurveyResultRegistrant.setText(String.valueOf(q1Count2));
        q1ans3NumSurveyResultRegistrant.setText(String.valueOf(q1Count3));
        q1ans4NumSurveyResultRegistrant.setText(String.valueOf(q1Count4));
        q1ans5NumSurveyResultRegistrant.setText(String.valueOf(q1Count5));

        q2ans1NumSurveyResultRegistrant.setText(String.valueOf(q2Count1));
        q2ans2NumSurveyResultRegistrant.setText(String.valueOf(q2Count2));
        q2ans3NumSurveyResultRegistrant.setText(String.valueOf(q2Count3));
        q2ans4NumSurveyResultRegistrant.setText(String.valueOf(q2Count4));
        q2ans5NumSurveyResultRegistrant.setText(String.valueOf(q2Count5));

        q3ans1NumSurveyResultRegistrant.setText(String.valueOf(q3Count1));
        q3ans2NumSurveyResultRegistrant.setText(String.valueOf(q3Count2));
        q3ans3NumSurveyResultRegistrant.setText(String.valueOf(q3Count3));
        q3ans4NumSurveyResultRegistrant.setText(String.valueOf(q3Count4));
        q3ans5NumSurveyResultRegistrant.setText(String.valueOf(q3Count5));
    }
}