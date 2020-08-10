package control;

import java.util.Scanner;

import model.Configuration;
import service.DBService;
import service.DBServiceImpl;
import service.FileService;
import service.FileServiceImpl;
import service.LogService;
import service.LogServiceImpl;

public class WarehouseCommandline {
	
public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	
	Configuration configuration;
	FileService fileService = new FileServiceImpl();
	DBService dbStaging = new DBServiceImpl("staging", "root", "1234");
	DBService dbWarehouse = new DBServiceImpl("datawarehouse", "root", "1234");
	LogService logService = new LogServiceImpl();
	
	boolean success = false;
	while (true) {
		System.out.print("Please enter the Configuaration ID: ");
		String command = sc.nextLine();
		
		if (command.equals("end")) {
			sc.close();
			break;
		}

		try {
			configuration = new Configuration(Integer.parseInt(command), "root", "1234");
			Download rp = new Download(configuration);
			success = rp.DownloadFile();
			
			if(!success) {
				System.out.println("Error appends at step 1.");
				System.out.println("\t-------------------\t------------------\t----------------");
				System.out.println("Enter \"end\" to stop");
				continue;
			}
			
			dbStaging.truncateTable(configuration.getConfigName());
			ExtractToStaging step2 = new ExtractToStaging(configuration, fileService, dbStaging, logService);
			success = step2.extractToStaging();
			if(!success) {
				System.out.println("Error appends at step 2.");
				System.out.println("\t-------------------\t------------------\t----------------");
				System.out.println("Enter \"end\" to stop");
				continue;
			}
			
			LoadToWarehouse step3 = new LoadToWarehouse(configuration, fileService, dbWarehouse, logService);
			success = step3.loadToWarehouse();
			if(!success) {
				System.out.println("Error appends at step 3.");
				System.out.println("\t-------------------\t------------------\t----------------");
				System.out.println("Enter \"end\" to stop");
				continue;
			}
			System.out.println("Process finished!");
			System.out.println("\t-------------------\t------------------\t----------------");
			System.out.println("Enter \"end\" to stop");
		} catch (Exception e) {
			System.out.println("Wrong Config ID");
			System.out.println("\t-------------------\t------------------\t----------------");
			System.out.println("Enter \"end\" to stop");
			continue;
		}

	}
}
}
