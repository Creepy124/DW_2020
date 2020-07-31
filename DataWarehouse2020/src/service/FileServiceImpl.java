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
		DataFormatter datafomatter = new DataFormatter();
		InputStream is = new FileInputStream(file);
		Workbook wb = WorkbookFactory.create(is);

		Sheet sheet = wb.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();
		String str = "";
		String tmp = "";
		while (rowIterator.hasNext()) {
			Row fRow = rowIterator.next();
			Iterator<Cell> cellIterator = fRow.iterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				String value = datafomatter.formatCellValue(cell);
//				if (value!=null && !value.isEmpty()) {
				tmp += value + ",";
//				}
			}
			String test = tmp;
			if (!test.replace(",", "").trim().isEmpty()) {
				str += tmp + "\n";
				tmp = "";
			}
		}
		File fileout = new File(
				file.getParent() + file.separator + file.getName().substring(0, file.getName().length() - 5) + ".csv");
		FileOutputStream fos = new FileOutputStream(fileout);
		fos.write(str.getBytes(StandardCharsets.UTF_8));
		fos.close();
		return fileout.getAbsolutePath();
	}

	public static void main(String[] args) {
		FileServiceImpl fileServiceImpl = new FileServiceImpl();
		try {
			fileServiceImpl.convertToCsv(
					"C:\\Users\\Phuong\\git\\DW_2020\\DataWarehouse2020\\local\\test\\17130208_sang_nhom13.xlsx");
		} catch (EncryptedDocumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
