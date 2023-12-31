package com.example.ssurvey.FBRecyclerView;

import static com.example.ssurvey.FirebaseConstants.SurveyCollectionName;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.ssurvey.FirebaseManager;
import com.example.ssurvey.R;
import com.example.ssurvey.SurveyItem;
import com.example.ssurvey.databinding.ActivityFbrecyclerExampleBinding;
import com.example.ssurvey.databinding.ActivityFirebaseExampleBinding;
import com.example.ssurvey.model.Survey;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FBRecyclerExampleActivity extends AppCompatActivity {
    /*
    private ActivityFbrecyclerExampleBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<SurveyItem> arrayList;
    private FirebaseManager fbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fbrecycler_example);
        binding = ActivityFbrecyclerExampleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fbManager = new FirebaseManager();

        recyclerView = findViewById(R.id.recyclerView);
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
                                SurveyItem surveyItem = new SurveyItem(dc.getDocument().toObject(Survey.class), dc.getDocument().getId());
                                arrayList.add(surveyItem);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

        recyclerView.setAdapter(adapter);
    }
    */

}