package com.example.ssurvey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ssurvey.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.example.ssurvey.util.Util;

/***
 * 로그인 화면 액티비티
 */// 231120 east
public class LoginActivity extends AppCompatActivity {

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

        etId = binding.id;
        etPw = binding.password;
        Button btLogin = binding.login;
        Button btJoin = binding.join;

        // 로그인 버튼이 눌렸을 때
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        // 회원가입 버튼이 눌렸을 때
        btJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                join();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // 현재 로그인한 유저가 있는지 확인 후, 로그인 되어있으면 메인 화면으로 이동한다.
        ChangeActivity();
    }

    /***
     * 입력된 학번ID를 이메일 양식으로 변환하여 반환
     * @return ID
     */
    private String getInputId() {
        return Util.id2EmailId(etId.getText().toString());
    }

    /***
     * 입력된 PW 반환
     * @return PW
     */
    private String getInputPw() {
        return etPw.getText().toString();
    }

    /***
     * 로그인
     */
    private void login() {
        mAuth.signInWithEmailAndPassword(getInputId(), getInputPw()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // 로그인 성공
                if (task.isSuccessful()) {
                    Log.d(TAG, "login success");
                    ChangeActivity();
                }
                // 로그인 실패
                else {
                    Log.d(TAG, "login failure");
                    Toast.makeText(getApplicationContext(), "아이디 혹은 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                    ChangeActivity();
                }
            }
        });
    }

    /***
     * 회원가입
     */
    private void join() {
        mAuth.createUserWithEmailAndPassword(getInputId(), getInputPw()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "join success");
                    ChangeActivity();
                } else {
                    Log.d(TAG, "join failure");
                    Toast.makeText(getApplicationContext(), "회원가입에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                    ChangeActivity();
                }
            }
        });
    }

    /***
     * 액티비티 전환
     */
    private void ChangeActivity() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("USER_PROFILE", "email: " + user.getEmail() + "\n" + "uid: " + user.getUid());
            startActivity(intent);
        }
    }
}