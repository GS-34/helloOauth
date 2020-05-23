package com.example.oauth.aop;

import com.example.oauth.param.UserInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Component
@Aspect
public class HelloAop {
    @Around("execution(* com.example.oauth.controller.HomeController.*(..)) || execution(* com.example.oauth.controller.HomeController.*())")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {

        HttpSession session = ((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest().getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");

        if (userInfo == null) {
            return "redirect:/";
        }

        Object result = pjp.proceed();
        return result;
    }

}
