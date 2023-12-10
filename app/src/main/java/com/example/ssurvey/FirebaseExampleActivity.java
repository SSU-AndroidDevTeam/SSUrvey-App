package com.example.ssurvey;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.ssurvey.databinding.ActivityFirebaseExampleBinding;
import com.example.ssurvey.model.Survey;
import com.example.ssurvey.model.SurveyResponse;
import com.example.ssurvey.model.SurveyStatistics;
import com.example.ssurvey.model.SurveyTarget;
import com.example.ssurvey.service.CbCode;
import com.example.ssurvey.service.SurveyCallback;
import com.example.ssurvey.service.SurveyReplicantCallback;
import com.example.ssurvey.service.SurveyResponseCallback;
import com.example.ssurvey.service.SurveyStatCallback;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


public class FirebaseExampleActivity extends AppCompatActivity {
    private FirebaseManager fbManager; // Firebase 작업은 모두 FirebaseManager를 거쳐서 처리한다
    private ActivityFirebaseExampleBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_example);
        binding = ActivityFirebaseExampleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fbManager = new FirebaseManager(); // 작업 전 새 객체 생성

        //////////////////////////////// A. 액티비티 단에서 Firestore 데이터 가져오는 예시 /////////////////////////////////
        // 1. FirebaseCallable 인터페이스의 call 함수를 오버라이드 (파이어베이스로부터 데이터 수신 시 어떻게 처리되길 원하는지 로직 작성)
        // 파이어베이스로부터 데이터를 받았을 때 실행되길 원하는 로직을 구현한다
        // 아래 예시에서는 파이어베이스로부터 받은 data의 name 정보를 액티비티의 TextView에 출력하고 있다
        SurveyCallback callable = new SurveyCallback() {
            @Override
            public void onCallback(Survey survey, CbCode cbCode) {
                // 예외 처리
                switch (cbCode) {
                    case OK:
                        break;
                    case ERROR:
                        Log.d("FB", "알 수 없는 이유로 파이어스토어에 접근할 수 없습니다.");
                        return;
                    case NOT_FOUND:
                        Log.d("FB", "파이어스토어에서 요청한 데이터를 찾을 수 없습니다.");
                        return;
                }
                // 로직 실행
                binding.text.setText(survey.getName()); // .getName()으로 name 정보를 읽어온다.
            }
        };

        // 2. FirebaseManager의 public한 메소드들 중 원하는 함수를 호출한다 (실제 데이터 요청)
        // 이때 함수 실행 시 인자에 필요한 것들을 모두 넘겨준다.
        // 인자는 함수마다 다를 수 있으나, FirebaseCallable 객체는 공통적으로 전달한다.
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼을 누르면 loadSurveyOfId()를 통해 파이어스토어의 설문 DB에 접근해 해당 Id의 도큐먼트를 요청한다
                // 이때 Id는 난수값이며, 파이어베이스에서 생성해주는 값이다.
                // 여기서는 예외적으로 DummySurvey라는 수동으로 등록해둔 Id를 사용하는 중이다
                // 아래 예시에서 요청할 데이터의 document명은 "DummySurvey"이다
                // 해당 함수에서는 내부적으로 인자로 전달한 FirebaseCallable 객체의 call() 함수가 실행된다
                fbManager.loadSurveyOfId(callable, "DummySurvey");
            }
        });

        //////////////////////////////// B. 액티비티 단에서 Firestore에 데이터 쓰는 예시 /////////////////////////////////
        // 1. Survey 클래스의 데이터를 생성한다.
        // 이때 Survey의 surveyData 멤버 변수에 모든 값들이 제대로 채워져 있어야 한다.
        // 값은 이 예시처럼 한번에 채울 필요는 없고 일련의 과정들을 거치며 하나씩 채워 나가도 괜찮다.
        Survey writeExample = new Survey(
                "축구선수 인기투표",
                "좋아하는 축구선수를 골라주세요!",
                "(구현 안됨)",
                "(구현 안됨)",
                "가장 좋아하는 스트라이커는?",
                "손흥민",
                "메시",
                "호날두",
                "수아레즈",
                "네이마르",
                "가장 좋아하는 골키퍼는?",
                "부폰",
                "노이어",
                "카시야스",
                "데 헤아",
                "오블락",
                "가장 좋아하는 미드필더는?",
                "박지성",
                "이니에스타",
                "사비",
                "긱스",
                "디마리아",
                new Timestamp(new Date(System.currentTimeMillis())), // 실제 사용 시에는 원하는 시각의 Date 객체를 생성해 전달해야함
                new Timestamp(new Date(System.currentTimeMillis())),
                new SurveyTarget()
        );

        // 2. 함수를 사용해 파이어스토어에 Write한다.
        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 등록된 설문의 고유 Id를 String을 반환한다.
                // 이 값은 추후 이 설문을 찾아야 할 때 사용할 수 있다.
                String uniqueId = fbManager.addNewSurvey(writeExample);
                binding.text2.setText(uniqueId);
            }
        });

        /////////////////////////////////// C. 설문 응답 결과를 Write하는 예시 /////////////////////////////
        // 설문 응답 결과는 <설문 uniqueId> 하위에 <설문 응답자 userId>로 저장된다
        SurveyResponse responseExample = new SurveyResponse(
                "<사용자 uniqueId 값>",
                1,
                2,
                3,
                new Timestamp(new Date(System.currentTimeMillis()))
        );

        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 사용자의 설문 응답 결과를 DB에 추가한다
                // 여기서 인자로 응답할 설문의 uniqueId를 전달해야 한다
                String responseId = fbManager.addSurveyResponse(responseExample, "DummySurvey");
                binding.text3.setText(responseId);
            }
        });

        ////////////////////////////////// D. 설문 응답 결과를  Read하는 예시 /////////////////////////////
        // 설문 응답을 Read하는 예시는 Survey를 read하는 것과 거의 동일하다
        // FirebaseManager의 loadSurveyResponse()에 콜백과 설문 id, 유저 id를 넘기면 해당 유저의 응답에 접근할 수 있다
        // 만약 해당 유저의 응답을 찾을 수 없다면 설문 결과로는 null값을, cbCode로는 NOT_FOUND를 전달한다.
        SurveyResponseCallback respCallable = new SurveyResponseCallback() {
            @Override
            public void onCallback(SurveyResponse response, CbCode cbCode) {
                switch (cbCode) {
                    case OK:
                        break;
                    case ERROR:
                        Log.d("FB", "알 수 없는 이유로 파이어스토어에 접근할 수 없습니다.");
                        return;
                    case NOT_FOUND:
                        Log.d("FB", "해당 유저의 응답을 찾을 수 없습니다.");
                        return;
                }
                // 로직 실행
                binding.text4.setText("홍길동의 DummySurvey 1번 문항 응답 번호: " + response.getQ1Response());
            }
        };

        binding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fbManager.loadSurveyResponse(respCallable, "DummySurvey", "홍길동");
            }
        });

        ///////////////////////////////// E. 설문 통계 산출 예시 ////////////////////////////////////////
        // 콜백 객체 정의
        SurveyStatCallback statCallable = new SurveyStatCallback() {
            @Override
            public void onCallback(SurveyStatistics statistics, CbCode cbCode) {
                // 예외 처리
                switch (cbCode) {
                    case OK:
                        break;
                    case ERROR:
                        Log.d("FB", "알 수 없는 이유로 파이어스토어에 접근할 수 없습니다.");
                        return;
                    case NOT_FOUND:
                        Log.d("FB", "파이어스토어에서 요청한 데이터를 찾을 수 없습니다.");
                        return;
                }
                // 로직 실행
                // SurveyStatistics의 여러 getter들을 사용해 원하는 통계값을 구할 수 있다
                String text = "총 응답자 수: " + statistics.getTotalRespondents();
                text += ", 1번 문항에서 3번을 선택한 사람의 수: " + statistics.getQ1ResponseCount(3);
                binding.text5.setText(text);
            }
        };

        binding.button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fbManager.getSurveyStatistics("DummySurvey", statCallable);
                // 지금은 DummySurvey를 문자열로 전달 중이지만, 실제 사용시에는 통계를 얻으려는 설문의 uniqueId를 전달해야 한다
            }
        });


        /////////////////////////////////////// F. 설문 참여자 정보 Read 예시 //////////////////////////////////////
        SurveyReplicantCallback replCallback = new SurveyReplicantCallback() {
            @Override
            public void onCallback(ArrayList<String> replicants, CbCode cbCode) {
                // 예외 처리
                switch (cbCode) {
                    case OK:
                        break;
                    case ERROR:
                        Log.d("FB", "알 수 없는 이유로 파이어스토어에 접근할 수 없습니다.");
                        return;
                    case NOT_FOUND:
                        Log.d("FB", "파이어스토어에서 요청한 데이터를 찾을 수 없습니다.");
                        return;
                }

                // 로직 실행
                for (String replicant : replicants) {
                    Log.d("FB", "설문 참여자: " + replicant);
                }
            }
        };

        binding.button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fbManager.getSurveyReplicants("DummySurvey", replCallback); // 설문 id 전달
            }
        });
    }
}