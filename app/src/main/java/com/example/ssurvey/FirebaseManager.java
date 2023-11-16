package com.example.ssurvey;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ExecutionError;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

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

    private void loadDocument(DocumentReference docRef, FirebaseCallable callable) {
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        callable.call(document.getData(), false);
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

    public void loadDataOfPath(FirebaseCallable callable, String collectionPath, String documentPath) {
        DocumentReference docRef = db.collection(collectionPath).document(documentPath);
        loadDocument(docRef, callable);
    }
}
