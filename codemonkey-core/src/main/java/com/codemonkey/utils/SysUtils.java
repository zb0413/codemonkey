package com.codemonkey.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.util.HtmlUtils;

import com.codemonkey.domain.AppRole;
import com.codemonkey.domain.AppUser;
import com.codemonkey.domain.IEntity;
import com.codemonkey.error.SysError;
import com.codemonkey.service.AppRoleService;

/**
 * 类描述：系统工具类
 */
public class SysUtils {

	private static final String ORACLE = "oracle";

	public static final String UTF8 = "UTF-8";

	public static final String APPLICATION_PROPERTIES = "classpath:application.properties";

	public static final String CURRENCT_USER = "current_user";

	public static final String DATE_FORMAT = "yyyy-MM-dd";

	public static final String FULL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static final String DATA_INITED = "dataInited";

	// public static final String CURRENCT_USER_LOGININFO =
	// "currenct_user_logininfo";
	private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

	private static Logger logger = getLog(SysUtils.class);

	public static Logger getLog(Class<?> clazz) {
		return Logger.getLogger(clazz);
	}

	public static boolean isEmpty(String s) {
		return StringUtils.isBlank(s);
	}

	public static boolean isNotEmpty(String s) {
		return StringUtils.isNotBlank(s);
	}

	public static boolean isEmpty(Collection<?> coll) {
		return CollectionUtils.isEmpty(coll);
	}

	public static boolean isNotEmpty(Collection<?> coll) {
		return CollectionUtils.isNotEmpty(coll);
	}

	public static boolean isEmpty(JSONArray array) {
		return (array == null) || (array.length() == 0);
	}

	public static boolean isNotEmpty(JSONArray array) {
		return (array != null) && (array.length() != 0);
	}

	public static JSONArray toJsonArry(Collection<?> list) {
		JSONArray ja = new JSONArray();
		if (SysUtils.isNotEmpty(list)) {
			for (Object obj : list) {
				if (obj instanceof IEntity) {
					ja.put(((IEntity) obj).listJson());
				} else if (obj instanceof Map) {
					@SuppressWarnings("unchecked")
					Map<String, Object> result = (Map<String, Object>) obj;
					JSONObject jo = new JSONObject();
					for (Entry<String, Object> entry : result.entrySet()) {
						jo.put(entry.getKey(), entry.getValue());
					}
					ja.put(jo);
				}
			}
		}
		return ja;
	}

	/**
	 * 方法描述：Objectnull处理
	 * 
	 * @param: str Object
	 * @return: 字符串
	 * @author: lt
	 */
	public static String nullObj2String(Object str) {
		if (str == null) {
			return "";
		} else
			return str.toString();
	}

	/**
	 * 方法描述：字符串trim处理
	 * 
	 * @param: str 字符串
	 * @return: trim处理
	 * @author: wy
	 */
	public static String trimString(String str) {
		if (str == null || "null".equals(str)) {
			return "";
		}
		return str.trim();
	}

	/**
	 * 方法描述：设置系统属性
	 * 
	 * @param: key 键
	 * @param: value 值
	 * @return: void
	 * @author: wy
	 */
	public static void putAttribute(String key, Object value) {
		initThreadLocalMap();
		threadLocal.get().put(key, value);
	}

	/**
	 * 方法描述：初期化ThreadLocal
	 * 
	 * @return: void
	 * @author: wy
	 */
	private static void initThreadLocalMap() {
		Map<String, Object> map = threadLocal.get();
		if (map == null) {
			map = new HashMap<String, Object>();
			threadLocal.set(map);
		}
	}

	/**
	 * 方法描述：取得系统属性
	 * 
	 * @param: key 键
	 * @param: value 值
	 * @return: void
	 * @author: wy
	 */
	public static Object getAttribute(String key) {
		initThreadLocalMap();
		return threadLocal.get().get(key);
	}

