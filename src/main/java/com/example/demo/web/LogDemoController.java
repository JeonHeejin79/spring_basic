package com.example.demo.web;

import com.example.demo.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor // 생성자에 final 필드 자동주입
public class LogDemoController {

    private final LogDemoService logDemoService;
    // private final MyLogger myLogger;
    private final ObjectProvider<MyLogger> myLoggerProvicer; // MyLooger 을 찾을수 있는 DL 이 주입된다.


    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {

        MyLogger myLogger = myLoggerProvicer.getObject(); // getMyLogger

        String requestUrl = request.getRequestURL().toString();

        myLogger.setRequestUrl(requestUrl);

        myLogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    }
}
