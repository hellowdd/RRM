package com.bocom.filter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener
{
    
    public static Map        userMap = new HashMap();
    private MySessionContext myc     = MySessionContext.getInstance();
    
    public void sessionCreated(HttpSessionEvent httpSessionEvent)
    {
        System.out.println("start..." + httpSessionEvent.getSession().getId());
        myc.AddSession(httpSessionEvent.getSession());
//        Map map = myc.getMymap();
//        Set<String> get = map.keySet();
//        for (String test : get)
//        {
//            System.out.println(test + "," + map.get(test));
//        }
    }
    
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent)
    {
        System.out.println("end..." + httpSessionEvent.getSession().getId());
        HttpSession session = httpSessionEvent.getSession();
        myc.DelSession(session);
    }
}