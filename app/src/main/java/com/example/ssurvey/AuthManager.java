package com.example.ssurvey;

import com.example.ssurvey.model.User;
import com.example.ssurvey.service.UserService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/***
 * AuthManager (Singleton)
 * 현재 로그인 한 유저에 대한 관리 클래스
 */
public class AuthManager {
    private static AuthManager instance = null;

    /** 학번ID 이메일 변환용 도메인 */
    public static final String EMAIL_DOMAIN = "@com.example.ssurvey";

    private FirebaseAuth mAuth;

    /** 현재 로그인 한 유저의 User 클래스 객체 */
    private User currentUser = null;

    private AuthManager() {
        mAuth = FirebaseAuth.getInstance();

        UserService.getInstance().getUser(getCurrentUserID(), (user, code) -> {
            currentUser = user;
        });
    }

    public static AuthManager getInstance() {
        if (instance == null)
            instance = new AuthManager();

        return instance;
    }

    /** 현재 유저 갱신 */
    public void renewCurrentUser() {
        if(getCurrentUserID() != null)
            UserService.getInstance().getUser(getCurrentUserID(), (user, code) -> {
                currentUser = user;
            });
    }

    /** 현재 로그인한 User 클래스 객체를 반환 */
    public User getCurrentUser() {
        return currentUser;
    }

    /** 현재 로그인 한 User의 ID (UID) 반환 */
    public String getCurrentUserID() {
        return mAuth.getCurrentUser().getUid();
    }

    /***
     * 학번을 이메일로 변환한 문자열을 반환해준다. (firebase email auth에 사용하기 위함)
     * @param id 학번
     * @return 학번을 이메일로 변환한 문자열
     */
    public static String id2Email(String id) {
        return id + EMAIL_DOMAIN;
    }
}
