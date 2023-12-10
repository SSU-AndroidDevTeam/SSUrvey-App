package com.example.ssurvey;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyInformation extends MainActivity {

    Button goEditProfileBtn; //회원 정보 수정 버튼
    Button goRegisteredSurveyBtn; //등록한 설문 버튼
    Button goParticipatedSurveyBtn; //참여한 설문 버튼
    Button goInvitedSurveyBtn; //초청된 설문 버튼
    Button goMyGiftBtn; //당첨된 경품 버튼
    Button goLogoutBtn; // 로그아웃 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);

        // XML에 정의된 ConstraintLayout 가져오기
        ConstraintLayout constraintLayout_myInformation = findViewById(R.id.constraintLayout_myInformation);

        navigationBar(constraintLayout_myInformation);

        goEditProfileBtn = findViewById(R.id.button_edit_my_information);
        goRegisteredSurveyBtn = findViewById(R.id.button_registered_my_information);
        goParticipatedSurveyBtn = findViewById(R.id.button_participated_my_information);
        goInvitedSurveyBtn = findViewById(R.id.button_invited_my_information);
        goMyGiftBtn = findViewById(R.id.button_gift_my_information);
        goLogoutBtn = findViewById(R.id.button_logout);

        goEditProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        goRegisteredSurveyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), RegisteredSurvey.class);
                startActivity(intent);
            }
        });

        goParticipatedSurveyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ParticipatedSurvey.class);
                startActivity(intent);
            }
        });

        goInvitedSurveyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), InvitedSurvey.class);
                startActivity(intent);
            }
        });

        goMyGiftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MyGift.class);
                startActivity(intent);
            }
        });

        goLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDial4Logout();
            }
        });
    }

    /** 로그아웃 다이얼로그 */
    public void showDial4Logout() {
        new AlertDialog.Builder(this).setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                .setNegativeButton("아니오", (dialog, which) -> {
                    dialog.dismiss();
                })
                .setPositiveButton("로그아웃", (dialog, which) -> {
                    // 로그아웃
                    AuthManager.getInstance().logout();
                    finish();

                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);
                })
                .create()
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 작성 중인 설문 대상 액티비티가 있다면 종료
        Survey_targetSelection.actvityFinish();
    }
}