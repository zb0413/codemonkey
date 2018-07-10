package com.codemonkey.domain;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.json.JSONArray;
import org.json.JSONObject;

import com.codemonkey.annotation.Label;
import com.codemonkey.annotation.SkipBuild;

@Entity
@Audited
@Label("角色表")
public class AppRole extends AbsEE {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="app_role_url_permissions", 
		joinColumns={@JoinColumn(name="app_role")},
		inverseJoinColumns={@JoinColumn(name="url_permissions")})
	@NotAudited
	@SkipBuild
	private Set<UrlPermission> urlPermissions = new HashSet<UrlPermission>();
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="app_role_cmp_permissions", 
		joinColumns={@JoinColumn(name="app_role")},
		inverseJoinColumns={@JoinColumn(name="cmp_permissions")})
	@NotAudited
	@SkipBuild
	private Set<CmpPermission> cmpPermissions = new HashSet<CmpPermission>();
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="app_role_function_node", 
		joinColumns={@JoinColumn(name="app_role")},
		inverseJoinColumns={@JoinColumn(name="function_node")})
	@NotAudited
	@SkipBuild
	private Set<FunctionNode> functionNodes = new HashSet<FunctionNode>();
	
	@Override
	public JSONObject detailJson() {
		JSONObject jo = super.detailJson();
		JSONArray urlPermissionsJa = new JSONArray();
		if(CollectionUtils.isNotEmpty(urlPermissions)){
			for(AppPermission p : urlPermissions){
				urlPermissionsJa.put(p.detailJson());
			}
		}
		jo.put("urlPermissions", urlPermissionsJa);
		
		JSONArray cmpPermissionsJa = new JSONArray();
		if(CollectionUtils.isNotEmpty(cmpPermissions)){
			for(AppPermission p : cmpPermissions){
				cmpPermissionsJa.put(p.detailJson());
			}
		}
		jo.put("cmpPermissions", cmpPermissionsJa);
		
		JSONArray functionNodesJa = new JSONArray();
		if(CollectionUtils.isNotEmpty(functionNodes)){
			for(FunctionNode p : functionNodes){
				functionNodesJa.put(p.detailJson());
			}
		}
		jo.put("functionNodes", functionNodesJa);
		
		return jo;
	}
	
	public Set<String> getPermissions() {
		Set<String> permissions = new HashSet<String>();
		if(urlPermissions == null || urlPermissions.isEmpty()) {
			return permissions;
		}
		
		Iterator<UrlPermission> it = urlPermissions.iterator();
		while(it.hasNext()){
			UrlPermission ap = it.next();
			permissions.add(ap.getPermission());
		}
		return permissions;
	}
	
	public Set<UrlPermission> getUrlPermissions() {
		return urlPermissions;
	}

	public void addUrlPermission(UrlPermission urlPermission) {
		urlPermissions.add(urlPermission);
	}

	public void addCmpPermission(CmpPermission cmpPermission) {
		cmpPermissions.add(cmpPermission);
	}
	
	public void addFunctionNode(FunctionNode functionNode) {
		functionNodes.add(functionNode);
	}

	
	public void clearUrlPermissions() {
		if(CollectionUtils.isNotEmpty(urlPermissions)){
			urlPermissions.clear();
		}
	}

	public void clearCmpPermissions() {
		if(CollectionUtils.isNotEmpty(cmpPermissions)){
			cmpPermissions.clear();
		}
	}
	
	public void clearFunctionNodes() {
		if(CollectionUtils.isNotEmpty(functionNodes)){
			functionNodes.clear();
		}
	}
	
	public Set<CmpPermission> getCmpPermissions(){
		return cmpPermissions;
	}

	public Set<FunctionNode> getFunctionNodes() {
		return functionNodes;
	}

	public void setFunctionNodes(Set<FunctionNode> functionNodes) {
		this.functionNodes = functionNodes;
	}
	
}
