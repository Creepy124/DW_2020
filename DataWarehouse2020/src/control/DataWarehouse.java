package control;

import java.io.File;
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
				dbService.truncateTable(config.getConfigName());
				MyFile myFile = logService.getFileWithStatus("ER");
				System.out.println(myFile.toString());
				String sourceFile = config.getDownloadPath() + "\\" + myFile.getFileName();
				File file = new File(sourceFile);
				if (file.exists()) {
					try {
						dbService.loadFile(sourceFile, config.getConfigName(), "|");
					} catch (SQLException e) {
						System.out.println("Can't load file!");
						e.printStackTrace();
					} finally {
						try {
							logService.insertLog(config.getConfigID(), myFile.getFileName(), myFile.getFileType(), "TR",
									getNowTime());
						} catch (SQLException e) {
							System.out.println("Can't insert log!");
							e.printStackTrace();
						}
					}
				} else {
					System.out.println("file does not exist!");
				}
			} else {
				System.out.println("Now no file status ER!");
			}
		} catch (SQLException e) {
			System.out.println("Get file action ER error!");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DataWarehouse dw = new DataWarehouse();
		dw.extractToStaging("sinhvien");
	}

}
