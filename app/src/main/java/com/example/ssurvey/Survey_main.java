package com.example.ssurvey;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.View;
import android.widget.Button;


//미입력된 텍스트뷰와 이미지뷰의 값을 여기서 서버에서 받아와서 넣어주는 함수를 만들어야 한다
//설문하기 누르면 설문진행중 화면으로 넘어가게 해야함

public class Survey_main extends MainActivity {

    Button surveyGoIngBtn; //설문메인에서 설문ing로 이동하는 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_main);

        // XML에 정의된 ConstraintLayout 가져오기
        ConstraintLayout constraintLayout_survey_main = findViewById(R.id.constraintLayout_survey_main);

        navigationBar(constraintLayout_survey_main);


        //설문메인에서 설문ing로 이동하는 버튼이벤트
        surveyGoIngBtn = findViewById(R.id.button_surveying_survey_main);
        surveyGoIngBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Survey_ing.class);
                startActivity(intent);
            }
        });
    }
}