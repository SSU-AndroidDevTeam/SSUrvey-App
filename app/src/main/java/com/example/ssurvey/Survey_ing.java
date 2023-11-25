package com.example.ssurvey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Survey_ing extends MainActivity {

    //각각의 textView들의 값을 서버에서 받아와서 넣어주는 함수 작성 필요

    Button surveyGoCompleteBtn; //설문 진행중에서 설문 완료로 이동하는 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_ing);

        // XML에 정의된 ConstraintLayout 가져오기
        ConstraintLayout constraintLayout_survey_ing = findViewById(R.id.constraintLayout_survey_ing);

        // navigationBar 함수 호출
        navigationBar(constraintLayout_survey_ing);

        //설문 진행중에서 설문 완료로 이동하는 버튼이벤트
        surveyGoCompleteBtn = findViewById(R.id.button_surveycomplete_survey_ing);
        surveyGoCompleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Survey_complete.class);
                startActivity(intent);
            }
        });
    }
}