package com.example.ssurvey;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Date;

import com.example.ssurvey.databinding.ActivityEditProfileBinding;
import com.example.ssurvey.model.User;
import com.example.ssurvey.service.UserService;

public class EditProfileActivity extends MainActivity {

    final String TAG = "EditProfileActivity";

    ActivityEditProfileBinding binding;

    User user;

    // password는 User 클래스에서 관리하지 않아 별도로 생성
    String password;

    /** @noinspection deprecation*/
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        super.hideKeyboard(binding.editProfileScrollView);
        
        user = AuthManager.getInstance().getCurrentUser();

        /* 현재 유저의 정보로 입력란 세팅 */
        binding.editProfileEtId.setText(user.getId());
        binding.editProfileEtName.setText(user.getName());
        checkRadioByString(binding.editProfileRadioSex, User.getStringMatchedSex(this, user.getSex()));
        Date birth = user.getBirth();
        binding.editProfileTxtInputBirth.
                setText(birth.getYear() + 1900 +
                        (birth.getMonth() >= 9 ? "-" : "-0") + (birth.getMonth() + 1) +
                        (birth.getDate() >= 10 ? "-" : "-0") + birth.getDate());
        binding.editProfileEtEmail.setText(user.getEmail());
        binding.editProfileEtPhone.setText(user.getPhone());
        checkRadioByString(binding.editProfileRadioStudentType, User.getStringMatchedStudentType(this, user.getStudentType()));
        if(user.getGrade() != 0) {
            binding.editProfileEtGrade.setText(String.valueOf(user.getGrade()));
            binding.editProfileLlGrade.setVisibility(View.VISIBLE);
        }
        checkRadioByString(binding.editProfileRadioStudentCourse, User.getStringMatchedStudentCourse(this, user.getStudentCourse()));
        checkRadioByString(binding.editProfileRadioCollege, User.getStringMatchedCollege(this, user.getCollege()));
        checkRadioByString(binding.editProfileRadioMultiMajor, User.getStringMatchedMultiMajor(this, user.getMultiMajor()));
        if(user.getMultiMajor() != User.MultiMajor.NONE) {
            checkRadioByString(binding.editProfileRadioCollegeMultiMajor, User.getStringMatchedCollege(this, user.getCollegeMultiMajor()));
            binding.editProfileLlCollegeMultiMajor.setVisibility(View.VISIBLE);
        }
        if(user.isHasChangeMajor()) binding.editProfileRadioHasChangeMajorTrue.setChecked(true);
        else binding.editProfileRadioHasChangeMajorFalse.setChecked(true);
        if(user.isHasTeaching()) binding.editProfileRadioHasTeachingTrue.setChecked(true);
        else binding.editProfileRadioHasTeachingFalse.setChecked(true);
        if(user.isHasFlunk()) binding.editProfileRadioHasFlunkTrue.setChecked(true);
        else binding.editProfileRadioHasFlunkFalse.setChecked(true);
        if(user.isHasWarning()) binding.editProfileRadioHasWarningTrue.setChecked(true);
        else binding.editProfileRadioHasWarningFalse.setChecked(true);
        if(user.isHasLeave()) binding.editProfileRadioHasLeaveTrue.setChecked(true);
        else binding.editProfileRadioHasLeaveFalse.setChecked(true);
        checkRadioByString(binding.editProfileRadioClub, User.getStringMatchedClub(this, user.getClub()));
        for (User.Club club : user.getClubInteresting()) {
            for(int i = 0; i < binding.editProfileLlChkbxClub.getChildCount(); i++) {
                CheckBox cb = (CheckBox) binding.editProfileLlChkbxClub.getChildAt(i);
                if(cb != null)
                    if(User.getStringMatchedClub(this, club).equals(cb.getText().toString()))
                        cb.setChecked(true);
            }
        }

