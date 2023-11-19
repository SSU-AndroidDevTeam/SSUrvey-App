package com.example.ssurvey.util;

/***
 * 유틸 클래스
 */// 231120 east
public class Util {
    public static final String EMAIL_DOMAIN = "@com.example.ssurvey";

    /***
     * 학번으로 받은 ID를 firebase email auth에 사용하기 위해 이메일 양식의 ID로 반환해준다.
     * @param id 학번
     * @return 이메일 양식으로 변환된 ID
     */
    public static String id2EmailId(String id) {
        return id + EMAIL_DOMAIN;
    }
}