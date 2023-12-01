package com.example.ssurvey;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ssurvey.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;
import java.util.regex.Matcher;


/***
 * 로그인 화면 액티비티
 */// 231120 east
public class LoginActivity extends MainActivity {

    final String TAG = "LoginActivity";

    ActivityLoginBinding binding;

    EditText etId, etPw;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();

        etId = binding.id;
        etPw = binding.password;
        Button btLogin = binding.login;
        Button btJoin = binding.join;

        // 로그인 버튼이 눌렸을 때
        btLogin.setOnClickListener(v -> login());

        // 회원가입 버튼이 눌렸을 때
        btJoin.setOnClickListener(v -> join());

        super.navigationBar(binding.getRoot());
    }

    @Override
    public void onStart() {
        super.onStart();
        // 현재 로그인한 유저가 있는지 확인 후, 로그인 되어있으면 메인 화면으로 이동한다.
        ChangeActivity();
    }

    private boolean isValidInput() {
        if(etId.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "학번을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(etPw.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        Pattern pattern = Pattern.compile("^\\d+$");
        Matcher matcher = pattern.matcher(etId.getText().toString());
        if(matcher.matches() == false) {
            Toast.makeText(getApplicationContext(), "학번은 숫자로만 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(etId.getText().toString().length() != 8) {
            Toast.makeText(getApplicationContext(), "학번은 여덟 자여야 합니다.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(etPw.getText().toString().length() < 6) {
            Toast.makeText(getApplicationContext(), "비밀번호는 여섯 자 이상이어야 합니다.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /***
     * 로그인
     */
    private void login() {
        if(isValidInput() == false)
            return;

        mAuth.signInWithEmailAndPassword(AuthManager.id2Email(etId.getText().toString()), etPw.getText().toString()).addOnCompleteListener(this, task -> {
            Log.d(TAG,"signInWithEmailAndPassword()");

            // 로그인 성공
            if (task.isSuccessful()) {
                Log.d(TAG, "login success");
                ChangeActivity();
            }
            // 로그인 실패
            else {
                Log.d(TAG, "login failure");
                Toast.makeText(getApplicationContext(), "아이디 혹은 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
            }

            Log.d(TAG,"~signInWithEmailAndPassword()");
        });
    }

    /***
     * 회원가입
     */
    private void join() {

        startActivity(new Intent(this, JoinActivity.class));

    }

    /***
     * 액티비티 전환
     */
    private void ChangeActivity() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // 어스 매니저에 현재 유저 정보 저장
            AuthManager.getInstance().renewCurrentUser();
            startActivity(new Intent(this, Home.class));
        }
    }
}