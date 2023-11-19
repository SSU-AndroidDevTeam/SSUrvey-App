package com.example.ssurvey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

    //editText에 입력된 설문 개요들을 서버로 보내주는 함수를 만들어야함

public class Survey_outline extends MainActivity {

    private static final int GALLERY_REQUEST_CODE = 100;
    private TextView startDateTextView;
    private ImageView startCalendarImageView;

    private TextView endDateTextView;
    private ImageView endCalendarImageView;

    private Calendar startDateCalendar;
    private Calendar endDateCalendar;

    ImageButton btnImageSelect; //이미지 선택 버튼
    Button surveyOutlineNextBtn; //설문 개요에서 설문 세부 사항으로 넘어가는 버튼

    private SharedViewModel sharedViewModel; //뷰모델

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_outline);

        // XML에 정의된 ConstraintLayout 가져오기
        ConstraintLayout constraintLayout_survey_outline = findViewById(R.id.constraintLayout_survey_outline);

        // navigationBar 함수 호출
        navigationBar(constraintLayout_survey_outline);

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        //설문 개요 제목 부분 가져오기
        EditText editTextSurveyTitle = findViewById(R.id.editText_survey_title_survey_outline);

        // 저장된 데이터 불러오기
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String savedSurveyTitle = preferences.getString("surveyTitle", "");
        editTextSurveyTitle.setText(savedSurveyTitle);


        //설문 개요에서 설문 세부 사항으로 넘어가는 버튼이벤트
        surveyOutlineNextBtn = findViewById(R.id.button_next_survey_outline);
        surveyOutlineNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 첫 번째 화면에서 입력한 데이터를 뷰모델에 저장
                sharedViewModel.setSurveyTitle(editTextSurveyTitle.getText().toString());

                // 데이터를 SharedPreferences에 저장
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("surveyTitle", editTextSurveyTitle.getText().toString());
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), Survey_questions.class);
                startActivity(intent);
            }
        });

        // 이미지 버튼을 사용하여 이미지 추가하기
        btnImageSelect = findViewById(R.id.imageSelect_btn);
        btnImageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이미지 선택 버튼 클릭 시
                openGallery();
            }
        });

        //달력관련 코드
        startDateTextView = findViewById(R.id.textView_survey_startDate_text);
        startCalendarImageView = findViewById(R.id.imageView_calander_startDate_btn);

        endDateTextView = findViewById(R.id.textView_survey_endDate_text);
        endCalendarImageView = findViewById(R.id.imageView_calander_endDate_btn);

        startDateCalendar = Calendar.getInstance();
        endDateCalendar = Calendar.getInstance();

        // 시작일 달력 이미지뷰 클릭 이벤트 설정
        startCalendarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(true);
            }
        });

        // 종료일 달력 이미지뷰 클릭 이벤트 설정
        endCalendarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(false);
            }
        });
    }



    //갤러리에 접근하여 이미지를 선택하고 이미지 버튼에 표시하는 메소드
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            // 이미지 버튼을 이동시키는 코드 추가
            moveButtonWithImage(selectedImageUri);
        }
    }
    // 픽셀 값을 dp로 변환하는 함수
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    private void moveButtonWithImage(Uri imageUri) {
        ImageView imageView = findViewById(R.id.imageSelect_btn);

        // Glide를 사용하여 이미지를 로드하고 이미지 버튼에 표시
        Glide.with(this)
                .load(imageUri)
                .into(imageView);

        // 이미지뷰의 크기 조절
        int desiredWidthInDp = 50; // 원하는 너비 (dp)
        int desiredHeightInDp = 50; // 원하는 높이 (dp)

        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.width = dpToPx(desiredWidthInDp);
        layoutParams.height = dpToPx(desiredHeightInDp);
        imageView.setLayoutParams(layoutParams);
    }


    // 달력 다이얼로그 보여주는 메소드
    private void showDatePickerDialog(final boolean isStartDate) {
        Calendar calendar = isStartDate ? startDateCalendar : endDateCalendar;

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(Calendar.YEAR, year);
                        selectedDate.set(Calendar.MONTH, month);
                        selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        if (isStartDate) {
                            startDateCalendar = selectedDate;
                            updateDateTextView(startDateTextView, startDateCalendar);
                        } else {
                            endDateCalendar = selectedDate;
                            updateDateTextView(endDateTextView, endDateCalendar);
                        }
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }
    // 텍스트뷰에 날짜 설정하는 메소드
    private void updateDateTextView(TextView textView, Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String selectedDate = dateFormat.format(calendar.getTime());
        textView.setText(selectedDate);
    }
}