	/**
	 * 方法描述：取得系统属性，并且按逗号分隔
	 * 
	 * @param key
	 * @return
	 */
	public static String[] getAttributeArray(String key) {
		if (getAttribute(key) != null) {
			String attribute = getAttribute(key).toString();
			if (isNotEmpty(attribute)) {
				String[] attributeArray = attribute.split(",");
				return attributeArray;
			}
		}
		return null;
	}

	/**
	 * 方法描述：取得本地化
	 * 
	 * @return: 本地化
	 * @author: wy
	 */
	public static String getCurrentLocale() {
		String locale = (String) SysUtils.getAttribute(ExtConstant.LOCALE);
		if (StringUtils.isBlank(locale)) {
			return ExtConstant.DEFAULT_LOCALE;
		}
		return locale;
	}

	/**
	 * 方法描述：取得主题
	 * 
	 * @param: session 会话
	 * @return: 主题
	 * @author: wy
	 */
	public static String getCurrentTheme(HttpSession session) {
		String theme = (String) session.getAttribute(ExtConstant.THEME);
		if (theme == null) {
			theme = ExtConstant.DEFAULT_THEME;
		}
		return theme;
	}

	/**
	 * 根据路径导出文件
	 * 
	 * @param out
	 *            输出流
	 * @param path
	 *            路径
	 */
	public static void exportPathToFile(OutputStream out, String path) {
		FileInputStream fis = null;
		OutputStream os = null;
		try {
			fis = new FileInputStream(path);

			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();

			os = new BufferedOutputStream(out);
			os.write(buffer);
			os.flush();
			os.close();

		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			fis = null;
			os = null;
		}

	}

	/**
	 * 方法描述：把资源数据写入数据库
	 * 
	 * @param: datasource 数据源
	 * @param: tableName 表名
	 * @param: sql 查询语句
	 * @param: out 输出流
	 * @param: fileType 文件类型 xml , xls
	 * @return: void
	 * @author: zhaobin
	 */

