package com.bocom.util;

import com.bocom.dto.session.SessionUserInfo;
import com.bocom.dto.session.UserRoleInfo;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bocom-qy on 2017-3-17.
 */
public class UserUtils {

    private static Logger logger = LoggerFactory
            .getLogger(UserUtils.class);


    public static String getUserId(HttpSession session) {
        SessionUserInfo sessionUserInfo = null;
        if (session.getAttribute("sessionUserInfo") != null) {
            sessionUserInfo =
                    (SessionUserInfo) session.getAttribute("sessionUserInfo");
        }
        if (sessionUserInfo == null) {
            return "";
        }
        return String.valueOf(sessionUserInfo.getUserId());
    }

    public static String getUserName(HttpSession session) {
        SessionUserInfo sessionUserInfo = null;
        if (session.getAttribute("sessionUserInfo") != null) {
            sessionUserInfo =
                    (SessionUserInfo) session.getAttribute("sessionUserInfo");
        }
        if (sessionUserInfo == null) {
            return "";
        }
        return String.valueOf(sessionUserInfo.getUserName());
    }

    public static List<String> getUserRoleCode(HttpSession session) {
        List<String> roleList = new ArrayList<>();
        SessionUserInfo sessionUserInfo = null;
        if (session.getAttribute("sessionUserInfo") != null) {
            sessionUserInfo =
                    (SessionUserInfo) session.getAttribute("sessionUserInfo");
        }
        if (sessionUserInfo == null) {
            return roleList;
        }
        for (UserRoleInfo userRoleInfo : sessionUserInfo.getUserRoles()) {
            if (!StringUtils.isEmpty(userRoleInfo.getRoleCode())) {
                roleList.add(userRoleInfo.getRoleCode());
            }
        }
        return roleList;
    }
    
    
    public static boolean isAdmin(HttpSession session) {
        List<String> roleList = new ArrayList<>();
        SessionUserInfo sessionUserInfo = null;
        if (session.getAttribute("sessionUserInfo") != null) {
            sessionUserInfo =
                    (SessionUserInfo) session.getAttribute("sessionUserInfo");
        }
        if (sessionUserInfo == null) {
            return false;
        }
        for (UserRoleInfo userRoleInfo : sessionUserInfo.getUserRoles()) {
            if (!StringUtils.isEmpty(userRoleInfo.getRoleCode())) {
                if(userRoleInfo.getRoleCode().equals("1")){
                	return true;
                }
            }
        }
        return false;
    }
}
