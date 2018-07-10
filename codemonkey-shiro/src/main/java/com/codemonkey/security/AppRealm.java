package com.codemonkey.security;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.codemonkey.domain.AppPermission;
import com.codemonkey.domain.AppUser;
import com.codemonkey.domain.UrlPermission;
import com.codemonkey.service.AppUserService;
import com.codemonkey.service.UrlPermissionService;

@Component
public class AppRealm extends AuthorizingRealm {
  
    @Autowired private AppUserService appUserService;
    @Autowired private UrlPermissionService urlPermissionService;
  
    /** 
     * 授权信息 
     */  
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {  
        
    	String username=(String)principals.fromRealm(getName()).iterator().next();  
        
    	if( username != null ){
        	AppUser user = appUserService.findBy("username" , username );
            if( user != null && user.getRoles() != null ){  
                SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
                if(user.isAdmin()){
                	List<UrlPermission> ps = urlPermissionService.findAll();
                	if(CollectionUtils.isNotEmpty(ps)){
                		for(UrlPermission p : ps){
                			info.addStringPermission(p.getPermission());
                		}
                	}
                }else{
                	for(AppPermission p : user.getPermissions()){
                    	info.addStringPermission(p.getPermission());
                    }
                }
                
                return info;  
            }  
        }  
        return null;  
    }  
  
    /** 
     * 认证信息 
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken ){  
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String userName = token.getUsername();  
        if( StringUtils.hasLength(userName)){  
            AppUser user = appUserService.findBy( "username", token.getUsername());  
            if( user != null ){
            	 return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(), getName());  
            }
        }
        return null;  
    }  
  
}  