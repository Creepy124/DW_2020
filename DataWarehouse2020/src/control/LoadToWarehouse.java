package control;

import java.sql.SQLException;

import model.Configuration;
import service.DBService;
import service.FileService;
import service.LogService;
import service.WritingError;

public class LoadToWarehouse {
	Configuration config;
	FileService fileService;
	DBService dbService;
	LogService logService;

	public LoadToWarehouse(Configuration configuration, FileService fileService, DBService dbService, LogService logService) {
		this.config = configuration;
		this.fileService = fileService;
		this.dbService = dbService;
		this.logService = logService;
	}

	//call procedure that compatible to config name
	public boolean loadToWarehouse() {
		String tableName = config.getConfigName();
		try {
			dbService.callProcedure("load"+tableName);
			return true;
		} catch (SQLException e) {
			WritingError.sendError(e.getMessage().toString(), config.getToEmails());
			return false;
		}
	}
}
