package com.example.ssurvey.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** 설문대상 클래스
 * 각 멤버 변수의 의미는 User 클래스에서 확인 가능 */
public class SurveyTarget {

    public boolean isTargeted(User user) {
        // 리스트가 비었거나, user의 해당 값을 내포하고 있는 경우 필터링 대상이 된다.
        if(!(sexes.isEmpty() || sexes.contains(user.getSex())))
            return false;

        if(!(studentTypes.isEmpty() || studentTypes.contains(user.getStudentType())))
            return false;

        if(!(studentCourses.isEmpty() || studentCourses.contains(user.getStudentCourse())))
            return false;

        // 졸업생이 아니면서 학부생인 경우만 학년을 검사한다.
        if(user.getStudentType() != User.StudentType.GRADUATE)
            if(user.getStudentCourse() == User.StudentCourse.BACHELOR || user.getStudentCourse() == User.StudentCourse.BACHELOR_MASTER)
                if(!(grades.isEmpty() || grades.contains(user.getGrade())))
                    return false;

        if(!(colleges.isEmpty() || colleges.contains(user.getCollege())))
            return false;

        if(!(multiMajors.isEmpty() || multiMajors.contains(user.getMultiMajor())))
            return false;

        // 다전공인 경우만 다전공 소속 대학을 검사한다.
        if(user.getMultiMajor() != User.MultiMajor.NONE)
            if(!(multiMajorColleges.isEmpty() || multiMajorColleges.contains(user.getCollegeMultiMajor())))
                return false;

        // Boolean 객체가 null이거나, user의 해당 boolean 값과 동일한 경우 필터링 대상이 된다.
        if(!(hasChangeMajor == null || hasChangeMajor == user.isHasChangeMajor()))
            return false;

        if(!(hasTeaching == null || hasTeaching == user.isHasTeaching()))
            return false;

        if(!(hasFlunk == null || hasFlunk == user.isHasFlunk()))
            return false;

        if(!(hasWarning == null || hasWarning == user.isHasWarning()))
            return false;

        if(!(hasLeave == null || hasLeave == user.isHasLeave()))
            return false;

        if(!(clubs.isEmpty() || clubs.contains(user.getClub())))
            return false;

        if(!clubInterestings.isEmpty()) {
            for (User.Club ci : user.getClubInteresting())
                if (clubInterestings.contains(ci))
                    return true;
            // 관심 동아리를 포함하고 있지 않다면 필터링 대상에서 제외된다.
            return false;
        }

        // 검사를 끝마치고 최종적으로 true를 반환하면 필터링 대상에 부합하는 것임
        return true;
    }

    private List<User.Sex> sexes = new ArrayList<>();

    private List<User.StudentType> studentTypes = new ArrayList<>();

    private List<User.StudentCourse> studentCourses = new ArrayList<>();

    private List<Integer> grades = new ArrayList<>();

    private List<User.College> colleges = new ArrayList<>();

    private List<User.MultiMajor> multiMajors = new ArrayList<>();

    private List<User.College> multiMajorColleges = new ArrayList<>();

    private Boolean hasChangeMajor = null;

    private Boolean hasTeaching = null;

    private Boolean hasFlunk = null;

    private Boolean hasWarning = null;

    private Boolean hasLeave = null;

    private List<User.Club> clubs = new ArrayList<>();

    private List<User.Club> clubInterestings = new ArrayList<>();

    public List<User.Sex> getSexes() {
        return sexes;
    }

    public void setSexes(List<User.Sex> sexes) {
        this.sexes = sexes;
    }

    public List<User.StudentType> getStudentTypes() {
        return studentTypes;
    }

    public void setStudentTypes(List<User.StudentType> studentTypes) {
        this.studentTypes = studentTypes;
    }

    public List<User.StudentCourse> getStudentCourses() {
        return studentCourses;
    }

    public void setStudentCourses(List<User.StudentCourse> studentCourses) {
        this.studentCourses = studentCourses;
    }

    public List<Integer> getGrades() {
        return grades;
    }

    public void setGrades(List<Integer> grades) {
        this.grades = grades;
    }

    public List<User.College> getColleges() {
        return colleges;
    }

    public void setColleges(List<User.College> colleges) {
        this.colleges = colleges;
    }

    public List<User.MultiMajor> getMultiMajors() {
        return multiMajors;
    }

    public void setMultiMajors(List<User.MultiMajor> multiMajors) {
        this.multiMajors = multiMajors;
    }

    public List<User.College> getMultiMajorColleges() {
        return multiMajorColleges;
    }

    public void setMultiMajorColleges(List<User.College> multiMajorColleges) {
        this.multiMajorColleges = multiMajorColleges;
    }

    public Boolean getHasChangeMajor() {
        return hasChangeMajor;
    }

    public void setHasChangeMajor(Boolean hasChangeMajor) {
        this.hasChangeMajor = hasChangeMajor;
    }

    public Boolean getHasTeaching() {
        return hasTeaching;
    }

    public void setHasTeaching(Boolean hasTeaching) {
        this.hasTeaching = hasTeaching;
    }

    public Boolean getHasFlunk() {
        return hasFlunk;
    }

    public void setHasFlunk(Boolean hasFlunk) {
        this.hasFlunk = hasFlunk;
    }

    public Boolean getHasWarning() {
        return hasWarning;
    }

    public void setHasWarning(Boolean hasWarning) {
        this.hasWarning = hasWarning;
    }

    public Boolean getHasLeave() {
        return hasLeave;
    }

    public void setHasLeave(Boolean hasLeave) {
        this.hasLeave = hasLeave;
    }

    public List<User.Club> getClubs() {
        return clubs;
    }

    public void setClubs(List<User.Club> clubs) {
        this.clubs = clubs;
    }

    public List<User.Club> getClubInterestings() {
        return clubInterestings;
    }

    public void setClubInterestings(List<User.Club> clubInterestings) {
        this.clubInterestings = clubInterestings;
    }
}
