package com.zycus.scriptExecutor.service.factory;

import com.zycus.scriptExecutor.service.DatabaseService;
import com.zycus.scriptExecutor.service.FileService;
import com.zycus.scriptExecutor.service.TurbatioService;
import com.zycus.scriptExecutor.service.impl.DatabaseServiceImpl;
import com.zycus.scriptExecutor.service.impl.FileServiceImpl;
import com.zycus.scriptExecutor.service.impl.TurbatioServiceImpl;

/**
 * Factory to get all Service methods.
 * 
 * @author harshvardhan.dudeja
 *
 */
public class ServiceFactory {

	private static DatabaseService databaseService = null;
	private static FileService fileService = null;
	private static TurbatioService turbatioService = null;

	public static DatabaseService getDatabaseService() {
		if (databaseService == null)
			databaseService = new DatabaseServiceImpl();
		return databaseService;
	}

	public static FileService getFileService() {
		if (fileService == null)
			fileService = new FileServiceImpl();
		return fileService;
	}

	public static TurbatioService getTurbatioService() {
		if (turbatioService == null)
			turbatioService = new TurbatioServiceImpl();
		return turbatioService;
	}
}
