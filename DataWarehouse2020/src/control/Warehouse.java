package control;

import java.io.IOException;
import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import model.Configuration;
import service.DBService;
import service.DBServiceImpl;
import service.FileService;
import service.FileServiceImpl;
import service.LogService;
import service.LogServiceImpl;

public class Warehouse {
	DBService dbControl;
	DBService dbStaging;
	DBService dbWarehouse;
	LogService logService;
	FileService fileService;

	public Warehouse() throws SQLException {
		dbControl = new DBServiceImpl("control", "root", "1234");
		dbStaging = new DBServiceImpl("staging", "root", "1234");
		dbWarehouse = new DBServiceImpl("datawarehouse", "root", "1234");
		logService = new LogServiceImpl();
		fileService = new FileServiceImpl();
	}

	public void run() throws AddressException, IOException, MessagingException {
		Configuration config;
		try {
			int configID = dbControl.getFlag("prepare");
			System.out.println(configID);
			if (configID != 0) {
				dbControl.updateFlag(configID, "Step1");
				config = new Configuration(configID, "root", "1234");
				
				Download step1  = new Download(config);
				step1.DownloadFile();
				dbControl.updateFlag(configID, "Done Step 1");
				
				ExtractToStaging step2 = new ExtractToStaging(config, fileService, dbStaging, logService);
				step2.extractToStaging();
				dbControl.updateFlag(configID, "Done Step 2");
				
				LoadToWarehouse step3 = new LoadToWarehouse(config, fileService, dbWarehouse, logService);
				step3.loadToWarehouse();
				dbControl.updateFlag(configID, "Done Step 3");
				
//				dbStaging.truncateTable(config.getConfigName());
				
				configID++;
				dbControl.updateFlag(configID, "prepare");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws SQLException, AddressException, IOException, MessagingException {
		Warehouse wh = new Warehouse();
		wh.run();
	}

}
