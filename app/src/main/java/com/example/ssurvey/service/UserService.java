package com.example.ssurvey.service;

import android.util.Log;
import com.example.ssurvey.model.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

/** User 컬렉션 접근 클래스 (Singleton) */
public class UserService {

    private static UserService instance = null;

    private final FirebaseFirestore db;

    private final String COLLECTION = "User";

    private final String TAG = "UserService";

    private UserService() {
        db = FirebaseFirestore.getInstance();
    }

    public static UserService getInstance() {
        if (instance == null)
            instance = new UserService();
        return instance;
    }

    /**
     * 유저 삽입
     */
    public void setUser(Map<String, Object> user) {
        Log.d(TAG, "setUser(Map<String, Object> user)");

        CollectionReference colRef = db.collection(COLLECTION);
        colRef.document(user.get("uid").toString()).set(user);

        Log.d(TAG, "~setUser(Map<String, Object> user)");
    }

    /**
     * 유저 삽입
     */
    public void setUser(User user) {
        Log.d(TAG, "setUser(User user)");

        CollectionReference colRef = db.collection(COLLECTION);
        colRef.document(user.getUid()).set(user);

        Log.d(TAG, "~setUser(User user)");
    }

    /**
     * 유저 가져오기
     */
    public void getUser(String uid, UserCallback callback) {
        Log.d(TAG, "getUser(String uid, UserCallback callback)");

        DocumentReference docRef = db.collection(COLLECTION).document(uid);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();

                if (document.exists()) {
                    Map<String, Object> data = document.getData();
                    Log.d(TAG, "getUser Result : " + data);

                    callback.onCallback(document.toObject(User.class), CbCode.OK);
                } else {
                    Log.d(TAG, "getUser Not Found");
                    callback.onCallback(null, CbCode.NOT_FOUND);
                }
            } else {
                Log.d(TAG, "getUser Error", task.getException());
                callback.onCallback(null, CbCode.ERROR);
            }
        });

        Log.d(TAG, "~getUser(String uid, UserCallback callback)");
    }

    /** 유저 가져오기 - By 학번 */
    public void getUserById(int id, UserCallback callback) {
        getUserById(String.valueOf(id), callback);
    }

    /** 유저 가져오기 - By 학번 */
    public void getUserById(String id, UserCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(COLLECTION).whereEqualTo("id", id).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                    if (!documents.isEmpty()) {
                        DocumentSnapshot document = documents.get(0);
                        if (document.exists()) {
                            Map<String, Object> data = document.getData();
                            Log.d(TAG, "getUser Result : " + data);

                            callback.onCallback(document.toObject(User.class), CbCode.OK);
                        } else {
                            Log.d(TAG, "getUser Not Found");
                            callback.onCallback(null, CbCode.NOT_FOUND);
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    // 쿼리 실패 처리
                    Log.d(TAG, "getUser Error : " + e.getMessage());
                    callback.onCallback(null, CbCode.ERROR);
                });
    }
}
