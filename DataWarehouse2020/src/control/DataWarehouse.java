package control;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

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

	public void extractToStaging(String configName, String passwordDB) throws SQLException {
		config = new Configuration(configName, passwordDB);
		fileService = new FileServiceImpl();
		dbService = new DBServiceImpl("staging",passwordDB);
		logService = new LogServiceImpl();

//		dbService.createTable(configName, config.getValue(), config.getFileColumnList());
		if (logService.getFileWithStatus("ER",passwordDB) != null) {
//			dbService.truncateTable(config.getConfigName());
			MyFile myFile = logService.getFileWithStatus("ER",passwordDB);
			System.out.println(myFile.toString());
			if (myFile.getFileType().equalsIgnoreCase("xlsx")) {
				fileService.convertXLSXToCSV(config.getDownloadPath()+"\\\\"+myFile.getFileName(), config.getDownloadPath());
				dbService.loadFile("", configName, "|");
			} else {
				dbService.loadFile(config.getDownloadPath()+"\\\\"+myFile.getFileName(), configName, ",");
			}
			logService.insertLog(config.getConfigID(), config.getFileName(), "TR", null, LocalDateTime.now().toString(), "");
		}
	}
	

	public static void main(String[] args) throws SQLException {
		DataWarehouse dw = new DataWarehouse();
		dw.extractToStaging("monhoc","");
	}


}