package com.example.ssurvey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;

public class Setting extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // XML에 정의된 ConstraintLayout 가져오기
        ConstraintLayout constraintLayout_setting = findViewById(R.id.constraintLayout_setting);

        navigationBar(constraintLayout_setting);

        //스위치가 온일 경우 사용자에 따라 참여/등록된 설문이 Ddate가 0일때 알림을 전송
        //스위치가 온일 경우 사용자에 따라 초청시스템이 거부 되도록하는 로직을 짜야함

    }
}