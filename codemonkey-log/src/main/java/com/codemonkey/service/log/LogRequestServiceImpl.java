package com.codemonkey.service.log;

import java.lang.reflect.Method;
import java.util.Date;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codemonkey.annotation.LogOp;
import com.codemonkey.domain.AppUser;
import com.codemonkey.domain.LogReqParam;
import com.codemonkey.domain.LogRequest;
import com.codemonkey.domain.OpResultStatus;
import com.codemonkey.service.LogicalServiceImpl;
import com.codemonkey.utils.OgnlConvertUtils;
import com.codemonkey.utils.SysUtils;

@Service
public class LogRequestServiceImpl extends LogicalServiceImpl<LogRequest> implements LogRequestService {

	@Autowired
	private LogReqParamService logReqParamService;
	
	@Override
	public LogRequest createEntity() {
		LogRequest logRequest = new LogRequest();
		return logRequest;
	}

	@Override
	public void saveLogRequest(JoinPoint joinPoint , Throwable throwable) {
		//目标类
		Class<?> target = joinPoint.getTarget().getClass();
		//方法
		Method opMethod = null;
		//方法参数类型
		Class<?>[] paramTypes = null;
		
		try {
			Method[] opMethods = target.getMethods();
			if(opMethods!=null && opMethods.length>0){
				for(Method m : opMethods){
					if(m.getName().equals(joinPoint.getSignature().getName())){
						paramTypes = m.getParameterTypes();
					}
				 }
			}
			//通过操作方法名与方法参数类型取得操作方法
			opMethod = target.getMethod(joinPoint.getSignature().getName() , paramTypes);
			if(opMethod != null){
				AppUser appUser = SysUtils.getCurrentUser();
				LogRequest logRequest = new LogRequest();
				logRequest.setOpUser(appUser);
				logRequest.setOpTime(new Date());
				logRequest.setOpClass(target.getName());
				if(throwable == null){
					logRequest.setResult(OpResultStatus.成功);
				}else{
					logRequest.setResult(OpResultStatus.失败);
					logRequest.setDescription(throwable.getMessage());
				}

				//操作类方法名
				logRequest.setClsMethod(opMethod.getName());
				//方法描述
				LogOp logAnnotation =  opMethod.getAnnotation(LogOp.class);
				if(logAnnotation != null){
					logRequest.setClsMedDesc(logAnnotation.methodDescption());
				}
				//保存操作日志
				save(logRequest);

				//方法参数名
				String[] paramNames = getParamNames(opMethod , target);
				//方法参数值
				Object[]  values = joinPoint.getArgs();

				//保存操作日志参数
				for(int i = 0 ; i < values.length ; i++){
					LogReqParam logReqParam = new LogReqParam();
					if(paramNames!=null && paramNames.length>0){
						logReqParam.setParam(paramNames[i]);
						logReqParam.setType(paramTypes[i].toString());
						logReqParam.setValue(OgnlConvertUtils.stringValue(values[i]));
					}
					logReqParam.setLogRequest(logRequest);
					logReqParamService.save(logReqParam);
				}
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		
	}

	private String[] getParamNames(Method m , Class<?> target) {
		
		String[] paramNames = null;
		try {
			ClassPool pool = ClassPool.getDefault();
			CtClass cc = pool.get(target.getName());
			CtMethod cm = cc.getDeclaredMethod(m.getName());
			// 使用javaassist的反射方法获取方法的参数名
			MethodInfo methodInfo = cm.getMethodInfo();
			CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
			LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
			paramNames = new String[cm.getParameterTypes().length];
			int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
			for (int i = 0; i < paramNames.length; i++){
				paramNames[i] = attr.variableName(i + pos);
			}

		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		
		return paramNames;
	}
	
}
