package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ExcelToCsv {
	
	static void xls(File inputFile) {
        // Get the workbook object for XLS file
        int count = 0;
        try {

            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(inputFile));
            for (int l = workbook.getNumberOfSheets() - 1; l >= 0; l--) {
                File outputFile = new File("local\\test\\a.csv");

                // For storing data into CSV files
                StringBuffer data = new StringBuffer();
                FileOutputStream fos = new FileOutputStream(outputFile);
                HSSFSheet sheet = workbook.getSheetAt(count);
                Cell cell;
                Row row;

                // Iterate through each rows from first sheet
                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {
                    row = rowIterator.next();
                    // For each row, iterate through each columns
                    Iterator<Cell> cellIterator = row.cellIterator();
                    int columnNumber = 1;
                    while (cellIterator.hasNext()) {
                        cell = cellIterator.next();
                        if (columnNumber > 1)
                        {
                            data.append(",");
                        }

                        switch (cell.getCellType()) {
                        case BOOLEAN:
                            data.append(cell.getBooleanCellValue());
                            break;

                        case NUMERIC:
                            data.append(cell.getNumericCellValue() );
                            break;

                        case STRING:
                            data.append(cell.getStringCellValue());
                            break;

                        case BLANK:
                            data.append("");
                            break;

                        default:
                            data.append(cell);
                        }
                         ++columnNumber;
                    }
                    data.append('\n');
                }

                fos.write(data.toString().getBytes());
                fos.close();
                count++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        File inputFile = new File("local\\test\\17130208_sang_nhom13.xlsx");
//        xls(inputFile);
//    }
    public static void main(String[] args) {
		System.out.println("hhh");
	}

}
