package com.example.ssurvey.service;

/** 콜백 상태 코드 */
public enum CbCode {
    /** 정상 수신 */
    OK,
    /** 대상 찾을 수 없음 */
    NOT_FOUND,
    /** 에러 발생 */
    ERROR
}