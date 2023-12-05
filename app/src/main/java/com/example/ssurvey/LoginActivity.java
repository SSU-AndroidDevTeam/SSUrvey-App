package com.example.ssurvey;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.ssurvey.databinding.ActivityLoginBinding;

/***
 * 로그인 화면 액티비티
 */
public class LoginActivity extends MainActivity {

    final String TAG = "LoginActivity";

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 로그인이 된 상태라면 로그인 액티비티를 닫는다.
        if (AuthManager.getInstance().isLoggedIn()) {
            finish();
            return;
        }

        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 로그인 버튼이 눌렸을 때
        binding.loginBtnLogin.setOnClickListener(v -> {
            if (!isValidInput())
                return;

            AuthManager.getInstance().getFirebaseAuth().
                    signInWithEmailAndPassword(AuthManager.id2Email(binding.loginEtId.getText().toString()), binding.loginEtPw.getText().toString()).
                    addOnCompleteListener(this, task -> {
                        Log.d(TAG, "signInWithEmailAndPassword()");

                        // 로그인 성공
                        if (task.isSuccessful()) {
                            Log.d(TAG, "login success");

                            // User 데이터 가져오기
                            AuthManager.getInstance().renewCurrentUser();

                            Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(this, Home.class));
                            finish();
                        }
                        // 로그인 실패
                        else {
                            Log.d(TAG, "login failure");
                            Toast.makeText(getApplicationContext(), "아이디 혹은 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                        }

                        Log.d(TAG, "~signInWithEmailAndPassword()");
                    });

        });

        // 회원가입 버튼이 눌렸을 때
        binding.loginBtnJoin.setOnClickListener(v -> {
            startActivity(new Intent(this, JoinActivity.class));
        });

        super.navigationBar(binding.getRoot());
        super.hideKeyboard(binding.getRoot());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 로그인이 된 상태라면 로그인 액티비티를 닫는다.
        if (AuthManager.getInstance().isLoggedIn())
            finish();
    }

    private boolean isValidInput() {
        String id = binding.loginEtId.getText().toString();
        String pw = binding.loginEtPw.getText().toString();

        if (id.length() == 0) {
            Toast.makeText(getApplicationContext(), "학번을 입력해주세요.", Toast.LENGTH_SHORT).show();
            focusView(binding.loginEtId);
            return false;
        }

        if (id.length() != 8) {
            Toast.makeText(getApplicationContext(), "학번은 여덟 자여야 합니다.", Toast.LENGTH_SHORT).show();
            focusView(binding.loginEtId);
            return false;
        }

        if (pw.length() == 0) {
            Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            focusView(binding.loginEtPw);
            return false;
        }

        if (pw.length() < 6) {
            Toast.makeText(getApplicationContext(), "비밀번호는 여섯 자 이상이어야 합니다.", Toast.LENGTH_SHORT).show();
            focusView(binding.loginEtPw);
            return false;
        }

        return true;
    }
}