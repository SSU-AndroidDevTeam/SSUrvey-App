package com.example.ssurvey.model;

import com.google.firebase.Timestamp;

public class SurveyResponse {
    /** TODO: 이 설문을 응답한 사람의 식별자 */
    private String userId;
    private int q1Response;
    private int q2Response;
    private int q3Response;

    /** TODO: 설문 응답일 */
    private Timestamp responseDate;

    public SurveyResponse() {}
    public SurveyResponse(
            String userId,
            int q1Response,
            int q2Response,
            int q3Response,
            Timestamp responseDate
    ) {
        this.userId = userId;
        this.q1Response = q1Response;
        this.q2Response = q2Response;
        this.q3Response = q3Response;
        this.responseDate = responseDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getQ1Response() {
        return q1Response;
    }

    public void setQ1Response(int q1Response) {
        this.q1Response = q1Response;
    }

    public int getQ2Response() {
        return q2Response;
    }

    public void setQ2Response(int q2Response) {
        this.q2Response = q2Response;
    }

    public int getQ3Response() {
        return q3Response;
    }

    public void setQ3Response(int q3Response) {
        this.q3Response = q3Response;
    }

    public Timestamp getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(Timestamp responseDate) {
        this.responseDate = responseDate;
    }

}
