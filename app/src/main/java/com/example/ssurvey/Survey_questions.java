package com.example.ssurvey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


//editText에 입력된 설문 질문들을 서버로 보내주는 함수를 만들어야함

public class Survey_questions extends MainActivity {

    Button surveyQuestionsNextBtn; //설문 세부 사항에서 설문 대상 선택으로 넘어가는 버튼
    Button surveyQuestionsBeforeBtn; //설문 세부 사항에서 설문 개요로 돌아가는 버튼


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_questions);

        // XML에 정의된 ConstraintLayout 가져오기
        ConstraintLayout constraintLayout_survey_questions = findViewById(R.id.constraintLayout_survey_questions);

        // navigationBar 함수 호출
        navigationBar(constraintLayout_survey_questions);


        //설문 세부 사항에서 설문 대상 선택으로 넘어가는 버튼이벤트
        //설문 세부 사항에서 설문 개요로 돌아가는 버튼이벤트
        surveyQuestionsNextBtn = findViewById(R.id.button_next_survey_questions);
        surveyQuestionsBeforeBtn = findViewById(R.id.button_before_survey_questions);

        surveyQuestionsNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Survey_targetSelection.class);
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

    }
}

