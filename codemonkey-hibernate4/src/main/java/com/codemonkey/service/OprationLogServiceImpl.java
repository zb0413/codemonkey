package com.codemonkey.service;

import java.util.Date;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.codemonkey.domain.OprationLog;
import com.codemonkey.error.FieldValidation;
import com.codemonkey.error.FormFieldValidation;
import com.codemonkey.utils.SysUtils;

@Service
public class OprationLogServiceImpl extends PhysicalServiceImpl<OprationLog> implements OprationLogService{

	@Override
	public OprationLog createEntity() {
		return new OprationLog();
	}
	
	@Override
	protected Set<FieldValidation> validate(OprationLog entity) {
		Set<FieldValidation> set = super.validate(entity);
		
		if(entity.getAppUser() == null && SysUtils.isEmpty(entity.getUserName())){
			set.add(new FormFieldValidation("user" , "操作人不能为空" ));
		}
		if(SysUtils.isEmpty(entity.getOprationType())){
			set.add(new FormFieldValidation("oprationType" , "操作类型不能为空" ));
		}
		if(entity.getOprationTime() == null ){
			set.add(new FormFieldValidation("oprationTime" , "操作时间不能为空" ));
		}
		if(SysUtils.isEmpty(entity.getMsg())){
			set.add(new FormFieldValidation("colIndex" , "操作描述不能为空" ));
		}
		if(SysUtils.isEmpty(entity.getEntityId())){
			set.add(new FormFieldValidation("entityId" , "操作实体ID不能为空" ));
		}
		return set;
	}

	/**
	 * 
	 */
	@Override
	public void saveLog(String oprationType , String entityId, String msg) {
		OprationLog oprationLog = new OprationLog();
		oprationLog.setUserName(SysUtils.getAttribute("CUR_LOGIN_USER_NAME").toString());
		oprationLog.setOprationType(oprationType);
		oprationLog.setEntityId(entityId);
		oprationLog.setOprationTime(new Date());
		oprationLog.setMsg(msg);
		save(oprationLog);
	}

	@Override
	public String getLogMessage(JSONObject oldInfo, JSONObject newInfo, String name, String desc, Boolean flag) {
		if(oldInfo == null){//新建信息
			if(flag){
				return "‘"+desc+"’ ："+ newInfo.get(name)+"; <p>";
			}else if( SysUtils.getString(newInfo, name) != null){
				return "‘"+desc+"’ ："+ newInfo.get(name)+"; <p>";
			}
		}else{//修改信息
			if(flag){
				if(!oldInfo.get(name).equals(newInfo.get(name))){   
					return "‘"+desc+"’ 更改为："+ newInfo.get(name)+"; <p>";
				}
			}else{
				if(SysUtils.getString(oldInfo, name)  != null){
					if( SysUtils.getString(newInfo, name) == null || !SysUtils.getString(oldInfo, name).equals(SysUtils.getString(newInfo, name))){
						return "‘"+desc+"’ 更改为："+ newInfo.get(name)+"; <p>";
					}
				}else if(oldInfo.get(name) == null && newInfo.get(name) != null){
					return "‘"+desc+"’ 更改为："+ newInfo.get(name)+"; <p>";
				}
			}
		}
		return "";
	}
}
