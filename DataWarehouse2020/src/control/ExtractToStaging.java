package control;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.poi.EncryptedDocumentException;

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
	private Configuration config;
	private FileService fileService;
	private DBService dbService;
	private LogService logService;
	private MyFile file;
	private String status = "";

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
		} catch (EncryptedDocumentException | IOException e) {
			status += "convert error, ";
			WritingError.sendError(e.toString(), config.getToEmails());
		} catch (SQLException e) {
			status += "load infile error, ";
			WritingError.sendError(e.toString(), config.getToEmails());
		}
	}

	// 5. update log
	public void updateLog() {
		try {
			if (status == "") {
				logService.updateAction(config.getConfigID(), file.getFileName(), "TR");
			} else {
				logService.updateStatus(config.getConfigID(), file.getFileName(), status);
			}
		} catch (SQLException e) {
			WritingError.sendError(e.toString(), config.getToEmails());
		}
	}

	public void extractToStaging() {
		if (getFile()) {
			truncateTable();
			loadToTable();
			updateLog();
		}
	}

	public static void main(String[] args) throws SQLException {
		Configuration config = new Configuration("monhoc", "root", "1234");
		FileService fileService = new FileServiceImpl();
		DBService dbService = new DBServiceImpl("staging", "root", "1234");
		LogService logService = new LogServiceImpl("control", "root", "1234");
		ExtractToStaging test = new ExtractToStaging(config, fileService, dbService, logService);
		test.extractToStaging();

	}

}