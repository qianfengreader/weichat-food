package com.qf.utils;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
public class CookieUtils {

    public String getToken(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return null;
        }
        String token = "";
        for (Cookie cookie : cookies) {

            if (cookie.getName().equals("token")) {
                token=cookie.getValue();
                return token;
            }
        }
        return null;
    }
}
