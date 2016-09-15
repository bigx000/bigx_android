/**
 * com.rightoo.util
 * StringUtil.java
 * 2014年10月29日 下午3:27:27
 * @author: z```s
 */
package com.bigx.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>字符串工具类</p>
 * 2014年10月29日 下午3:27:27
 * @author: z```s
 */
public class StringUtil {
	
	private static final char[] EMOJI_SOFTBANK_TABLE = { 0x21d4, 0x25b2, 0x25bc, 0x25cb, 0x25cf, 0x3013, 0xe001,
        0xe002, 0xe003, 0xe004, 0xe005, 0xe006, 0xe007, 0xe008, 0xe009, 0xe00a, 0xe00b, 0xe00c, 0xe00d, 0xe00e,
        0xe00f, 0xe010, 0xe011, 0xe012, 0xe013, 0xe014, 0xe015, 0xe016, 0xe017, 0xe018, 0xe019, 0xe01a, 0xe01b,
        0xe01c, 0xe01d, 0xe01e, 0xe01f, 0xe020, 0xe021, 0xe022, 0xe023, 0xe024, 0xe025, 0xe026, 0xe027, 0xe028,
        0xe029, 0xe02a, 0xe02b, 0xe02c, 0xe02d, 0xe02e, 0xe02f, 0xe030, 0xe031, 0xe032, 0xe033, 0xe034, 0xe035,
        0xe036, 0xe037, 0xe038, 0xe039, 0xe03a, 0xe03b, 0xe03c, 0xe03d, 0xe03e, 0xe03f, 0xe040, 0xe041, 0xe042,
        0xe043, 0xe044, 0xe045, 0xe046, 0xe047, 0xe048, 0xe049, 0xe04a, 0xe04b, 0xe04c, 0xe04d, 0xe04e, 0xe04f,
        0xe050, 0xe051, 0xe052, 0xe053, 0xe054, 0xe055, 0xe056, 0xe057, 0xe058, 0xe059, 0xe05a, 0xe101, 0xe102,
        0xe103, 0xe104, 0xe105, 0xe106, 0xe107, 0xe108, 0xe109, 0xe10a, 0xe10b, 0xe10c, 0xe10d, 0xe10e, 0xe10f,
        0xe110, 0xe111, 0xe112, 0xe113, 0xe114, 0xe115, 0xe116, 0xe117, 0xe118, 0xe119, 0xe11a, 0xe11b, 0xe11c,
        0xe11d, 0xe11e, 0xe11f, 0xe120, 0xe121, 0xe122, 0xe123, 0xe124, 0xe125, 0xe126, 0xe127, 0xe128, 0xe129,
        0xe12a, 0xe12b, 0xe12c, 0xe12d, 0xe12e, 0xe12f, 0xe130, 0xe131, 0xe132, 0xe133, 0xe134, 0xe135, 0xe136,
        0xe137, 0xe138, 0xe139, 0xe13a, 0xe13b, 0xe13c, 0xe13d, 0xe13e, 0xe13f, 0xe140, 0xe141, 0xe142, 0xe143,
        0xe144, 0xe145, 0xe146, 0xe147, 0xe148, 0xe149, 0xe14a, 0xe14b, 0xe14c, 0xe14d, 0xe14e, 0xe14f, 0xe150,
        0xe151, 0xe152, 0xe153, 0xe154, 0xe155, 0xe156, 0xe157, 0xe158, 0xe159, 0xe15a, 0xe201, 0xe202, 0xe203,
        0xe204, 0xe205, 0xe206, 0xe207, 0xe208, 0xe209, 0xe20a, 0xe20b, 0xe20c, 0xe20d, 0xe20e, 0xe20f, 0xe210,
        0xe211, 0xe212, 0xe213, 0xe214, 0xe215, 0xe216, 0xe217, 0xe218, 0xe219, 0xe21a, 0xe21b, 0xe21c, 0xe21d,
        0xe21e, 0xe21f, 0xe220, 0xe221, 0xe222, 0xe223, 0xe224, 0xe225, 0xe226, 0xe227, 0xe228, 0xe229, 0xe22a,
        0xe22b, 0xe22c, 0xe22d, 0xe22e, 0xe22f, 0xe230, 0xe231, 0xe232, 0xe233, 0xe234, 0xe235, 0xe236, 0xe237,
        0xe238, 0xe239, 0xe23a, 0xe23b, 0xe23c, 0xe23d, 0xe23e, 0xe23f, 0xe240, 0xe241, 0xe242, 0xe243, 0xe244,
        0xe245, 0xe246, 0xe247, 0xe248, 0xe249, 0xe24a, 0xe24b, 0xe24c, 0xe24d, 0xe24e, 0xe24f, 0xe250, 0xe251,
        0xe252, 0xe253, 0xe301, 0xe302, 0xe303, 0xe304, 0xe305, 0xe306, 0xe307, 0xe308, 0xe309, 0xe30a, 0xe30b,
        0xe30c, 0xe30d, 0xe30e, 0xe30f, 0xe310, 0xe311, 0xe312, 0xe313, 0xe314, 0xe315, 0xe316, 0xe317, 0xe318,
        0xe319, 0xe31a, 0xe31b, 0xe31c, 0xe31d, 0xe31e, 0xe31f, 0xe320, 0xe321, 0xe322, 0xe323, 0xe324, 0xe325,
        0xe326, 0xe327, 0xe328, 0xe329, 0xe32a, 0xe32b, 0xe32c, 0xe32d, 0xe32e, 0xe32f, 0xe330, 0xe331, 0xe332,
        0xe333, 0xe334, 0xe335, 0xe336, 0xe337, 0xe338, 0xe339, 0xe33a, 0xe33b, 0xe33c, 0xe33d, 0xe33e, 0xe33f,
        0xe340, 0xe341, 0xe342, 0xe343, 0xe344, 0xe345, 0xe346, 0xe347, 0xe348, 0xe349, 0xe34a, 0xe34b, 0xe34c,
        0xe34d, 0xe401, 0xe402, 0xe403, 0xe404, 0xe405, 0xe406, 0xe407, 0xe408, 0xe409, 0xe40a, 0xe40b, 0xe40c,
        0xe40d, 0xe40e, 0xe40f, 0xe410, 0xe411, 0xe412, 0xe413, 0xe414, 0xe415, 0xe416, 0xe417, 0xe418, 0xe419,
        0xe41a, 0xe41b, 0xe41c, 0xe41d, 0xe41e, 0xe41f, 0xe420, 0xe421, 0xe422, 0xe423, 0xe424, 0xe425, 0xe426,
        0xe427, 0xe428, 0xe429, 0xe42a, 0xe42b, 0xe42c, 0xe42d, 0xe42e, 0xe42f, 0xe430, 0xe431, 0xe432, 0xe433,
        0xe434, 0xe435, 0xe436, 0xe437, 0xe438, 0xe439, 0xe43a, 0xe43b, 0xe43c, 0xe43d, 0xe43e, 0xe43f, 0xe440,
        0xe441, 0xe442, 0xe443, 0xe444, 0xe445, 0xe446, 0xe447, 0xe448, 0xe449, 0xe44a, 0xe44b, 0xe44c, 0xe501,
        0xe502, 0xe503, 0xe504, 0xe505, 0xe506, 0xe507, 0xe508, 0xe509, 0xe50b, 0xe50c, 0xe50d, 0xe50e, 0xe50f,
        0xe510, 0xe511, 0xe512, 0xe513, 0xe514, 0xe515, 0xe516, 0xe517, 0xe518, 0xe519, 0xe51a, 0xe51b, 0xe51c,
        0xe51d, 0xe51e, 0xe51f, 0xe520, 0xe521, 0xe522, 0xe523, 0xe524, 0xe525, 0xe526, 0xe527, 0xe528, 0xe529,
        0xe52a, 0xe52b, 0xe52c, 0xe52d, 0xe52e, 0xe52f, 0xe530, 0xe531, 0xe532, 0xe533, 0xe534, 0xe535, 0xe536,
        0xe537, 0xff5e, 0xffe5 };
	
	private static final Set<Character> EMOJI_SET = new HashSet<Character>();
	
	static {
        for (int i = 0, len = EMOJI_SOFTBANK_TABLE.length; i < len; i++) {
            EMOJI_SET.add(EMOJI_SOFTBANK_TABLE[i]);
        }
    }
	/**
	 * <p>判断是否为空</p>
	 * @param arg
	 * @return
	 * 2014年10月29日 下午3:32:51
	 * @author: z```s
	 */
	public static boolean isNullOrEmpty(String arg) {
		boolean rel = false;
		if (arg == null || arg.isEmpty()) {
			rel = true;
		}
		return rel;
	}
	
	/**
	 * <p>验证手机号</p>
	 * @param phoneNo
	 * @return
	 * 2014年10月29日 下午3:28:24
	 * @author: z```s
	 */
	public static boolean isPhoneNo(String phoneNo) {
		if (isNullOrEmpty(phoneNo)) {
			return false;
		}
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(phoneNo);
        return m.matches();
	}
	
	/**
	 * <p>验证邮箱</p>
	 * @param email
	 * @return
	 * 2014年10月29日 下午3:29:33
	 * @author: z```s
	 */
	public static boolean isEmail(String email) {
		if (isNullOrEmpty(email)) {
			return false;
		}
		String regex = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        return m.matches();
	}
	
	/**
	 * <p>验证是否为纯数字</p>
	 * @param number
	 * @return
	 * 2014年10月29日 下午3:33:26
	 * @author: z```s
	 */
	public static boolean isNumber(String number) {
		if (isNullOrEmpty(number)) {
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(number).matches();
	}
	
	/**
	 * <p></p>
	 * @param source
	 * @return
	 * 2015年8月4日 下午3:12:52
	 * @author: z```s
	 * @version 1.0
	 */
	public static boolean containsEmoji(String source) {
		if (isNullOrEmpty(source)) {
			return false;
		}
		for (int i = 0, len = source.length(); i < len; i++) {
			char code = source.charAt(i);
			if (isEmojiCharacter(code)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * <p></p>
	 * @param c
	 * @return
	 * 2015年8月4日 下午3:12:55
	 * @author: z```s
	 * @version 1.0
	 */
	public static boolean isEmojiCharacter(char c) {
		return EMOJI_SET.contains(c);
	}
	
	/**
	 * <p></p>
	 * @param source
	 * @return
	 * 2015年8月4日 下午3:39:42
	 * @author: z```s
	 */
	public static String filterEmoji(String source) {
		if (!containsEmoji(source)) {
			return source;
		}
		int len = source.length();
		StringBuilder builder = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char code = source.charAt(i);
			if (!isEmojiCharacter(code)) {
				builder.append(code);
			}
		}
		return builder.toString();
	}
	
}
