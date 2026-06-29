package com.jiaolong.cm.common.core.util;

import lombok.experimental.UtilityClass;

/**
 * desc: 随机字符串工具类
 * user: pan
 * date: 2024-08-22 10:31
 */
@UtilityClass
public class RandomStringUtil {

	private final String NUMBERS = "0123456789";
	private final String TEXT = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private char[] numberCharSet = NUMBERS.toCharArray();
	private char[] charSet = TEXT.toCharArray();

	/**
	 * 生成长度为length的字符串, length > 0
	 *
	 * @param length
	 * @return
	 */
	public String random(int length) {
		if (length < 1) {
			return "";
		}
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int a = (int) (Math.random() * 62); // 0-1,等于0,不等于1
			str.append(charSet[a]);
		}
		return str.toString();
	}

	public String random6() {
		return random(6);
	}

	public String random18() {
		return random(18);
	}


	/**
	 * 生成长度在10以内的数字字符串,  > 0 < length < 10
	 *
	 * @param length
	 * @return
	 */
	public String randomNumberStr(int length) {
		if (length > 0 && length <= 10) {
			StringBuffer str = new StringBuffer();
			for (int i = 0; i < length; i++) {
				int a = (int) (Math.random() * 10); // 0-1,等于0,不等于1
				str.append(numberCharSet[a]);
			}
			return str.toString();
		}
		return "";
	}

	public String randomNumberStr6() {
		return randomNumberStr(6);
	}

}
