package com.imooc.aspect_demo;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.After;

public class Aspect_Filter {
    //针对增加SnowFilter注解的服务外层进行统一日志处理
    @Pointcut("")
    public void filter(){}

    @Around("filter()")
    public Object doInvoke(ProceedingJoinPoint joinPoint){

        return null;
    }
}
