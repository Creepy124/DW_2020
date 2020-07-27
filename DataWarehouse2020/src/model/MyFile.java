package model;

public class MyFile {
	private String fileName;
	private String fileType;
	
	public MyFile(String fileName, String fileType) {
		this.fileName = fileName;
		this.fileType = fileType;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileType() {
		return fileType;
	}

	@Override
	public String toString() {
		return "MyFile [fileName=" + fileName + ", fileType=" + fileType + "]";
	}

}
