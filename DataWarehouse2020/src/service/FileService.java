package service;

import java.io.File;

public interface FileService {
	
	public String readLines(String value, String delim);
	public String readValuesTXT(File s_file, String delim);
	public String readValuesXLSX(File s_file);

}
