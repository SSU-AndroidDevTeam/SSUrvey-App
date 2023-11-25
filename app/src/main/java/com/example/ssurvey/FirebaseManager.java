package com.example.ssurvey;

import static com.example.ssurvey.FirebaseConstants.SurveyCollectionName;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ssurvey.model.Survey;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseManager {
    private FirebaseFirestore db;

    FirebaseManager() {
        try {
            onCreate();
        } catch (Exception e) {
            Log.d("FB", "FirebaseManager 생성 과정에서 에러가 발생했습니다.");
        }
    }

    void onCreate() throws Exception {
        db = FirebaseFirestore.getInstance();
    }

    private void loadSurveyDocument(DocumentReference docRef, SurveyCallable callable) {
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        callable.call(document.toObject(Survey.class), false);
                    } else {
                        callable.call(null, false);
                    }
                } else {
                    callable.call(null, true);
                }
            }
        });
        docRef.get();
    }

    public void loadSurveyOfId(SurveyCallable callable, String documentId) {
        DocumentReference docRef = db.collection(SurveyCollectionName).document(documentId);
        loadSurveyDocument(docRef, callable);
    }

    // 설문을 DB에 올린 후 고유 id를 반환한다
    public String addNewSurvey(Survey survey) {
        DocumentReference docRef = db.collection(SurveyCollectionName).document();
        docRef.set(survey);
        return docRef.getId();
    }
}
