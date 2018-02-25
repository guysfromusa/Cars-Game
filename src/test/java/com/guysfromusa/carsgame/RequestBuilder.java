package com.guysfromusa.carsgame;

/**
 * Created by Tomasz Bradlo, 25.02.18
 */

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.nio.charset.Charset;

import static java.util.Collections.singletonList;


public class RequestBuilder<T> {

    private HttpHeaders headers = new HttpHeaders();

    private T body;

    public RequestBuilder(){
        headers.setAcceptCharset(singletonList(Charset.forName("UTF-8")));
    }

    public RequestBuilder<T> body(T body) {
        this.body = body;
        headers.setContentType(MediaType.APPLICATION_JSON);
        return this;
    }

    public HttpEntity<T> build(){
        return new HttpEntity<>(body, headers);
    }
}