package com.example.ssurvey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ssurvey.model.Survey;
import com.example.ssurvey.model.SurveyResponse;
import com.example.ssurvey.service.CbCode;
import com.example.ssurvey.service.SurveyCallback;
import com.google.firebase.Timestamp;

import java.util.Date;

public class Survey_ing extends MainActivity {

    private FirebaseManager fbManager;
    Button surveyGoCompleteBtn; //설문 진행중에서 설문 완료로 이동하는 버튼

    RadioGroup radioGroup1, radioGroup2, radioGroup3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_ing);

        // XML에 정의된 ConstraintLayout 가져오기
        ConstraintLayout constraintLayout_survey_ing = findViewById(R.id.constraintLayout_survey_ing);

        // navigationBar 함수 호출
        navigationBar(constraintLayout_survey_ing);

        radioGroup1 = findViewById(R.id.radioGroup_radioGroup1_survey_ing);
        radioGroup2 = findViewById(R.id.radioGroup_radioGroup2_survey_ing);
        radioGroup3 = findViewById(R.id.radioGroup_radioGroup3_survey_ing);

        fbManager = new FirebaseManager();

        // SurveyId를 Intent에서 가져옴
        Intent intent = getIntent();
        String surveyId = intent.getStringExtra("surveyId");
        Log.d("FB", "surveyId");

        //설문 진행중에서 설문 완료로 이동하는 버튼이벤트
        surveyGoCompleteBtn = findViewById(R.id.button_surveycomplete_survey_ing);
        surveyGoCompleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedRadioButton1Id = radioGroup1.getCheckedRadioButtonId();

                int q1Result = 1;
                int q2Result = 1;
                int q3Result = 1;
                if (selectedRadioButton1Id == R.id.radioButton_btn1_question1_survey_ing) {
                    q1Result = 1;
                } else if (selectedRadioButton1Id == R.id.radioButton_btn2_question1_survey_ing) {
                    q1Result = 2;
                } else if (selectedRadioButton1Id == R.id.radioButton_btn3_question1_survey_ing) {
                    q1Result = 3;
                } else if (selectedRadioButton1Id == R.id.radioButton_btn4_question1_survey_ing) {
                    q1Result = 4;
                } else if (selectedRadioButton1Id == R.id.radioButton_btn5_question1_survey_ing) {
                    q1Result = 5;
                }

                int selectedRadioButton2Id = radioGroup2.getCheckedRadioButtonId();

                if (selectedRadioButton2Id == R.id.radioButton_btn1_question2_survey_ing) {
                    q2Result = 1;
                } else if (selectedRadioButton2Id == R.id.radioButton_btn2_question2_survey_ing) {
                    q2Result = 2;
                } else if (selectedRadioButton2Id == R.id.radioButton_btn3_question2_survey_ing) {
                    q2Result = 3;
                } else if (selectedRadioButton2Id == R.id.radioButton_btn4_question2_survey_ing) {
                    q2Result = 4;
                } else if (selectedRadioButton2Id == R.id.radioButton_btn5_question2_survey_ing) {
                    q2Result = 5;
                }

                int selectedRadioButton3Id = radioGroup3.getCheckedRadioButtonId();

                if (selectedRadioButton3Id == R.id.radioButton_btn1_question3_survey_ing) {
                    q3Result = 1;
                } else if (selectedRadioButton3Id == R.id.radioButton_btn2_question3_survey_ing) {
                    q3Result = 2;
                } else if (selectedRadioButton3Id == R.id.radioButton_btn3_question3_survey_ing) {
                    q3Result = 3;
                } else if (selectedRadioButton3Id == R.id.radioButton_btn4_question3_survey_ing) {
                    q3Result = 4;
                } else if (selectedRadioButton3Id == R.id.radioButton_btn5_question3_survey_ing) {
                    q3Result = 5;
                }

                AuthManager authManager = AuthManager.getInstance();

                SurveyResponse response = new SurveyResponse(
                        authManager.getCurrentId(),
                        q1Result,
                        q2Result,
                        q3Result,
                        new Timestamp(new Date(System.currentTimeMillis()))
                );

                if (surveyId == null || surveyId.isEmpty()) {
                    Log.wtf("SurveySubmit", "NO SURVEYID");
                }
                String responseId = fbManager.addSurveyResponse(response, surveyId);

                Intent intent = new Intent(getApplicationContext(), Survey_complete.class);
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
            TextView titleSurveyIng = findViewById(R.id.textView_title_survey_ing);

            // Survey의 정보를 UI에 설정
            titleSurveyIng.setText(survey.getName());

            TextView q1SurveyIng = findViewById(R.id.textView_question1_title_survey_ing);
            TextView q1ans1SurveyIng = findViewById(R.id.textView_question1_radio1_survey_ing);
            TextView q1ans2SurveyIng = findViewById(R.id.textView_question1_radio2_survey_ing);
            TextView q1ans3SurveyIng = findViewById(R.id.textView_question1_radio3_survey_ing);
            TextView q1ans4SurveyIng = findViewById(R.id.textView_question1_radio4_survey_ing);
            TextView q1ans5SurveyIng = findViewById(R.id.textView_question1_radio5_survey_ing);

            TextView q2SurveyIng = findViewById(R.id.textView_question2_title_survey_ing);
            TextView q2ans1SurveyIng = findViewById(R.id.textView_question2_radio1_survey_ing);
            TextView q2ans2SurveyIng = findViewById(R.id.textView_question2_radio2_survey_ing);
            TextView q2ans3SurveyIng = findViewById(R.id.textView_question2_radio3_survey_ing);
            TextView q2ans4SurveyIng = findViewById(R.id.textView_question2_radio4_survey_ing);
            TextView q2ans5SurveyIng = findViewById(R.id.textView_question2_radio5_survey_ing);

            TextView q3SurveyIng = findViewById(R.id.textView_question3_title_survey_ing);
            TextView q3ans1SurveyIng = findViewById(R.id.textView_question3_radio1_survey_ing);
            TextView q3ans2SurveyIng = findViewById(R.id.textView_question3_radio2_survey_ing);
            TextView q3ans3SurveyIng = findViewById(R.id.textView_question3_radio3_survey_ing);
            TextView q3ans4SurveyIng = findViewById(R.id.textView_question3_radio4_survey_ing);
            TextView q3ans5SurveyIng = findViewById(R.id.textView_question3_radio5_survey_ing);

            q1SurveyIng.setText(survey.getQ1Desc());
            q1ans1SurveyIng.setText(survey.getQ1Ans1());
            q1ans2SurveyIng.setText(survey.getQ1Ans2());
            q1ans3SurveyIng.setText(survey.getQ1Ans3());
            q1ans4SurveyIng.setText(survey.getQ1Ans4());
            q1ans5SurveyIng.setText(survey.getQ1Ans5());

            q2SurveyIng.setText(survey.getQ2Desc());
            q2ans1SurveyIng.setText(survey.getQ2Ans1());
            q2ans2SurveyIng.setText(survey.getQ2Ans2());
            q2ans3SurveyIng.setText(survey.getQ2Ans3());
            q2ans4SurveyIng.setText(survey.getQ2Ans4());
            q2ans5SurveyIng.setText(survey.getQ2Ans5());

            q3SurveyIng.setText(survey.getQ3Desc());
            q3ans1SurveyIng.setText(survey.getQ3Ans1());
            q3ans2SurveyIng.setText(survey.getQ3Ans2());
            q3ans3SurveyIng.setText(survey.getQ3Ans3());
            q3ans4SurveyIng.setText(survey.getQ3Ans4());
            q3ans5SurveyIng.setText(survey.getQ3Ans5());
        }
        else {
            Log.d("Survey_main", "Survey object is null");
        }
    }
}