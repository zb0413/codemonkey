package com.codemonkey.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.codemonkey.domain.BaseDictionary;
import com.codemonkey.error.FieldValidation;
import com.codemonkey.error.FormFieldValidation;
import com.codemonkey.utils.SysUtils;

@Service
public class BaseDictionaryServiceImpl extends
		LogicalServiceImpl<BaseDictionary> implements BaseDictionaryService {

	@Override
	public BaseDictionary createEntity() {
		return new BaseDictionary();
	}

	@Override
	protected Set<FieldValidation> validate(BaseDictionary baseDictionary) {
		Set<FieldValidation> set = super.validate(baseDictionary);

		if (SysUtils.isEmpty(baseDictionary.getFieldType())) {
			set.add(new FormFieldValidation("fieldType", "分组名称不能为空！"));
		}
		if (SysUtils.isEmpty(baseDictionary.getName())) {
			set.add(new FormFieldValidation("name", "显示名称不能为空！"));
		}
		if (SysUtils.isEmpty(baseDictionary.getCodeValue())) {
			set.add(new FormFieldValidation("codeValue", "码值名称不能为空！"));
		}

//		if (baseDictionary.getId() == null
//				&& isUnique(baseDictionary, "fieldType",
//						baseDictionary.getFieldType())) {
//			set.add(new FormFieldValidation("fieldType", "只能新建或更新已有分组名称的数据！"));
//		}

		if (!isUnique(baseDictionary, "fieldType&&name",
				baseDictionary.getFieldType(), baseDictionary.getName())) {
			set.add(new FormFieldValidation("name", "同分组下的显示名称不能相同！(分组 ‘"
					+ baseDictionary.getFieldType() + "’ 的显示名称 ‘"
					+ baseDictionary.getName() + "’)"));
		}

		if (!isUnique(baseDictionary, "fieldType&&codeValue",
				baseDictionary.getFieldType(), baseDictionary.getCodeValue())) {
			set.add(new FormFieldValidation("codeValue", "同分组下的码值不能相同！(分组‘"
					+ baseDictionary.getFieldType() + "’ 的码值 ‘"
					+ baseDictionary.getCodeValue() + "’)"));
		}

		return set;
	}

	@Override
	public Long getId(String codeValue, String fieldType) {

		BaseDictionary d = findBy("codeValue&&fieldType", codeValue, fieldType);

		if (d != null) {
			return d.getId();
		}

		return null;
	}

	@Override
	public BaseDictionary get(String codeValue, String fieldType) {
		return findBy("codeValue&&fieldType", codeValue, fieldType);
	}
	
	@Override
	public BaseDictionary getBycode(String code, String fieldType) {
		return findBy("code&&fieldType", code, fieldType);
	}

}
