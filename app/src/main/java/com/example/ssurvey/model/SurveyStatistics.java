package com.example.ssurvey.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ssurvey.service.CbCode;
import com.example.ssurvey.service.SurveyResponseCallback;
import com.example.ssurvey.service.SurveyStatCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class SurveyStatistics {
    // NOTE: 이 값들을 Reflective하게 동적으로 SurveyResponse의 멤버명을 긁어오는 식으로 구현하는게 더 이상적이나, 일단 하드코딩한다
    private final String q1RespFieldName = "q1Response";
    private final String q2RespFieldName = "q2Response";
    private final String q3RespFieldName = "q3Response";

    public SurveyStatistics() {
        q1ResponseCountArray = new int[6];
        q2ResponseCountArray = new int[6];
        q3ResponseCountArray = new int[6];
    }

    private void updateQuestionResponseCount(int questionNumber, int chosenValue) {
        switch (questionNumber) {
            case 1:
                q1ResponseCountArray[chosenValue] += 1;
                break;
            case 2:
                q2ResponseCountArray[chosenValue] += 1;
                break;
            case 3:
                q3ResponseCountArray[chosenValue] += 1;
                break;
            default:
                Log.d("FB", chosenValue+"번 항목을 찾을 수 없습니다.");
                return;
        }
    }

    /**
     * 단일 응답에 대한 정보를 반영해 통계 정보를 갱신한다
     * 특별한 이유가 없는 이상 액티비티 단에서 직접 호출하지 말 것
     * */
    public void updateStatistics(Map<String, Object> map) {
        // 데이터 적합성 검사
        if (!map.containsKey(q1RespFieldName) || !map.containsKey(q2RespFieldName) || !map.containsKey(q3RespFieldName)) {
            Log.d("FB", "설문 응답 정보에서 적절한 필드 값을 찾을 수 없습니다.");
            return;
        };

        totalRespondents += 1;

        // NOTE: 값을 사용하기 전에 파베에서 먼저 타입이 적절한 지 확인하는게 더 안전
        updateQuestionResponseCount(1, Integer.parseInt(map.get(q1RespFieldName).toString()));
        updateQuestionResponseCount(2, Integer.parseInt(map.get(q2RespFieldName).toString()));
        updateQuestionResponseCount(3, Integer.parseInt(map.get(q3RespFieldName).toString()));
    }
    private int totalRespondents = 0;
    // 질문 1의 각 항목별 응답자 수
    private int q1ResponseCountArray[];
    // 질문 2의 각 항목별 응답자 수
    private int q2ResponseCountArray[];
    // 질문 3의 각 항목별 응답자 수
    private int q3ResponseCountArray[];

    /** 통계값 접근자들 */
    public int getQ1ResponseCount(int responseNumber) {
        return this.q1ResponseCountArray[responseNumber];
    }
    public int getQ2ResponseCount(int responseNumber) {
        return this.q2ResponseCountArray[responseNumber];
    }
    public int getQ3ResponseCount(int responseNumber) {
        return this.q3ResponseCountArray[responseNumber];
    }
    public int getTotalRespondents() {
        return this.totalRespondents;
    }
}
