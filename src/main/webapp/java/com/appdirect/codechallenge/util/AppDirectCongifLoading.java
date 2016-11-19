package com.appdirect.codechallenge.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;

public class AppDirectCongifLoading {

	private static AppDirectCongifLoading appDirectCongifLoading;
	
	 private Properties prop = null;
	private AppDirectCongifLoading(){
		this.prop = new Properties();
		loadConfiguration();
	}

	public Properties getProp() {
		return prop;
	}

	public static AppDirectCongifLoading getInstance(){
		if(appDirectCongifLoading==null){
			synchronized (AppDirectCongifLoading.class) {
				if(appDirectCongifLoading==null){
					appDirectCongifLoading = new  AppDirectCongifLoading();
				}
			}
		}
		return appDirectCongifLoading;
	}

	private Properties loadConfiguration(){
		 InputStream stream = null;
		try {	
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		    stream = classLoader.getResourceAsStream("config.properties");
		    System.out.println("Stream : "+stream);
		    System.out.println("Prop : "+prop);
	        prop.load(stream);


		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
return prop;
	  }
}
