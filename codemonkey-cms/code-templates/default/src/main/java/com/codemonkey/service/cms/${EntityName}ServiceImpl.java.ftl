package com.codemonkey.service.${subPackage};


import org.springframework.stereotype.Service;

import ${entityFullName};
import com.codemonkey.service.LogicalServiceImpl;

@Service
public class ${EntityName}ServiceImpl extends LogicalServiceImpl<${EntityName}> implements ${EntityName}Service {

	@Override
	public ${EntityName} createEntity() {
		${EntityName} ${entityName} = new ${EntityName}();
		return ${entityName};
	}
	
}
