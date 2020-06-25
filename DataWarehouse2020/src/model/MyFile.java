package model;

public class MyFile {
	private String fileName;
	private String fileType;
	
	public MyFile() {
	}
	
	public MyFile(String fileName, String fileType) {
		this.fileName = fileName;
		this.fileType = fileType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Override
	public String toString() {
		return "MyFile [fileName=" + fileName + ", fileType=" + fileType + "]";
	}

}
