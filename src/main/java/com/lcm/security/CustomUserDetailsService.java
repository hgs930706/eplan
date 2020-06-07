package com.lcm.security;

import auo.cim.uac.cli.util.json.JSONObject;
import com.lcm.domain.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Deprecated
@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Deprecated
    @Override
    public UserDetails loadUserByUsername(String username) {
        return null;
    }

}
