package co.daoting.common;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.crypto.Cipher;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.*;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author wangyf
 * 
 */
public class CommonUtils {

	private static final char[] chars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
			'V', 'W', 'X', 'Y', 'Z' };
	private static final int SIZE = 6;
	
	/**
	 * 检查路径是否存在，不存在则创建路径
	 * 
	 * @param path
	 */
	public static void checkPath(String path) {
		String[] paths = null;
		if (path.contains("/")) {
//			paths = path.split(File.separator);
			paths = path.split("/");
		} else {
			paths = path.split(File.separator + File.separator);
		}
		if (paths == null || paths.length == 0) {
			return;
		}
		String pathdir = "";
		for (String string : paths) {
			pathdir = pathdir + string + File.separator;
			File file = new File(pathdir);
			if (!file.exists()) {
				file.mkdir();
			}
		}
	}

	/**
	 * 判断String是否为空
	 * 
	 */
	public static boolean isEmptyString(String value) {
		if (value == null || value.length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 只判断多个String是否为空(无论有没全角或半角的空格) 若非空则返回true,否则返回false
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isEmptyAllString(String... str) {
		if (null == str) {
			return true;
		}
		for (String s : str) {
			if (isEmptyString(s)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * String -> int
	 * 
	 * @param string
	 * @return int
	 */
	public static int parseInt(String string, int def) {
		if (isEmptyString(string)) {
			return def;
		}
		int num = def;
		try {
			num = Integer.parseInt(string);
		} catch (Exception e) {
			num = def;
		}
		return num;
	}

	/**
	 * String -> short
	 * 
	 * @param string
	 * @return int
	 */
	public static short parseShort(String string, short def) {
		if (isEmptyString(string)) {
			return def;
		}
		short num = def;
		try {
			num = Short.parseShort(string);
		} catch (Exception e) {
			num = def;
		}
		return num;
	}

	/**
	 * String -> long
	 * 
	 * @param string
	 * @return long
	 */
	public static long parseLong(String string, long def) {
		if (isEmptyString(string)) {
			return def;
		}
		long num;
		try {
			num = Long.parseLong(string);
		} catch (Exception e) {
			System.out.println(string);
			e.printStackTrace();
			num = def;
		}
		return num;
	}

	/**
	 * String -> byte
	 *
	 * @param string
	 * @return long
	 */
	public static byte parseByte(String string, byte def) {
		if (isEmptyString(string)) {
			return def;
		}
		byte num;
		try {
			num = Byte.parseByte(string);
		} catch (Exception e) {
			System.out.println(string);
			e.printStackTrace();
			num = def;
		}
		return num;
	}

	/**
	 * String -> double
	 * 
	 * @param string
	 * @return long
	 */
	public static double parseDouble(String string, double def) {
		if (isEmptyString(string)) {
			return def;
		}
		double num;
		try {
			num = Double.parseDouble(string);
		} catch (Exception e) {
			num = def;
		}
		return num;
	}

	/**
	 * String -> float
	 * 
	 * @param string
	 * @return long
	 */
	public static float parseFloat(String string, float def) {
		if (isEmptyString(string)) {
			return def;
		}
		float num;
		try {
			num = Float.parseFloat(string);
		} catch (Exception e) {
			System.out.println(string);
			e.printStackTrace();
			num = def;
		}
		return num;
	}

	/**
	 * String -> float
	 * 
	 * @param string
	 * @return long
	 */
	public static float parseFloat(String string, float def, int digit) {
		if (isEmptyString(string)) {
			return def;
		}
		float num;
		try {
			num = Float.parseFloat(string);
		} catch (Exception e) {
			num = def;
		}
		if (digit > 0 && num != def) {
			BigDecimal bigDecimal = new BigDecimal(num);
			float twoDecimalP = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
			return twoDecimalP;
		} else {
			return num;
		}
	}

	/**
	 * @param date
	 * @param string
	 * @return
	 */
	public static String getTimeFormat(Date date, String string) {
		SimpleDateFormat sdFormat;
		if (isEmptyString(string)) {
			sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else {
			sdFormat = new SimpleDateFormat(string);
		}
		try {
			return sdFormat.format(date);
		} catch (Exception e) {
			return "";
		}
	}

	public static String getLongToString(String longTime) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(Long.valueOf(longTime));
		return time;
	}

	/**
	 * 格式化时间 String转换Date "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param date
	 * @param format
	 * @return 格式化之后的时间
	 */
	public static Date getDateFormat(String date, String format) {
		if (isEmptyString(date)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static int hasNext(List<?> a) {
		if (a != null && a.size() > 0) {
			return 1;
		}
		return 0;
	}

	/**
	 * 过滤字符串中的可能存在XSS攻击的非法字符
	 * 
	 * @param value
	 * @return
	 */
	public static String cleanXSS(String value) {
		value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
		value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
		value = value.replaceAll("'", "& #39;");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		value = value.replaceAll("script", "");
		value = value.replaceAll(" ", "");
		return value;
	}

	/**
	 * 获取随机6位验证码
	 * 
	 * @return
	 */
	public static String getRandomVcode() {
		Random random = new Random();
		String result = "";
		for (int i = 0; i < 6; i++) {
			result += random.nextInt(10);
		}
		return result;
	}

	/**
	 * 计算地球上任意两点(经纬度)距离
	 * 
	 * @param long1
	 *            第一点经度
	 * @param lat1
	 *            第一点纬度
	 * @param long2
	 *            第二点经度
	 * @param lat2
	 *            第二点纬度
	 * @return 返回距离 单位：米
	 */
	public static double Distance(double long1, double lat1, double long2, double lat2) {
		double a, b, R;
		R = 6378137; // 地球半径
		lat1 = lat1 * Math.PI / 180.0;
		lat2 = lat2 * Math.PI / 180.0;
		a = lat1 - lat2;
		b = (long1 - long2) * Math.PI / 180.0;
		double d;
		double sa2, sb2;
		sa2 = Math.sin(a / 2.0);
		sb2 = Math.sin(b / 2.0);
		d = 2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));
		return d;
	}


	/**
	 * 判断是否是今天
	 * @param date
	 * @return
	 */
	public static final Boolean IsNowDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date zero = calendar.getTime();
		if (zero.getTime()>date.getTime()) {
			return false;
		}
		return true;
	}



	/**
	 * 将传入的时间转换成 **小时前 ***几天前的格式
	 * 
	 * @param date
	 * @return
	 */
	public static final String getTimeDiff(Date date) {
		Calendar currentDate = Calendar.getInstance();// 获取当前时间
		long diff = currentDate.getTimeInMillis() - date.getTime();
		if (diff <= 0) {
			return 1 + "秒钟前";
		} else if (diff < 60000) {
			int second = (int) (diff / 1000);
			if (second == 0) {
				return 1 + "秒钟前";
			} else {
				return second + "秒钟前";
			}

		} else if (diff < 3600000) {
			int minute = (int) (diff / 60000);
			if (minute == 0) {
				return 1 + "分钟前";
			} else {
				return minute + "分钟前";
			}

		} else if (diff < 86400000) {
			int hour = (int) (diff / 3600000);
			if (hour == 0) {
				hour = 1;
			}
			return hour + "小时前";
		} else if (diff < 864000000) {
			int day = (int) (diff / 86400000);
			if (day == 0) {
				day = 1;
			}
			return day + "天前";
		} // 十天以内

		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdFormat.format(date);
	}

	public static boolean checkSession(String accesstoken, String userToken) {
		return !CommonUtils.isEmptyString(accesstoken) && accesstoken.equals(userToken);
	}

	public static int getAge(Date startDate, Date endDate) {
		if (startDate == null) {
			return 0;
		}
		Calendar calendarStart = Calendar.getInstance();
		Calendar calendarEnd = Calendar.getInstance();
		calendarStart.setTime(startDate);
		calendarEnd.setTime(endDate);
		int endYear = calendarEnd.get(Calendar.YEAR);
		int startYear = calendarStart.get(Calendar.YEAR);
		return endYear - startYear;

	}

	/**
	 * 获得该月第一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getFirstDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		// 设置年份
		cal.set(Calendar.YEAR, year);
		// 设置月份
		cal.set(Calendar.MONTH, month);
		// 获取某月最小天数
		int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最小天数
		cal.set(Calendar.DAY_OF_MONTH, firstDay);
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String firstDayOfMonth = sdf.format(cal.getTime());
		return firstDayOfMonth;
	}

	/**
	 * 获得该月最后一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		// 设置年份
		cal.set(Calendar.YEAR, year);
		// 设置月份
		cal.set(Calendar.MONTH, month);
		// 获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String lastDayOfMonth = sdf.format(cal.getTime());
		return lastDayOfMonth;
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}
	
	/**
	 * 计算两个日期之间相差的时间
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int timesBetween(Date smdate, Date bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = time2 - time1;

		return Integer.parseInt(String.valueOf(between_days));
	}
	
	public static String addDate(String start,int num){
		try {
			Date d= getDateFormat(start, "yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			c.add(Calendar.DAY_OF_MONTH, num);
			return getTimeFormat(c.getTime(), "yyyy-MM-dd");
		} catch (Exception e) {
			return null;
		}
	}

	public Date parse(String strDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(strDate);
	}

	// 由出生日期获得年龄
	public static int getAge(Date birthDay) throws Exception {
		if (birthDay == null) {
			return 0;
		}

		Calendar cal = Calendar.getInstance();

		if (cal.before(birthDay)) {
			throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
		}
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				age--;
			}
		}
		return age;
	}

	public static String getTimeDiff(String startTime, String endTime) {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date begin;
		Date end;
		String str = "";
		try {
			begin = dfs.parse(startTime);
			end = dfs.parse(endTime);
			long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
			long day1 = between / (24 * 3600);
			long hour1 = between % (24 * 3600) / 3600;
			long minute1 = between % 3600 / 60;
			long second1 = between % 60 / 60;
			str += day1 + "天" + hour1 + "小时" + minute1 + "分" + second1 + "秒";
		} catch (ParseException e) {

			e.printStackTrace();

		}
		return str;
	}

	/**
	 * 获取当前时间 yyyyMMddHHmmss
	 *
	 * @return String
	 */
	public static String getCurrTime() {
		Date now = new Date();
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String s = outFormat.format(now);
		return s;
	}

	/**
	 * 取出一个指定长度大小的随机正整数.
	 *
	 * @param length
	 *            int 设定所取出随机数的长度。length小于11
	 * @return int 返回生成的随机数。
	 */
	public static int buildRandom(int length) {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return (int) ((random * num));
	}

	/**
	 * MD5加密
	 *
	 * @param s
	 * @return 加密后String(大写)
	 */
	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			// 使用MD5创建MessageDigest对象
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte b = md[i];
				str[k++] = hexDigits[b >> 4 & 0xf];
				str[k++] = hexDigits[b & 0xf];
			}
			return new String(str).toUpperCase();
		} catch (Exception e) {
			return null;
		}
	}


	/**
	 * 获取请求所有参数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String,String> getAllParams(HttpServletRequest request) {
		Map<String,String> map = new HashMap<String,String>();
		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();

			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				if (paramValue.length() != 0) {
					map.put(paramName, paramValue);
				}
			}
		}
		return map;
	};

	public static String caculTime2(Date date) {
		String time = "";
		try {
			long diff = System.currentTimeMillis() - date.getTime();
			if (diff > 0) {
				long day = diff / (3600 * 1000 * 24);
				long hour = (diff - day * (3600 * 1000 * 24)) / (3600 * 1000);
				long minute = (diff - day * (3600 * 1000 * 24) - hour * (3600 * 1000)) / (60 * 1000);
				if (hour < 1 && day < 1) {
					// 一小时内
					if (minute == 0) {
						time = "刚刚";
					} else {
						time = minute + "分钟前";
					}
				} else if (hour >= 1 && day < 1) {
					// 一天内
					time = hour + "小时前";
				} else {
					// 大于一周
					time = getTimeFormat(date, "yyyy-MM-dd");
				}
			} else {
				time = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}

	public static String caculTime2(Date date,String format) {
		String time = "";
		try {
			long diff = System.currentTimeMillis() - date.getTime();
			if (diff > 0) {
				long day = diff / (3600 * 1000 * 24);
				long hour = (diff - day * (3600 * 1000 * 24)) / (3600 * 1000);
				long minute = (diff - day * (3600 * 1000 * 24) - hour * (3600 * 1000)) / (60 * 1000);
				if (hour < 1 && day < 1) {
					// 一小时内
					if (minute == 0) {
						time = "刚刚";
					} else {
						time = minute + "分钟前";
					}
				} else if (hour >= 1 && day < 1) {
					// 一天内
					time = hour + "小时前";
				} else {
					// 大于一周
					time = getTimeFormat(date, format);
				}
			} else {
				time = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}

	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 *
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map doXMLParse(String strxml) throws Exception {
		if (null == strxml || "".equals(strxml)) {
			return null;
		}

		Map m = new HashMap();
		InputStream in = String2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List children = e.getChildren();
			if (children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = getChildrenText(children);
			}

			m.put(k, v);
		}

		// 关闭流
		in.close();

		return m;
	}

	/**
	 * 获取子结点的xml
	 *
	 * @param children
	 * @return String
	 */
	@SuppressWarnings("rawtypes")
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if (!children.isEmpty()) {
			Iterator it = children.iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if (!list.isEmpty()) {
					sb.append(getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}

		return sb.toString();
	}

	public static InputStream String2Inputstream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}

	/**
	 * 获取用户当前请求的IP地址
	 *
	 * @param request
	 * @return
	 */

	public static String getIpAddr(HttpServletRequest request) {
		String ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		// "***.***.***.***".length()
		if (ipAddress != null && ipAddress.length() > 15) {
			// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

	/**
	 * 获取请求路径和参数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getFullURLWithParam(HttpServletRequest request) {
		String requestUrl = request.getRequestURL().toString() + "?";
		Map<String, String[]> map = request.getParameterMap();
		Set<Entry<String, String[]>> keSet = map.entrySet();
		for (Iterator<Entry<String, String[]>> itr = keSet.iterator(); itr.hasNext();) {
			Entry<String, String[]> me = itr.next();
			String key = me.getKey(); // 获取参数名
			String value = me.getValue()[0]; // 获取参数值
			requestUrl = requestUrl + key + "=" + value + "&";
		}
		try {
			requestUrl = URLEncoder.encode(requestUrl, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return requestUrl;
	}

	public static String removeStartZero(String str) {
		if(CommonUtils.isEmptyString(str)){
			return null;
		}
		String newStr = str.replaceAll("^(0+)", "");
		return newStr;
	}

	public static String getvenderMinDate(String date,String format){
		return  getTimeFormat(getDateFormat(date, format), "yyyyMMddHHmmss");
	}

	public static String getvenderDayDate(String date,String format){
		return  getTimeFormat(getDateFormat(date, format), "yyyyMMdd");
	}

	  public static String sendPost(String url, String param) {
	        PrintWriter out = null;
	        BufferedReader in = null;
	        String result = "";
	        try {
	            URL realUrl = new URL(url);
	            // 打开和URL之间的连接
	            URLConnection conn = realUrl.openConnection();
	            // 设置通用的请求属性
	            conn.setRequestProperty("accept", "*/*");
	            conn.setRequestProperty("connection", "Keep-Alive");
	            conn.setRequestProperty("user-agent",  "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            conn.setRequestProperty("Accept-Charset", "utf-8");
	            conn.setRequestProperty("contentType", "utf-8");
	            // 发送POST请求必须设置如下两行
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            // 获取URLConnection对象对应的输出流
	            out = new PrintWriter(conn.getOutputStream());
	            // 发送请求参数
	            out.print(param);
	            // flush输出流的缓冲
	            out.flush();
	            // 定义BufferedReader输入流来读取URL的响应
	            in = new BufferedReader(new InputStreamReader(
	            		conn.getInputStream(), "utf-8"));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line  + "\n";
	            }
	        } catch (Exception e) {
	            System.out.println("发送 POST 请求出现异常！"+e);
	            e.printStackTrace();
	        }
	        //使用finally块来关闭输出流、输入流
	        finally{
	            try{
	                if(out!=null){
	                    out.close();
	                }
	                if(in!=null){
	                    in.close();
	                }
	            }
	            catch(IOException ex){
	                ex.printStackTrace();
	            }
	        }
	        return result;
	    }

	  /**
		 * 发起https请求
		 *
		 * @param requestUrl
		 *            请求地址
		 * @param requestMethod
		 *            请求方式（GET、POST）
		 * @param outputStr
		 *            提交的数据
		 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
		 */
		public static JSONObject httpRequest(String requestUrl, String requestMethod,
                                             String outputStr) {
			JSONObject jsonObject = null;
			StringBuffer buffer = new StringBuffer();
			try {
				// 创建SSLContext对象
				TrustManager[] tm = { (TrustManager) new MyX509TrustManager() };
				SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
				sslContext.init(null, tm, new java.security.SecureRandom());
				URL url = new URL(requestUrl);
				URLConnection httpUrlConn = url.openConnection();
				httpUrlConn.setDoOutput(true);
				httpUrlConn.setDoInput(true);
				httpUrlConn.setUseCaches(false);
				// 设置请求方式（GET/POST）
				if ("GET".equalsIgnoreCase(requestMethod)) {
					httpUrlConn.connect();
				}
				// 当有数据需要提交时
				if (null != outputStr) {
					OutputStream outputStream = httpUrlConn.getOutputStream();
					// 编码格式
					outputStream.write(outputStr.getBytes("UTF-8"));
					outputStream.close();
				}
				// 将返回的输入流转换成字符串
				InputStream inputStream = httpUrlConn.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String str = null;
				while ((str = bufferedReader.readLine()) != null) {
					buffer.append(str);
				}
				bufferedReader.close();
				inputStreamReader.close();
				// 释放资源
				inputStream.close();
				jsonObject = JSONObject.parseObject(buffer.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return jsonObject;
		}

		public static String getRandomReqNo() {
	          int length=10;
	         String base = "abcdefghijklmnopqrstuvwxyz0123456789";
	         Random random = new Random();
	         StringBuffer sb = new StringBuffer();
	         for (int i = 0; i < length; i++) {
	             int number = random.nextInt(base.length());
	             sb.append(base.charAt(number));
	         }
	         return sb.toString();
	     }

		public static String createCode() {
			StringBuffer sb = new StringBuffer();
			Random ran = new Random();
			for (int i = 0; i < SIZE; i++) {
				// 取随机字符索引
				int n = ran.nextInt(chars.length);
				sb.append(chars[n]);
			}
			return sb.toString();
		}

	public static Map<String, Object> validate(Errors errors) {
		Map<String, Object> errorMap = new HashMap<>();
		// 获取错误列表
		List<ObjectError> oes = errors.getAllErrors();
		for (ObjectError oe : oes) {
			String key = null;
			String msg = null;
			// 字段错误
			if (oe instanceof FieldError) {
				FieldError fe = (FieldError) oe;
				key = fe.getField();// 获取错误验证字段名
			} else {
				// 非字段错误
				key = oe.getObjectName();// 获取验证对象名称
			}
			// 错误信息
			msg = oe.getDefaultMessage();
			errorMap.put(key, msg);
		}
		return errorMap;
	}

	// strTime要转换的String类型的时间
	// formatType时间格式
	// strTime的时间格式和formatType的时间格式必须相同
	public static long stringToLong(String strTime, String formatType)
			throws ParseException {
		Date date = stringToDate(strTime, formatType); // String类型转成date类型
		if (date == null) {
			return 0;
		} else {
			long currentTime = dateToLong(date); // date类型转成long类型
			return currentTime;
		}
	}

	// strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
	// HH时mm分ss秒，
	// strTime的时间格式必须要与formatType的时间格式相同
	public static Date stringToDate(String strTime, String formatType)
			throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(formatType);
		Date date = null;
		date = formatter.parse(strTime);
		return date;
	}

	// date要转换的date类型的时间
	public static long dateToLong(Date date) {
		return date.getTime();
	}

	public static String getErrorMessage(Errors errors){
		List<ObjectError> allErrors = errors.getAllErrors();
		if(CollectionUtils.isEmpty(allErrors)){
			return null;
		}
		return allErrors.get(0).getDefaultMessage();
	}


	public static String convertSnakeToCamel(String snake) {

		if (snake == null) {
			return null;
		}

		if (!snake.contains("_")) {
			return snake;
		}

		StringBuilder result = new StringBuilder();

		String[] split = StringUtils.split(snake, "_");
		int index = 0;
		for (String s : split) {
			if (index == 0) {
				result.append(s.toLowerCase());
			} else {
				result.append(capitalize(s));
			}
			index++;
		}

		return result.toString();
	}

	private static String capitalize(String s) {

		if (s == null) {
			return null;
		}

		if (s.length() == 1) {
			return s.toUpperCase();
		}

		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	/**
	 * 检查是否缺少参数
	 *
	 * @param param 需要检查的为空的参数们
	 * @return true 缺少 false 不缺少
	 */
	public static boolean checkMissParam(Object... param) {
		if (param == null || param.length == 0) {
			return false;
		}
		for (Object object : param) {
			if (object == null || "".equals(object) || object.toString().length() == 0) {
				return true;
			}
		}
		return false;
	}
	
	 /**
	 * 去除两端空格
	 */
	public static final String filterSpace(String str) {
		str =StringUtils.isBlank(str)?null:str.trim();
		return str;
	}

	public static int byteParseInt(Byte param){
		if(param == null){
			return 0;
		}
		int num = 0;
		try {
			num = Integer.valueOf(param);
			return num;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 获取随机4位验证码
	 *
	 * @return
	 */
	public static String getFourRandomVcode() {
		Random random = new Random();
		String result = "";
		for (int i = 0; i < 4; i++) {
			result += random.nextInt(10);
		}
		return result;
	}

	public static boolean dateIsNow(Date date){
		//当前时间
		Date now = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		//获取今天的日期
		String nowDay = sf.format(now);
		//对比的时间
		String day = sf.format(date);
		return day.equals(nowDay);
	}

	private static Pattern phoneP = Pattern.compile("^1\\d{10}$");

	private static Pattern emailP = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");

	private static Pattern passwordP=Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z_]{8,20}$");

	public static boolean isPhone(String mobiles) {
		Matcher matcher = phoneP.matcher(mobiles);
		return matcher.matches();
	}

	public static boolean isEmail(String string) {
		Matcher matcher = emailP.matcher(string);
		return matcher.matches();
	}

	public static boolean isPassword(String password) {
		Matcher matcher = passwordP.matcher(password);
		return matcher.matches();
	}

	public static String uuid(){
		return UUID.randomUUID().toString();
	}

	public static String guid(){
		String randomGUID = new RandomGUID().toStr();
		return randomGUID;
	}

	public static boolean compareUserPasswd(String passwordSalt, String passwordDB, String password ) throws UnsupportedEncodingException {

		byte[] saltBytes = DatatypeConverter.parseBase64Binary(passwordSalt);

		byte[] passwordBytes = password.getBytes("UTF-16LE");

		byte[] combinedBytes = new byte[saltBytes.length + passwordBytes.length];

		System.arraycopy(saltBytes, 0, combinedBytes, 0, saltBytes.length);
		System.arraycopy(passwordBytes, 0, combinedBytes, saltBytes.length, passwordBytes.length);

		byte[] encode = SHA1.encode(combinedBytes);

		String binary = DatatypeConverter.printBase64Binary(encode);

		return  passwordDB.equals(binary);
	}

	public static String comparePasswd(String passwordSalt, String password) throws UnsupportedEncodingException {
		byte[] saltBytes = DatatypeConverter.parseBase64Binary(passwordSalt);

		byte[] passwordBytes = password.getBytes("UTF-16LE");

		byte[] combinedBytes = new byte[saltBytes.length + passwordBytes.length];

		System.arraycopy(saltBytes, 0, combinedBytes, 0, saltBytes.length);
		System.arraycopy(passwordBytes, 0, combinedBytes, saltBytes.length, passwordBytes.length);

		byte[] encode = SHA1.encode(combinedBytes);

		String binary = DatatypeConverter.printBase64Binary(encode);

		return binary;
	}

	public static void main(String[] args) {
		try {
			System.out.println(uuid());
			System.out.println(comparePasswd("U66L/aB6PE/HGsSN55RINQ==", "123456"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * 根据十进制数字获取配送模式规则名称
	 * @param n
	 * @return
	 */
	public static String getModenameByInteger(Integer n){
		if(n==null){
			n=0;
		}
		String s = Integer.toBinaryString(n);
		StringBuilder builder=new StringBuilder();
		if(s.length()<7){
			s="0000000"+s;
		}
		for(int i=1;i<=s.length();i++){
			if(i<=7&&"0".equals(String.valueOf(s.charAt(s.length()-i)))){
				if(TextUtils.isEmpty(builder.toString())){
					builder.append(getWeekDay(i));
				}else{
					builder.append("、"+getWeekDay(i));
				}
			}
		}
		return builder.toString();
	}
	
	public static String getWeekDay(int i) {
		String weekDate="";
		switch(i){
	    case 1 :
	      weekDate="周一";
	       break;
	    case 2 :
	    	weekDate="周二";
	       break;
	    case 3 :
	    	weekDate="周三";
	       break;
	    case 4 :
	    	weekDate="周四";
	       break;
	    case 5 :
	    	weekDate="周五";
	       break;
	    case 6 :
	    	weekDate="周六";
	       break;
	    case 7 :
	    	weekDate="周日";
	       break;
	   }
	  return weekDate;
	}

	public static Boolean makeTimeNow(String time){
		// 判断时间是否到今天
		//获取string对应date日期
		Date dateTime = getDateFormat(time, "yyyy-MM-dd");

		// 获取date对应的Calendar对象
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateTime);

		// 获取时间年月日
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		Date date = new Date(year,month,day);

		// 当前时间
		Calendar c = Calendar.getInstance();
		Date now = new Date(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));

		// false 已到  true 未到
		return date.before(now);
	}

	public static String getPlaintext(String str, String privateKey) throws Exception{
		//64位解码加密后的字符串
		byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
		//base64编码的私钥
		byte[] decoded = Base64.decodeBase64(privateKey);
		RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
		//RSA解密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, priKey);
		String outStr = new String(cipher.doFinal(inputByte));
		return outStr;
	}

	public static String md5(String plainText) {
		//定义一个字节数组
		byte[] secretBytes = null;
		try {
			// 生成一个MD5计算摘要的对象
			MessageDigest md = MessageDigest.getInstance("MD5");
			//对字符串进行hash处理
			md.update(plainText.getBytes());
			//获得hash运算后数据
			secretBytes = md.digest();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("没有md5这个算法！");
		}
		//将hash后的数据转换为16进制数字
		String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
		// 如果生成数字未满32位，需要前面补0
		for (int i = 0; i < 32 - md5code.length(); i++) {
			md5code = "0" + md5code;
		}
		return md5code;
	}
}
