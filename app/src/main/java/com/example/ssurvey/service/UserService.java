package com.example.ssurvey.service;

import android.util.Log;
import androidx.annotation.NonNull;
import com.example.ssurvey.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class UserService {

    private static UserService instance = null;

    private FirebaseFirestore db;

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

    /** 유저 삽입 */
    public void setUser(Map<String, Object> user) {
        Log.d(TAG, "setUser(Map<String, Object> user)");

        CollectionReference colRef = db.collection(COLLECTION);
        colRef.document(user.get("uid").toString()).set(user);

        Log.d(TAG, "~setUser(Map<String, Object> user)");
    }

    /** 유저 삽입 */
    public void setUser(User user) {
        Log.d(TAG, "setUser(User user)");

        CollectionReference colRef = db.collection(COLLECTION);
        Map<String, Object> data = new HashMap<>();
        data.put("uid", user.getUid());
        data.put("firebaseId", user.getFirebaseId());
        data.put("id", user.getId());
        colRef.document(user.getUid()).set(data);

        Log.d(TAG, "~setUser(User user)");
    }

    /** 유저 가져오기 */
    public void getUser(String uid, UserCallback callback) {
        Log.d(TAG, "getUser(String uid, UserCallback callback)");

        DocumentReference docRef = db.collection(COLLECTION).document(uid);
        docRef.get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        Map<String, Object> data = document.getData();
                        Log.d(TAG, "getUser Result : " + data);

                        String uid = data.get("uid").toString();
                        String firebaseId = data.get("firebaseId").toString();
                        String id = data.get("id").toString();

                        callback.onCallback(new User(uid, firebaseId, id), CbCode.OK);
                    } else {
                        Log.d(TAG, "getUser Not Found");
                        callback.onCallback(null, CbCode.NOT_FOUND);
                    }
                } else {
                    Log.d(TAG, "getUser Error", task.getException());
                    callback.onCallback(null, CbCode.ERROR);
                }
            }
        });

        Log.d(TAG, "~getUser(String uid, UserCallback callback)");
    }
}
