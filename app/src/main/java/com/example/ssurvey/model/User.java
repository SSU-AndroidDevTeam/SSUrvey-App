package com.example.ssurvey.model;
import java.util.Date;

public class User {

    /** 학생 유형 */
    public enum StudentType {
        /** 재학생 */
        IN,
        /** 휴학생 */
        LEAVE,
        /** 졸업생 */
        GRADUATE
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

    /** 다전공 이수 유형 */
    public enum MultiMajor {
        /** 해당 없음 */
        NONE,
        /** 부전공 */
        MINOR,
        /** 복수전공 */
        DOUBLE_MAJOR
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

    /** 동아리 범주 */
    public enum Club {
        /** 소속(관심) 동아리 없음 */
        NONE,
        /** 운동/스포츠 */
        SPORTS,
        /** 아웃도어/여행 */
        OUTDOOR,
        /** 인문학/책/글 */
        BOOKS,
        /** 외국어 */
        LANGUAGE,
        /** 문화/공연/축제 */
        CULTURE,
        /** 음악/악기 */
        MUSIC,
        /** 공예/만들기 */
        CRAFTS,
        /** 댄스/무용 */
        DANCE,
        /** 사교/인맥 */
        SOCIAL,
        /** 사진/영상 */
        PICTURE,
        /** 게임/오락 */
        GAMES,
        /** 요리/제조 */
        COOKING,
        /** 봉사활동 */
        VOLUNTEER,
        /** 반려동물 */
        PET,
        /** 그외 */
        ETC
    }


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

    /** 이름 */
    private String name;

    /** 성별 */
    private String sex;

    /** 이메일 */
    private String email;

    /** 휴대폰 번호 */
    private String phone;

    /** 생년월일 */
    private Date birth;

    /** 학생 유형 */
    private StudentType studentType;

    /** 학생 학년 */
    private Integer grade;

    /** 학생 과정 */
    private StudentCourse studentCourse;

    /** 소속 대학 */
    private College college;

    /** 다전공 이수 유형 */
    private MultiMajor multiMajor;

    /** 다전공 소속 대학 */
    private College collegeMultiMajor = College.NONE;

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
    private Club clubInteresting = Club.NONE;

    public String getUid() {
        return uid;
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
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

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
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

    public Club getClubInteresting() {
        return clubInteresting;
    }

    public void setClubInteresting(Club clubInteresting) {
        this.clubInteresting = clubInteresting;
    }
}