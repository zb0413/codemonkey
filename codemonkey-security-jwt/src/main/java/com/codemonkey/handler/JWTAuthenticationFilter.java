package com.codemonkey.handler;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.codemonkey.config.SecurityConstant;
import com.codemonkey.utils.ResponseUtil;
import com.codemonkey.utils.SysUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JWTAuthenticationFilter extends BasicAuthenticationFilter   {

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(SecurityConstant.HEADER);
        if (SysUtils.isEmpty(header) || !header.startsWith(SecurityConstant.TOKEN_SPLIT)) {
        	ResponseUtil.out(response, ResponseUtil.resultMap(false , 403 , "您没有权限"));
            //chain.doFilter(request, response);
            return;
        }
//        try {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(request, response);
            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }catch (Exception e){
//            throw e;
//        }

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse response) {

        String token = request.getHeader(SecurityConstant.HEADER);
        if (SysUtils.isNotEmpty(token)) {
            // 解析token
            Claims claims = null;
            try {
                claims = Jwts.parser()
                        .setSigningKey(SecurityConstant.JWT_SIGN_KEY)
                        .parseClaimsJws(token.replace(SecurityConstant.TOKEN_SPLIT, ""))
                        .getBody();

                //获取用户名
                String username = claims.getSubject();

                //获取权限
                List<GrantedAuthority> authorities = new ArrayList<>();
                String authority = claims.get(SecurityConstant.AUTHORITIES).toString();

                if(SysUtils.isNotEmpty(authority)){
                    List<String> list = new Gson().fromJson(authority, new TypeToken<List<String>>(){}.getType());
                    for(String ga : list){
                        authorities.add(new SimpleGrantedAuthority(ga));
                    }
                }
                if(SysUtils.isNotEmpty(username)) {
                    //此处password不能为null
                    User principal = new User(username, "", authorities);
                    return new UsernamePasswordAuthenticationToken(principal, null, authorities);
                }
            } catch (ExpiredJwtException e) {
    			throw new AuthenticationServiceException("登录已失效，请重新登录" , e); 
               //ResponseUtil.out(response, ResponseUtil.resultMap(false , 600 , "登录已失效，请重新登录"));
            } catch (Exception e){
            	 //ResponseUtil.out(response, ResponseUtil.resultMap(false , 500 , "解析token错误"));
            	throw new AuthenticationServiceException("其他未知错误" , e); 
            }
        }
        return null;
    }

}

