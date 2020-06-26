package control;

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

	public String getNowTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	public void extractToStaging(String configName) {
		config = new Configuration(configName);
		fileService = new FileServiceImpl();
		dbService = new DBServiceImpl("staging");
		logService = new LogServiceImpl();

		try {
			if (logService.getFileWithStatus("ER") != null) {
				MyFile myFile = logService.getFileWithStatus("ER");
				System.out.println(myFile.toString());
				if (!dbService.existTable("temp")) {
					System.out.println("Create database " + myFile.getFileName());
					dbService.createTable("temp", config.getVariabless(), config.getFileColumnList());
				}
				String sourceFile = config.getDownloadPath() + "\\" + myFile.getFileName();
				if (myFile.getFileType().equalsIgnoreCase("txt")) {
					System.out.println("read txt file ...");
					dbService.insertValues("temp", config.getFileColumnList(),
							fileService.readValuesTXT(sourceFile, "|"));
				} else if (myFile.getFileType().equalsIgnoreCase("xlsx")) {
					System.out.println("read excel file ...");
					System.out.println(fileService.readValuesXLSX(sourceFile));
					dbService.insertValues("temp", config.getFileColumnList(), fileService.readValuesXLSX(sourceFile));
				} else {
					System.out.println("Can't read this file");
				}
//			logService.insertLog(config.getConfigID(), myFile.getFileName(), myFile.getFileType(), "TR", getNowTime());
			} else {
				System.out.println("Now no file status ER");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DataWarehouse dw = new DataWarehouse();
		dw.extractToStaging("sinhvien");
	}

}
