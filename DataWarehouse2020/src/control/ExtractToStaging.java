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
	static final String DEFAUT = "null";

	private Configuration config;
	private FileService fileService;
	private DBService dbService;
	private LogService logService;
	private MyFile file;

	public ExtractToStaging(Configuration configuration, FileService fileService, DBService dbService,
			LogService logService) {
		// 2. Lấy 1 dòng trong bảng configuration theo tên tương ứng
		this.config = configuration;
		this.fileService = fileService;
		this.dbService = dbService;
		this.logService = logService;
	}

	// 3. Get file status ER
	public boolean getFile() {
		try {

			file = logService.getFileWithAction(config.getConfigID(), "ER");

			if (file != null) {
				System.out.println(file.toString());
				return true;
			} else
				return false;

		} catch (SQLException e) {
			WritingError.sendError(e.toString() + "\n ExtractToStaging.java step3", config.getToEmails());
			return false;
		}
	}

	// 4. loadToTable
	public void loadToTable() {
		try {
			if (file.getFileType().equalsIgnoreCase("xlsx") || file.getFileType().equalsIgnoreCase("xls")) {
				// 4.1.
				String newFileName = fileService.convertToCsv(config.getDownloadPath() + "\\" + file.getFileName());

				// 4.2.
				dbService.loadFile(config.getDownloadPath() + "\\\\" + newFileName, config.getConfigName(),
						config.getFileDilimiter());

			} else {
				// 4.3.
				dbService.loadFile(config.getDownloadPath() + "\\\\" + file.getFileName(), config.getConfigName(),
						config.getFileDilimiter());
			}
			// 4.4.
			updateLog("TR");
		} catch (EncryptedDocumentException | IOException | SQLException e) {
			WritingError.sendError(e.toString() + "\n ExtractToStaging.java Step 4", config.getToEmails());
			updateLog("ERR");
		}
	}

	// 5. update log
	public void updateLog(String action) {
		try {
			logService.updateAction(file.getFileName(), action);
		} catch (SQLException e) {
			WritingError.sendError(e.toString() + "\n ExtractToStaging.java", config.getToEmails());
		}
	}

	// 5.
	private void tranform() {

		String[] columns = config.getFileColumnList().split(",");
		for (int i = 1; i < columns.length; i++) {
			try {
				if (i == 1) {
					dbService.deleteNullID(config.getConfigName(), columns[i]);
				} else
					dbService.tranformNullValue(config.getConfigName(), columns[i], DEFAUT);

				updateLog("" + config.getConfigID());

			} catch (SQLException e) {
				WritingError.sendError("Cant't Tranform. ExtractToStaging.java Column= " + i, config.getToEmails());
			}
		}
	}

	public void extractToStaging() {
		while (getFile()) {
			loadToTable();
		}
		// 5.
		tranform();
	}

	public static void main(String[] args) throws SQLException {
		Configuration config = new Configuration(1, "root", "");
		FileService fileService = new FileServiceImpl();
		DBService dbService = new DBServiceImpl("staging", "root", "");
		LogService logService = new LogServiceImpl();
		ExtractToStaging test = new ExtractToStaging(config, fileService, dbService, logService);
		test.extractToStaging();

	}

}