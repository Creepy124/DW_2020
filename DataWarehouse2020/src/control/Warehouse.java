package control;

import model.Configuration;
import service.DBService;
import service.DBServiceImpl;
import service.FileService;
import service.FileServiceImpl;
import service.LogService;
import service.LogServiceImpl;

public class Warehouse {
	Download step1;
	ExtractToStaging step2;
	LoadToWarehouse step3;
	Configuration config;
	FileService fileService;
	DBService dbService;
	LogService logService;
	
	public Warehouse() {
		fileService = new FileServiceImpl();
		dbService = new DBServiceImpl("staging", "root", "");
		logService = new LogServiceImpl("control", "root", "");
		
		
	}
	
	public static void main(String[] args) {
		
	}

}
