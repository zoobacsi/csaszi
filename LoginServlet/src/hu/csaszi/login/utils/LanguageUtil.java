package hu.csaszi.login.utils;

import java.io.IOException;
import java.util.Properties;

public class LanguageUtil {

	private static Properties properties = new Properties();
	
	private LanguageUtil(){
		
	}
	
	static{
		try{
			
			properties.load(LanguageUtil.class.getClassLoader().getResourceAsStream("language/Language.properties"));
		
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String get(String key){
		
		return properties.getProperty(key);
	}
}
