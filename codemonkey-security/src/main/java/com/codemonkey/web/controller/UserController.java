package com.codemonkey.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codemonkey.config.SecurityConfig;
import com.codemonkey.domain.AppUser;
import com.codemonkey.domain.FunctionNode;
import com.codemonkey.domain.TreeEntity;
import com.codemonkey.service.AppRoleService;
import com.codemonkey.service.AppUserService;
import com.codemonkey.service.FunctionNodeService;
import com.codemonkey.tree.TreeUtils;
import com.codemonkey.utils.ResultUtil;
import com.codemonkey.utils.SysUtils;
import com.codemonkey.vo.Result;


@RestController
@Api("用户接口")
@RequestMapping("/xboot/user")
public class UserController {

	@Autowired
    private SecurityConfig securityConfig;
	
    @Autowired
    private AppUserService userService;

    @Autowired
    private AppRoleService roleService;
    
    @Autowired
    private FunctionNodeService functionNodeService;


    @RequestMapping(value = "/info",method = RequestMethod.GET)
    @ApiOperation(value = "获取当前登录用户接口")
    public Result<AppUser> getUserInfo(){

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser u = userService.findBy("username" , user.getUsername());
        u.setPassword(null);
        return new ResultUtil<AppUser>().setData(u);
    }
    
    @RequestMapping(value = "/unlock",method = RequestMethod.POST)
    @ApiOperation(value = "解锁验证密码")
    public Result<Object> unLock(@RequestParam String password){

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser u = userService.findBy("username" , user.getUsername());
        if(!securityConfig.getPasswordEncoder().matches(password, u.getPassword())){
            return new ResultUtil<Object>().setErrorMsg("密码不正确");
        }
        return new ResultUtil<Object>().setData(null);
    }
    
    @RequestMapping(value = "/getMenuList/{userId}",method = RequestMethod.GET)
    @ApiOperation(value = "获取用户页面菜单数据")
    public Result<JSONObject> getAllMenuList(@PathVariable String userId){

        //用户所有权限 已排序去重
        List<FunctionNode> list = functionNodeService.findAll();
        
        List<TreeEntity<?>> tempList = new ArrayList<TreeEntity<?>>();
        
        if(SysUtils.isNotEmpty(list)){
        	for(FunctionNode fn : list ){
        		tempList.add(fn);
        	}
        }
        
        JSONObject jo = TreeUtils.buildTree(tempList);

        return new ResultUtil<JSONObject>().setData(jo);
    }
}
