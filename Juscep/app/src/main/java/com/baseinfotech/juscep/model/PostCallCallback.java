package com.baseinfotech.juscep.model;

import okhttp3.Response;

public interface PostCallCallback {
    void processResponse(Response response, String id, String responseString);
}
