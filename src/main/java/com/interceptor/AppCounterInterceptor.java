package com.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.app.datastore.SimpleDataStore;
import com.app.visitor.counter.service.VisitorLocationService;
import com.app.visitor.counter.service.bean.VisitorLocation;

public class AppCounterInterceptor implements HandlerInterceptor {

    @Autowired
    private VisitorLocationService visitorLocationService;

    @Autowired
    private SimpleDataStore        simpleDataStore;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception exception) throws Exception {
        System.out.println("In afterCompletion");

        countLocation(request, response);
        countPageView(request, response);

    }

    private void countPageView(HttpServletRequest request,HttpServletResponse response) {
        String requestUri = request.getRequestURI();
        
        if (getCookie(request, "countPageView_"+requestUri) == null) {
            
            Long counter = (Long) simpleDataStore.get(requestUri);
            if (counter != null) {
                counter = counter + 1;
            } else {
                counter = Long.valueOf("0");
            }
            simpleDataStore.put(requestUri, counter);
            
        }
    }

    private void countLocation(HttpServletRequest request,HttpServletResponse response) {
        String ipAddress = request.getRemoteAddr();

        if (getCookie(request, "countLocation") == null) {

            VisitorLocation location = visitorLocationService.getLocation(ipAddress);
            String key = location.getCity();
            Long counter = (Long) simpleDataStore.get(key);
            if (counter != null) {
                counter = counter + 1;
            } else {
                counter = Long.valueOf("0");
            }
            simpleDataStore.put(key, counter);
            
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        System.out.println("In postHandle");

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("In preHandle");
        
        /*
         * Cookie creation should happen from preHandle only
         */
        
        response.addCookie(createCookie("countPageView_"+request.getRequestURI(), "true", "/", 24*60*60));
        
        response.addCookie(createCookie("countLocation", "true", "/", 24*60*60));
        
        return true;
    }
    
    private Cookie getCookie(HttpServletRequest req, String cookieName) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }

        return null;
    }

    private Cookie createCookie(String name, String value, String path, int expiry) {
        Cookie ck = new Cookie(name, value);
        ck.setMaxAge(expiry);
        ck.setPath(path);
        return ck;
    }
}
