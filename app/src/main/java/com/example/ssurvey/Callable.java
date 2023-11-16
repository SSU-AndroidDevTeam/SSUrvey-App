package com.example.ssurvey;

import java.util.Map;

interface FirebaseCallable {
    public void call(Map<String, Object> data, boolean hasFailed);
}
