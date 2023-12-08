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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class InvitedSurvey extends MainActivity {

    private RecyclerView recyclerView;
    private SurveyListAdapter adapter;
    private ArrayList<SurveyItem> arrayList;
    private FirebaseManager fbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invited_survey);

        // XML에 정의된 ConstraintLayout 가져오기
        ConstraintLayout constraintLayout_myInformation = findViewById(R.id.constraintLayout_invited_survey);

        navigationBar(constraintLayout_myInformation);

        fbManager = new FirebaseManager();

        recyclerView = findViewById(R.id.recyclerView_invited_survey);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        arrayList = new ArrayList<>();

        adapter = new SurveyListAdapter(arrayList, getApplicationContext(), SurveyListAdapter.FILTER_INVITED);
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
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                SurveyItem surveyItem = new SurveyItem(dc.getDocument().toObject(Survey.class), dc.getDocument().getId());
                                arrayList.add(surveyItem);
                            }
                            adapter.notifyDataSetChanged();
                        }

                        // 디데이 순 정렬
                        Collections.sort(arrayList);
                    }
                });
    }
}