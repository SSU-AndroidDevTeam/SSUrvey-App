package com.example.ssurvey;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.ssurvey.databinding.ActivityFirebaseExampleBinding;
import com.example.ssurvey.model.Survey;
import com.example.ssurvey.service.CbCode;
import com.example.ssurvey.service.SurveyCallback;
import com.google.firebase.Timestamp;

import java.util.Date;

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
                new Timestamp(new Date(23,11,25)),
                new Timestamp(new Date(23,11,25)),
                "(구현 안됨)"
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
    }
}