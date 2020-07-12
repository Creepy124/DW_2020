package service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileService {
	
	public String readLines(String value, String delim);
	public String readValuesTXT(String sourceFile, String delim)  throws IOException ;
	public String readValuesXLSX(String sourceFile) throws IOException ;
	public boolean moveFile(String target_dir, File file) throws IOException ;
	public void writeLinesToFile(String fPath, String lines) throws IOException;
	
}
