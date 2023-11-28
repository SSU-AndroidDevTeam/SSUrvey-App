package com.example.ssurvey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Survey_targetSelection extends MainActivity {

    Button surveyTargetSelectionNextBtn; //설문 대상 선택에서 홈 화면으로 넘어가는 버튼
    Button surveyTargetSelectionBeforeBtn; //설문 대상 선택에서 설문 세부 사항으로 돌아가는 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_target_selection);

        // XML에 정의된 ConstraintLayout 가져오기
        ConstraintLayout constraintLayout_survey_targetSelection = findViewById(R.id.constraintLayout_survey_targetSelection);

        // navigationBar 함수 호출
        navigationBar(constraintLayout_survey_targetSelection);

        surveyTargetSelectionNextBtn = findViewById(R.id.button_next_survey_targetSelection);
        surveyTargetSelectionBeforeBtn = findViewById(R.id.button_before_survey_targetSelection);
        surveyTargetSelectionNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Survey_giftSetting.class); //경품 설정 화면으로 넘어가는 버튼이벤트
                startActivity(intent);
            }
        });
        surveyTargetSelectionBeforeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Survey_questions.class); //설문 세부 사항으로 돌아가는 버튼이벤트
                startActivity(intent);
            }
        });

    }
}


//체크박스가 체크된 것들을 확인하여 그에 맞는 textView의 텍스트를 서버로 보내주는 함수를 만들어야함