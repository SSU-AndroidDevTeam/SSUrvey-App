package com.example.ssurvey;

import static com.example.ssurvey.FirebaseConstants.SurveyCollectionName;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ssurvey.FBRecyclerView.SurveyListAdapter;
import com.example.ssurvey.model.Survey;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<SurveyItem> arrayList;
    private FirebaseManager fbManager;

    Button homeGoMyInfoBtn; //홈 화면에서 내 정보 화면으로 넘어가는 버튼
    Button homeGoSettingBtn; //홈 화면에서 설정 화면으로 넘어가는 버튼
    Button homeGoSurveyOutlineBtn; //홈 화면에서 설문 개요 화면으로 넘어가는 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 계정 정보 가져오기
        AuthManager.getInstance();

        fbManager = new FirebaseManager();

        recyclerView = findViewById(R.id.recyclerview_home);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        adapter = new SurveyListAdapter(arrayList, getApplicationContext());

        fbManager.getDb().collection(SurveyCollectionName)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.e("Firestore에 접근하는 과정에서 에러가 발생했습니다.",error.getMessage());
                            return;
                        }
                        for(DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                SurveyItem surveyItem = new SurveyItem(dc.getDocument().toObject(Survey.class));
                                arrayList.add(surveyItem);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

        recyclerView.setAdapter(adapter);


        homeGoMyInfoBtn = findViewById(R.id.button_goMyInfo_home);
        homeGoSettingBtn = findViewById(R.id.button_goSetting_home);
        homeGoSurveyOutlineBtn = findViewById(R.id.button_goSurveyOutline_home);

        homeGoMyInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(AuthManager.getInstance().isLoggedIn())
                    intent = new Intent(getApplicationContext(), MyInformation.class);
                else
                    intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        homeGoSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Setting.class);
                startActivity(intent);
            }
        });

        homeGoSurveyOutlineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Survey_outline.class);
                startActivity(intent);
            }
        });
    }

}