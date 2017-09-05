package me.Demonly.osrs.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import me.Demonly.osrs.util.Store;

public class Config {
	
	public HashMap<String, String> config = new HashMap<String, String>();
	public Store store = new Store();
	public Properties properties = new Properties();
	
	public void save() {
		for (Map.Entry<String, String> entry : config.entrySet()) {
			
			properties.put(entry.getKey(), entry.getValue());
		}
		
		try {
			properties.store(new FileOutputStream(store.store+"/config.yml"), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveDefaultConfig() {
		
		try {
			InputStream config = this.getClass().getResourceAsStream("config.yml");
			FileUtils.copyInputStreamToFile(config, new File(store.store, "config.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void load() {
		try {
			properties.load(new FileInputStream(store.store+"/config.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (String key : properties.stringPropertyNames()) {
			config.put(key, properties.get(key).toString());
		}
	}
	
	public int getInt(String key) {
		int value = Integer.parseInt(config.get(key));
		return value;
	}
	
	public String getString(String key) {
		if (config.get(key) instanceof String) {
			return config.get(key);
		}
		
		return null;
	}

}
