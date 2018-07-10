package com.codemonkey.utils;


public class ChineseUtils {

	private static final String ISO8859_1 = "ISO8859-1";

	private static final String GB2312 = "GB2312";

	private final static int[] li_SecPosValue = { 1601, 1637, 1833, 2078, 2274,
			2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858,
			4027, 4086, 4390, 4558, 4684, 4925, 5249, 5590 };

	private final static String[] lc_FirstLetter ={"A", "B","C","D","E",
			"F","G","H","J","K","L","M","N","O","P","Q","R",
			"S","T","W","X","Y","Z"};

	public static String getAllFirstLetter(String str) {
		if (str == null || str.trim().length() == 0) {
			return "";
		}

		String _str = "";
		for (int i = 0; i < str.length(); i++) {
			_str = _str + ChineseUtils.getFirstLetter(str.substring(i, i + 1));
		}

		return _str;
	}

	public static String getFirstLetter(String chinese) {

		if (chinese == null || chinese.trim().length() == 0) {
			return "";
		}

		chinese = ChineseUtils.conversionStr(chinese, GB2312, ISO8859_1);

		if (chinese.length() > 1) {

			int li_SectorCode = (int) chinese.charAt(0); // 汉字区码
			int li_PositionCode = (int) chinese.charAt(1); // 汉字位码
			li_SectorCode = li_SectorCode - 160;
			li_PositionCode = li_PositionCode - 160;
			int li_SecPosCode = li_SectorCode * 100 + li_PositionCode; // 汉字区位码

			if (li_SecPosCode > 1600 && li_SecPosCode < 5958) {
				for (int i = 0; i < 23; i++) {
					if (li_SecPosCode >= li_SecPosValue[i]
							&& li_SecPosCode < li_SecPosValue[i + 1]) {
						chinese = lc_FirstLetter[i];
						break;
					}
				}
			} else {
				chinese = ChineseUtils.conversionStr(chinese, ISO8859_1, GB2312);
				chinese = chinese.substring(0, 1);
			}
		}

		return chinese;
	}

	private static String conversionStr(String str, String fromCharset, String toCharset) {
		return SysUtils.switchCharset(str, fromCharset, toCharset);
	}
}
