package com.codemonkey.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.codemonkey.annotation.Label;
import com.codemonkey.annotation.SkipBuild;

import lombok.Getter;
import lombok.Setter;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Table(indexes={
	@Index(columnList = "code" , name="code_index"),
	@Index(columnList = "name" , name="name_index")
})
@Getter
@Setter
public class AbsEntity<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(generator="pkgenerator") 
    @GenericGenerator(name="pkgenerator", strategy = "increment") 
    @Label("主键")
    @SkipBuild
    private T id;
	
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
	
	@Label("删除标记")
	@SkipBuild
	@Audited
	@Type(type="true_false")
	private Boolean delFlg = false;
	
	@SkipBuild
	@Label("创建人用户名")
	@CreatedBy
	private String createdBy;
	
	@SkipBuild
	@Label("创建日期")
	@CreatedDate
	private Date creationDate;
	
	@SkipBuild
	@Label("修改日期")
	@LastModifiedDate
	private Date modificationDate;
	
	@SkipBuild
	@Label("修改人用户名")
	@LastModifiedBy
	private String modifiedBy;
	
	public boolean isNew(){
		return getId() == null;
	}
	
	public boolean equals(IEntity<T> obj){
		
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
	
}
