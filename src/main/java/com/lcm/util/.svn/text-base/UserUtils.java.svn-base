package com.lcm.util;

import auo.cim.uac.cli.jspbean.UacAuthority;
import com.lcm.security.JwtUser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class UserUtils {

    public static JwtUser currentUser() {
        JwtUser user = new JwtUser();
        user.setEmployeeId("");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (request.getSession().getAttribute("user") != null) {
            user.setEmployeeId(((UacAuthority) request.getSession().getAttribute("user")).getUserId());
        }
        return user;
    }
}
