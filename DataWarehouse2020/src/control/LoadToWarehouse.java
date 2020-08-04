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

	public void loadToWarehouse() {
		try {
			dbService.callProcedure("");
		} catch (SQLException e) {
			WritingError.sendError(e.getStackTrace().toString(), config.getToEmails());
		}
	}
}
