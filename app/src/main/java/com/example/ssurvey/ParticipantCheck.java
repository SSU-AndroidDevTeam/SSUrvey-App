package com.example.ssurvey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.ssurvey.model.Survey;
import com.example.ssurvey.service.CbCode;
import com.example.ssurvey.service.SurveyCallback;
import com.example.ssurvey.service.SurveyReplicantCallback;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ParticipantCheck extends MainActivity {

    Button goSurveyResultRegistrantBtn;
    private ArrayList<String> replicantIds;
    private FirebaseManager fbManager;
    private RecyclerView recyclerView;
    private ReplicantRecycleAdapter replAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_check);

        // XML에 정의된 ConstraintLayout 가져오기
        ConstraintLayout constraintLayout_survey_main = findViewById(R.id.constraintLayout_participant_check);
        navigationBar(constraintLayout_survey_main);
        goSurveyResultRegistrantBtn = findViewById(R.id.button_before_participant_check);

        // 인텐트에서 설문 아이디 추출
        Intent intent = getIntent();
        String surveyId = intent.getStringExtra("surveyId");

        // 설문 참여자 목록 가져오기
        fbManager = new FirebaseManager();
        fbManager.getSurveyReplicants(surveyId, replicantCallback); // replicantIds에 저장

        goSurveyResultRegistrantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SurveyResultRegistrant.class);
                startActivity(intent);
            }
        });
    }

    // SurveyCallback 구현
    private SurveyReplicantCallback replicantCallback = new SurveyReplicantCallback() {
        @Override
        public void onCallback(ArrayList<String> replicants, CbCode cbCode) {
            // 예외 처리
            switch (cbCode) {
                case OK:
                    break;
                case ERROR:
                    Log.d("FB", "알 수 없는 이유로 파이어스토어에 접근할 수 없습니다.");
                    break;
                case NOT_FOUND:
                    Log.d("FB", "파이어스토어에서 요청한 데이터를 찾을 수 없습니다.");
                    break;
            }

            // 로직
            replicantIds = replicants;
            AuthManager authManager = AuthManager.getInstance();

            // 각 id별 이름 가져오기
            ArrayList<String> replicantNames = new ArrayList<>();
            for (String repId : replicantIds) {
                replicantNames.add("<아이디 " + repId + "의 유저명>"); // TODO: 유저 id -> 유저 명으로 변환하는 함수 필요. 여기 한 줄만 수정하면 됩니다
            }

            // 리사이클러뷰 생성 및 어댑터 생성, 연결
            recyclerView = (RecyclerView) findViewById(R.id.recyclerView_participant_check);
            replAdapter = new ReplicantRecycleAdapter();

            // 참여자 이름 목록 전달
            replAdapter.setReplicantNames(replicantNames);
            recyclerView.setAdapter(replAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        }
    };
}