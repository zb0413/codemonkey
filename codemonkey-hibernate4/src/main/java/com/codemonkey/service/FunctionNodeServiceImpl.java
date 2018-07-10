package com.codemonkey.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.codemonkey.domain.AppRole;
import com.codemonkey.domain.FunctionNode;
import com.codemonkey.error.FieldValidation;
import com.codemonkey.error.FormFieldValidation;
import com.codemonkey.utils.SysUtils;

@Primary
@Service
public class FunctionNodeServiceImpl extends LogicalServiceImpl<FunctionNode> implements FunctionNodeService{

	@Override
	public FunctionNode createEntity() {
		return new FunctionNode();
	}
	
	@Override
	protected Set<FieldValidation> validate(FunctionNode entity) {
		Set<FieldValidation> set = super.validate(entity);
		FunctionNode parent = entity.getParent();
		if(parent != null && SysUtils.isNotEmpty(parent.getViewClass())){
			set.add(new FormFieldValidation("parent" , "父节点不能为最底层的功能菜单！"));
		}
		return set;
	}
	
	@Override
	public List<FunctionNode> getFunctionNodesByRoles(List<AppRole> roles) {
		return getFunctionNodesByRoles(roles , null);
	}

	@Override
	public List<FunctionNode> getFunctionNodesByRoles(List<AppRole> roles, Long rootId) {
		
		StringBuffer hql = new StringBuffer() ;
		List<Object> params = new ArrayList<Object>();
		
		if(SysUtils.isEmpty(roles)){
			return null;
		}	
		hql.append("select FN from AppRole R inner join R.functionNodes FN where FN.delFlg = ?0 and R.id in (");
		
		params.add(Boolean.FALSE);
		
		for(int i = 0 ; i < roles.size() ; i++){
			Long roleid = (Long) roles.get(i).getId();
			if(i == roles.size()-1){
				hql.append(" ?");
				hql.append((i+1));
				hql.append(" )");
			}else{
				hql.append(" ?");
				hql.append((i+1));
				hql.append(" ,");
			}
			params.add(roleid);
		}
		
		hql.append(" order by FN.sortIndex asc ");
		
		List<FunctionNode> functionNodes = list(hql.toString() , params.toArray());
		
		return functionNodes;
		
	}
	
	@Override
	public void save(FunctionNode entity) {
		if(SysUtils.isNotEmpty(entity.getCode())){
			super.save(entity);
			return;
		}
		
		FunctionNode fn = entity.getParent();
		if(fn != null){
			String code = fn.getCode();
			if(code == null){
				code = "";
			}
			long count = countBy("parent.id&&code_isNotNull", fn.getId());
			count++;
			code += SysUtils.formatNumber(count , "00");
			entity.setCode(code);
		}else{
			long count = countBy("parent_isNull");
			count++;
			String code = "FN" + SysUtils.formatNumber(count , "00");
			entity.setCode(code);
		}
		getDao().save(entity);
	}
	
}
