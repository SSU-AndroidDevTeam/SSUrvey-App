package com.example.ssurvey;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ssurvey.model.Survey;
import com.example.ssurvey.service.CbCode;
import com.example.ssurvey.service.SurveyCallback;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Survey_main extends MainActivity {

    private FirebaseManager fbManager;
    Button surveyGoIngBtn; //설문메인에서 설문ing로 이동하는 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_main);

        // XML에 정의된 ConstraintLayout 가져오기
        ConstraintLayout constraintLayout_survey_main = findViewById(R.id.constraintLayout_survey_main);

        navigationBar(constraintLayout_survey_main);

        fbManager = new FirebaseManager();

        // SurveyId를 Intent에서 가져옴
        Intent intent = getIntent();
        String surveyId = intent.getStringExtra("surveyId");

        //설문메인에서 설문ing로 이동하는 버튼이벤트
        surveyGoIngBtn = findViewById(R.id.button_surveying_survey_main);
        surveyGoIngBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Survey_ing.class);
                intent.putExtra("surveyId", surveyId);
                startActivity(intent);
            }
        });

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
            TextView titleSurveyMain = findViewById(R.id.textView_title_survey_main);
            TextView dateSurveyMain = findViewById(R.id.textView_date_survey_main);
            TextView dDateSurveyMain = findViewById(R.id.textView_Ddate_survey_main);
            TextView descSurveyMain = findViewById(R.id.textView_discription_survey_main);

            // Survey의 정보를 UI에 설정
            titleSurveyMain.setText(survey.getName());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

// Firebase Timestamp 객체를 Date 객체로 변환
            Date openDate = survey.getOpenDate().toDate();
            Date closeDate = survey.getCloseDate().toDate();

// Date 객체를 문자열로 포맷
            String formattedOpenDate = dateFormat.format(openDate);
            String formattedCloseDate = dateFormat.format(closeDate);

            String displayText = formattedOpenDate + " ~ " + formattedCloseDate;
            dateSurveyMain.setText(displayText);

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

            dDateSurveyMain.setText(dDayText);

            // 설문 설명 설정
            descSurveyMain.setText(survey.getDesc());
        } else {
            Log.d("Survey_main", "Survey object is null");
        }
    }
}