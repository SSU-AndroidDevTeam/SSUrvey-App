package com.example.ssurvey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EditProfile extends MainActivity {

    Button editCompleteBtn; //회원 정보 수정 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // XML에 정의된 ConstraintLayout 가져오기
        ConstraintLayout constraintLayout_myInformation = findViewById(R.id.constraintLayout_editProfile);

        navigationBar(constraintLayout_myInformation);

        editCompleteBtn = findViewById(R.id.button_edit_complete_editProfile);

        editCompleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyInformation.class); //설문 대상 선택으로 돌아가는 버튼이벤트
                startActivity(intent);
            }
        });
    }
}