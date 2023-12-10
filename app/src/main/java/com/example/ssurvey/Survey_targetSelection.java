package com.example.ssurvey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.example.ssurvey.databinding.ActivitySurveyTargetSelectionBinding;
import com.example.ssurvey.model.SurveyTarget;
import com.example.ssurvey.model.User;

public class Survey_targetSelection extends MainActivity {

    static Survey_targetSelection activity = null;

    public static void actvityFinish() {
        if(activity != null)
            activity.finish();
        activity = null;
    };

    ActivitySurveyTargetSelectionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        binding = ActivitySurveyTargetSelectionBinding.inflate(getLayoutInflater());

        binding.surveyTargetSelectionChkbxStudentCourseBachelor.setOnCheckedChangeListener((cb, isChecked) -> {
            // 학생 과정 중 학사 과정을 선택한 경우 학년 섹션을 보인다.
            if(isChecked)
                binding.surveyTargetSelectionLlGrade.setVisibility(View.VISIBLE);
            // 선택을 푼 경우, 학석사 과정도 체크되어 있지 않으면 학년 섹션을 숨긴다.
            else if(!binding.surveyTargetSelectionChkbxStudentCourseBachelorMaster.isChecked())
                binding.surveyTargetSelectionLlGrade.setVisibility(View.GONE);
        });

        // 학생 과정 중 학석사 과정을 선택한 경우
        binding.surveyTargetSelectionChkbxStudentCourseBachelorMaster.setOnCheckedChangeListener((cb, isChecked) -> {
            // 학생 과정 중 학석사 과정을 선택한 경우 학년 섹션을 보인다.
            if(isChecked)
                binding.surveyTargetSelectionLlGrade.setVisibility(View.VISIBLE);
            // 선택을 푼 경우, 학사 과정도 체크되어 있지 않으면 학년 섹션을 숨긴다.
            else if(!binding.surveyTargetSelectionChkbxStudentCourseBachelor.isChecked())
                binding.surveyTargetSelectionLlGrade.setVisibility(View.GONE);
        });
        
        binding.surveyTargetSelectionChkbxMultiMajorMinor.setOnCheckedChangeListener((cb, isChecked) -> {
            // 다전공 이수 유형 중 부전공을 선택한 경우, 다전공 소속 대학 섹션을 보인다.
            if(isChecked)
                binding.surveyTargetSelectionLlCollegeMultiMajor.setVisibility(View.VISIBLE);
            // 선택을 푼 경우, 복수전공도 체크되어 있지 않으면 다전공 소속 대학 섹션을 숨긴다.
            else if(!binding.surveyTargetSelectionChkbxMultiMajorDoubleMajor.isChecked())
                binding.surveyTargetSelectionLlCollegeMultiMajor.setVisibility(View.GONE);
        });

        binding.surveyTargetSelectionChkbxMultiMajorDoubleMajor.setOnCheckedChangeListener((cb, isChecked) -> {
            // 다전공 이수 유형 중 복수전공을 선택한 경우, 다전공 소속 대학 섹션을 보인다.
            if(isChecked)
                binding.surveyTargetSelectionLlCollegeMultiMajor.setVisibility(View.VISIBLE);
            // 선택을 푼 경우, 부전공도 체크되어 있지 않으면 다전공 소속 대학 섹션을 숨긴다.
            else if(!binding.surveyTargetSelectionChkbxMultiMajorMinor.isChecked())
                binding.surveyTargetSelectionLlCollegeMultiMajor.setVisibility(View.GONE);
        });
        
        binding.surveyTargetSelectionBtnNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                targeting();
                // 다음 단계인 설문조사 경품 설정으로 전환
                Intent intent = new Intent(getApplicationContext(), Survey_giftSetting.class);
                startActivity(intent);
            }
        });
        binding.surveyTargetSelectionBtnBeforeStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 이전 단계인 설문조사 세부 사항으로 전환
                Intent intent = new Intent(getApplicationContext(), Survey_questions.class);
                startActivity(intent);
            }
        });

        super.navigationBar(binding.getRoot());
        setContentView(binding.getRoot());
    }

    /** 체크박스 양쪽을 검사하여 True, False를 반환하거나, 모두 선택 or 모두 미선택인 경우 null을 반환한다 */
    private Boolean getBooleanByBothCheckBox(CheckBox cbTrue, CheckBox cbFalse) {
        if((cbTrue.isChecked() && cbFalse.isChecked()) || (!cbTrue.isChecked() && !cbFalse.isChecked()))
            return null;

        if(cbTrue.isChecked())
            return Boolean.valueOf(true);

        // cbFalse.isChecked()가 참일 경우와 동일
        return Boolean.valueOf(false);
    }

    // 설문 객체에 설문 대상 넣기
    private void targeting() {
        SurveyTarget st = new SurveyTarget();

        // 성별
        for(int i = 0; i < binding.surveyTargetSelectionLlChkbxSex.getChildCount(); i++) {
            CheckBox cb = (CheckBox) binding.surveyTargetSelectionLlChkbxSex.getChildAt(i);
            if(cb.isChecked())
                st.getSexes().add(User.getSex(this, cb.getText().toString()));
        }

        // 학생 유형
        for(int i = 0; i < binding.surveyTargetSelectionLlChkbxStudentType.getChildCount(); i++) {
            CheckBox cb = (CheckBox) binding.surveyTargetSelectionLlChkbxStudentType.getChildAt(i);
            if(cb.isChecked())
                st.getStudentTypes().add(User.getStudentType(this, cb.getText().toString()));
        }

        // 학생 과정
        for(int i = 0; i < binding.surveyTargetSelectionLlChkbxStudentCourse.getChildCount(); i++) {
            CheckBox cb = (CheckBox) binding.surveyTargetSelectionLlChkbxStudentCourse.getChildAt(i);
            if(cb.isChecked())
                st.getStudentCourses().add(User.getStudentCourse(this, cb.getText().toString()));
        }

        // 학생 학년
        if(binding.surveyTargetSelectionLlGrade.getVisibility() == View.VISIBLE)
            for(int i = 0; i < binding.surveyTargetSelectionLlChkbxGrade.getChildCount(); i++) {
                CheckBox cb = (CheckBox) binding.surveyTargetSelectionLlChkbxGrade.getChildAt(i);
                if(cb.isChecked())
                    st.getGrades().add(i + 1);
            }

        // 소속 대학
        for(int i = 0; i < binding.surveyTargetSelectionLlChkbxCollege.getChildCount(); i++) {
            CheckBox cb = (CheckBox) binding.surveyTargetSelectionLlChkbxCollege.getChildAt(i);
            if(cb.isChecked())
                st.getColleges().add(User.getCollege(this, cb.getText().toString()));
        }

        // 다전공 이수 유형
        for(int i = 0; i < binding.surveyTargetSelectionLlChkbxMultiMajor.getChildCount(); i++) {
            CheckBox cb = (CheckBox) binding.surveyTargetSelectionLlChkbxMultiMajor.getChildAt(i);
            if(cb.isChecked())
                st.getMultiMajors().add(User.getMultiMajor(this, cb.getText().toString()));
        }

        // 다전공 소속 대학
        if(binding.surveyTargetSelectionLlCollegeMultiMajor.getVisibility() == View.VISIBLE)
            for(int i = 0; i < binding.surveyTargetSelectionLlChkbxCollegeMultiMajor.getChildCount(); i++) {
                CheckBox cb = (CheckBox) binding.surveyTargetSelectionLlChkbxCollegeMultiMajor.getChildAt(i);
                if(cb.isChecked())
                    st.getMultiMajorColleges().add(User.getCollege(this, cb.getText().toString()));
            }

        // 전과 유무
        st.setHasChangeMajor(getBooleanByBothCheckBox(binding.surveyTargetSelectionChkbxHasChangeMajorTrue,
                binding.surveyTargetSelectionChkbxHasChangeMajorFalse));

        // 교직 이수 유무
        st.setHasTeaching(getBooleanByBothCheckBox(binding.surveyTargetSelectionChkbxHasTeachingTrue,
                binding.surveyTargetSelectionChkbxHasTeachingFalse));

        // 유급 기록 유무
        st.setHasFlunk(getBooleanByBothCheckBox(binding.surveyTargetSelectionChkbxHasFlunkTrue,
                binding.surveyTargetSelectionChkbxHasFlunkTrue));

        // 학사 경고 기록 유무
        st.setHasWarning(getBooleanByBothCheckBox(binding.surveyTargetSelectionChkbxHasWarningTrue,
                binding.surveyTargetSelectionChkbxHasWarningFalse));

        // 휴학 기록 유무
        st.setHasLeave(getBooleanByBothCheckBox(binding.surveyTargetSelectionChkbxHasLeaveTrue,
                binding.surveyTargetSelectionChkbxHasLeaveFalse));

        // 소속 동아리
        for(int i = 0; i < binding.surveyTargetSelectionLlChkbxClub.getChildCount(); i++) {
            CheckBox cb = (CheckBox) binding.surveyTargetSelectionLlChkbxClub.getChildAt(i);
            if(cb.isChecked())
                st.getClubs().add(User.getClub(this, cb.getText().toString()));
        }

        // 관심 동아리
        for(int i = 0; i < binding.surveyTargetSelectionLlChkbxClubIntrst.getChildCount(); i++) {
            CheckBox cb = (CheckBox) binding.surveyTargetSelectionLlChkbxClubIntrst.getChildAt(i);
            if(cb.isChecked())
                st.getClubInterestings().add(User.getClub(this, cb.getText().toString()));
        }

        MakeSurvey.makeSurvey.setTarget(st);
    }
}