package control;

import java.sql.SQLException;

import model.Configuration;
import model.MyFile;
import service.DBService;
import service.DBServiceImpl;
import service.FileService;
import service.FileServiceImpl;
import service.LogService;
import service.LogServiceImpl;

public class ExtractToStaging {
	Configuration config;
	FileService fileService;
	DBService dbService;
	LogService logService;
	
	public ExtractToStaging(Configuration configuration, FileService fileService, DBService dbService, LogService logService) {
		this.config = configuration;
		this.fileService = fileService;
		this.dbService = dbService;
		this.logService = logService;
	}
	
	public void extractToStaging() throws SQLException {
		if (!dbService.existTable(config.getConfigName())) {
			dbService.createTable(config.getConfigName(), config.getFileVariables(), config.getFileColumnList());
		}
		dbService.truncateTable(config.getConfigName());
		MyFile file = logService.getFileWithStatus(config.getConfigID(),"ER");
		dbService.loadFile(config.getDownloadPath()+"\\\\"+file.getFileName(), config.getConfigName(), ",");
	}
	
	public static void main(String[] args) throws SQLException {
		Configuration config = new Configuration("monhoc", "root", "");
		FileService fileService = new FileServiceImpl();
		DBService dbService = new DBServiceImpl("staging", "root", "");
		LogService logService = new LogServiceImpl("control", "root", "");
		ExtractToStaging test = new ExtractToStaging(config, fileService, dbService, logService);
		test.extractToStaging();
	}


}