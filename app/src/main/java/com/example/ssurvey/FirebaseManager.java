package com.example.ssurvey;

import static com.example.ssurvey.FirebaseConstants.SurveyCollectionName;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ssurvey.model.Survey;
import com.example.ssurvey.service.CbCode;
import com.example.ssurvey.service.SurveyCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

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

    /** 설문을 DB에 올린 후 고유 id를 반환한다 */
    public String addNewSurvey(Survey survey) {
        DocumentReference docRef = db.collection(SurveyCollectionName).document();
        docRef.set(survey);
        return docRef.getId();
    }
}
