package com.example.ssurvey.model;

import io.reactivex.rxjava3.annotations.NonNull;

public class User {

    public User(String uid, String firebaseId, String id) {
        this.uid = uid;
        this.firebaseId = firebaseId;
        this.id = id;
    }

    /** 유저 고유 ID (UID) */
    private String uid;

    /** 파이어베이스 인증용 이메일 형식의 ID */
    private String firebaseId;

    /** 학번 */
    private String id;

    public String getUid() { return uid; }
    public String getFirebaseId() { return firebaseId; }
    public String getId() { return id; }
}