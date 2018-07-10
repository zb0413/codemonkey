package com.codemonkey.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.json.JSONObject;

import com.codemonkey.annotation.Label;
import com.codemonkey.annotation.SkipBuild;
import com.codemonkey.domain.seq.SequenceCreator;
import com.codemonkey.utils.OgnlUtils;

@MappedSuperclass
@Table(indexes={
	@Index(columnList = "code" , name="code_index"),
	@Index(columnList = "name" , name="name_index")
})
public class AbsEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(generator="pkgenerator") 
    @GenericGenerator(name="pkgenerator", strategy = "increment") 
    @Label("主键")
    @SkipBuild
    private Long id;
	
    @Label("编码")
    @Audited
    private String code;
    
    @Label("名称")
    @Audited
	private String name;
	
    @Label("描述")
    @Audited
    @Lob
	private String description;
	
	@Version
	@Label("版本")
	private Integer version;
	
	@SkipBuild
	@Temporal(TemporalType.TIMESTAMP)
	@Audited
	@Label("创建日期")
	private Date creationDate;
	
	@SkipBuild
	@Audited
	@Label("创建人用户名")
	private String createdBy;
	
	@SkipBuild
	@Audited
	@Label("创建人id")
	private Long createdByUserId;
	
	@Label("修改日期")
	@SkipBuild
	@Temporal(TemporalType.TIMESTAMP)
	@Audited
	private Date modificationDate;
	
	@Label("修改人用户名")
	@SkipBuild
	@Audited
	private String modifiedBy;
	
	@Label("修改人id")
	@SkipBuild
	@Audited
	private Long modifiedByUserId;
	
	@Label("删除标记")
	@SkipBuild
	@Audited
	@Type(type="true_false")
	private Boolean delFlg = false;
	
	public SequenceCreator getSequenceCreator() {
		return null;
	}
	
	public boolean isNew(){
		return getId() == null;
	}
	
	public JSONObject listJson() {
		JSONObject jo = new JSONObject();
		jo.put("id", OgnlUtils.stringValue("id", this));
		jo.put("version", OgnlUtils.stringValue("version", this));
		jo.put("code", OgnlUtils.stringValue("code", this));
		jo.put("name", OgnlUtils.stringValue("name", this));
		jo.put("description", OgnlUtils.stringValue("description", this));
		jo.put("creationDate", OgnlUtils.stringValue("creationDate", this));
		jo.put("modificationDate", OgnlUtils.stringValue("modificationDate", this));
		jo.put("createdBy", OgnlUtils.stringValue("createdBy", this));
		jo.put("modifiedBy", OgnlUtils.stringValue("modifiedBy", this));
		jo.put("delFlg", OgnlUtils.stringValue("delFlg", this));
		return jo;
	}
	
	public boolean equals(IEntity obj){
		
		if(!this.getClass().equals(obj.getClass())){
			return false;
		}
		
		if(this.isNew() || obj.isNew()){
			return false;
		}
		
		if(getId().equals(obj.getId())){
			return true;
		}
		
		return false;
	}
	
	public JSONObject detailJson(){
		return listJson();
	}
	
	public String getSyncNamingSpace(){
		return null;
	}
	
	public void setCreatedUser(AppUser user) {
		if(user != null){
			this.setCreatedBy(user.getUsername());
			this.setCreatedByUserId(user.getId());
		}
	}

	public void setModifiedUser(AppUser user) {
		if(user != null){
			this.setModifiedBy(user.getUsername());
			this.setModifiedByUserId(user.getId());
		}
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
	    return description;
	}
	
	public void setDescription(String description) {
	    this.description = description;
	}
	
	public Integer getVersion() {
		return version;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(Boolean delFlg) {
		this.delFlg = delFlg;
	}

	public Long getCreatedByUserId() {
		return createdByUserId;
	}

	public void setCreatedByUserId(Long createdByUserId) {
		this.createdByUserId = createdByUserId;
	}

	public Long getModifiedByUserId() {
		return modifiedByUserId;
	}

	public void setModifiedByUserId(Long modifiedByUserId) {
		this.modifiedByUserId = modifiedByUserId;
	}
	
}
