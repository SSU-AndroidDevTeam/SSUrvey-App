package com.example.ssurvey;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

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
        Button myInfoBtn = createButton(R.drawable.button_infoimage);
        Button homeBtn = createButton(R.drawable.button_homeimage);
        Button settingBtn = createButton(R.drawable.button_settingimage);
        linearLayout.addView(myInfoBtn);
        linearLayout.addView(homeBtn);
        linearLayout.addView(settingBtn);

        // Constraint 설정
        setupConstraints(constraintLayout, linearLayout);

        // 각 버튼에 대한 클릭 이벤트 처리
        myInfoBtn.setOnClickListener(new MyButtonClickListener(MyInformation.class));
        homeBtn.setOnClickListener(new MyButtonClickListener(Home.class));
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

    private Button createButton(int backgroundResource) {
        Button button = new Button(this);
        // layout_width를 0dp로 설정하고 layout_weight를 부여
        button.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));  // 1.0f는 weight입니다.

        // 배경 이미지 설정
        button.setBackgroundResource(backgroundResource);

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
}