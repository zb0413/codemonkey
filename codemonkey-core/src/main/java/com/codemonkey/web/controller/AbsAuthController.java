package com.codemonkey.web.controller;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codemonkey.SysProp;
import com.codemonkey.domain.AppRole;
import com.codemonkey.domain.AppUser;
import com.codemonkey.error.SysError;
import com.codemonkey.security.VerifyOsStartUp;
import com.codemonkey.service.AppUserService;
import com.codemonkey.service.AuthService;
import com.codemonkey.utils.ExtConstant;
import com.codemonkey.utils.SysUtils;

public abstract class AbsAuthController extends AbsExtController<AppUser>{

	protected static final String LOGOUT = "logout";

	protected static final String LOGIN = "login";

	protected static final String UNAUTHORIZED = "unauthorized";

	protected static final String SIGNUP = "signup";
	
	protected static final String SSO = "sso";
	
	protected static final String HOME = "home";
	
	public static final String LAST_VISIT_URL = "LAST_VISIT_URL";
	
	public static final String LAST_VISIT_PARAMS = "LAST_VISIT_PARAMS";
	
	private Logger log = SysUtils.getLog(AbsAuthController.class);
	
	@Autowired private AppUserService appUserService;
	
	@Autowired private SysProp sysProp;
	
	@Autowired private AuthService authService;
	
	@RequestMapping(SSO)
	public String sso(HttpServletRequest req , ModelMap map) {

		String username = authService.ssoLogin();
		
		AppUser user = appUserService.findBy("username", username);
    	SysUtils.putAttribute(SysUtils.CURRENCT_USER , user);
    	req.getSession().setAttribute(SysUtils.CURRENCT_USER, user);
    	req.getSession().setAttribute(ExtConstant.CURRENT_USER_ID, user.getId()+"");
    	req.getSession().setAttribute(ExtConstant.DEV_MODE, sysProp.getDevMode());
    	
    	req.setAttribute("module", sysProp.getDefaultModule());
    	updateSessionAttributes(req);
		
    	return HOME;
	}
	
	@RequestMapping(HOME)
    public String home(HttpServletRequest req , ModelMap map) {
		
		String homeView = getHomeView();
		updateSessionAttributes(req);
		
		if(SysUtils.isNotEmpty(homeView)){
			return homeView;
		}
		
        return homeView;
    }

	protected String getHomeView() {
		return HOME;
	}
	
	@RequestMapping(UNAUTHORIZED)
    public String unauthorized() {
        return UNAUTHORIZED;
    }
	
	@RequestMapping(LOGIN)
	public String login(
			@RequestParam(required = false) String username , 
			@RequestParam(required = false) String password , 
			@RequestParam(required = false) String module , 
			ModelMap model,
			HttpServletRequest req,
			RedirectAttributes ra){
		
		boolean osVerifyFlg = false;
		log.debug(username + ": " + password);
		
		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password) ){
			 return SIGNUP;
		}

		// 系统登陆验证处理
		if("prod".equals(sysProp.getDevMode().toLowerCase())){
			List<String> list = VerifyOsStartUp.getAllMacAddresses();
			if(list.contains(sysProp.getOsVerifyCode().toLowerCase())){
				osVerifyFlg = true;
			}
			if(!osVerifyFlg){
				return "verifyError";
			}
		}
		
		AppUser user = doLogin(username, password, module, model, req, ra);
		
		if(user == null){
    		req.setAttribute("errorMsg", "用户名或密码错误");
    		return SIGNUP;
    	}
    	
		return afterLoginView(req.getSession());
	}
	
	@RequestMapping("ajaxLogin")
	@ResponseBody
	public String ajaxLogin(
			@RequestParam(required = false , defaultValue="") String username , 
			@RequestParam(required = false , defaultValue="") String password , 
			@RequestParam(required = false) String module , 
			ModelMap model,
			HttpServletRequest req,
			RedirectAttributes ra){
		
		doLogin(username , password , module , model , req , ra);
		AppUser user = appUserService.findBy("username", username);
    	
		if (user != null) {
			return success();
		}
		
		return error("用户名或密码错误");
	}
	
	public AppUser doLogin( String username ,  String password , String module , 
			ModelMap model,
			HttpServletRequest req,
			RedirectAttributes ra){
		
    	boolean flag = authService.doLogin(username , password);
    	
    	if(flag){
    		AppUser user = appUserService.findBy("username", username);
        	
    		if (user != null) {
    			
    			SysUtils.putAttribute(SysUtils.CURRENCT_USER , user);
    	    	req.getSession().setAttribute(SysUtils.CURRENCT_USER, user);
    	    	req.getSession().setAttribute(ExtConstant.CURRENT_USER_ID, user.getId()+"");
    	    	req.getSession().setAttribute(ExtConstant.CURRENT_USERNAME, user.getUsername());
    	    	req.getSession().setAttribute(ExtConstant.DEV_MODE, sysProp.getDevMode());
    			req.getSession().setAttribute("LOGIN_DATE", SysUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
    			Set<AppRole> appRoles = user.getRoles();
    			StringBuilder sb = new StringBuilder();
    			for (AppRole appRole : appRoles) {
    				sb.append(appRole.getName());
    				sb.append(",");
    			}
    			req.getSession().setAttribute("CURRENT_USER_ROLE", sb.toString());
    			
    		}
    		
        	if(StringUtils.isEmpty(module)){
        		req.setAttribute("module", sysProp.getDefaultModule());
        	}else{
        		req.setAttribute("module", module);
        	}
        	
        	updateSessionAttributes(req);
        	
        	SysUtils.syncToSysUtils(req);
        	
        	return user;
    	}
    	
    	return null;
	}
	
	protected String afterLoginView(HttpSession session) {
		String url = (String)session.getAttribute(AbsAuthController.LAST_VISIT_URL);
		if(url != null){
			return "redirect:" + url;
		}
		return "redirect:" + sysProp.getSuccessUrl();
	}

	@RequestMapping(LOGOUT)
	public String logout(RedirectAttributes ra , HttpSession session){
		
		authService.doLogout();
//		session.invalidate();

		return toLogoutView();
	}

	protected String toLogoutView() {
		return "redirect:" + sysProp.getLoginUrl();
	}
	
	@RequestMapping("changePassword")
	@ResponseBody
	public String changePassword(ServletRequest request){
			
		JSONObject params = parseJson(request.getParameterMap());
		
		if(!params.has("id") || StringUtils.isBlank(params.getString("id"))){
			AppUser user = SysUtils.getCurrentUser();
			params.put("id", user.getId());
		}
		
		AppUser t = appUserService.get(SysUtils.getCurrentUser().getId());
			
		boolean needOldPassword = params.getBoolean("needOldPassword");
		if(needOldPassword){
			if(!params.has("oldPassword")){
				throw new SysError("旧密码不正确");
			}
			String oldPassword =  params.getString("oldPassword");
			if(!t.getPassword().equals(authService.encryptPassword(oldPassword , t.getSalt()))){
				throw new SysError("旧密码不正确");
			}
		}
		
		authService.doChangePassword(t , params.getString("password"));

		return result(t);
	}
	
	
	@Override
	protected AppUserService service() {
		return appUserService;
	}
}
