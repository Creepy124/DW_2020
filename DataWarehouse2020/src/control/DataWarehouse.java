package control;

import model.Configuration;
import service.DBService;
import service.DBServiceImpl;
import service.FileService;
import service.FileServiceImpl;

public class DataWarehouse {
	Configuration configuration;
	FileService fileService;
	DBService dbService;
	
	public void extractToStaging(String configName) {
		configuration = new Configuration(configName);
		fileService = new FileServiceImpl();
		dbService = new DBServiceImpl("staging");
		
		
		
	}

}