        /* 비밀번호 수정 버튼 처리 */
        binding.editProfileBtnPwChange.setOnClickListener(v -> {
            if(binding.editProfileLlPw.getVisibility() == View.GONE) {
                binding.editProfileLlPw.setVisibility(View.VISIBLE);
                binding.editProfileBtnPwChange.setText("비밀번호 수정 취소");
            } else {
                binding.editProfileLlPw.setVisibility(View.GONE);
                binding.editProfileBtnPwChange.setText("비밀번호 수정");
            }
        });

        /* 휴대폰 번호 입력란 포커스 전후 처리 */
        binding.editProfileEtPhone.setOnFocusChangeListener((v, hasFocus) -> {
            String phone = binding.editProfileEtPhone.getText().toString();
            if (hasFocus) {
                if(!isEmptyString(phone)) {
                    // 최대 글자수 11자로 제한
                    InputFilter[] filters = new InputFilter[1];
                    filters[0] = new InputFilter.LengthFilter(11);
                    binding.editProfileEtPhone.setFilters(filters);
                    binding.editProfileEtPhone.setText(phone.replaceAll("[^0-9]", ""));
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
                        binding.editProfileEtPhone.setFilters(filters);
                        binding.editProfileEtPhone.setText(fommattedPhone);
                    }
                }
            }
        });

        /* 이름 입력란 포커스 아웃 처리 */
        binding.editProfileEtName.setOnFocusChangeListener((v, hasFocus) -> {
            String name = binding.editProfileEtName.getText().toString();
            if (!hasFocus)
                if(!isEmptyString(name))
                    // 입력란에 Enter가 들어가는 경우가 있어서 한글을 제외한 모든 글자 삭제 처리
                    binding.editProfileEtName.setText(name.replaceAll("[^가-힣]", ""));
        });

        // 여기서 설정하는 user의 값은 단순히 Visible 제어용이지, DB에 저장되는 것과 관련 없음.
        binding.editProfileRadioStudentType.setOnCheckedChangeListener((group, button) -> {
            // 졸업생인 경우 학년 필드를 숨김 처리한다.
            if(button == binding.editProfileRadioStudentTypeGraduate.getId()) {
                user.setStudentType(User.StudentType.GRADUATE);
                binding.editProfileLlGrade.setVisibility(View.GONE);
            }
            else {
                user.setStudentType(User.StudentType.IN);
                if(user.getStudentCourse() == User.StudentCourse.BACHELOR)
                    binding.editProfileLlGrade.setVisibility(View.VISIBLE);
            }
        });

        // 여기서 설정하는 user의 값은 단순히 Visible 제어용이지, DB에 저장되는 것과 관련 없음.
        binding.editProfileRadioStudentCourse.setOnCheckedChangeListener((group, button) -> {
            if(button == binding.editProfileRadioStudentCourseBachelor.getId() || button == binding.editProfileRadioStudentCourseBachelorMaster.getId()) {
                user.setStudentCourse(User.StudentCourse.BACHELOR);
                if(user.getStudentType() == User.StudentType.IN)
                    binding.editProfileLlGrade.setVisibility(View.VISIBLE);
            }
            else {
                // 학사, 학석사 과정이 아닌 경우 학년 필드를 숨김 처리한다.
                binding.editProfileLlGrade.setVisibility(View.GONE);
                user.setStudentCourse(User.StudentCourse.MASTER);
            }
        });

        binding.editProfileRadioMultiMajor.setOnCheckedChangeListener((group, button) -> {
            if(button == binding.editProfileRadioMultiMajorNone.getId())
                // 다전공 이수 유형이 해당 없음인 경우 다전공 소속 대학 필드를 숨김 처리한다.
                binding.editProfileLlCollegeMultiMajor.setVisibility(View.GONE);
            else
                binding.editProfileLlCollegeMultiMajor.setVisibility(View.VISIBLE);
        });

        binding.editProfileBtnSubmit.setOnClickListener(v -> {
            String resultMsg = setUserAndResultMsg();

            if(resultMsg.length() > 0)
                Toast.makeText(this, setUserAndResultMsg(), Toast.LENGTH_SHORT).show();
            // 모든 검사를 통과
            else {
                Log.d(TAG, "유저 업데이트 시작");
                // 파이어베이스 인증에 비밀번호 수정 반영
                if(binding.editProfileLlPw.getVisibility() == View.VISIBLE)
                    AuthManager.getInstance().updatePassword(password);

                // 파이어베이스에 유저 정보 업데이트
                UserService.getInstance().setUser(user);
                Toast.makeText(this, "정보 수정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                
                // 현재 유저 정보 갱신
                AuthManager.getInstance().setCurrentUser(user);
                finish();

                Log.d(TAG, "유저 업데이트 종료");
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
        binding.editProfileScrollView.post(() -> {
            binding.editProfileScrollView.smoothScrollTo(0, v4scroll.getTop());
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

    /** 같은 문자열이 있는 경우 해당 라디오버튼을 선택 */
    private void checkRadioByString(RadioGroup rg, String strInRb) {
        if(rg != null) {
            for(int i = 0; i < rg.getChildCount(); i++) {
                RadioButton rd = (RadioButton) rg.getChildAt(i);
                if(rd != null) {
                    if(strInRb.equals(rd.getText().toString()))
                        rd.setChecked(true);
                }
            }
        }
    }

    /** 입력값 예외 처리 및 User 객체에 삽입. ""을 반환하면 입력값에 문제가 없다 */
    public String setUserAndResultMsg() {

        user.setId(binding.editProfileEtId.getText().toString());
        if(isEmptyString(user.getId())) {
            scrollAndFocus(binding.editProfileTxtId, binding.editProfileEtId);
            return "학번을 입력해 주세요.";
        }

        if(user.getId().length() != 8) {
            scrollAndFocus(binding.editProfileTxtId, binding.editProfileEtId);
            return "학번은 여덟 자리여야 합니다.";
        }

        if(binding.editProfileLlPw.getVisibility() == View.VISIBLE) {
            password = binding.editProfileEtPw.getText().toString();
            String pwChk = binding.editProfileEtPwChk.getText().toString();

            if (isEmptyString(password)) {
                scrollAndFocus(binding.editProfileTxtPw, binding.editProfileEtPw);
                return "비밀번호를 입력해 주세요.";
            }

            if (password.length() < 6) {
                scrollAndFocus(binding.editProfileTxtPw, binding.editProfileEtPw);
                return "비밀번호는 최소 6자 이상이어야 합니다.";
            }

            if (isEmptyString(pwChk)) {
                scrollAndFocus(binding.editProfileTxtPwChk, binding.editProfileEtPwChk);
                return "비밀번호 확인을 입력해 주세요.";
            }

            if (pwChk.length() < 6) {
                scrollAndFocus(binding.editProfileTxtPwChk, binding.editProfileEtPwChk);
                return "비밀번호는 최소 6자 이상이어야 합니다.";
            }

            if (!password.equals(pwChk)) {
                scrollAndFocus(binding.editProfileTxtPw, binding.editProfileEtPw);
                return "비밀번호와 비밀번호 확인이 동일하지 않습니다.";
            }
        }

        user.setName(binding.editProfileEtName.getText().toString());
        if(isEmptyString(user.getName())) {
            scrollAndFocus(binding.editProfileTxtName, binding.editProfileEtName);
            return "이름을 입력해 주세요.";
        }

        if(user.getName().length() < 2) {
            scrollAndFocus(binding.editProfileTxtName, binding.editProfileEtName);
            return "이름은 성을 포함해서 최소 2자 이상이어야 합니다.";
        }

        if(!user.getName().matches("[가-힣]+")) {
            scrollAndFocus(binding.editProfileTxtName, binding.editProfileEtName);
            return "이름은 한글로만 적어주세요.";
        }

        if(binding.editProfileRadioSex.getCheckedRadioButtonId() == -1) {
            scrollAndFocus(binding.editProfileTxtSex, binding.editProfileRadioSex);
            return "성별을 선택해 주세요.";
        }
        user.setSex(binding.editProfileRadioSexMale.isChecked() ? User.Sex.MALE : User.Sex.FEMALE);

        if(user.getBirth() == null) {
            scrollAndFocus(binding.editProfileTxtBirth, binding.editProfileBtnBirth);
            return "생년월일을 선택해주세요.";
        }

        user.setEmail(binding.editProfileEtEmail.getText().toString());
        if(isEmptyString(user.getEmail())) {
            scrollAndFocus(binding.editProfileTxtEmail, binding.editProfileEtEmail);
            return "이메일을 입력해 주세요.";
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches()) {
            scrollAndFocus(binding.editProfileTxtEmail, binding.editProfileEtEmail);
            return "올바른 이메일 형식이 아닙니다.";
        }

        user.setPhone(binding.editProfileEtPhone.getText().toString());
        if(isEmptyString(user.getPhone())) {
            scrollAndFocus(binding.editProfileTxtPhone, binding.editProfileEtPhone);
            return "휴대폰 번호를 입력해 주세요.";
        }

        if(user.getPhone().length() < 11) {
            scrollAndFocus(binding.editProfileTxtPhone, binding.editProfileEtPhone);
            return "휴대폰 번호를 온전하게 입력해 주세요.";
        }

        if(!user.getPhone().replaceAll("[^0-9]", "").matches("^010[0-9]+")) {
            scrollAndFocus(binding.editProfileTxtPhone, binding.editProfileEtPhone);
            return "올바른 휴대폰 번호가 아닙니다.";
        }

        if(binding.editProfileRadioStudentType.getCheckedRadioButtonId() == -1) {
            scrollAndFocus(binding.editProfileTxtStudentType, binding.editProfileRadioStudentType);
            return "학생 유형을 선택해 주세요.";
        }
        user.setStudentType(User.getStudentType(this, getStringInSelectedButton(binding.editProfileRadioStudentType)));

        if(binding.editProfileRadioStudentCourse.getCheckedRadioButtonId() == -1) {
            scrollAndFocus(binding.editProfileTxtStudentCourse, binding.editProfileRadioStudentCourse);
            return "학생 과정을 선택해 주세요.";
        }
        user.setStudentCourse(User.getStudentCourse(this, getStringInSelectedButton(binding.editProfileRadioStudentCourse)));

        if(binding.editProfileLlGrade.getVisibility() == View.VISIBLE) {
            String strGrade = binding.editProfileEtGrade.getText().toString();
            if(isEmptyString(strGrade)) {
                scrollAndFocus(binding.editProfileLlGrade, binding.editProfileEtGrade);
                return "학년을 입력해 주세요.";
            }
            int grade = Integer.parseInt(strGrade);
            if(User.StudentCourse.BACHELOR_MASTER.equals(user.getStudentCourse())) {
                if(grade < 3 || grade > 5) {
                    scrollAndFocus(binding.editProfileLlGrade, binding.editProfileEtGrade);
                    return "학석사 통합 과정생의 학년은 3부터 5사이의 숫자만 입력 가능합니다.";
                }
            } else if(grade < 1 || grade > 5) {
                scrollAndFocus(binding.editProfileLlGrade, binding.editProfileEtGrade);
                return "학년은 1부터 5사이의 숫자만 입력 가능합니다.";
            }
            user.setGrade(grade);
        } else
            user.setGrade(0);

        if(binding.editProfileRadioCollege.getCheckedRadioButtonId() == -1) {
            scrollAndFocus(binding.editProfileTxtCollege, binding.editProfileRadioCollege);
            return "소속 대학을 선택해 주세요.";
        }
        user.setCollege(User.getCollege(this, getStringInSelectedButton(binding.editProfileRadioCollege)));

        if(binding.editProfileRadioMultiMajor.getCheckedRadioButtonId() == -1) {
            scrollAndFocus(binding.editProfileTxtMultiMajor, binding.editProfileRadioMultiMajor);
            return "다전공 이수 유형을 선택해 주세요.";
        }
        user.setMultiMajor(User.getMultiMajor(this, getStringInSelectedButton(binding.editProfileRadioMultiMajor)));

        if(binding.editProfileLlCollegeMultiMajor.getVisibility() == View.VISIBLE) {
            if (binding.editProfileRadioCollegeMultiMajor.getCheckedRadioButtonId() == -1) {
                scrollAndFocus(binding.editProfileLlCollegeMultiMajor, binding.editProfileRadioCollegeMultiMajor);
                return "다전공 소속 대학을 선택해 주세요.";
            }
            user.setCollegeMultiMajor(User.getCollege(this, getStringInSelectedButton(binding.editProfileRadioCollegeMultiMajor)));
        }

        if(binding.editProfileRadioHasChangeMajor.getCheckedRadioButtonId() == -1) {
            scrollAndFocus(binding.editProfileTxtHasChangeMajor, binding.editProfileRadioHasChangeMajor);
            return "전과 (학과 이동) 유무를 선택해 주세요.";
        }
        user.setHasChangeMajor(binding.editProfileRadioHasChangeMajorTrue.isChecked());

        if(binding.editProfileRadioHasTeaching.getCheckedRadioButtonId() == -1) {
            scrollAndFocus(binding.editProfileTxtHasTeaching, binding.editProfileRadioHasTeaching);
            return "교직 이수 유무를 선택해 주세요.";
        }
        user.setHasTeaching(binding.editProfileRadioHasTeachingTrue.isChecked());

        if(binding.editProfileRadioHasFlunk.getCheckedRadioButtonId() == -1) {
            scrollAndFocus(binding.editProfileTxtHasFlunk, binding.editProfileRadioHasFlunk);
            return "유급 기록 유무를 선택해 주세요.";
        }
        user.setHasFlunk(binding.editProfileRadioHasFlunkTrue.isChecked());

        if(binding.editProfileRadioHasWarning.getCheckedRadioButtonId() == -1) {
            scrollAndFocus(binding.editProfileTxtHasWarning, binding.editProfileRadioHasWarning);
            return "학사 경고 기록 유무를 선택해 주세요.";
        }
        user.setHasWarning(binding.editProfileRadioHasWarningTrue.isChecked());

        if(binding.editProfileRadioHasLeave.getCheckedRadioButtonId() == -1) {
            scrollAndFocus(binding.editProfileTxtHasLeave, binding.editProfileRadioHasLeave);
            return "휴학 기록 유무를 선택해 주세요.";
        }
        user.setHasLeave(binding.editProfileRadioHasLeaveTrue.isChecked());

        if (binding.editProfileRadioClub.getCheckedRadioButtonId() == -1) {
            scrollAndFocus(binding.editProfileTxtClub, binding.editProfileRadioClub);
            return "소속 동아리를 선택해 주세요.";
        }
        user.setClub(User.getClub(this, getStringInSelectedButton(binding.editProfileRadioClub)));

        // 관심 동아리
        user.clearClubInteresting();
        for(int i = 0; i < binding.editProfileLlChkbxClub.getChildCount(); i++) {
            CheckBox cb = (CheckBox) binding.editProfileLlChkbxClub.getChildAt(i);
            if(cb != null && cb.isChecked())
                if(cb.getText() != null)
                    user.addClubInterestingElement(User.getClub(this, cb.getText().toString()));
        }

        return "";
    }

    /** @noinspection deprecation*/
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("정보 수정 중단")
                .setMessage("정보 수정을 중단하시겠습니까?")
                .setNegativeButton("취소", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("나가기", (dialog, which) -> super.onBackPressed())
                .create()
                .show();
    }
}