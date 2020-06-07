package com.lcm.web.rest;
import auo.cim.uac.cli.UCLCtx;
import auo.cim.uac.cli.jspbean.UacAuthority;
import auo.cim.uac.cli.jspbean.UacFnValidator;
import com.lcm.domain.UserInfo;
import com.lcm.security.JwtUser;
import com.lcm.security.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@RequestMapping("/login")
@RestController
public class LoginController {

    private final AuthenticationManager authenticationManager;

    @Value("${index-url}")
    private String indexUrl;

    private final TokenProvider tokenProvider;

    @Value("${uac.path}")
    private String path;//uac加载文件路径

    public LoginController(AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @PostConstruct
    private void init() {
        try {
            String uacPath;
            if (!StringUtils.isEmpty(path)) {
                uacPath = path;
            } else {
                uacPath = getClass().getClassLoader().getResource("uac").toURI().getPath();
            }
            System.out.println("uac path ----> " + uacPath);
            UCLCtx.setConfigPath(uacPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/uacLogin")
    public void login(HttpServletRequest request, HttpServletResponse response)  throws IOException, ServletException {
        try{
            HttpSession session = request.getSession();
            UacFnValidator fnValidator = new UacFnValidator(request.getSession().getServletContext());
            fnValidator.initial(request, response);
            UacAuthority uacAuthority = fnValidator.getFunctions(session, "US");
            boolean isPass = uacAuthority.isPass();
            if(isPass){
                response.sendRedirect(indexUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/uacLogin")
    public void uacLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        try{
            UacFnValidator fnValidator = new UacFnValidator(request.getSession().getServletContext());
            fnValidator.initial(request, response);
            UacAuthority uacAuthority = fnValidator.getFunctions(session, "US");
            boolean isPass = uacAuthority.isPass();
            if(isPass){

                Map<String,String> map = new HashMap<>();
                String[] locations = uacAuthority.getLocations();//变成Site的选项
                for (String name : locations) {//获得roleMap
                    String[] name2 = uacAuthority.getFunctions(name);
                    for (String name3 : name2) {
                        map.put(name3,name3);

                    }
                }
                session.setAttribute("sites",locations);
                session.setAttribute("roleMap",map);
                session.setAttribute("user",uacAuthority);

                List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
                for (String key : map.keySet()){
                    list.add(new SimpleGrantedAuthority(key));
                }

                Authentication authentication = new UsernamePasswordAuthenticationToken(uacAuthority.getInputAccount(), "", list);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                response.sendRedirect(indexUrl);

            }
        } catch (Exception e) {
            try {
                session.invalidate();
                UacFnValidator fnValidator = new UacFnValidator(request.getSession().getServletContext());
                fnValidator.initial(request, response);
                fnValidator.getFunctions(session, "US");
            }catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }
    @GetMapping("/getUser")
    public  ResponseEntity<UserInfo> getUser(HttpSession session) throws IOException {
        UacAuthority uacAuthority = (UacAuthority)session.getAttribute("user");
        UserInfo user = new UserInfo();
        user.setUserId(uacAuthority.getUserId());
        user.setRealName(uacAuthority.getUserName());
        return ResponseEntity.ok(user);
    }
    @GetMapping("/loginOut")
    public void loginOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect(indexUrl);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, String>> UserLoginByCAP(String authToken) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authToken, "user");
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        String jwt = tokenProvider.createToken(authentication, true);
        Map<String, String> map = new HashMap();
        map.put("idToken", jwt);
        JwtUser user = (JwtUser) authentication.getPrincipal();
        map.put("realName", user.getRealName());
        map.put("employeeId", user.getEmployeeId());
        return ResponseEntity.ok(map);
    }
}
