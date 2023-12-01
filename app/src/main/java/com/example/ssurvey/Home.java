package com.example.ssurvey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

//홈화면에 정보를 받아서 띄워주는 함수 있어야함

public class Home extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Recyclerview_adapter_home adapter;

    Button homeGoMyInfoBtn; //홈 화면에서 내 정보 화면으로 넘어가는 버튼
    Button homeGoSettingBtn; //홈 화면에서 설정 화면으로 넘어가는 버튼
    Button homeGoSurveyOutlineBtn; //홈 화면에서 설문 개요 화면으로 넘어가는 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 계정 정보 가져오기
        AuthManager.getInstance();

        recyclerView = findViewById(R.id.recyclerview_home);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 샘플 데이터 생성 (SurveyItem 대신 실제 데이터 모델 클래스로 변경)
        List<SurveyItem> data = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            data.add(new SurveyItem("Title " + i, "Description " + i, "Date " + i));
        }
        // ...

        // 어댑터 설정
        adapter = new Recyclerview_adapter_home(data);
        recyclerView.setAdapter(adapter);

        // 리사이클러뷰 아이템 클릭 이벤트 처리
        adapter.setOnItemClickListener(new Recyclerview_adapter_home.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // 클릭된 아이템의 위치에 대한 처리를 여기에 추가

                // 예시: 클릭된 아이템의 데이터 가져오기
                SurveyItem clickedItem = data.get(position);

                // 새로운 액티비티로 이동하는 코드
                Intent intent = new Intent(Home.this, Survey_main.class);
                // 여기에 클릭된 아이템과 관련된 데이터를 인텐트에 추가할 수 있습니다.
                // 예시: 아이템의 제목을 전달
                //intent.putExtra("itemTitle", clickedItem.getTitle());
                startActivity(intent);
            }
        });


        homeGoMyInfoBtn = findViewById(R.id.button_goMyInfo_home);
        homeGoSettingBtn = findViewById(R.id.button_goSetting_home);
        homeGoSurveyOutlineBtn = findViewById(R.id.button_goSurveyOutline_home);

        homeGoMyInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(AuthManager.getInstance().isLoggedIn())
                    intent = new Intent(getApplicationContext(), MyInformation.class);
                else
                    intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        homeGoSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Setting.class);
                startActivity(intent);
            }
        });

        homeGoSurveyOutlineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Survey_outline.class);
                startActivity(intent);
            }
        });
    }

}