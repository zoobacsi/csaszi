package hu.csaszi.twodee.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class PropsUtil {

    public static final String CONFIG_PATH = "res/properties/config.properties";
    
	private Properties properties;
	private OutputStream outputStream;
	private InputStream inputStream;
	
	public static PropsUtil instance;
	
	private PropsUtil(){
	
		properties = new Properties();
	}
	
	public static PropsUtil getInstance(){
		
		if(instance == null){
			instance = new PropsUtil();
			instance.loadFromPropsImpl();
		}
		
		
		return instance;
	}
	
	public static void writeToProps(Map<String, String> props){
		
		getInstance().writeToPropsImpl(props);
	}
	
	private void writeToPropsImpl(Map<String, String> props){
		
		try {

			outputStream = new FileOutputStream(CONFIG_PATH);

			// set the properties value
			for(Entry<String, String> entry : props.entrySet()){
				properties.setProperty(entry.getKey(), entry.getValue());
			}
			
			// save properties to project root folder
			properties.store(outputStream, "config");

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	public static void loadFromProps(){
		
		getInstance().loadFromPropsImpl();
	}

	private void loadFromPropsImpl(){
		
		try {

			inputStream = new FileInputStream(CONFIG_PATH);

			// load a properties file
			properties.load(inputStream);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String getString(String key, String defaultValue){
		return getInstance().getStringImpl(key, defaultValue);
	}
		
	private String getStringImpl(String key){
		return getStringImpl(key, null);
	}
		
	private String getStringImpl(String key, String defaultValue){
		
		Object value = properties.getProperty(key, defaultValue);
		 
		if(value != null){
			
			return String.valueOf(value);
		} else {
			return "";
		}
	}
	
	public static int getInteger(String key, int defaultValue){
		
		System.out.println("getint " + key + " " + defaultValue);
		try{
			String valueString = getInstance().getStringImpl(key);
			
			if(valueString == null || valueString.isEmpty()){
				throw new NumberFormatException();
			}
			
			return Integer.parseInt(valueString);
		
		} catch (NumberFormatException e){
			return defaultValue;
		}
	}
	
	public static double getDouble(String key, double defaultValue){
		
		try{
			String valueString = getInstance().getStringImpl(key);
			
			if(valueString == null || valueString.isEmpty()){
				throw new NumberFormatException();
			}
			
			return Double.parseDouble(valueString);
		
		} catch (NumberFormatException e){
			return defaultValue;
		}
	}
	
	public static float getFloat(String key, float defaultValue){
		
		try{
			String valueString = getInstance().getStringImpl(key);
			
			if(valueString == null || valueString.isEmpty()){
				throw new NumberFormatException();
			}
			
			return Float.parseFloat(valueString);
		
		} catch (NumberFormatException e){
			return defaultValue;
		}
	}
	
	public static boolean getBoolean(String key){
		
		return Boolean.parseBoolean(getInstance().getStringImpl(key));
	}
}
