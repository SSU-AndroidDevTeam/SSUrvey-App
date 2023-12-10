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

    public interface Callback {
        public void onCallback();
    }

    private static AuthManager instance = null;

    /** 학번ID 이메일 변환용 도메인 */
    public static final String EMAIL_DOMAIN = "@com.example.ssurvey";

    private FirebaseAuth firebaseAuth;

    /** 현재 로그인 한 유저의 User 클래스 객체 */
    private User currentUser = null;

    private AuthManager() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public static AuthManager getInstance() {
        if (instance == null)
            instance = new AuthManager();

        return instance;
    }

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public void initCurrentUser(Callback callback) {
        if(!getCurrentUid().isEmpty() && currentUser == null)
            UserService.getInstance().getUser(getCurrentUid(), (user, code) -> {
                currentUser = user;
                callback.onCallback();
            });
        else
            callback.onCallback();
    }

    /** 현재 유저 갱신 */
    public void renewCurrentUser() {
        if(!getCurrentUid().isEmpty())
            UserService.getInstance().getUser(getCurrentUid(), (user, code) -> {
                currentUser = user;
            });
    }

    /** 현재 로그인한 User 클래스 객체를 반환 */
    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) { currentUser = user; }

    /** 현재 로그인 한 User의 고유 ID (UID) 반환 (학번이 아님) */
    public String getCurrentUid() {
        if(firebaseAuth.getCurrentUser() != null)
            return firebaseAuth.getCurrentUser().getUid();
        return "";
    }

    /** 현재 로그인 한 User의 학번 (ID) 반환 */
    public String getCurrentId() {
        if(currentUser != null)
            return currentUser.getId();
        return "";
    }

    /** 로그아웃 */
    public void logout() {
        firebaseAuth.signOut();
        currentUser = null;
    }

    /** 현재 로그인이 되어있는가? */
    public boolean isLoggedIn() {
        return firebaseAuth.getCurrentUser() != null;
    }

    /** 현재 로그인 한 유저의 비밀번호 업데이트 */
    public void updatePassword(String newPw) {
        firebaseAuth.getCurrentUser().updatePassword(newPw);
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
