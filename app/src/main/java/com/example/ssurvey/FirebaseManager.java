package com.example.ssurvey;

import static com.example.ssurvey.FirebaseConstants.SurveyCollectionName;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ssurvey.model.Survey;
import com.example.ssurvey.model.SurveyResponse;
import com.example.ssurvey.model.SurveyStatistics;
import com.example.ssurvey.service.CbCode;
import com.example.ssurvey.service.SurveyCallback;
import com.example.ssurvey.service.SurveyResponseCallback;
import com.example.ssurvey.service.SurveyStatCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class FirebaseManager {
    private FirebaseFirestore db;

    public FirebaseManager() {
        try {
            onCreate();
        } catch (Exception e) {
            Log.d("FB", "FirebaseManager 생성 과정에서 에러가 발생했습니다.");
        }
    }

    void onCreate() throws Exception {
        db = FirebaseFirestore.getInstance();
    }

    public FirebaseFirestore getDb() {return db;}

    private void loadSurveyDocument(DocumentReference docRef, SurveyCallback callable) {
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        callable.onCallback(document.toObject(Survey.class), CbCode.OK);
                    } else {
                        callable.onCallback(null, CbCode.ERROR);
                    }
                } else {
                    callable.onCallback(null, CbCode.NOT_FOUND);
                }
            }
        });
        docRef.get();
    }

    public void loadSurveyOfId(SurveyCallback callable, String documentId) {
        DocumentReference docRef = db.collection(SurveyCollectionName).document(documentId);
        loadSurveyDocument(docRef, callable);
    }

    private void loadSurveyResponseDocument(DocumentReference docRef, SurveyResponseCallback callable) {
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        callable.onCallback(document.toObject(SurveyResponse.class), CbCode.OK);
                    } else {
                        // NOTE: 사용자가 설문에 참여하지 않았을 때 해당 에러 발생
                        callable.onCallback(null, CbCode.NOT_FOUND);
                    }
                } else {
                    callable.onCallback(null, CbCode.ERROR);
                }
            }
        });
        docRef.get();
    }

    /** 설문을 DB에 올린 후 고유 id를 반환한다 */
    public String addNewSurvey(Survey survey) {
        DocumentReference docRef = db.collection(SurveyCollectionName).document();
        docRef.set(survey);
        return docRef.getId();
    }

    /**
     * 주어진 uniqueId의 설문에 주어진 userId의 유저가 응답한 결과를 반환한다.
     * 만약 응답 기록이 없다면 콜백의 cbCode에 NOT_FOUND를 인자로 전달한다.
     * */
    public void loadSurveyResponse(SurveyResponseCallback callable, String surveyId, String userId) {
        DocumentReference docRef = db.collection(surveyId).document(userId);
        loadSurveyResponseDocument(docRef, callable);
    }

    /**
     * 설문 응답 결과를 DB에 올린다.
     * 해당 설문의 고유 id 하위의 컬렉션 내에 응답자의 userId값을 id로 한 도큐먼트가 저장된다.
     * 이미 응답한 설문 결과를 갱신하기 위해서도 사용할 수 있다
     *  */
    public String addSurveyResponse(SurveyResponse response, String surveyId) {
        DocumentReference docRef = db.collection(surveyId).document(response.getUserId());
        docRef.set(response);
        return docRef.getId();
    }

    /**
     * 해당 설문의 응답 결과 통계를 Read할 수 있는 SurveyStatistics 객체를 생성해 반환한다.
     * 새로 업데이트된 통계를 가져오고 싶다면 SurveyStatistics 객체를 다시 한번 get해야 한다.
     * */
    public void getSurveyStatistics(String surveyUniqueId, SurveyStatCallback callable) {
        CollectionReference colRef = db.collection(surveyUniqueId);
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    SurveyStatistics stat = new SurveyStatistics();
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        stat.updateStatistics(doc.getData()); // 각 응답 결과들마다 통계 갱신
                    }
                    callable.onCallback(stat, CbCode.OK);
                } else {
                    Log.d("FB", "설문 응답 자료들을 파이어스토어에서 가져오는 과정에서 에러가 발생했습니다.");
                    callable.onCallback(null, CbCode.ERROR);
                }
            }
        });
        colRef.get();
    }
}


