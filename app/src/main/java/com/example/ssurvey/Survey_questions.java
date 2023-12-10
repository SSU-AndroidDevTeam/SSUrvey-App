package com.example.ssurvey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ssurvey.model.Survey;


//editText에 입력된 설문 질문들을 서버로 보내주는 함수를 만들어야함

public class Survey_questions extends MainActivity {


    Button surveyQuestionsNextBtn; //설문 세부 사항에서 설문 대상 선택으로 넘어가는 버튼
    Button surveyQuestionsBeforeBtn; //설문 세부 사항에서 설문 개요로 돌아가는 버튼

    EditText q1DescEditText, q1Ans1EditText, q1Ans2EditText, q1Ans3EditText, q1Ans4EditText, q1Ans5EditText;
    EditText q2DescEditText, q2Ans1EditText, q2Ans2EditText, q2Ans3EditText, q2Ans4EditText, q2Ans5EditText;
    EditText q3DescEditText, q3Ans1EditText, q3Ans2EditText, q3Ans3EditText, q3Ans4EditText, q3Ans5EditText;

    String q1DescStr, q1Ans1Str, q1Ans2Str, q1Ans3Str, q1Ans4Str, q1Ans5Str;
    String q2DescStr, q2Ans1Str, q2Ans2Str, q2Ans3Str, q2Ans4Str, q2Ans5Str;
    String q3DescStr, q3Ans1Str, q3Ans2Str, q3Ans3Str, q3Ans4Str, q3Ans5Str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_questions);

        // XML에 정의된 ConstraintLayout 가져오기
        ConstraintLayout constraintLayout_survey_questions = findViewById(R.id.constraintLayout_survey_questions);

        // navigationBar 함수 호출
        navigationBar(constraintLayout_survey_questions);

        surveyQuestionsNextBtn = findViewById(R.id.button_next_survey_questions);
        surveyQuestionsBeforeBtn = findViewById(R.id.button_before_survey_questions);

        surveyQuestionsNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Survey_targetSelection.class);
                // 선택한 항목을 유지하기 위해 기존 액티비티 재활용
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
        surveyQuestionsBeforeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Survey_outline.class);
                startActivity(intent);
            }
        });

        q1DescEditText = findViewById(R.id.editText_question1_title_survey_questions);
        q1Ans1EditText = findViewById(R.id.editText_question1_radio1_survey_questions);
        q1Ans2EditText = findViewById(R.id.editText_question1_radio2_survey_questions);
        q1Ans3EditText = findViewById(R.id.editText_question1_radio3_survey_questions);
        q1Ans4EditText = findViewById(R.id.editText_question1_radio4_survey_questions);
        q1Ans5EditText = findViewById(R.id.editText_question1_radio5_survey_questions);

        q2DescEditText = findViewById(R.id.editText_question2_title_survey_questions);
        q2Ans1EditText = findViewById(R.id.editText_question2_radio1_survey_questions);
        q2Ans2EditText = findViewById(R.id.editText_question2_radio2_survey_questions);
        q2Ans3EditText = findViewById(R.id.editText_question2_radio3_survey_questions);
        q2Ans4EditText = findViewById(R.id.editText_question2_radio4_survey_questions);
        q2Ans5EditText = findViewById(R.id.editText_question2_radio5_survey_questions);

        q3DescEditText = findViewById(R.id.editText_question3_title_survey_questions);
        q3Ans1EditText = findViewById(R.id.editText_question3_radio1_survey_questions);
        q3Ans2EditText = findViewById(R.id.editText_question3_radio2_survey_questions);
        q3Ans3EditText = findViewById(R.id.editText_question3_radio3_survey_questions);
        q3Ans4EditText = findViewById(R.id.editText_question3_radio4_survey_questions);
        q3Ans5EditText = findViewById(R.id.editText_question3_radio5_survey_questions);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Survey survey = MakeSurvey.makeSurvey;

        if (survey.getQ1Desc() == null) {
            q1DescStr = q1DescEditText.getText().toString();
        } else {
            q1DescStr = survey.getQ1Desc();
        }
        q1DescEditText.setText(q1DescStr);

        if (survey.getQ1Ans1() == null) {
            q1Ans1Str = q1Ans1EditText.getText().toString();
        } else {
            q1Ans1Str = survey.getQ1Ans1();
        }
        q1Ans1EditText.setText(q1Ans1Str);

        if (survey.getQ1Ans2() == null) {
            q1Ans2Str = q1Ans2EditText.getText().toString();
        } else {
            q1Ans2Str = survey.getQ1Ans2();
        }
        q1Ans2EditText.setText(q1Ans2Str);

        if (survey.getQ1Ans3() == null) {
            q1Ans3Str = q1Ans3EditText.getText().toString();
        } else {
            q1Ans3Str = survey.getQ1Ans3();
        }
        q1Ans3EditText.setText(q1Ans3Str);

        if (survey.getQ1Ans4() == null) {
            q1Ans4Str = q1Ans4EditText.getText().toString();
        } else {
            q1Ans4Str = survey.getQ1Ans4();
        }
        q1Ans4EditText.setText(q1Ans4Str);

        if (survey.getQ1Ans5() == null) {
            q1Ans5Str = q1Ans5EditText.getText().toString();
        } else {
            q1Ans5Str = survey.getQ1Ans5();
        }
        q1Ans5EditText.setText(q1Ans5Str);

        if (survey.getQ2Desc() == null) {
            q2DescStr = q2DescEditText.getText().toString();
        } else {
            q2DescStr = survey.getQ2Desc();
        }
        q2DescEditText.setText(q2DescStr);

        if (survey.getQ2Ans1() == null) {
            q2Ans1Str = q2Ans1EditText.getText().toString();
        } else {
            q2Ans1Str = survey.getQ2Ans1();
        }
        q2Ans1EditText.setText(q2Ans1Str);

        if (survey.getQ2Ans2() == null) {
            q2Ans2Str = q2Ans2EditText.getText().toString();
        } else {
            q2Ans2Str = survey.getQ2Ans2();
        }
        q2Ans2EditText.setText(q2Ans2Str);

        if (survey.getQ2Ans3() == null) {
            q2Ans3Str = q2Ans3EditText.getText().toString();
        } else {
            q2Ans3Str = survey.getQ2Ans3();
        }
        q2Ans3EditText.setText(q2Ans3Str);

        if (survey.getQ2Ans4() == null) {
            q2Ans4Str = q2Ans4EditText.getText().toString();
        } else {
            q2Ans4Str = survey.getQ2Ans4();
        }
        q2Ans4EditText.setText(q2Ans4Str);

        if (survey.getQ2Ans5() == null) {
            q2Ans5Str = q2Ans5EditText.getText().toString();
        } else {
            q2Ans5Str = survey.getQ2Ans5();
        }
        q2Ans5EditText.setText(q2Ans5Str);

        if (survey.getQ3Desc() == null) {
            q3DescStr = q3DescEditText.getText().toString();
        } else {
            q3DescStr = survey.getQ3Desc();
        }
        q3DescEditText.setText(q3DescStr);

        if (survey.getQ3Ans1() == null) {
            q3Ans1Str = q3Ans1EditText.getText().toString();
        } else {
            q3Ans1Str = survey.getQ3Ans1();
        }
        q3Ans1EditText.setText(q3Ans1Str);

        if (survey.getQ3Ans2() == null) {
            q3Ans2Str = q3Ans2EditText.getText().toString();
        } else {
            q3Ans2Str = survey.getQ3Ans2();
        }
        q3Ans2EditText.setText(q3Ans2Str);

        if (survey.getQ3Ans3() == null) {
            q3Ans3Str = q3Ans3EditText.getText().toString();
        } else {
            q3Ans3Str = survey.getQ3Ans3();
        }
        q3Ans3EditText.setText(q3Ans3Str);

        if (survey.getQ3Ans4() == null) {
            q3Ans4Str = q3Ans4EditText.getText().toString();
        } else {
            q3Ans4Str = survey.getQ3Ans4();
        }
        q3Ans4EditText.setText(q3Ans4Str);

        if (survey.getQ3Ans5() == null) {
            q3Ans5Str = q3Ans5EditText.getText().toString();
        } else {
            q3Ans5Str = survey.getQ3Ans5();
        }
        q3Ans5EditText.setText(q3Ans5Str);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Survey survey = MakeSurvey.makeSurvey;

        survey.setQ1Desc(q1DescEditText.getText().toString());
        survey.setQ1Ans1(q1Ans1EditText.getText().toString());
        survey.setQ1Ans2(q1Ans2EditText.getText().toString());
        survey.setQ1Ans3(q1Ans3EditText.getText().toString());
        survey.setQ1Ans4(q1Ans4EditText.getText().toString());
        survey.setQ1Ans5(q1Ans5EditText.getText().toString());

        survey.setQ2Desc(q2DescEditText.getText().toString());
        survey.setQ2Ans1(q2Ans1EditText.getText().toString());
        survey.setQ2Ans2(q2Ans2EditText.getText().toString());
        survey.setQ2Ans3(q2Ans3EditText.getText().toString());
        survey.setQ2Ans4(q2Ans4EditText.getText().toString());
        survey.setQ2Ans5(q2Ans5EditText.getText().toString());

        survey.setQ3Desc(q3DescEditText.getText().toString());
        survey.setQ3Ans1(q3Ans1EditText.getText().toString());
        survey.setQ3Ans2(q3Ans2EditText.getText().toString());
        survey.setQ3Ans3(q3Ans3EditText.getText().toString());
        survey.setQ3Ans4(q3Ans4EditText.getText().toString());
        survey.setQ3Ans5(q3Ans5EditText.getText().toString());
    }
}

