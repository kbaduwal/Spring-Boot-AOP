package com.luv2code.aopdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyDemoLoggingAspect {

    // this is where we add all our related advice for logging

    // let's start with an @Before advice

    @Before("execution(* add*(com.luv2code.aopdemo.Account))")
    public void beforeAddAccountAdvice(){
        System.out.println("\n======> Executing @Before advice on addAccount()");
    }

}
