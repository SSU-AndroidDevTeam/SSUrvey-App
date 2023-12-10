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
import com.example.ssurvey.model.SurveyTarget;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Home extends MainActivity {

    private RecyclerView recyclerView;
    private SurveyListAdapter adapter;
    private ArrayList<SurveyItem> arrayList;
    private FirebaseManager fbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fbManager = new FirebaseManager();

        recyclerView = findViewById(R.id.recyclerview_home);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        arrayList = new ArrayList<>();

        adapter = new SurveyListAdapter(arrayList, getApplicationContext(), SurveyListAdapter.FILTER_ALL);
        recyclerView.setAdapter(adapter);

        // 계정 정보 가져오기
        // 계정 정보를 가져오기 전에 설문 리스트를 먼저 가져오는 문제가 있어서, Callback이 된 후 설문을 받아오도록 처리하였음.
        AuthManager.getInstance().initCurrentUser(() -> {
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
                                    Survey survey = dc.getDocument().toObject(Survey.class);

                                    // 타겟 필터링
                                    if(AuthManager.getInstance().isLoggedIn())
                                        if(!survey.getTarget().isTargeted(AuthManager.getInstance().getCurrentUser()))
                                            continue;

                                    SurveyItem surveyItem = new SurveyItem(survey, dc.getDocument().getId());
                                    arrayList.add(surveyItem);
                                }
                                adapter.notifyDataSetChanged();
                            }

                            // 디데이 순 정렬
                            Collections.sort(arrayList);
                        }
                    });
        });

        super.navigationBar(findViewById(R.id.constraintLayout_home));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 작성 중인 설문 대상 액티비티가 있다면 종료
        Survey_targetSelection.actvityFinish();
    }
}