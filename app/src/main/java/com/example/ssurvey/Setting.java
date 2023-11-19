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
    }
}