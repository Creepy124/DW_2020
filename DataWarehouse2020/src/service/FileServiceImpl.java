package service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
 * This class contain all methods that relative to files that were downloaded from remote such as: 
 * move file to another place,
 * convert xlsx to csv
 * write to a file (error file)
 */
public class FileServiceImpl implements FileService {

	@Override
	public boolean moveFile(String target_dir, File file) throws IOException {
		BufferedInputStream bReader = new BufferedInputStream(new FileInputStream(file));
		BufferedOutputStream bWriter = new BufferedOutputStream(
				new FileOutputStream(target_dir + File.separator + file.getName()));
		byte[] buff = new byte[1024 * 10];
		int data = 0;
		while ((data = bReader.read(buff)) != -1) {
			bWriter.write(buff, 0, data);
		}
		bReader.close();
		bWriter.close();
		file.delete();
		return true;
	}

	@Override
	public void writeLinesToFile(String fPath, String lines) throws IOException {
		Path file = Paths.get(fPath);
		DataOutputStream dos;
		dos = new DataOutputStream(Files.newOutputStream(file, StandardOpenOption.APPEND));
		dos.write(lines.getBytes());
		dos.flush();
	}

	public String convertToCsv(String path) throws EncryptedDocumentException, IOException {
		File file = new File(path);
		InputStream is = new FileInputStream(file);
		Workbook wb = WorkbookFactory.create(is);
		Sheet sheet = wb.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();
		String str = "";
		int line = 0;
		int colNum = 0;
		while (rowIterator.hasNext()) {
			line++;
			Row fRow = rowIterator.next();
			if (line == 1) {
				colNum = fRow.getLastCellNum();
			}
			for (int i = 0; i < colNum; i++) {
				DataFormatter df = new DataFormatter();
				Cell cell = fRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
				String s = "";
				if (cell.getCellType() == CellType.FORMULA) {
					if (cell.getCachedFormulaResultType() == CellType.NUMERIC) {
						str += "" + cell.getNumericCellValue() + " ";
						if(i != colNum - 1) {
							str += "| ";
						}
					}

					else if (cell.getCachedFormulaResultType() == CellType.STRING) {
						str += cell.getStringCellValue() + " ";
						
					}
				}

				else if (cell.getCellType() == CellType.BLANK || cell == null) {
					if(i != colNum - 1) {
						str += "| ";
					}
				}

				else if (cell.getCellType() != CellType.BLANK) {
					str += df.formatCellValue(cell) + " ";
					if(i != colNum - 1) {
						str += "| ";
					}
				}
			}
			str += "\n";
		}
		str = str.trim() + " ";
		File fileout = new File(
				file.getParent() + file.separator + file.getName().substring(0, file.getName().length() - 5) + ".csv");
		FileOutputStream fos = new FileOutputStream(fileout);
		fos.write(str.getBytes(StandardCharsets.UTF_8));
		return fileout.getName();
	}

	public static void main(String[] args) {
		FileServiceImpl fileServiceImpl = new FileServiceImpl();
		try {
			fileServiceImpl.convertToCsv("E:\\Warehouse2\\monhoc2013.xlsx");
		} catch (EncryptedDocumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
