package com.example.ssurvey;



import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ssurvey.model.Survey;

public class Survey_giftSetting extends MainActivity {

    private FirebaseManager fbManager;

    private static final int GALLERY_REQUEST_CODE1 = 100;
    private static final int GALLERY_REQUEST_CODE2 = 200;

    Button surveyGiftSettingRegisterBtn; //설문 선물 설정에서 홈 화면으로 넘어가는 버튼
    Button surveyGiftSettingBeforeBtn; //설문 선물 설정에서 설문 대상 선택으로 돌아가는 버튼

    ImageButton SelectGiftMainBtn; //메인 선물 선택 버튼
    ImageButton SelectGiftWinnerBtn; //당첨자용 선물 선택 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_gift_setting);

        fbManager = new FirebaseManager();

        // XML에 정의된 ConstraintLayout 가져오기
        ConstraintLayout constraintLayout_myInformation = findViewById(R.id.constraintLayout_survey_gift_setting);

        navigationBar(constraintLayout_myInformation);

        surveyGiftSettingRegisterBtn = findViewById(R.id.button_register_survey_giftSetting);
        surveyGiftSettingBeforeBtn = findViewById(R.id.button_before_survey_giftSetting);

        surveyGiftSettingRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Firebase Firestore에 설문 등록
                String uniqueId = fbManager.addNewSurvey(MakeSurvey.makeSurvey);

                // 설문이 성공적으로 등록되면 MakeSurvey 클래스의 makeSurvey 초기화
                if (uniqueId != null && !uniqueId.isEmpty()) {
                    MakeSurvey.resetSurvey();

                    // 등록 성공 시, 홈 화면으로 이동
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);
                } else {
                    // 등록 실패 시, 사용자에게 알림 (예: 토스트 메시지)
                    Toast.makeText(getApplicationContext(), "설문 등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        surveyGiftSettingBeforeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Survey_targetSelection.class); //설문 대상 선택으로 돌아가는 버튼이벤트
                // 선택한 항목을 유지하기 위해 기존 액티비티 재활용
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        SelectGiftMainBtn = findViewById(R.id.imageButton_giftMain_survey_giftSetting);
        SelectGiftMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이미지 선택 버튼 클릭 시
                openGallery(100);
            }
        });

        SelectGiftWinnerBtn = findViewById(R.id.imageButton_giftWinner_survey_giftSetting);
        SelectGiftWinnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이미지 선택 버튼 클릭 시
                openGallery(200);
            }
        });
    }

    // 갤러리에 접근하여 이미지를 선택하고 이미지 버튼에 표시하는 메소드
    private void openGallery(int requestCode) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            // 이미지 버튼을 이동시키는 코드 추가
            if (requestCode == GALLERY_REQUEST_CODE1) {
                moveButtonWithImage(selectedImageUri, R.id.imageButton_giftMain_survey_giftSetting);
            } else if (requestCode == GALLERY_REQUEST_CODE2) {
                moveButtonWithImage(selectedImageUri, R.id.imageButton_giftWinner_survey_giftSetting);
            }
        }
    }

    private void moveButtonWithImage(Uri imageUri, int imageViewId) {
        ImageView imageView = findViewById(imageViewId);

        // Glide를 사용하여 이미지를 로드하고 이미지 버튼에 표시
        Glide.with(this)
                .load(imageUri)
                .into(imageView);
    }

}


