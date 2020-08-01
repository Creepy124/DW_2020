package control;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.formula.udf.UDFFinder;

import model.Configuration;
import model.MyFile;
import service.DBService;
import service.DBServiceImpl;
import service.FileService;
import service.FileServiceImpl;
import service.LogService;
import service.LogServiceImpl;
import service.WritingError;

public class ExtractToStaging {
	static final String DEFAUT = "null";

	private Configuration config;
	private FileService fileService;
	private DBService dbService;
	private LogService logService;
	private MyFile file;

	public ExtractToStaging(Configuration configuration, FileService fileService, DBService dbService,
			LogService logService) {
		this.config = configuration;
		this.fileService = fileService;
		this.dbService = dbService;
		this.logService = logService;
	}

	// 2. Get file status ER
	public boolean getFile() {
		try {
			file = logService.getFileWithAction(config.getConfigID(), "ER");
			if (file != null) {
				return true;
			} else
				return false;
		} catch (SQLException e) {
			WritingError.sendError(e.toString(), config.getToEmails());
			return false;
		}
	}

	// 3. truncateTable
	public void truncateTable() {
		try {
			dbService.truncateTable(config.getConfigName());
		} catch (SQLException e) {
			WritingError.sendError(e.toString(), config.getToEmails());
			e.printStackTrace();
		}
	}

	// 4. loadToTable
	public void loadToTable() {
		try {
			if (file.getFileType().equalsIgnoreCase("xlsx") || file.getFileType().equalsIgnoreCase("xls")) {
				fileService.convertToCsv(config.getDownloadPath() + "\\" + file.getFileName());
			}
			dbService.loadFile(config.getDownloadPath() + "\\\\" + file.getFileName(), config.getConfigName(),
					config.getFileDilimiter());
			updateLog("TR");
		} catch (EncryptedDocumentException | IOException e) {
			WritingError.sendError(e.toString(), config.getToEmails());
			updateLog("ERR");
		} catch (SQLException e) {
			WritingError.sendError(e.toString(), config.getToEmails());
			updateLog("ERR");
		}
	}

	// 5. update log
	public void updateLog(String message) {
		try {
			logService.updateAction(config.getConfigID(), message);
		} catch (SQLException e) {
			WritingError.sendError(e.toString(), config.getToEmails());
		}
	}

	private void tranform() {
		String[] columns = config.getFileColumnList().split(",");
		for (int i = 1; i < columns.length; i++) {
			try {
				if (i == 1) {
					dbService.deleteNullID(config.getConfigName(), columns[i]);
				} else
					dbService.tranformNullValue(config.getConfigName(), columns[i], DEFAUT);
			updateLog("WH");
			} catch (SQLException e) {
				WritingError.sendError("Cant't Tranform. ExtractToStaging. Column= " + i, config.getToEmails());
			}
		}
	}

	public void extractToStaging() {
		while (getFile()) {
			loadToTable();
		}
		tranform();
	}

	public static void main(String[] args) throws SQLException {
		Configuration config = new Configuration("sinhvien", "root", "");
		FileService fileService = new FileServiceImpl();
		DBService dbService = new DBServiceImpl("staging", "root", "");
		LogService logService = new LogServiceImpl("control", "root", "");
		ExtractToStaging test = new ExtractToStaging(config, fileService, dbService, logService);
		test.extractToStaging();

	}

}