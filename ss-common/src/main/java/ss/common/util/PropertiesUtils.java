package ss.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class PropertiesUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtils.class);

	/** 资源属性 */
	private static Properties properties;

	/**
	 * 私有构造方法
	 */
	private PropertiesUtils() {
	}

	static {
		properties = new Properties();
	  	// 读取配置文件
		loadPropertiesFile(new String[]{"config.properties"});
	}

	private static  void   loadPropertiesFile(String[] fileNames){
		for( String fileName : fileNames){
		 InputStream  is = PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName);
		try {
			properties.load(is);
		} catch (IOException e) {
			LOGGER.info("配置文件读取失败");
			e.printStackTrace();
		}
		}
	}
	
	/**
	 * 获取配置信息
	 * 
	 * @param key 键
	 * @return 配置信息
	 */
	public static String getValue(String key) {
		String value = properties.getProperty(key);
		if (StringUtils.isBlank(value)) {
			LOGGER.warn("没有获取指定key的值，请确认资源文件中是否存在【{}】" , key);
		}
		return value;
	}

	/**
	 * 获取配置信息
	 * 
	 * @param key 键
	 * @param param 参数
	 * @return 配置信息
	 */
	public static String getValue(String key, Object[] param) {
		String value = getValue(key);
		return MessageFormat.format(value, param);
	}

}
