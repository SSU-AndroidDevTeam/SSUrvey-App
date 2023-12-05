package com.example.ssurvey.model;
import android.content.Context;

import com.example.ssurvey.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {

    /** 성별 */
    public enum Sex {
        /** 남성 */
        MALE,
        /** 여성 */
        FEMALE
    }

    /** 학생 유형 */
    public enum StudentType {
        /** 재학생 */
        IN,
        /** 휴학생 */
        LEAVE,
        /** 졸업생 */
        GRADUATE
    }

    /** R.string에 등록된 문자열과 비교 후 대응되는 StudentType 반환  */
    public static StudentType getStudentType(Context context, String key) {
        if(context.getString(R.string.user_student_type_leave).equals(key))
            return StudentType.LEAVE;
        if(context.getString(R.string.user_student_type_graduate).equals(key))
            return StudentType.GRADUATE;
        return StudentType.IN;
    }

    /** 학생 과정 */
    public enum StudentCourse {
        /** 학사 과정 */
        BACHELOR,
        /** 학석사 통합 과정 */
        BACHELOR_MASTER,
        /** 석사 과정 */
        MASTER,
        /** 석박사 통합 과정 */
        MASTER_DOCTOR,
        /** 박사 과정 */
        DOCTOR
    }

    /** R.string에 등록된 문자열과 비교 후 대응되는 StudentCourse 반환  */
    public static StudentCourse getStudentCourse(Context context, String key) {
        if(context.getString(R.string.user_student_course_bachelor_master).equals(key))
            return StudentCourse.BACHELOR_MASTER;
        if(context.getString(R.string.user_student_course_master).equals(key))
            return StudentCourse.MASTER;
        if(context.getString(R.string.user_student_course_master_doctor).equals(key))
            return StudentCourse.MASTER_DOCTOR;
        if(context.getString(R.string.user_student_course_doctor).equals(key))
            return StudentCourse.DOCTOR;
        return StudentCourse.BACHELOR;
    }

    /** 소속 대학 */
    public enum College {
        /** 해당 없음 (다전공 소속 대학용) */
        NONE,
        /** 인문대학 */
        HUMANITIES,
        /** 자연과학대학 */
        NATURAL,
        /** 법과대학 */
        LAW,
        /** 사회과학대학 */
        SOCIAL,
        /** 경제통상대학 */
        ECONOMICS,
        /** 경영대학 */
        BUSINESS,
        /** 공과대학 */
        ENGINEERING,
        /** IT대학 */
        IT,
        /** 융합특성화자유전공학부 */
        CONVERGENCE,
        /** 차세대반도체 혁신융합대학 */
        SEMICONDUCTOR
    }

    /** R.string에 등록된 문자열과 비교 후 대응되는 College 반환  */
    public static College getCollege(Context context, String key) {
        if(context.getString(R.string.user_college_humanities).equals(key))
            return College.HUMANITIES;
        if(context.getString(R.string.user_college_natural).equals(key))
            return College.NATURAL;
        if(context.getString(R.string.user_college_law).equals(key))
            return College.LAW;
        if(context.getString(R.string.user_college_social).equals(key))
            return College.SOCIAL;
        if(context.getString(R.string.user_college_economics).equals(key))
            return College.ECONOMICS;
        if(context.getString(R.string.user_college_business).equals(key))
            return College.BUSINESS;
        if(context.getString(R.string.user_college_engineering).equals(key))
            return College.ENGINEERING;
        if(context.getString(R.string.user_college_it).equals(key))
            return College.IT;
        if(context.getString(R.string.user_college_convergence).equals(key))
            return College.CONVERGENCE;
        if(context.getString(R.string.user_college_semiconductor).equals(key))
            return College.SEMICONDUCTOR;
        return College.NONE;
    }

    /** 다전공 이수 유형 */
    public enum MultiMajor {
        /** 해당 없음 */
        NONE,
        /** 부전공 */
        MINOR,
        /** 복수전공 */
        DOUBLE_MAJOR
    }

    /** R.string에 등록된 문자열과 비교 후 대응되는 MultiMajor 반환  */
    public static MultiMajor getMultiMajor(Context context, String key) {
        if(context.getString(R.string.user_multi_major_minor).equals(key))
            return MultiMajor.MINOR;
        if(context.getString(R.string.user_multi_major_double_major).equals(key))
            return MultiMajor.DOUBLE_MAJOR;
        return MultiMajor.NONE;
    }

    /** 동아리 범주 */
    public enum Club {
        /** 소속(관심) 동아리 없음 */
        NONE,
        /** 운동 / 스포츠 */
        SPORTS,
        /** 아웃도어 / 여행 */
        OUTDOOR,
        /** 인문학 / 책 / 글 */
        BOOKS,
        /** 외국어 */
        LANGUAGE,
        /** 문화 / 공연 / 축제 */
        CULTURE,
        /** 음악 / 악기 */
        MUSIC,
        /** 공예 / 만들기 */
        CRAFTS,
        /** 댄스 / 무용 */
        DANCE,
        /** 사교 / 인맥 */
        SOCIAL,
        /** 사진 / 영상 */
        PICTURE,
        /** 게임 / 오락 */
        GAMES,
        /** 개발 / 코딩 */
        DEVELOP,
        /** 요리 / 제조 */
        COOKING,
        /** 봉사활동 */
        VOLUNTEER,
        /** 그외 */
        ETC
    }

    /** R.string에 등록된 문자열과 비교 후 대응되는 Club 반환  */
    public static Club getClub(Context context, String key) {
        if(context.getString(R.string.user_club_sports).equals(key))
            return Club.SPORTS;
        if(context.getString(R.string.user_club_outdoor).equals(key))
            return Club.OUTDOOR;
        if(context.getString(R.string.user_club_books).equals(key))
            return Club.BOOKS;
        if(context.getString(R.string.user_club_language).equals(key))
            return Club.LANGUAGE;
        if(context.getString(R.string.user_club_culture).equals(key))
            return Club.CULTURE;
        if(context.getString(R.string.user_club_music).equals(key))
            return Club.MUSIC;
        if(context.getString(R.string.user_club_crafts).equals(key))
            return Club.CRAFTS;
        if(context.getString(R.string.user_club_dance).equals(key))
            return Club.DANCE;
        if(context.getString(R.string.user_club_social).equals(key))
            return Club.SOCIAL;
        if(context.getString(R.string.user_club_picture).equals(key))
            return Club.PICTURE;
        if(context.getString(R.string.user_club_games).equals(key))
            return Club.GAMES;
        if(context.getString(R.string.user_club_develop).equals(key))
            return Club.DEVELOP;
        if(context.getString(R.string.user_club_cooking).equals(key))
            return Club.COOKING;
        if(context.getString(R.string.user_club_volunteer).equals(key))
            return Club.VOLUNTEER;
        if(context.getString(R.string.user_club_etc).equals(key))
            return Club.ETC;
        return Club.NONE;
    }

    /** 빈 User 객체 생성용 */
    public User() {
        this.clubInteresting = new ArrayList<Club>();
    }

    public User(String uid, String firebaseId, String id) {
        super();
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

    /** 이름 */
    private String name;

    /** 성별 */
    private Sex sex;

    /** 생년월일 */
    private Date birth;

    /** 이메일 */
    private String email;

    /** 휴대폰 번호 */
    private String phone;

    /** 학생 유형 */
    private StudentType studentType;

    /** 학생 과정 */
    private StudentCourse studentCourse;

    /** 학생 학년 (학부생만 유효) */
    private int grade = 0;

    /** 소속 대학 */
    private College college;

    /** 다전공 이수 유형 */
    private MultiMajor multiMajor;

    /** 다전공 소속 대학 */
    private College collegeMultiMajor = College.NONE;

    /** 전과 (학과 이동) 유무 */
    private boolean hasChangeMajor;

    /** 교직 이수 유무 */
    private boolean hasTeaching;

    /** 유급 기록 유무 */
    private boolean hasFlunk;

    /** 학사 경고 기록 유무 */
    private boolean hasWarning;

    /** 휴학 기록 유무 */
    private boolean hasLeave;

    /** 소속 동아리 범주 */
    private Club club = Club.NONE;

    /** 관심 동아리 범주 */
    private List<Club> clubInteresting;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public StudentType getStudentType() {
        return studentType;
    }

    public void setStudentType(StudentType studentType) {
        this.studentType = studentType;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public StudentCourse getStudentCourse() {
        return studentCourse;
    }

    public void setStudentCourse(StudentCourse studentCourse) {
        this.studentCourse = studentCourse;
    }

    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }

    public MultiMajor getMultiMajor() {
        return multiMajor;
    }

    public void setMultiMajor(MultiMajor multiMajor) {
        this.multiMajor = multiMajor;
    }

    public College getCollegeMultiMajor() {
        return collegeMultiMajor;
    }

    public void setCollegeMultiMajor(College collegeMultiMajor) {
        this.collegeMultiMajor = collegeMultiMajor;
    }

    public boolean isHasChangeMajor() { return hasChangeMajor; }

    public void setHasChangeMajor(boolean hasChangeMajor) { this.hasChangeMajor = hasChangeMajor; }

    public boolean isHasTeaching() {
        return hasTeaching;
    }

    public void setHasTeaching(boolean hasTeaching) {
        this.hasTeaching = hasTeaching;
    }

    public boolean isHasFlunk() {
        return hasFlunk;
    }

    public void setHasFlunk(boolean hasFlunk) {
        this.hasFlunk = hasFlunk;
    }

    public boolean isHasWarning() {
        return hasWarning;
    }

    public void setHasWarning(boolean hasWarning) {
        this.hasWarning = hasWarning;
    }

    public boolean isHasLeave() {
        return hasLeave;
    }

    public void setHasLeave(boolean hasLeave) {
        this.hasLeave = hasLeave;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public List<Club> getClubInteresting() {
        return clubInteresting;
    }

    public void addClubInterestingElement(Club club) {
        clubInteresting.add(club);
    }

    public void clearClubInteresting() {
        clubInteresting.clear();
    }
}