package com.appdirect.codechallenge;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.appdirect.codechallenge.util.AppDirectCongifLoading;

public class AppDirectServletContextListener implements ServletContextListener{



	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("****Configuration Getting Loaded****");
		AppDirectCongifLoading appDirectCongifLoading = AppDirectCongifLoading.getInstance();
		System.out.println("****Configuration Loaded Finished****");
		
	}
}
