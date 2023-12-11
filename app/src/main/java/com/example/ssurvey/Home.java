package com.example.ssurvey;

import static com.example.ssurvey.FirebaseConstants.SurveyCollectionName;

import android.annotation.SuppressLint;
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
import com.example.ssurvey.databinding.ActivityHomeBinding;
import com.example.ssurvey.model.Survey;
import com.example.ssurvey.model.SurveyTarget;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Home extends MainActivity {

    ActivityHomeBinding binding;

    private SurveyListAdapter adapter;
    private ArrayList<SurveyItem> arrayList;
    private FirebaseManager fbManager;

    private Date nowTime;

    /** 설문 리스트 첫 로딩이 끝났는가 */
    private boolean isFirstLoaded = false;

    /** 마지막 로딩 후 설문 상태값 (진행 중 | 예정 | 종료) */
    private SurveyItem.SurveyState lastState;

    /** 설문조사 항목의 날짜에 따른 상태를 반환한다 */
    private SurveyItem.SurveyState getSurveyState(Survey survey) {
        boolean start = nowTime.after(survey.getOpenDate().toDate());
        Date endTime = new Date();
        endTime.setTime(survey.getCloseDate().toDate().getTime() + (24 * 3600 * 1000));
        boolean end = nowTime.before(endTime);

        if(start && end)
            return SurveyItem.SurveyState.IN;
        else if(!end)
            return SurveyItem.SurveyState.COMPLETE;

        return SurveyItem.SurveyState.SOON;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());

        fbManager = new FirebaseManager();
        binding.recyclerviewHome.setHasFixedSize(true);
        binding.recyclerviewHome.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        arrayList = new ArrayList<>();
        adapter = new SurveyListAdapter(arrayList, getApplicationContext(), SurveyListAdapter.FILTER_ALL);
        binding.recyclerviewHome.setAdapter(adapter);

        // 계정 정보 가져오기
        // 계정 정보를 가져오기 전에 설문 리스트를 먼저 가져오는 문제가 있어서, Callback이 된 후 설문을 받아오도록 처리하였음.
        AuthManager.getInstance().initCurrentUser(() -> {
            loadSurveyAndSetAdapter(SurveyItem.SurveyState.IN);
        });

        binding.buttonIngHome.setOnClickListener(v -> {
            loadSurveyAndSetAdapter(SurveyItem.SurveyState.IN);
            v.setBackground(getDrawable(R.drawable.button_home));
            binding.buttonSoonHome.setBackground(getDrawable(R.drawable.button_home_disabled));
            binding.buttonCompleteHome.setBackground(getDrawable(R.drawable.button_home_disabled));
        });
        binding.buttonSoonHome.setOnClickListener(v -> {
            loadSurveyAndSetAdapter(SurveyItem.SurveyState.SOON);
            v.setBackground(getDrawable(R.drawable.button_home));
            binding.buttonIngHome.setBackground(getDrawable(R.drawable.button_home_disabled));
            binding.buttonCompleteHome.setBackground(getDrawable(R.drawable.button_home_disabled));
        });
        binding.buttonCompleteHome.setOnClickListener(v -> {
            loadSurveyAndSetAdapter(SurveyItem.SurveyState.COMPLETE);
            v.setBackground(getDrawable(R.drawable.button_home));
            binding.buttonIngHome.setBackground(getDrawable(R.drawable.button_home_disabled));
            binding.buttonSoonHome.setBackground(getDrawable(R.drawable.button_home_disabled));
        });

        super.navigationBar(binding.getRoot());
        setContentView(binding.getRoot());
    }
    
    /** 설문 리스트 어댑터 갱신 */
    private void loadSurveyAndSetAdapter(SurveyItem.SurveyState state) {
        lastState = state;
        adapter.clear();
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

                                // 상태 필터링
                                if(getSurveyState(survey) != state)
                                    continue;

                                SurveyItem surveyItem = new SurveyItem(survey, dc.getDocument().getId(), state);
                                arrayList.add(surveyItem);
                            }
                            adapter.notifyDataSetChanged();
                        }
                        // 디데이 순 정렬
                        Collections.sort(arrayList);

                        isFirstLoaded = true;
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setNowTime();

        // onCreate 시 호출이 겹치기 때문에 첫 로딩을 한 뒤에만 로딩하도록 처리
        if(isFirstLoaded)
            loadSurveyAndSetAdapter(lastState);

        // 작성 중인 설문 대상 액티비티가 있다면 종료
        Survey_targetSelection.actvityFinish();
    }

    private void setNowTime() {
        nowTime = new Date();
        nowTime.setTime(nowTime.getTime() + (9 * 3600 * 1000));     // 한국 시간대 적용 (GMT+9)
    }
}