package com.example.ssurvey;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // XML에 정의된 ConstraintLayout 가져오기
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);

        // navigationBar 함수 호출
        navigationBar(constraintLayout);
    }

    protected void navigationBar(ConstraintLayout constraintLayout) {
        // LinearLayout 생성 및 추가
        LinearLayout linearLayout = createLinearLayout();
        constraintLayout.addView(linearLayout);

        // 버튼 생성 및 추가
        ImageButton myInfoBtn = createButton(R.drawable.button_infoimage);
        ImageButton mainBtn;

        // 현재 액티비티가 Home이면
        if(SSUrvey.getCurrentActivityClass() == Home.class) {
            // 가운데 메인 버튼을 설문 생성 버튼으로 한다
            mainBtn = createButton(R.drawable.button_surveyplusimage);
            mainBtn.setOnClickListener(new MyButtonClickListener(Survey_outline.class));
        }
        // 현재 액티비티가 Home이 아니라면
        else {
            // 가운데 메인 버튼을 홈 버튼으로 한다
            mainBtn = createButton(R.drawable.button_homeimage);
            mainBtn.setOnClickListener(new MyButtonClickListener(Home.class));
        }

        ImageButton settingBtn = createButton(R.drawable.button_settingimage);
        linearLayout.addView(myInfoBtn);
        linearLayout.addView(mainBtn);
        linearLayout.addView(settingBtn);

        // Constraint 설정
        setupConstraints(constraintLayout, linearLayout);

        // 각 버튼에 대한 클릭 이벤트 처리
        if(AuthManager.getInstance().isLoggedIn()) {
            // 같은 액티비티가 띄워진 경우 중복 띄우기 방지
            if(SSUrvey.getCurrentActivityClass() != MyInformation.class)
                myInfoBtn.setOnClickListener(new MyButtonClickListener(MyInformation.class));
        }
        else
            if(SSUrvey.getCurrentActivityClass() != LoginActivity.class)
                myInfoBtn.setOnClickListener(new MyButtonClickListener(LoginActivity.class));

        if(SSUrvey.getCurrentActivityClass() != Setting.class)
            settingBtn.setOnClickListener(new MyButtonClickListener(Setting.class));
    }

    private LinearLayout createLinearLayout() {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setId(View.generateViewId()); // ID 설정

        // dp를 px로 변환
        int heightInDp = 60;
        float density = getResources().getDisplayMetrics().density;
        int heightInPx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, heightInDp, getResources().getDisplayMetrics());

        // 높이 설정
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, heightInPx));

        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        return linearLayout;
    }

    private ImageButton createButton(int backgroundResource) {
        ImageButton button = new ImageButton(this);
        // layout_width를 0dp로 설정하고 layout_weight를 부여
        button.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));  // 1.0f는 weight입니다.

        // 배경 이미지 설정
        button.setImageResource(backgroundResource);
        button.setBackgroundColor(getColor(R.color.white));
        button.setScaleType(ImageView.ScaleType.FIT_CENTER);

        return button;
    }

    private void setupConstraints(ConstraintLayout constraintLayout, View view) {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);

        int viewId = view.getId();

        // 뷰에 제약 조건 설정
        constraintSet.connect(viewId, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
        constraintSet.connect(viewId, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
        constraintSet.connect(viewId, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);

        // 적용된 제약 조건을 ConstraintLayout에 적용
        constraintSet.applyTo(constraintLayout);
    }

    // 클릭 이벤트 처리를 외부로 분리한 클래스
    private class MyButtonClickListener implements View.OnClickListener {
        private Class<?> destinationActivityClass;

        public MyButtonClickListener(Class<?> destinationActivityClass) {
            this.destinationActivityClass = destinationActivityClass;
        }

        @Override
        public void onClick(View v) {
            // 버튼에 따른 처리
            Intent intent = new Intent(MainActivity.this, destinationActivityClass);
            startActivity(intent);
        }
    }

    /** 입력란 외 부분 터치 시 키보드 숨기기 */
    @SuppressLint("ClickableViewAccessibility")
    public void hideKeyboard(ConstraintLayout layout) {
        layout.setOnTouchListener((v, event) -> {
            if(!(v instanceof EditText)) {
                // 포커스 해제
                if(getCurrentFocus() != null) getCurrentFocus().clearFocus();
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
            return false;
        });
    }

    /** 입력란 외 부분 터치 시 키보드 숨기기 */
    @SuppressLint("ClickableViewAccessibility")
    public void hideKeyboard(LinearLayout layout) {
        layout.setOnTouchListener((v, event) -> {
            if(!(v instanceof EditText)) {
                // 포커스 해제
                if(getCurrentFocus() != null) getCurrentFocus().clearFocus();
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
            return false;
        });
    }

    /** 입력란 외 부분 터치 시 키보드 숨기기 */
    @SuppressLint("ClickableViewAccessibility")
    public void hideKeyboard(ScrollView layout) {
        layout.setOnTouchListener((v, event) -> {
            if(!(v instanceof EditText)) {
                // 포커스 해제
                if(getCurrentFocus() != null) getCurrentFocus().clearFocus();
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
            return false;
        });
    }

    /** 입력하지 않은 입력 개체에 대한 스크롤 & 포커싱에 사용
     * @Params
     * v4focus : 포커스 대상 View
     * */
    protected void focusView (View v4focus) {
        v4focus.requestFocus();

        // 포커스 대상 View가 EditText라면
        if(v4focus instanceof EditText) {
            // 인풋 커서를 입력한 텍스트에서 가장 마지막에 두고,
            ((EditText) v4focus).setSelection(((EditText) v4focus).getText().length());
            // 키보드를 띄운다.
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).showSoftInput(v4focus, InputMethodManager.SHOW_IMPLICIT);
        }
    }
}