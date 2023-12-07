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

            // 날짜 형식에 맞게 변환하여 표시 (예시, 실제 날짜 형식에 따라 수정 필요)
            dateSurveyMain.setText(survey.getOpenDate().toDate().toString() + " ~ " + survey.getCloseDate().toDate().toString());

            // D-day 계산 및 표시 (예시, 실제 D-day 계산 로직에 따라 수정 필요)
            long dDayMillis = survey.getCloseDate().toDate().getTime() - System.currentTimeMillis();
            int dDay = (int) (dDayMillis / (24 * 60 * 60 * 1000));
            dDateSurveyMain.setText("D-" + dDay);

            // 설문 설명 설정
            descSurveyMain.setText(survey.getDesc());
        } else {
            Log.d("Survey_main", "Survey object is null");
        }
    }
}