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

public class WarehouseScheduled {
	DBService dbControl;
	DBService dbStaging;
	DBService dbWarehouse;
	LogService logService;
	FileService fileService;

	public WarehouseScheduled() throws SQLException {
		dbControl = new DBServiceImpl("control", "root", "1234");
		dbStaging = new DBServiceImpl("staging", "root", "1234");
		dbWarehouse = new DBServiceImpl("datawarehouse", "root", "1234");
		logService = new LogServiceImpl();
		fileService = new FileServiceImpl();
	}

	public void run() throws AddressException, IOException, MessagingException {
		Configuration config;
		boolean success = false;
		try {
			int configID = dbControl.getFlag("prepare");
			System.out.println(configID);
			if (configID != 0) {
				config = new Configuration(configID, "root", "1234");
				
				//Step 1
				dbControl.updateFlag(configID, "Running Step 1");
				Download step1  = new Download(config);
				success = step1.DownloadFile();
				if(!success) 
					return;
				
				dbControl.updateFlag(configID, "Done Step 1");
				
				//Step 2
				dbControl.updateFlag(configID, "Running Step 2");
				dbStaging.truncateTable(config.getConfigName());
				ExtractToStaging step2 = new ExtractToStaging(config, fileService, dbStaging, logService);
				success = step2.extractToStaging();
				if(!success) 
					return;
				dbControl.updateFlag(configID, "Done Step 2");
				
				//Step 3
				dbControl.updateFlag(configID, "Running Step 3");
				LoadToWarehouse step3 = new LoadToWarehouse(config, fileService, dbWarehouse, logService);
				success = step3.loadToWarehouse();
				if(!success) 
					return;
				dbControl.updateFlag(configID, "Done Step 3");
				
				//next config
				configID++;
				int nextConfig = dbControl.nextConfig(configID);
				dbControl.updateFlag(nextConfig, "prepare");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}

	public static void main(String[] args) throws SQLException, AddressException, IOException, MessagingException {
		WarehouseScheduled wh = new WarehouseScheduled();
		wh.run();
	}

}
