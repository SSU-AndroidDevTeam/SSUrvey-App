package com.example.ssurvey;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import com.example.ssurvey.databinding.ActivityJoinBinding;
import com.example.ssurvey.model.User;
import com.example.ssurvey.service.UserService;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class JoinActivity extends MainActivity {

    final String TAG = "JoinActivity";

    ActivityJoinBinding binding;

    User user;

    // password는 User 클래스에서 관리하지 않아 별도로 생성
    String password;

    /** @noinspection deprecation*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 로그인이 된 상태라면 회원가입 액티비티를 닫는다.
        if(AuthManager.getInstance().isLoggedIn()) {
            finish();
            return;
        }

        super.onCreate(savedInstanceState);
        binding = ActivityJoinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        super.hideKeyboard(binding.joinScrollView);

        /* 생년월일 다이얼로그 */
        binding.joinBtnBirth.setOnClickListener(v -> {
            Calendar calNow = Calendar.getInstance();
            int year4Dial, month4Dial, day4Dial;

            // 이미 선택된 날짜가 있는 경우, 기선택한 날짜 기준으로 다이얼로그를 띄운다.
            if(user.getBirth() != null) {
                year4Dial = user.getBirth().getYear();
                month4Dial = user.getBirth().getMonth();
                day4Dial = user.getBirth().getDate();
            } else {
                year4Dial = calNow.get(Calendar.YEAR) - 19;
                month4Dial = 0;
                day4Dial = 1;
            }

            @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, day) -> {
                        binding.joinTxtInputBirth.setText(year + (month >= 9 ? "-" : "-0") + (month + 1) + (day >= 10 ? "-" : "-0") + day);
                        user.setBirth(new Date(year - 1900, month, day));
                    }, year4Dial, month4Dial, day4Dial);

            Calendar calMax = Calendar.getInstance();
            calMax.set(calNow.get(Calendar.YEAR) - 18, Calendar.MARCH, 0);

            datePickerDialog.getDatePicker().setMaxDate(calMax.getTimeInMillis());
            datePickerDialog.show();
        });

        /* 휴대폰 번호 입력란 포커스 전후 처리 */
        binding.joinEtPhone.setOnFocusChangeListener((v, hasFocus) -> {
            String phone = binding.joinEtPhone.getText().toString();
            if (hasFocus) {
                if(!isEmptyString(phone)) {
                    // 최대 글자수 11자로 제한
                    InputFilter[] filters = new InputFilter[1];
                    filters[0] = new InputFilter.LengthFilter(11);
                    binding.joinEtPhone.setFilters(filters);
                    binding.joinEtPhone.setText(phone.replaceAll("[^0-9]", ""));
                }
            }
            else {
                if(!isEmptyString(phone)) {
                    phone = phone.replaceAll("[^0-9]", "");
                    if(phone.length() == 11) {
                        // 최대 글자수 형식화된 휴대폰 번호 문자열 길이 만큼 제한
                        String fommattedPhone = phone.substring(0, 3) + "-" + phone.substring(3, 7) + "-" + phone.substring(7);
                        InputFilter[] filters = new InputFilter[1];
                        filters[0] = new InputFilter.LengthFilter(fommattedPhone.length());
                        binding.joinEtPhone.setFilters(filters);
                        binding.joinEtPhone.setText(fommattedPhone);
                    }
                }
            }
        });

        /* 이름 입력란 포커스 아웃 처리 */
        binding.joinEtName.setOnFocusChangeListener((v, hasFocus) -> {
            String name = binding.joinEtName.getText().toString();
            if (!hasFocus)
                if(!isEmptyString(name))
                    // 입력란에 Enter가 들어가는 경우가 있어서 한글을 제외한 모든 글자 삭제 처리
                    binding.joinEtName.setText(name.replaceAll("[^가-힣]", ""));
        });

        // 여기서 설정하는 user의 값은 단순히 Visible 제어용이지, DB에 저장되는 것과 관련 없음.
        binding.joinRadioStudentType.setOnCheckedChangeListener((group, button) -> {
            // 졸업생인 경우 학년 필드를 숨김 처리한다.
            if(button == binding.joinRadioStudentTypeGraduate.getId()) {
                user.setStudentType(User.StudentType.GRADUATE);
                binding.joinLlGrade.setVisibility(View.GONE);
            }
            else {
                user.setStudentType(User.StudentType.IN);
                if(user.getStudentCourse() == User.StudentCourse.BACHELOR)
                    binding.joinLlGrade.setVisibility(View.VISIBLE);
            }
        });

        // 여기서 설정하는 user의 값은 단순히 Visible 제어용이지, DB에 저장되는 것과 관련 없음.
        binding.joinRadioStudentCourse.setOnCheckedChangeListener((group, button) -> {
            if(button == binding.joinRadioStudentCourseBachelor.getId() || button == binding.joinRadioStudentCourseBachelorMaster.getId()) {
                user.setStudentCourse(User.StudentCourse.BACHELOR);
                if(user.getStudentType() == User.StudentType.IN)
                    binding.joinLlGrade.setVisibility(View.VISIBLE);
            }
            else {
                // 학사, 학석사 과정이 아닌 경우 학년 필드를 숨김 처리한다.
                binding.joinLlGrade.setVisibility(View.GONE);
                user.setStudentCourse(User.StudentCourse.MASTER);
            }
        });

        binding.joinRadioMultiMajor.setOnCheckedChangeListener((group, button) -> {
            if(button == binding.joinRadioMultiMajorNone.getId())
                // 다전공 이수 유형이 해당 없음인 경우 다전공 소속 대학 필드를 숨김 처리한다.
                binding.joinLlCollegeMultiMajor.setVisibility(View.GONE);
            else
                binding.joinLlCollegeMultiMajor.setVisibility(View.VISIBLE);
        });

        user = new User();
        binding.joinBtnJoin.setOnClickListener(v -> {
            String resultMsg = setUserAndResultMsg();

            if(resultMsg.length() > 0)
                Toast.makeText(this, setUserAndResultMsg(), Toast.LENGTH_SHORT).show();
            // 모든 검사를 통과
            else {
                // 프로그래스 아이콘 노출 및 터치 불능 처리
                binding.joinProgress.setVisibility(View.VISIBLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Toast.makeText(this, "회원 가입 중입니다.", Toast.LENGTH_SHORT).show();

                // 학번을 이메일 형식으로 바꿔 Firebase 인증용 ID로 등록한다.
                String firebaseId = AuthManager.id2Email(user.getId());

                // Firebase Authentication에 사용자 등록
                AuthManager.getInstance().getFirebaseAuth().
                        createUserWithEmailAndPassword(firebaseId, password).addOnCompleteListener(this, task -> {
                            Log.d(TAG, "createUserWithEmailAndPassword()");

                            if (task.isSuccessful()) {
                                Log.d(TAG, "Firebase Authentication 등록 성공");

                                if(task.getResult().getUser() != null)
                                    user.setUid(task.getResult().getUser().getUid());
                                user.setFirebaseId(firebaseId);

                                // DB에 유저 등록
                                UserService.getInstance().setUser(user);

                                // 어스 매니저에 현재 유저 정보 저장
                                AuthManager.getInstance().setCurrentUser(user);
                                Toast.makeText(this, "회원 가입에 성공했습니다.", Toast.LENGTH_LONG).show();

                                // 메인 화면으로 이동
                                startActivity(new Intent(this, Home.class));
                                finish();
                            } else {
                                Log.d(TAG, "Firebase Authentication 등록 실패");

                                Exception exception = task.getException();
                                if (exception instanceof FirebaseAuthUserCollisionException)
                                    Toast.makeText(getApplicationContext(), "이미 등록된 학번입니다.", Toast.LENGTH_SHORT).show();
                                else if (exception instanceof FirebaseAuthWeakPasswordException)
                                    Toast.makeText(getApplicationContext(), "비밀번호가 너무 짧습니다.", Toast.LENGTH_SHORT).show();
                                else if (exception instanceof FirebaseAuthInvalidCredentialsException)
                                    Toast.makeText(getApplicationContext(), "잘못된 학번입니다.", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(getApplicationContext(), "회원 가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            }

                            // 프로그래스 아이콘 숨김 및 터치 가능 처리
                            binding.joinProgress.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                            Log.d(TAG, "~createUserWithEmailAndPassword()");
                    });
            }
        });
    }

    private boolean isEmptyString(String str) {
        return str == null || str.length() == 0;
    }

    /** 입력하지 않은 입력 개체에 대한 스크롤 & 포커싱에 사용
     * @Params
     * v4scroll : 스크롤 대상 View
     * @Params
     * v4focus : 포커스 대상 View
     * */
    private void scrollAndFocus(View v4scroll, View v4focus) {
        binding.joinScrollView.post(() -> {
            binding.joinScrollView.smoothScrollTo(0, v4scroll.getTop());
            v4focus.requestFocus();

            // 포커스 대상 View가 EditText라면
            if(v4focus instanceof EditText) {
                // 인풋 커서를 입력한 텍스트에서 가장 마지막에 두고,
                ((EditText) v4focus).setSelection(((EditText) v4focus).getText().length());
                // 키보드를 띄운다.
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).showSoftInput(v4focus, InputMethodManager.SHOW_IMPLICIT);
            }
        });
    }
    
    /** 선택한 라디오 버튼의 텍스트 문자열 반환 */
    private String getStringInSelectedButton(RadioGroup rg) {
        if(rg != null) {
            RadioButton rd = findViewById(rg.getCheckedRadioButtonId());
            if(rd != null)
                if(rd.getText() != null)
                    if (!isEmptyString(rd.getText().toString())) {
                        return rd.getText().toString();
                    }
        }
        return "";
    }

    /** 입력값 예외 처리 및 User 객체에 삽입. ""을 반환하면 입력값에 문제가 없다 */
    public String setUserAndResultMsg() {

        user.setId(binding.joinEtId.getText().toString());
        if(isEmptyString(user.getId())) {
            scrollAndFocus(binding.joinTxtId, binding.joinEtId);
            return "학번을 입력해 주세요.";
        }

        if(user.getId().length() != 8) {
            scrollAndFocus(binding.joinTxtId, binding.joinEtId);
            return "학번은 여덟 자리여야 합니다.";
        }

        password = binding.joinEtPw.getText().toString();
        String pwChk = binding.joinEtPwChk.getText().toString();

        if(isEmptyString(password)) {
            scrollAndFocus(binding.joinTxtPw, binding.joinEtPw);
            return "비밀번호를 입력해 주세요.";
        }

        if(password.length() < 6) {
            scrollAndFocus(binding.joinTxtPw, binding.joinEtPw);
            return "비밀번호는 최소 6자 이상이어야 합니다.";
        }

        if(isEmptyString(pwChk)) {
            scrollAndFocus(binding.joinTxtPwChk, binding.joinEtPwChk);
            return "비밀번호 확인을 입력해 주세요.";
        }

        if(pwChk.length() < 6) {
            scrollAndFocus(binding.joinTxtPwChk, binding.joinEtPwChk);
            return "비밀번호는 최소 6자 이상이어야 합니다.";
        }

        if(!password.equals(pwChk)) {
            scrollAndFocus(binding.joinTxtPw, binding.joinEtPw);
            return "비밀번호와 비밀번호 확인이 동일하지 않습니다.";
        }

        user.setName(binding.joinEtName.getText().toString());
        if(isEmptyString(user.getName())) {
            scrollAndFocus(binding.joinTxtName, binding.joinEtName);
            return "이름을 입력해 주세요.";
        }

        if(user.getName().length() < 2) {
            scrollAndFocus(binding.joinTxtName, binding.joinEtName);
            return "이름은 성을 포함해서 최소 2자 이상이어야 합니다.";
        }

        if(!user.getName().matches("[가-힣]+")) {
            scrollAndFocus(binding.joinTxtName, binding.joinEtName);
            return "이름은 한글로만 적어주세요.";
        }

        if(binding.joinRadioSex.getCheckedRadioButtonId() == -1) {
            scrollAndFocus(binding.joinTxtSex, binding.joinRadioSex);
            return "성별을 선택해 주세요.";
        }
        user.setSex(binding.joinRadioSexMale.isChecked() ? User.Sex.MALE : User.Sex.FEMALE);

        if(user.getBirth() == null) {
            scrollAndFocus(binding.joinTxtBirth, binding.joinBtnBirth);
            return "생년월일을 선택해주세요.";
        }

        user.setEmail(binding.joinEtEmail.getText().toString());
        if(isEmptyString(user.getEmail())) {
            scrollAndFocus(binding.joinTxtEmail, binding.joinEtEmail);
            return "이메일을 입력해 주세요.";
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches()) {
            scrollAndFocus(binding.joinTxtEmail, binding.joinEtEmail);
            return "올바른 이메일 형식이 아닙니다.";
        }

        user.setPhone(binding.joinEtPhone.getText().toString());
        if(isEmptyString(user.getPhone())) {
            scrollAndFocus(binding.joinTxtPhone, binding.joinEtPhone);
            return "휴대폰 번호를 입력해 주세요.";
        }

        if(user.getPhone().length() < 11) {
            scrollAndFocus(binding.joinTxtPhone, binding.joinEtPhone);
            return "휴대폰 번호를 온전하게 입력해 주세요.";
        }

        if(!user.getPhone().replaceAll("[^0-9]", "").matches("^010[0-9]+")) {
            scrollAndFocus(binding.joinTxtPhone, binding.joinEtPhone);
            return "올바른 휴대폰 번호가 아닙니다.";
        }

        if(binding.joinRadioStudentType.getCheckedRadioButtonId() == -1) {
            scrollAndFocus(binding.joinTxtStudentType, binding.joinRadioStudentType);
            return "학생 유형을 선택해 주세요.";
        }
        user.setStudentType(User.getStudentType(this, getStringInSelectedButton(binding.joinRadioStudentType)));

        if(binding.joinRadioStudentCourse.getCheckedRadioButtonId() == -1) {
            scrollAndFocus(binding.joinTxtStudentCourse, binding.joinRadioStudentCourse);
            return "학생 과정을 선택해 주세요.";
        }
        user.setStudentCourse(User.getStudentCourse(this, getStringInSelectedButton(binding.joinRadioStudentCourse)));

        if(binding.joinLlGrade.getVisibility() == View.VISIBLE) {
            String strGrade = binding.joinEtGrade.getText().toString();
            if(isEmptyString(strGrade)) {
                scrollAndFocus(binding.joinLlGrade, binding.joinEtGrade);
                return "학년을 입력해 주세요.";
            }

            int grade = Integer.parseInt(strGrade);
            if(grade < 1 || grade > 5) {
                scrollAndFocus(binding.joinLlGrade, binding.joinEtGrade);
                return "학년은 1부터 5사이의 숫자만 입력 가능합니다.";
            }
            user.setGrade(grade);
        }

        if(binding.joinRadioCollege.getCheckedRadioButtonId() == -1) {
            scrollAndFocus(binding.joinTxtCollege, binding.joinRadioCollege);
            return "소속 대학을 선택해 주세요.";
        }
        user.setCollege(User.getCollege(this, getStringInSelectedButton(binding.joinRadioCollege)));

        if(binding.joinRadioMultiMajor.getCheckedRadioButtonId() == -1) {
            scrollAndFocus(binding.joinTxtMultiMajor, binding.joinRadioMultiMajor);
            return "다전공 이수 유형을 선택해 주세요.";
        }
        user.setMultiMajor(User.getMultiMajor(this, getStringInSelectedButton(binding.joinRadioMultiMajor)));

        if(binding.joinLlCollegeMultiMajor.getVisibility() == View.VISIBLE) {
            if (binding.joinRadioCollegeMultiMajor.getCheckedRadioButtonId() == -1) {
                scrollAndFocus(binding.joinLlCollegeMultiMajor, binding.joinRadioCollegeMultiMajor);
                return "다전공 소속 대학을 선택해 주세요.";
            }
            user.setCollegeMultiMajor(User.getCollege(this, getStringInSelectedButton(binding.joinRadioCollegeMultiMajor)));
        }

        if(binding.joinRadioHasChangeMajor.getCheckedRadioButtonId() == -1) {
            scrollAndFocus(binding.joinTxtHasChangeMajor, binding.joinRadioHasChangeMajor);
            return "전과 (학과 이동) 유무를 선택해 주세요.";
        }
        user.setHasChangeMajor(binding.joinRadioHasChangeMajorTrue.isSelected());

        if(binding.joinRadioHasTeaching.getCheckedRadioButtonId() == -1) {
            scrollAndFocus(binding.joinTxtHasTeaching, binding.joinRadioHasTeaching);
            return "교직 이수 유무를 선택해 주세요.";
        }
        user.setHasTeaching(binding.joinRadioHasTeachingTrue.isSelected());

        if(binding.joinRadioHasFlunk.getCheckedRadioButtonId() == -1) {
            scrollAndFocus(binding.joinTxtHasFlunk, binding.joinRadioHasFlunk);
            return "유급 기록 유무를 선택해 주세요.";
        }
        user.setHasFlunk(binding.joinRadioHasFlunkTrue.isSelected());

        if(binding.joinRadioHasWarning.getCheckedRadioButtonId() == -1) {
            scrollAndFocus(binding.joinTxtHasWarning, binding.joinRadioHasWarning);
            return "학사 경고 기록 유무를 선택해 주세요.";
        }
        user.setHasWarning(binding.joinRadioHasWarningTrue.isSelected());

        if(binding.joinRadioHasLeave.getCheckedRadioButtonId() == -1) {
            scrollAndFocus(binding.joinTxtHasLeave, binding.joinRadioHasLeave);
            return "휴학 기록 유무를 선택해 주세요.";
        }
        user.setHasLeave(binding.joinRadioHasLeaveTrue.isSelected());

        if (binding.joinRadioClub.getCheckedRadioButtonId() == -1) {
            scrollAndFocus(binding.joinTxtClub, binding.joinRadioClub);
            return "소속 동아리를 선택해 주세요.";
        }
        user.setClub(User.getClub(this, getStringInSelectedButton(binding.joinRadioClub)));

        // 관심 동아리
        user.clearClubInteresting();
        for(int i = 0; i < binding.joinLlChkbxClub.getChildCount(); i++) {
            CheckBox cb = (CheckBox) binding.joinLlChkbxClub.getChildAt(i);
            if(cb != null && cb.isChecked())
                if(cb.getText() != null)
                    user.addClubInterestingElement(User.getClub(this, cb.getText().toString()));
        }

        return "";
    }

    /** @noinspection deprecation*/
    @Override
    public void onBackPressed() {
        if(binding.joinProgress.getVisibility() == View.GONE) {
            new AlertDialog.Builder(this).setTitle("회원 가입 중단").setMessage("작성하던 내용이 사라집니다.\n회원 가입을 중단하시겠습니까?")
                    .setNegativeButton("계속 작성", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .setPositiveButton("나가기", (dialog, which) -> {
                        super.onBackPressed();
                    })
                    .create()
                    .show();
        }
    }
}

