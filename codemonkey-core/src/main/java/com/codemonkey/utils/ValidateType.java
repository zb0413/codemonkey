package com.codemonkey.utils;

import org.apache.commons.lang.StringUtils;

/**
 * 类描述：正则验证类型
 */
public enum ValidateType {
	NOT_BLANK {
		@Override
		public boolean validate(String value) {
			return StringUtils.isNotBlank(value);
		}

		@Override
		public String getMessage() {
			return "不能为空";
		}
	},
	EMAIL {
		@Override
		public boolean validate(String value) {
			if (StringUtils.isNotBlank(value)) {
				return RegUtils.matches(RegUtils.EMAIL_REG, value);
			}
			return true;
		}

		@Override
		public String getMessage() {
			return "邮箱格式不正确";
		}
	},
	ID_CARD {
		@Override
		public boolean validate(String value) {
			if (StringUtils.isNotBlank(value)) {
				return RegUtils.matches(RegUtils.ID_CARD_REG, value);
			}
			return true;
		}

		@Override
		public String getMessage() {
			return "身份证号不正确";
		}
	},
	MOBILE_PHONE {
		@Override
		public boolean validate(String value) {
			if (StringUtils.isNotBlank(value)) {
				return RegUtils.matches(RegUtils.MOBILE_PHONE_REG, value);
			}
			return true;
		}

		@Override
		public String getMessage() {
			return "手机号格式不正确";
		}
	},
	PHONE {
		@Override
		public boolean validate(String value) {
			if (StringUtils.isNotBlank(value)) {
				return RegUtils.matches(RegUtils.NUM_SPE_REG, value);
			}
			return true;
		}

		@Override
		public String getMessage() {
			return "号码格式不正确";
		}
	},
	FIX_PHONE {
		@Override
		public boolean validate(String value) {
			if (StringUtils.isNotBlank(value)) {
				return RegUtils.matches(RegUtils.FIX_PHONE_REG, value);
			}
			return true;
		}

		@Override
		public String getMessage() {
			return "固定电话格式不正确(如：0411-88888888)";
		}
	},
	CONTACT_PHONE {
		@Override
		public boolean validate(String value) {
			return MOBILE_PHONE.validate(value) || FIX_PHONE.validate(value);
		}

		@Override
		public String getMessage() {
			return "联系电话格式不正确，请填写手机号或固定电话(如：0411-88888888)";
		}
	},
	GROUP_PHONE {
		@Override
		public boolean validate(String value) {
			if (StringUtils.isNotBlank(value)) {
				return RegUtils.matches(RegUtils.GROUP_PHONE_REG, value);
			}
			return true;
		}

		@Override
		public String getMessage() {
			return "集团电话格式不正确";
		}
	},
	EXTENSION_PHONE {
		@Override
		public boolean validate(String value) {
			if (StringUtils.isNotBlank(value)) {
				return RegUtils.matches(RegUtils.EXTENSION_PHONE_REG, value);
			}
			return true;
		}

		@Override
		public String getMessage() {
			return "分机电话格式不正确";
		}
	},
	NUM_LETTER_UNDERLINE {
		@Override
		public boolean validate(String value) {
			if (StringUtils.isNotBlank(value)) {
				return RegUtils.matches(RegUtils.NUM_LETTER_UNDERLINE_REG,
						value);
			}
			return true;
		}

		@Override
		public String getMessage() {
			return "必须为数字，字母或下划线";
		}
	},
	NUM_LETTER {
		@Override
		public boolean validate(String value) {
			if (StringUtils.isNotBlank(value)) {
				return RegUtils.matches(RegUtils.NUM_LETTER_REG, value);
			}
			return true;
		}

		@Override
		public String getMessage() {
			return "必须为数字或字母";
		}
	},
	CAPITAL_LETTER {
		@Override
		public boolean validate(String value) {
			if (StringUtils.isNotBlank(value)) {
				return RegUtils.matches(RegUtils.CAPITAL_LETTER_REG, value);
			}
			return true;
		}

		@Override
		public String getMessage() {
			return "必须为大写英文字母";
		}
	},
	NON_REAL_NUM {
		@Override
		public boolean validate(String value) {
			if (StringUtils.isNotBlank(value)) {
				return RegUtils.matches(RegUtils.NON_REAL_NUM_REG, value);
			}
			return true;
		}

		@Override
		public String getMessage() {
			return "必须为非负实数";
		}
	},
	NON_INT {
		@Override
		public boolean validate(String value) {
			if (StringUtils.isNotBlank(value)) {
				return RegUtils.matches(RegUtils.INT_REG, value);
			}
			return true;
		}

		@Override
		public String getMessage() {
			return "必须为非负整数";
		}
	},
	POS_INT {
		@Override
		public boolean validate(String value) {
			if (StringUtils.isNotBlank(value)) {
				return RegUtils.matches(RegUtils.POS_INT_REG, value);
			}
			return true;
		}

		@Override
		public String getMessage() {
			return "请输入正整数";
		}
	},
	IP {
		@Override
		public boolean validate(String value) {
			if (StringUtils.isNotBlank(value)) {
				return RegUtils.matches(RegUtils.IP_REG, value);
			}
			return true;
		}

		@Override
		public String getMessage() {
			return "ip地址格式错误";
		}
	},
	CHINESE {
		@Override
		public boolean validate(String value) {
			if (StringUtils.isNotBlank(value)) {
				return RegUtils.matches(RegUtils.CHINESE_REG, value);
			}
			return true;
		}

		@Override
		public String getMessage() {
			return "请输入中文";
		}
	},
	POSTCODE {
		@Override
		public boolean validate(String value) {
			if (StringUtils.isNotBlank(value)) {
				return RegUtils.matches(RegUtils.POSTCODE_REG, value);
			}
			return true;
		}

		@Override
		public String getMessage() {
			return "邮政编码格式错误";
		}
	},
	REGULARSTR {
		@Override
		public boolean validate(String value) {
			if (StringUtils.isNotBlank(value)) {
				if (value.length() > 255) {
					return false;
				} else {
					return true;
				}
			}
			return true;
		}

		@Override
		public String getMessage() {
			return "字符长度超出范围";
		}
	},
	REGULARNUM {
		@Override
		public boolean validate(String value) {
			if (StringUtils.isNotBlank(value)) {
				boolean bln = POS_INT.validate(value);
				if(!bln){
					return false;
				}
				bln = REGULARSTR.validate(value);
				if(!bln){
					return false;
				}
				if (Long.parseLong(value.trim()) > Integer.MAX_VALUE) {
					return false;
				} else {
					return true;
				}
			}
			return true;
		}

		@Override
		public String getMessage() {
			return "整数超出范围";
		}
	}
	;

	public abstract boolean validate(String value);

	public abstract String getMessage();

}
