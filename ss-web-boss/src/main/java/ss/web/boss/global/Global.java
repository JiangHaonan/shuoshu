package ss.web.boss.global;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mutou
 * @date 2017年7月17日
 */
public class Global {
	
	public static Map<String, String> ssConfig = new HashMap<String, String>();
	
	public static String getConfig(String key){
		if(ssConfig.get(key) != null){
			return ssConfig.get(key);
		}
		return "";
	}
	
	public static void setConfig(String key , String value){
		ssConfig.put(key, value);
	} 
}
