package com.library.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class SessionHelper {
    public HttpSession getSession(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
    }
    public void removeSessionAttribute(String key){
        try {
            HttpSession session = getSession();
            session.removeAttribute(key);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
