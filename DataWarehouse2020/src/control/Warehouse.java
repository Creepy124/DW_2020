package control;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;

import model.Configuration;
import model.MyFile;
import service.DBService;
import service.DBServiceImpl;
import service.FileService;
import service.LogService;

public class Warehouse {
	Date expired = new Date(new GregorianCalendar(9999, 12, 31).getTimeInMillis());

	Configuration config;
	FileService fileService;
	DBService dbService;
	LogService logService;

	public Warehouse(Configuration configuration, FileService fileService, DBService dbService, LogService logService) {
		this.config = configuration;
		this.fileService = fileService;
		this.dbService = dbService;
		this.logService = logService;
	}

	public void loadDimStudent() {

	}

	public void loadDimSubject() {

	}

	public void loadDimClass() {

	}

	public void loadFact() {

	}
}
