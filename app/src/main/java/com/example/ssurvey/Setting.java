package com.example.ssurvey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class Setting extends MainActivity {

    Switch sw_participate, sw_register, sw_invite;

    NotificationManager manager;
    NotificationCompat.Builder builder;

    private static String CHANNEL_ID = "SSurvey";
    private static String CHANEL_NAME = "SSurveyNoti";


    // 유저 아이디와 등록자 아이디가 같고 Ddate가 0이면
    // 유저 아이디가 유저가 참여한 설문에 존재하고 Ddate가 0이면
    // 참여자 목록에서 설문 초청 버튼을 누르면

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // XML에 정의된 ConstraintLayout 가져오기
        ConstraintLayout constraintLayout_setting = findViewById(R.id.constraintLayout_setting);

        navigationBar(constraintLayout_setting);

        sw_participate = findViewById(R.id.switch_participated_setting);
        sw_register = findViewById(R.id.switch_registered_setting);
        sw_invite = findViewById(R.id.switch_invited_setting);

        sw_participate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sw_participate.isChecked()) {
                    showNoti("설문 종료!", "참여한 설문이 종료되었습니다.");
                } else {
                    // 스위치가 꺼진 경우에 수행할 작업 추가
                }
            }
        });
        sw_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sw_register.isChecked()) {
                    showNoti("설문 종료!", "등록한 설문이 종료되었습니다.");
                } else {
                    // 스위치가 꺼진 경우에 수행할 작업 추가
                }
            }
        });
        sw_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sw_invite.isChecked()) {

                } else {
                    showNoti("설문 초청!", "새로운 설문에 초청되었습니다.");
                }
            }
        });
    }

    public void showNoti(String title, String text) {
        builder = null;
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // 버전 오레오 이상일 경우
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(
                    new NotificationChannel(CHANNEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            );

            builder = new NotificationCompat.Builder(this, CHANNEL_ID);

            // 하위 버전일 경우
        } else {
            builder = new NotificationCompat.Builder(this);
        }

        // 알림창 제목
        builder.setContentTitle(title);

        // 알림창 메시지
        builder.setContentText(text);

        // 알림창 아이콘
        builder.setSmallIcon(R.drawable.surveyregister);

        Notification notification = builder.build();

        // 알림창 실행
        manager.notify(1, notification);
    }
}