package com.example.ssurvey.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.Timestamp;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Survey {
    /** 설문조사 제목 */
    private String name;
    /** 설문조사 설명 */
    private String desc;
    /** TODO: 이 설문에 등록된 기프티콘의 식별자 */
    private String giftId;
    /** TODO: 이 설문을 등록한 사람의 식별자 */
    private String hostId;
    /** 질문 1 */
    private String q1Desc;
    private String q1Ans1;
    private String q1Ans2;
    private String q1Ans3;
    private String q1Ans4;
    private String q1Ans5;
    /** 질문 2 */
    private String q2Desc;
    private String q2Ans1;
    private String q2Ans2;
    private String q2Ans3;
    private String q2Ans4;
    private String q2Ans5;
    /** 질문 3 */
    private String q3Desc;
    private String q3Ans1;
    private String q3Ans2;
    private String q3Ans3;
    private String q3Ans4;
    private String q3Ans5;
    /** TODO: 설문 게시일 */
    private Timestamp openDate;
    /** TODO: 설문 마감일 */
    private Timestamp closeDate;
    /** TODO: 설문 대상 군집 특정을 위한 값 */
    private String target;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getQ1Desc() {
        return q1Desc;
    }

    public void setQ1Desc(String q1Desc) {
        this.q1Desc = q1Desc;
    }

    public String getQ1Ans1() {
        return q1Ans1;
    }

    public void setQ1Ans1(String q1Ans1) {
        this.q1Ans1 = q1Ans1;
    }

    public String getQ1Ans2() {
        return q1Ans2;
    }

    public void setQ1Ans2(String q1Ans2) {
        this.q1Ans2 = q1Ans2;
    }

    public String getQ1Ans3() {
        return q1Ans3;
    }

    public void setQ1Ans3(String q1Ans3) {
        this.q1Ans3 = q1Ans3;
    }

    public String getQ1Ans4() {
        return q1Ans4;
    }

    public void setQ1Ans4(String q1Ans4) {
        this.q1Ans4 = q1Ans4;
    }

    public String getQ1Ans5() {
        return q1Ans5;
    }

    public void setQ1Ans5(String q1Ans5) {
        this.q1Ans5 = q1Ans5;
    }

    public String getQ2Desc() {
        return q2Desc;
    }

    public void setQ2Desc(String q2Desc) {
        this.q2Desc = q2Desc;
    }

    public String getQ2Ans1() {
        return q2Ans1;
    }

    public void setQ2Ans1(String q2Ans1) {
        this.q2Ans1 = q2Ans1;
    }

    public String getQ2Ans2() {
        return q2Ans2;
    }

    public void setQ2Ans2(String q2Ans2) {
        this.q2Ans2 = q2Ans2;
    }

    public String getQ2Ans3() {
        return q2Ans3;
    }

    public void setQ2Ans3(String q2Ans3) {
        this.q2Ans3 = q2Ans3;
    }

    public String getQ2Ans4() {
        return q2Ans4;
    }

    public void setQ2Ans4(String q2Ans4) {
        this.q2Ans4 = q2Ans4;
    }

    public String getQ2Ans5() {
        return q2Ans5;
    }

    public void setQ2Ans5(String q2Ans5) {
        this.q2Ans5 = q2Ans5;
    }

    public String getQ3Desc() {
        return q3Desc;
    }

    public void setQ3Desc(String q3Desc) {
        this.q3Desc = q3Desc;
    }

    public String getQ3Ans1() {
        return q3Ans1;
    }

    public void setQ3Ans1(String q3Ans1) {
        this.q3Ans1 = q3Ans1;
    }

    public String getQ3Ans2() {
        return q3Ans2;
    }

    public void setQ3Ans2(String q3Ans2) {
        this.q3Ans2 = q3Ans2;
    }

    public String getQ3Ans3() {
        return q3Ans3;
    }

    public void setQ3Ans3(String q3Ans3) {
        this.q3Ans3 = q3Ans3;
    }

    public String getQ3Ans4() {
        return q3Ans4;
    }

    public void setQ3Ans4(String q3Ans4) {
        this.q3Ans4 = q3Ans4;
    }

    public String getQ3Ans5() {
        return q3Ans5;
    }

    public void setQ3Ans5(String q3Ans5) {
        this.q3Ans5 = q3Ans5;
    }

    public Timestamp getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Timestamp openDate) {
        this.openDate = openDate;
    }

    public Timestamp getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Timestamp closeDate) {
        this.closeDate = closeDate;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Survey() {

    }
    public Survey(
        String name,
        String desc,
        String giftId,
        String hostId,

        String q1Desc,
        String q1Ans1,
        String q1Ans2,
        String q1Ans3,
        String q1Ans4,
        String q1Ans5,

        String q2Desc,
        String q2Ans1,
        String q2Ans2,
        String q2Ans3,
        String q2Ans4,
        String q2Ans5,

        String q3Desc,
        String q3Ans1,
        String q3Ans2,
        String q3Ans3,
        String q3Ans4,
        String q3Ans5,

        Timestamp openDate,
        Timestamp closeDate,
        String target
    ) {
        this.name = name;
        this.desc = desc;
        this.giftId = giftId;
        this.hostId = hostId;
        this.q1Desc = q1Desc;
        this.q1Ans1 = q1Ans1;
        this.q1Ans2 = q1Ans2;
        this.q1Ans3 = q1Ans3;
        this.q1Ans4 = q1Ans4;
        this.q1Ans5 = q1Ans5;
        this.q2Desc = q2Desc;
        this.q2Ans1 = q2Ans1;
        this.q2Ans2 = q2Ans2;
        this.q2Ans3 = q2Ans3;
        this.q2Ans4 = q2Ans4;
        this.q2Ans5 = q2Ans5;
        this.q3Desc = q3Desc;
        this.q3Ans1 = q3Ans1;
        this.q3Ans2 = q3Ans2;
        this.q3Ans3 = q3Ans3;
        this.q3Ans4 = q3Ans4;
        this.q3Ans5 = q3Ans5;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.target = target;
    }
}
