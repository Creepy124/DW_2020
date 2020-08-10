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
//1. Kết nối vào database control và lấy ra config_id có flag = "prepare"
			int configID = dbControl.getFlag("prepare");
			System.out.println(configID);
			if (configID != 0) {
				config = new Configuration(configID, "root", "1234");
				
//2. Thay đổi thay đổi trạng thái của cờ = "Running step 1"
				//Step 1
				dbControl.updateFlag(configID, "Running Step 1");
//3. Tiến hành down load file
				Download step1  = new Download(config);
				success = step1.DownloadFile();
				if(!success) 
					return;
				
//				dbControl.updateFlag(configID, "Done Step 1");
				
//4. Thay đổi thay đổi trạng thái của cờ = "Running step 2"
				//Step 2
				dbControl.updateFlag(configID, "Running Step 2");
				dbStaging.truncateTable(config.getConfigName());
//5.Tiến hành truncate staging và load file vào staging và transform
				ExtractToStaging step2 = new ExtractToStaging(config, fileService, dbStaging, logService);
				success = step2.extractToStaging();
				if(!success) 
					return;
//				dbControl.updateFlag(configID, "Done Step 2");
				
//6.Thay đổi thay đổi trạng thái của cờ = "Running step 2"
				//Step 3
				dbControl.updateFlag(configID, "Running Step 3");
//7.Tiến hành load file từ staging và warehouse
				LoadToWarehouse step3 = new LoadToWarehouse(config, fileService, dbWarehouse, logService);
				success = step3.loadToWarehouse();
				if(!success) 
					return;
//8.Thay đổi thay đổi trạng thái của cờ = "Done Step 3"
				dbControl.updateFlag(configID, "Done Step 3");
				
//9.Thay đổi thay đổi flag của config kế tiếp thành "prepare"
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
