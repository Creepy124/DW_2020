package control;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import model.Configuration;
import model.MyFile;
import service.DBService;
import service.DBServiceImpl;
import service.FileService;
import service.FileServiceImpl;
import service.LogService;
import service.LogServiceImpl;

public class DataWarehouse {
	Configuration config;
	FileService fileService;
	DBService dbService;
	LogService logService;
	
	public void downloadFromSourceToLocal() throws IOException {
		Downloading download = new Downloading();
		download.downloading(config.getConfigName(), config.getSourcePassword(), config.getSourceHost(), config.getSourceRemoteFile(), config.getDownloadPath(), config.getSourcePort());
		
	}

	public void extractToStaging(String configName, String password) throws SQLException {
		config = new Configuration(configName, password);
		fileService = new FileServiceImpl();
		dbService = new DBServiceImpl("staging",password);
		logService = new LogServiceImpl();

		if (logService.getFileWithStatus("ER",password) != null) {
			dbService.truncateTable(config.getConfigName());
			MyFile myFile = logService.getFileWithStatus("ER",password);
			System.out.println(myFile.toString());
			String sourceFile = config.getDownloadPath() + "\\" + myFile.getFileName();
			File file = new File(sourceFile);
		}
	}
	

	public static void main(String[] args) throws SQLException {
		DataWarehouse dw = new DataWarehouse();
		dw.extractToStaging("sinhvien","");
	}


}