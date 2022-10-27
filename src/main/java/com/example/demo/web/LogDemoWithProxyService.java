package com.example.demo.web;

import com.example.demo.web.common.MyLogger;
import com.example.demo.web.common.MyLoggerWithProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDemoWithProxyService {

    // private final MyLogger myLoggerl;
    private final MyLoggerWithProxy myLoggerProvider;

    public void logic(String id) {
        myLoggerProvider.log("service id = " + id);
    }
}
