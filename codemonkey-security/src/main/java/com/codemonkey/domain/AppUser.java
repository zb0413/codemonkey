package com.codemonkey.domain;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

import org.apache.tomcat.util.codec.binary.Base64;
//import org.apache.shiro.crypto.RandomNumberGenerator;
//import org.apache.shiro.crypto.SecureRandomNumberGenerator;
//import org.apache.shiro.crypto.hash.Sha256Hash;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import com.codemonkey.annotation.Label;
import com.codemonkey.annotation.SkipBuild;

@Entity
@Audited
@Getter
@Setter
public class AppUser extends AbsEE {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Label("用户名")
	private String username;
	
	@Label("邮箱")
	private String email;
	
	@Label("密码")
	@SkipBuild
	private String password;
	
	@Label("明文密码")
	@Transient
	private String rawPassword;
	
	@Label("盐值")
	private String salt;
	
	@Label("手机号码")
	private String workerMobilephone;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="app_user_roles", 
		joinColumns={@JoinColumn(name="app_user")},
		inverseJoinColumns={@JoinColumn(name="roles")})
	@NotAudited
	private Set<AppRole> roles = new HashSet<AppRole>();

	@ManyToOne
	@Label("用户组")
	private AppUserGroup appUserGroup;
	
	public static String generateSalt(){
		SecureRandom secureRandom = new SecureRandom();
		byte[] bytes = new byte[128];
		secureRandom.nextBytes(bytes);
		String salt = Base64.encodeBase64String(bytes);
		
		return salt;
	}
	
	public void addAppRole(AppRole appRole1) {
		roles.add(appRole1);
	}

}
