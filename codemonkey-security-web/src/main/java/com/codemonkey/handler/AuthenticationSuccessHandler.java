package com.codemonkey.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.codemonkey.config.SecurityConfig;

/**
 * 登录成功处理类
 */
@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired private SecurityConfig securityConfig;
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

    	super.onAuthenticationSuccess(request, response, authentication);
//        //用户选择保存登录状态几天
//        String saveTime = request.getParameter(SecurityConstant.SAVE_LOGIN);
//        if(StrUtil.isNotBlank(saveTime)&&Boolean.valueOf(saveTime)){
//            tokenExpireTime = saveLoginTime * 60 * 24;
//        }
//        String username = ((UserDetails)authentication.getPrincipal()).getUsername();
//        List<GrantedAuthority> list = (List<GrantedAuthority>) ((UserDetails)authentication.getPrincipal()).getAuthorities();
//        List<String> authorities = new ArrayList<>();
//        for(GrantedAuthority g : list){
//            authorities.add(g.getAuthority());
//        }
//        //登陆成功生成JWT
//        String token = Jwts.builder()
//                //主题 放入用户名
//                .setSubject(username)
//                //自定义属性 放入用户拥有权限
//                .claim(SecurityConstant.AUTHORITIES, new Gson().toJson(authorities))
//                //失效时间
//                .setExpiration(new Date(System.currentTimeMillis() + tokenExpireTime * 60 * 1000))
//                //签名算法和密钥
//                .signWith(SignatureAlgorithm.HS512, SecurityConstant.JWT_SIGN_KEY)
//                .compact();
//        token = SecurityConstant.TOKEN_SPLIT + token;
//
//        ResponseUtil.out(response, ResponseUtil.resultMap(true,200,"登录成功", token));
    	
    	
//    		request.getRequestDispatcher(securityConfig.getHomeUrl()).forward(request, response);
//    		getRedirectStrategy().sendRedirect(request, response, securityConfig.getHomeUrl());
//    		response.sendRedirect(securityConfig.getHomeUrl());
    }
}
