package com.bocom.task;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class FileTaskListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		new TimerManager();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
