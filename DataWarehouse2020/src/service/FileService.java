package service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;

public interface FileService {

	public boolean moveFile(String target_dir, File file) throws IOException ;
	public void writeLinesToFile(String fPath, String lines) throws IOException;
	public String convertToCsv(String path) throws EncryptedDocumentException, IOException;
	
}