	public static void exportDataToFile(DataSource datasource,
			String tableName, String sql, OutputStream out, String fileType) {
		IDatabaseConnection connection = null;
		QueryDataSet partialDataSet = null;
		try {
			connection = new DatabaseConnection(datasource.getConnection(),
					null);
			connection.getConfig().setProperty(
					DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, true);
			partialDataSet = new QueryDataSet(connection);
			if (isNotEmpty(sql)) {
				partialDataSet.addTable(tableName, sql);
			} else {
				partialDataSet.addTable(tableName);
			}
			if ("xml".equalsIgnoreCase(fileType)) {
				XmlDataSet.write(partialDataSet, out);
				logger.info("表【" + tableName + "】数据已导出");
			} else if ("xls".equalsIgnoreCase(fileType)) {
				XlsDataSet.write(partialDataSet, out);
				logger.info("表【" + tableName + "】数据已导出");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("表【" + tableName + "】数据导出出错");
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void loadDataToDB(DataSource datasource, String filePath,
			String tablename) {
		loadDataToDB(datasource, filePath, DatabaseOperation.REFRESH, tablename);
	}

	/**
	 * 方法描述：把资源数据写入数据库
	 * 
	 * @param: datasource 数据源
	 * @param: filePath 文件路径
	 * @return: void
	 * @author: wy
	 */

	public static void loadDataToDB(DataSource datasource, String filePath) {
		loadDataToDB(datasource, filePath, DatabaseOperation.REFRESH, null);
	}

	public static void loadDataToDB(DataSource datasource, String filePath,
			DatabaseOperation op, String tablename) {

		IDatabaseConnection connection = null;
		try {
			PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();

			Resource[] resources = null;
			try {
				resources = patternResolver.getResources(filePath);
			} catch (FileNotFoundException e) {
				logger.debug("no default data found!!!");
			}

			if (resources == null) {
				return;
			}

			// 判断文件名格式是否正确（是否以"数字-"开头）

			for (Resource re : resources) {
				logger.info("loaded resource : " + re.getFilename());

				if (!RegUtils.matches("^\\d+-default-data.+", re.getFilename())) {
					throw new DataSetException(re.getFilename() + "文件名格式错误，应该以“排序号-default-”开头。");
				}
			}

			// 把文件名称排序
			for (int i = 0; i < resources.length; i++) {

				for (int j = i; j > 0; j--) {
					int a = Integer.valueOf(resources[j].getFilename().split("-")[0]);
					int b = Integer.valueOf(resources[j - 1].getFilename().split("-")[0]);
					if (a < b) {
						Resource temp = resources[j - 1];
						resources[j - 1] = resources[j];
						resources[j] = temp;
					}
				}
			}

			// initialize your database connection here
			Properties pro = loadProperties(getResource(APPLICATION_PROPERTIES).getInputStream());
			String schema = null;
			if (ORACLE.equals(pro.getProperty("DB_TYPE"))) {
				schema = pro.getProperty("jdbc.username").toUpperCase();
			}
			connection = new DatabaseConnection(datasource.getConnection(), schema);

			for (int i = 0; i < resources.length; i++) {
				if (resources[i].exists()) {

					if (isEmpty(tablename)) {
						logger.info(resources[i].getFilename());
						// initialize your dataset here
						IDataSet dataSet = createDataSet(resources[i]);
						op.execute(connection, dataSet);
					} else {
						if (resources[i].getFilename().contains(tablename)) {
							logger.info(resources[i].getFilename());
							IDataSet dataSet = createDataSet(resources[i]);

							op.execute(connection, dataSet);
						}
					}
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 方法描述：创建数据集
	 * 
	 * @param: resource 资源
	 * @return: IDataSet
	 * @author: wy
	 */
	protected static IDataSet createDataSet(Resource resource)
			throws DataSetException, IOException {
		IDataSet dataSet = null;
		if (resource.getFilename().endsWith(".xml")) {
			dataSet = new XmlDataSet(resource.getInputStream());
		} else if (resource.getFilename().endsWith(".xls")) {
			dataSet = new XlsDataSet(resource.getInputStream());
		} else {
			throw new DataSetException("unsupport data type");
		}

		ReplacementDataSet ds = new ReplacementDataSet(dataSet);
		ds.addReplacementObject("[NULL]", null);

		return ds;
	}

	// public static void sendEmail(String subject , String msg , String to){
	// try {
	// Email email = new SimpleEmail();
	// email.setFrom(getAppSetting("mail.default.from"));
	// email.setHostName(getAppSetting("mail.host"));
	// //
	// email.setSmtpPort(Integer.valueOf(resourceUtils.getAppSetting("mail.smtp.port")));
	// String username = getAppSetting("mail.username");
	// String password = getAppSetting("mail.password");
	// email.setAuthenticator(new DefaultAuthenticator(username , password));
	// //
	// email.setTLS(Boolean.valueOf(resourceUtils.getAppSetting("mail.tls")));
	// email.setSubject(subject);
	// email.setMsg(msg);
	// email.addTo(to);
	// email.send();
	// } catch (EmailException e) {
	// e.printStackTrace();
	// }
	// }

	/**
	 * 方法描述：格式化日期
	 * 
	 * @param: date 日期
	 * @return: 格式化后日期
	 * @author: wy
	 */
	public static String formatDate(Date date) {
		return formatDate(date, SysUtils.DATE_FORMAT);
	}

	/**
	 * 方法描述：格式化日期
	 * 
	 * @param: date 日期
	 * @param: format 格式
	 * @return: 格式化后日期
	 * @author: wy
	 */
	public static String formatDate(Date date, String format) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 方法描述：格式化小数
	 * 
	 * @param: value 数值
	 * @return: 格式化后小数
	 * @author: wy
	 */
	public static String formatDouble(Double value) {
		if (value == null) {
			return "";
		}
		NumberFormat sdf = NumberFormat.getInstance();
		return sdf.format(value);
	}

	public static String formatNumber(Number value) {
		return formatNumber(value, null);
	}

	public static String formatNumber(Number value, String pattern) {

		if (value == null) {
			return "";
		}

		NumberFormat nf = null;
		if (SysUtils.isEmpty(pattern)) {
			nf = NumberFormat.getInstance();
		} else {
			nf = new DecimalFormat(pattern);
		}
		return nf.format(value);
	}

	/**
	 * 方法描述：把字符串转换成日期
	 * 
	 * @param: dateString 字符串
	 * @return: 日期
	 * @author: wy
	 */
	public static Date toDate(String dateString) {

		return toDate(dateString, SysUtils.DATE_FORMAT);
	}

	/**
	 * 方法描述：把字符串转换成日期
	 * 
	 * @param: dateString 字符串
	 * @param: fmt 格式
	 * @return: 日期
	 * @author: wy
	 */
	public static Date toDate(String dateString, String fmt) {
		try {
			if (StringUtils.isBlank(dateString)) {
				return null;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(fmt);
			return sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void handleException(Exception e) {
		e.printStackTrace();
	}

	public static String formatString(String s, Object... args) {
		if (args == null || args.length == 0) {
			return s;
		}

		String msg = s;

		String regex = "\\{(\\d+)\\}";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(s);
		int i = 0;
		while (m.find()) {
			String part = m.group();
			if (i >= args.length) {
				break;
			}
			msg = msg.replace(part, args[i] != null ? args[i].toString() : "");
			i++;
		}
		return msg;
	}

	public static String getCurrentUsername() {
		AppUser user = getCurrentUser();

		return user != null ? user.getUsername() : null;
	}

	public static AppUser getCurrentUser() {
		return (AppUser) SysUtils.getAttribute(CURRENCT_USER);
	}

	public static boolean isAdmin() {
		AppUser user = getCurrentUser();
		Set<AppRole> role = user.getRoles();
		for (AppRole appRole : role) {
			if (AppRoleService.ROLE_ADMIN.equals(appRole.getCode())) {
				return true;
			}
		}
		return false;
	}

	public static void copyToJSON(JSONObject target, Map<String, Object> source) {
		if (target == null || source == null) {
			return;
		}
		Set<Entry<String, Object>> set = source.entrySet();
		Iterator<Entry<String, Object>> it = set.iterator();
		while (it.hasNext()) {
			Entry<String, Object> e = it.next();
			target.put(e.getKey().toString(),
					HtmlUtils.htmlEscape(e.getValue().toString()));
		}
	}

	/**
	 * 方法描述：获取代理对象的目标对象
	 * 
	 * @param: proxy 代理
	 * @return: 代理对象
	 * @author: wy
	 */
	public static Object getTarget(Object proxy) {

		if (!AopUtils.isAopProxy(proxy)) {
			return proxy;// 不是代理对象
		}

		if (AopUtils.isJdkDynamicProxy(proxy)) {
			return getJdkDynamicProxyTargetObject(proxy);
		} else { // cglib
			return getCglibProxyTargetObject(proxy);
		}

	}

	private static Object getCglibProxyTargetObject(Object proxy) {
		try {
			Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
			h.setAccessible(true);
			Object dynamicAdvisedInterceptor = h.get(proxy);

			Field advised = dynamicAdvisedInterceptor.getClass()
					.getDeclaredField("advised");
			advised.setAccessible(true);

			Object target = ((AdvisedSupport) advised
					.get(dynamicAdvisedInterceptor)).getTargetSource()
					.getTarget();

			return target;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Object getJdkDynamicProxyTargetObject(Object proxy) {
		Field h;
		try {
			h = proxy.getClass().getSuperclass().getDeclaredField("h");
			h.setAccessible(true);
			AopProxy aopProxy = (AopProxy) h.get(proxy);

			Field advised = aopProxy.getClass().getDeclaredField("advised");
			advised.setAccessible(true);

			Object target = ((AdvisedSupport) advised.get(aopProxy))
					.getTargetSource().getTarget();
			return target;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String encode(String source) {
		return encode(source, SysUtils.UTF8);
	}

	public static String encode(String source, String coding) {
		try {
			return URLEncoder.encode(source, coding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getBytes(String str) {
		try {
			return new String(str.getBytes("ISO-8859-1"), SysUtils.UTF8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String switchCharset(String s) {
		return switchCharset(s, "ISO-8859-1", SysUtils.UTF8);
	}

	public static String switchCharset(String s, String fromCharset,
			String toCharset) {
		try {
			return new String(s.getBytes(fromCharset), toCharset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String decode(String source) {
		return decode(source, SysUtils.UTF8);
	}

	public static String decode(String source, String coding) {
		try {
			return URLDecoder.decode(source, coding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JSONObject decodeJson(String source) {
		try {
			if (isEmpty(source)) {
				return new JSONObject();
			}
			return new JSONObject(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new JSONObject();
	}

	public static Object[] appendArray(Object[] array, Object obj) {
		List<Object> list = new ArrayList<Object>();
		if (array != null) {
			list.addAll(Arrays.asList(array));
		}
		list.add(obj);
		return list.toArray();
	}

	// public static String exportTableDescription(Class<?> clazz, String
	// tableName) {
	// StringBuffer buffer = new StringBuffer("\ntable:\n");
	// buffer.append(tableName);
	// buffer.append("-");
	// Label label = clazz.getAnnotation(Label.class);
	// if (label != null) {
	// buffer.append(label.value());
	// }
	// buffer.append("\n");
	//
	// return exportFieldsDescription(clazz, buffer);
	// }

	// public static String exportTableDescription(Class<?> clazz) {
	// StringBuffer buffer = new StringBuffer("\ntable:\n");
	//
	// buffer.append(propToColumn(clazz.getSimpleName()));
	// buffer.append("-");
	//
	// Label label = clazz.getAnnotation(Label.class);
	// if (label != null) {
	// buffer.append(label.value());
	// }
	// buffer.append("\n");
	//
	// return exportFieldsDescription(clazz, buffer);
	// }

	// private static String exportFieldsDescription(Class<?> clazz,
	// StringBuffer buffer) {
	// List<Field> fields = ClassHelper.getAllFields(clazz);
	// if (SysUtils.isNotEmpty(fields)) {
	// for (Field f : fields) {
	//
	// if (f.getAnnotation(OneToMany.class) != null
	// || f.getAnnotation(Transient.class) != null
	// || f.getAnnotation(ManyToMany.class) != null) {
	// continue;
	// }
	//
	// buffer.append(propToColumn(f.getName()));
	// buffer.append("\t");
	//
	// Label label = f.getAnnotation(Label.class);
	// if (label != null) {
	// buffer.append(label.value());
	// }
	//
	// buffer.append("\n");
	// }
	// }
	// return buffer.toString();
	// }

	public static int currentYear() {
		return year(new DateTime());
	}

	public static int year(DateTime date) {
		return date.getYear();
	}

	public static int currentMonth() {
		return month(new DateTime());
	}

	public static int month(DateTime date) {

		if (date == null) {
			return -1;
		}

		return date.getMonthOfYear();
	}

	public static int currentDate() {
		return date(new DateTime());
	}

	public static int date(DateTime date) {

		if (date == null) {
			return -1;
		}

		return date.getDayOfMonth();
	}

	public static int currentSeason() {
		return season(new DateTime());
	}

	public static int season(DateTime date) {

		int month = month(date);

		if (month >= 1 && month <= 3) {
			return 1;
		} else if (month >= 4 && month <= 6) {
			return 2;
		} else if (month >= 7 && month <= 9) {
			return 3;
		} else if (month >= 10 && month <= 12) {
			return 4;
		}
		return -1;
	}

	public static DateTime addDay(DateTime date, int count) {
		if (date == null) {
			return null;
		}
		return date.plusDays(count);
	}

	public static DateTime addMonth(DateTime date, int count) {
		if (date == null) {
			return null;
		}
		return date.plusMonths(count);
	}

	public static DateTime addYear(DateTime date, int count) {
		if (date == null) {
			return null;
		}
		return date.plusYears(count);
	}

	public static boolean isAjax(HttpServletRequest r) {

		String uri = r.getRequestURI();
		String header = r.getHeader("X-Requested-With");

		if (isNotEmpty(header) && header.equals("XMLHttpRequest")) {
			return true;
		} else if (uri.startsWith("/ext/mobile/")) {
			return true;
		}

		return false;
	}

	public static Double getDouble(JSONObject json, String key) {
		if (isEmpty(json, key)) {
			return null;
		}
		return json.getDouble(key);
	}

	public static String getString(JSONObject json, String key) {
		if (isEmpty(json, key)) {
			return null;
		}
		return json.getString(key);
	}

	public static Long getLong(JSONObject json, String key) {
		if (isEmpty(json, key)) {
			return null;
		}
		return json.getLong(key);
	}

	public static Integer getInt(JSONObject json, String key) {
		if (isEmpty(json, key)) {
			return null;
		}
		return json.getInt(key);
	}

	public static Boolean getBoolean(JSONObject json, String key) {
		if (isEmpty(json, key)) {
			return null;
		}
		return json.getBoolean(key);
	}

	private static boolean isEmpty(JSONObject json, String key) {

		if (json == null || isEmpty(key)) {
			return true;
		}

		if (!json.has(key)) {
			return true;
		}

		String s = json.getString(key);
		if (isEmpty(s)) {
			return true;
		}

		if ("NULL".equalsIgnoreCase(s)) {
			return true;
		}

		return false;
	}

	public static void writePropterty(String key, String value) {
		writePropterty(key, value, APPLICATION_PROPERTIES);
	}

	public static void writePropterty(String key, String value, String path) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put(key, value);
			File file = getResource(path).getFile();
			writePropterties(map, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static File getTemplateFile(String path) {
		File file = null;
		try {
			file = getResource(path).getFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	public static Properties loadProperties(File file) {
		Properties prop = new Properties();
		InputStream in;
		try {
			in = new FileInputStream(file);
			return loadProperties(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return prop;
	}
	
	public static Properties loadProperties(InputStream in) {
		Properties prop = new Properties();
		try {
			prop.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	public static void writePropterties(Map<String, String> map, File file) {
		Properties prop = loadProperties(file);

		if (prop == null) {
			prop = new Properties();
		}

		OutputStream output = null;
		try {
			output = new FileOutputStream(file);
			Set<String> keys = map.keySet();
			if (SysUtils.isNotEmpty(keys)) {
				for (String key : keys) {
					prop.setProperty(key, map.get(key));
				}
			}
			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static Resource getResource(String classpathResoure) {
		Resource[] rs = getResources(classpathResoure);
		if (rs != null && rs.length > 0) {
			return rs[0];
		}

		return null;
	}

	public static Resource[] getResources(String classpathResoure) {
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = null;
		try {
			resources = resolver.getResources(classpathResoure);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resources;
	}

	public static boolean isJson(String body) {
		return isJsonObject(body) || isJsonArray(body);
	}

	public static boolean isJsonArray(String body) {
		return false;
	}

	public static boolean isJsonObject(String body) {
		return false;
	}

	/**
	 * Collections
	 *
	 **/
	// 并集
	public static Collection<?> uion(Collection<?> a, Collection<?> b) {

		return CollectionUtils.union(a, b);

	}

	// 交集
	public static Collection<?> intersection(Collection<?> a, Collection<?> b) {

		if (a == null || b == null) {
			return null;
		}

		return CollectionUtils.intersection(a, b);
	}

	// 交集的补集
	public static Collection<?> disjunction(Collection<?> a, Collection<?> b) {

		if (a == null || b == null) {
			return null;
		}

		return CollectionUtils.disjunction(a, b);
	}

	// 集合相减
	public static Collection<?> subtract(Collection<?> a, Collection<?> b) {

		if (a == null || b == null) {
			return null;
		}

		return CollectionUtils.subtract(a, b);
	}

	/**
	 * 1.+ URL 中+号表示空格 %2B 2.空格 URL中的空格可以用 %20 -- 3./ 分隔目录和子目录 %2F 4.? 分隔实际的 URL
	 * 和参数 %3F 6.# 表示书签 %23 7.& URL 中指定的参数间的分隔符 %26 8.= URL 中指定参数的值 %3D 9." 双引号
	 * %22 10.' 单引号 %27 11.\ 反斜杠 %5C
	 */
	public static String decodeURL(String s) {

		if (s == null) {
			return null;
		}

		return s.replace("%2B", "+").replace("%20", " ").replace("%2F", "/")
				.replace("%3F", "?").replace("%23", "#").replace("%26", "&")
				.replace("%3D", "=").replace("%22", "\"").replace("%27", "\'")
				.replace("%5C", "\\");
	}

	/**
	 * 判断数组是否含有与目标字符串相同的元素
	 * 
	 * @param aim
	 * @param array
	 * @return
	 */
	public static boolean isContainString(String aim, String[] array) {

		if (isEmpty(aim) || array == null || array.length == 0) {
			return false;
		}

		for (String i : array) {
			if (aim.equals(i)) {
				return true;
			}
		}
		return false;
	}

	public static String encrypt(String algorithm, String message, String salt) {

		MessageDigest md = null;

		if ("md5".equalsIgnoreCase(algorithm)) {
			md = DigestUtils.getMd5Digest();
		} else if ("md2".equalsIgnoreCase(algorithm)) {
			md = DigestUtils.getMd2Digest();
		} else if ("sha1".equalsIgnoreCase(algorithm)) {
			md = DigestUtils.getSha1Digest();
		} else if ("sha256".equalsIgnoreCase(algorithm)) {
			md = DigestUtils.getSha256Digest();
		} else if ("sha384".equalsIgnoreCase(algorithm)) {
			md = DigestUtils.getSha384Digest();
		} else if ("sha512".equalsIgnoreCase(algorithm)) {
			md = DigestUtils.getSha512Digest();
		}

		if (md == null) {
			throw new SysError("加密算法不存在: " + algorithm);
		}
		md.reset();
		if(SysUtils.isNotEmpty(salt)){
			md.update(salt.getBytes());
		}
		byte[] p = md.digest(message.getBytes());

		int iterations = 1023; // already hashed once above
		// iterate remaining number:
		for (int i = 0; i < iterations; i++) {
			md.reset();
			p = md.digest(p);
		}
		return Base64.encodeBase64String(p);
	}

	public static List<Map<String, Object>> toCrossTable(
			List<Map<String, Object>> list, String colKey, String rowKey,
			String valueKey) {
		List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
		Set<Object> colValueList = new HashSet<Object>();

		if (SysUtils.isNotEmpty(list)) {
			for (Map<String, Object> m : list) {
				colValueList.add(m.get(colKey));
			}
		}

		for (Object obj : colValueList) {
			Map<String, Object> newMap = new HashMap<String, Object>();
			newMap.put(colKey, obj);

			List<Map<String, Object>> subList = findSubList(list, obj, colKey);

			if (SysUtils.isNotEmpty(subList)) {
				for (Map<String, Object> m : subList) {
					newMap.put((String) m.get(rowKey), m.get(valueKey));
				}
				list2.add(newMap);
			}
		}
		return list2;
	}

	private static List<Map<String, Object>> findSubList(
			List<Map<String, Object>> list, Object obj, String colKey) {
		List<Map<String, Object>> subList = new ArrayList<Map<String, Object>>();

		if (SysUtils.isNotEmpty(list)) {
			for (Map<String, Object> m : list) {
				if (obj.equals(m.get(colKey))) {
					subList.add(m);
				}
			}
		}
		return subList;
	}

	public static void syncToSysUtils(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Enumeration<?> enumeration = session.getAttributeNames();
		while (enumeration.hasMoreElements()) {
			String name = (String) enumeration.nextElement();
			SysUtils.putAttribute(name, session.getAttribute(name));
		}
	}

	public static File createFile(String path) {
		File f = new File(path);
		try {
			f.createNewFile();
			return f;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean fileExists(String path){
		File f = new File(path);
		return f.exists();
	}
	
	public static boolean isNotIncluded(Collection<?> list, Object d) {
		return !isIncluded(list , d);
	}
	
	public static boolean isIncluded(Collection<?> list, Object d) {
		
		if(d == null){
			return true;
		}
		
		if(SysUtils.isEmpty(list)){
			return false;
		}
		
		for(Object data : list){
			if(data.equals(d)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isNotIncluded(Collection<?> list, IEntity d) {
		return !isIncluded(list , d);
	}
	
	public static boolean isIncluded(Collection<?> list, IEntity d) {
		
		if(d == null){
			return true;
		}
		
		if(SysUtils.isEmpty(list)){
			return false;
		}
		
		for(Object data : list){
			IEntity entity = (IEntity) data;
			if(entity.getId().equals(d.getId())){
				return true;
			}
		}
		return false;
	}
	
	public static List<Map<String , Integer>> getDivider(int total ,int poolSize){
		List<Map<String , Integer>> divider = new ArrayList<Map<String , Integer>>();
		
		for (int i = 0; i < poolSize; i++) {
			int increment = total / poolSize + 1;
			int start = increment * i;
			int end = increment * i + increment;
			if (end > total){
				end = total;
			}
			Map<String , Integer> m = new HashMap<String , Integer>();
			m.put("start", start);
			m.put("end", end);
			divider.add(m);
		}
		return divider;
	}

	// List<Map<String , Object>> readExcel(File file){
	// if(file == null) {
	// return null;
	// }
	// String fileName = file.getName();
	// String subfix = fileName.substring(fileName.lastIndexOf(".") + 1,
	// fileName.length());
	// if("xls".equals(subfix) || "xlsx".equals(subfix)) {
	// return readXls(file);
	// }
	// return null;
	// }
	//
	// private List<Map<String, Object>> readXls(File file) {
	// try{
	// InputStream is = new FileInputStream(file);
	// HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
	// List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	// // Read the Sheet
	// for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets();
	// numSheet++) {
	// HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
	// if (hssfSheet == null) {
	// continue;
	// }
	//
	// List<String> keys = null;
	// // Read the Row
	// for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
	// HSSFRow hssfRow = hssfSheet.getRow(rowNum);
	//
	// //read key from first row
	// if(rowNum == 1){
	// keys = buildKeys(hssfRow);
	// }
	//
	// if(SysUtils.isNotEmpty(keys) && hssfRow != null){
	// Map<String, Object> map = new HashMap<String, Object>();
	// for(short i = 0 ; i < keys.size() ; i++){
	// HSSFCell cell = hssfRow.getCell(i);
	// map.put(keys.get(i), cell.getStringCellValue());
	// }
	// list.add(map);
	// }
	// }
	// }
	// return list;
	// }catch(Exception e){
	// e.printStackTrace();
	// }
	// return null;
	// }
	//
	// private List<String> buildKeys(HSSFRow hssfRow) {
	// List<String> keys = new ArrayList<String>();
	// if(hssfRow != null){
	// @SuppressWarnings("unchecked")
	// Iterator<HSSFCell> it = hssfRow.cellIterator();
	// while(it.hasNext()){
	// keys.add(it.next().getStringCellValue());
	// }
	// }
	// return keys;
	// }

}
