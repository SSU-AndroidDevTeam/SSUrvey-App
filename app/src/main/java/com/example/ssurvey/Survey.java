package com.example.ssurvey;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.Timestamp;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Survey {
    private Map<String, Object> surveyData;
    Survey(Map<String, Object> data) {
        // NOTE: data에 누락된 항목이 없어야 한다
        surveyData = data;
    }
    Survey(
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

            Integer openDate,
            Integer closeDate,

            String target
    ) {
        surveyData = createMap(
                name,
                desc,
                giftId,
                hostId,
                q1Desc, q1Ans1, q1Ans2, q1Ans3, q1Ans4, q1Ans5,
                q2Desc, q2Ans1, q2Ans2, q2Ans3, q2Ans4, q2Ans5,
                q3Desc, q3Ans1, q3Ans2, q3Ans3, q3Ans4, q3Ans5,
                openDate, closeDate,
                target
        );
    }

    Map<String, Object> createMap(
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

            Integer openDate,
            Integer closeDate,

            String target
    ) {
        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("desc", desc);
        data.put("giftId", "<등록한 이모티콘 고유 식별자>"); // TODO
        data.put("hostId", "<등록한 유저 고유 식별자>"); // TODO


        data.put("q1Desc", q1Desc);
        data.put("q1Ans1", q1Ans1);
        data.put("q1Ans2", q1Ans2);
        data.put("q1Ans3", q1Ans3);
        data.put("q1Ans4", q1Ans4);
        data.put("q1Ans5", q1Ans5);

        data.put("q2Desc", q2Desc);
        data.put("q2Ans1", q2Ans1);
        data.put("q2Ans2", q2Ans2);
        data.put("q2Ans3", q2Ans3);
        data.put("q2Ans4", q2Ans4);
        data.put("q2Ans5", q2Ans5);

        data.put("q3Desc", q3Desc);
        data.put("q3Ans1", q3Ans1);
        data.put("q3Ans2", q3Ans2);
        data.put("q3Ans3", q3Ans3);
        data.put("q3Ans4", q3Ans4);
        data.put("q3Ans5", q3Ans5);

        data.put("openDate", new Timestamp(0,0)); // TODO
        data.put("closeDate", new Timestamp(0,0)); // TODO

        data.put("target", "<설문 타깃>"); // TODO
        return data;
    }

    public Map<String, Object> getData() {
        return surveyData;
    }
}
