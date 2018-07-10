package com.codemonkey.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codemonkey.domain.FunctionNode;
import com.codemonkey.service.AppRoleService;
import com.codemonkey.service.FunctionNodeService;
import com.codemonkey.tree.ChildNode;
import com.codemonkey.tree.ParentNode;
import com.codemonkey.tree.TreeNode;
import com.codemonkey.utils.SysUtils;

@Controller
@RequestMapping("/ext/roleFunctionNode/**")
public class RoleFunctionNodeTreeController extends AbsTreeExtController<FunctionNode> {

	@Autowired private FunctionNodeService functionNodeService;
	@Autowired private AppRoleService appRoleService;
	
	@Override
	protected List<TreeNode> buildTreeNodes(HttpServletRequest request) {
		
		String idString = request.getParameter("id");
		String appRoleIdString = request.getParameter("appRoleId");
		
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		List<FunctionNode> list = null;
		if(SysUtils.isEmpty(idString)){
			list = service().findAllBy("parent.id_isNull");
		}else {
			Long id = Long.valueOf(idString);
			if(id < 0 ){
				list = service().findAllBy("parent.id_isNull");
			}else{
				list = service().findAllBy("parent.id", id);
			}
		}
		
		if(CollectionUtils.isNotEmpty(list)){
			if(SysUtils.isEmpty(appRoleIdString))
			{
				for(FunctionNode p : list){
					TreeNode node = null;
					
					long count = service().countBy("parent.id", p.getId());
					if(count > 0){
						node = new ParentNode();
						buildTreeNode(p , node);
						node.addAttr("checked", false);
						
					}else{
						node = new ChildNode();
						buildTreeNode(p , node);
						node.addAttr("checked", false);
					}
					nodes.add(node);
				}
			}
			else
			{
				Long roleId = Long.valueOf(appRoleIdString);
				for(FunctionNode p : list){
					TreeNode node = null;
					
					long count = service().countBy("parent.id", p.getId());
					if(count > 0){
						node = new ParentNode();
						node.addAttr("expanded", true);
						buildTreeNode(p , node);
						node.addAttr("checked", appRoleService.hasFunctionNode(roleId , p.getId()));
						
					}else{
						node = new ChildNode();
						buildTreeNode(p , node);
						node.addAttr("checked", appRoleService.hasFunctionNode(roleId , p.getId()));
					}
					nodes.add(node);
				}
			}
		}
		
		return nodes;
	}

	@Override
	protected FunctionNodeService service() {
		return functionNodeService;
	}

	@Override
	protected String getNodeText(JSONObject listJson) {
		return listJson.getString("name");
	}

}

