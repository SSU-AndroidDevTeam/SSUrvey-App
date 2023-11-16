package com.example.ssurvey;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.ssurvey.databinding.ActivityFirebaseExampleBinding;
import com.example.ssurvey.databinding.ActivityMainBinding;

import java.util.Map;

public class FirebaseExampleActivity extends AppCompatActivity {
    private ActivityFirebaseExampleBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_example);
        binding = ActivityFirebaseExampleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 클라 단에서 Firestore 데이터 가져오는 예시
        // 1. FirebaseCallable 인터페이스의 call 함수를 오버라이드 (파이어베이스로부터 데이터 수신 시 어떻게 처리되길 원하는지 로직 작성)
        // 파이어베이스로부터 데이터를 받았을 때 실행되길 원하는 로직을 구현한다
        // 아래 예시에서는 파이어베이스로부터 받은 data의 name 정보를 액티비티의 TextView에 출력하고 있다
        FirebaseCallable callable = new FirebaseCallable() {
            @Override
            public void call(Map<String, Object> data, boolean hasFailed) {
                // 예외 처리
                if (hasFailed) {
                    Log.d("FB", "알 수 없는 이유로 파이어스토어에 접근할 수 없습니다.");
                    return;
                } else if (data == null) {
                    Log.d("FB", "파이어스토어에서 요청한 데이터를 찾을 수 없습니다.");
                }

                // 로직 실행
                binding.text.setText((String) data.get("name"));
            }
        };

        // 2. FirebaseManager의 public한 메소드들 중 원하는 함수를 호출한다 (실제 데이터 요청)
        // 이때 함수 실행 시 인자에 필요한 것들을 모두 넘겨준다.
        // 인자는 함수마다 다를 수 있으나, FirebaseCallable 객체는 공통적으로 전달한다.
        FirebaseManager fbManager = new FirebaseManager();
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼을 누르면 loadDataOfPath()를 통해 파이어베이스에게 데이터를 요청한다
                // 해당 함수는 인자로 전달한 collection의 해당 document를 파이어베이스로부터 읽어온다.
                // 아래 예시에서 요청할 데이터의 collection명은 "Surveys"이고 document명은 "DummySurvey"이다
                // 해당 함수에서는 내부적으로 인자로 전달한 FirebaseCallable 객체의 call() 함수가 실행된다
                fbManager.loadDataOfPath(callable, "Surveys", "DummySurvey");
            }
        });
    }
}