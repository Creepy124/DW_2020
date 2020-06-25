package service;

import java.io.File;

public interface FileService {
	
	public String readLines(String value, String delim);
	public String readValuesTXT(String sourceFile, String delim);
	public String readValuesXLSX(String sourceFile);
	public boolean moveFile(String target_dir, File file);

}
