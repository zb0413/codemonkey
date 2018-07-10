package com.codemonkey.security;

import com.codemonkey.domain.IEnum;

public enum Operation implements IEnum{
	
	CREATE{
		@Override
		public String getText() {
			return "新建";
		}
	},
	DESTROY{
		@Override
		public String getText() {
			return "删除";
		}
	},
	UPDATE{
		@Override
		public String getText() {
			return "更新";
		}
	},
	READ{
		@Override
		public String getText() {
			return "读取";
		}
	},
	
	POST;
	
//	EDIT{
//		@Override
//		public RequestType getRequestType(){
//			return RequestType.HTML;
//		}
//	},
//	LIST{
//		@Override
//		public RequestType getRequestType(){
//			return RequestType.HTML;
//		}
//	},
//	NEW{
//		@Override
//		public RequestType getRequestType(){
//			return RequestType.HTML;
//		}
//	};

	public RequestType getRequestType(){
		return RequestType.JSON;
	}
	
	@Override
	public String getName() {
		return name();
	}

	@Override
	public String getText() {
		return name();
	}
	
}
