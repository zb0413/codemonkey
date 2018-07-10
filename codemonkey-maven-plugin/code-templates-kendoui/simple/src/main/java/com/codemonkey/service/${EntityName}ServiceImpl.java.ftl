package com.codemonkey.service.hospital;


import org.springframework.stereotype.Service;

import com.codemonkey.domain.${EntityName};
import com.codemonkey.service.LogicalServiceImpl;

@Service
public class ${EntityName}ServiceImpl extends LogicalServiceImpl<${EntityName}> implements ${EntityName}Service {

	@Override
	public ${EntityName} createEntity() {
		${EntityName} ${entityName} = new ${EntityName}();
		return ${entityName};
	}
	
}
