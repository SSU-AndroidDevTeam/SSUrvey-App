package com.example.ssurvey;

import static com.example.ssurvey.FirebaseConstants.SurveyCollectionName;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import com.example.ssurvey.FBRecyclerView.SurveyListAdapter;
import com.example.ssurvey.model.Survey;
import com.example.ssurvey.service.CbCode;
import com.example.ssurvey.service.SurveyReplicantCallback;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class ParticipatedSurvey extends MainActivity {

    private RecyclerView recyclerView;
    private SurveyListAdapter adapter;
    private ArrayList<SurveyItem> arrayList;
    private FirebaseManager fbManager;

    boolean isParticipated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participated_survey);

        // XML에 정의된 ConstraintLayout 가져오기
        ConstraintLayout constraintLayout_myInformation = findViewById(R.id.constraintLayout_participated_survey);

        navigationBar(constraintLayout_myInformation);

        fbManager = new FirebaseManager();
        AuthManager authManager = AuthManager.getInstance();
        String currentUserID = authManager.getCurrentId();

        recyclerView = findViewById(R.id.recyclerView_participated_survey);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        arrayList = new ArrayList<>();

        adapter = new SurveyListAdapter(arrayList, getApplicationContext(), SurveyListAdapter.FILTER_PARTICIPATED);
        recyclerView.setAdapter(adapter);

        fbManager.getDb().collection(SurveyCollectionName)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            Log.e("Firestore에 접근하는 과정에서 에러가 발생했습니다.",error.getMessage());
                            return;
                        }
                        for(DocumentChange dc : value.getDocumentChanges()){
                            isParticipated = false;
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                SurveyItem surveyItem = new SurveyItem(dc.getDocument().toObject(Survey.class), dc.getDocument().getId());
                                fbManager.getSurveyReplicants(surveyItem.getSurveyId(), new SurveyReplicantCallback() {
                                    @Override
                                    public void onCallback(ArrayList<String> replicants, CbCode cbCode) {
                                        // 예외 처리
                                        switch (cbCode) {
                                            case OK:
                                                if (replicants.contains(currentUserID)) {
                                                    // 현재 사용자가 참여자 목록에 포함되어 있는 경우
                                                    arrayList.add(surveyItem);
                                                    adapter.notifyDataSetChanged();
                                                    Collections.sort(arrayList);
                                                    Log.d("FB", "현재 사용자가 참여자입니다.");
                                                } else {
                                                    // 현재 사용자가 참여자 목록에 포함되어 있지 않은 경우
                                                    Log.d("FB", "현재 사용자가 참여자가 아닙니다.");
                                                }
                                                break;
                                            case ERROR:
                                                Log.d("FB", "알 수 없는 이유로 파이어스토어에 접근할 수 없습니다.");
                                                break;
                                            case NOT_FOUND:
                                                Log.d("FB", "파이어스토어에서 요청한 데이터를 찾을 수 없습니다.");
                                                break;
                                        }
                                    }
                                });
                            }
                        }

                    }
                });
    }
}